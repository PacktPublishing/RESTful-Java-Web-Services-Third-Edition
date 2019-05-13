/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.sse;

import com.packtpub.rest.ch5.model.Department;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * This class demonstrates Server Sent Event offerings in Jersey
 *
 * @author Jobinesh
 */
@Path("departments/events")
@Singleton
public class SSEEnabledDeptResource {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter5-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final ReentrantReadWriteLock deptUpdateLock = new ReentrantReadWriteLock();

    private static ArrayList<String> modifiedDepts = new ArrayList<String>();
    private static final SseBroadcaster broadcaster = new SseBroadcaster();
    private static final Logger logger = Logger.getLogger(SSEEnabledDeptResource.class.getName());

    /**
     * Clients registers for SSE by calling this method via REST API: GET
     * departments/events HTTP/1.1
     *
     * @param lastEventId
     * @return
     */
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput manageSSEEvents(@HeaderParam(SseFeature.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastEventId) {
        EventOutput eventOutput = new EventOutput();
        if (lastEventId > 0) {
            replayMissedUpdates(lastEventId, eventOutput);
        }
        if (!broadcaster.add(eventOutput)) {
            logger.severe("!!! Unable to add new event output to the broadcaster !!!");
            // let's try to force a 5s delayed client reconnect attempt
            throw new ServiceUnavailableException(5L);
        }
        return eventOutput;
    }

    /**
     * This method updates Department resource When this method is invoked, it
     * broadcasts modification even to all subscribers. A clients added itself
     * as subscriber by calling manageSSEEvents() via API: GET
     * departments/events
     *
     * @param id
     * @param entity
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Short id, Department entity) {
        try {
            deptUpdateLock.writeLock().lock();
            entityManager.merge(entity);
            final int modifiedListIndex = modifiedDepts.size() + 1;
            // Broadcasting an un-named event with the name of the newly added item in data         
            broadcaster.broadcast(createItemEvent(modifiedListIndex, entity.getDepartmentName()));
            // Broadcasting a named "size" event with the current size of the items collection in data
            broadcaster.broadcast(new OutboundEvent.Builder().name("totalUpdates").data(Integer.class, modifiedListIndex).build());
            modifiedDepts.add(entity.getDepartmentName());
        } finally {
            deptUpdateLock.writeLock().unlock();
        }
    }

    /**
     * Helper method for creating OutboundEvent
     *
     * @param eventId
     * @param name
     * @return
     */
    private OutboundEvent createItemEvent(final int eventId, final String name) {
        logger.info("Creating event id [" + eventId + "] name [" + name + "]");
        return new OutboundEvent.Builder().id(String.valueOf(eventId)).data(String.class, name).build();
    }
    /**
     * Reply missed event since lastEventId
     * @param lastEventId
     * @param eventOutput 
     */
    private void replayMissedUpdates(final int lastEventId, final EventOutput eventOutput) {
        try {
            deptUpdateLock.readLock().lock();
            for (int i = lastEventId; i < modifiedDepts.size(); i++) {
                eventOutput.write(createItemEvent(i, modifiedDepts.get(i)));
            }
        } catch (IOException ex) {
            throw new InternalServerErrorException("Error replaying missed events", ex);
        } finally {
            deptUpdateLock.readLock().unlock();
        }
    }
}
