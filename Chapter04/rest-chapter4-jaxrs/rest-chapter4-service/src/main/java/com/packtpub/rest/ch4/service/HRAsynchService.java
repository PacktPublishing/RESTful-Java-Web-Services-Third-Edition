/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.service;

import com.packtpub.rest.ch4.model.Department;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A REST resource class which demonstrates asynchronous JAX-RS API implementations
 * @author Jobinesh
 */
@Stateless
@Path("hr/asynch")
public class HRAsynchService {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter4-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(HRAsynchService.class.getName());
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public HRAsynchService() {

    }

    @GET
    @Path("departments")
    @Produces(MediaType.APPLICATION_JSON)
    public void findAllDepartments(@Suspended AsyncResponse asyncResponse) {
        
        asyncResponse.setTimeoutHandler(new DeptQueryTimeoutHandler(asyncResponse));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        executorService.execute(new LongRunningDeptQuery(asyncResponse));
    }

    class LongRunningDeptQuery implements Runnable {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.packtpub_rest-chapter4-service_war_1.0-SNAPSHOTPU");
        EntityManager entityManagerLocal = emf.createEntityManager();
        AsyncResponse asyncResponse;

        LongRunningDeptQuery(final AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        public void run() {

            CriteriaQuery cq = entityManagerLocal.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Department.class));
            List<Department> depts = entityManagerLocal.createQuery(cq).getResultList();
            GenericEntity<List<Department>> entity
                    = new GenericEntity<List<Department>>(depts) {
                    };
            asyncResponse.resume(Response.ok().entity(entity).build());
        }

    }

    class DeptQueryTimeoutHandler implements TimeoutHandler {

        AsyncResponse asyncResponse;

        DeptQueryTimeoutHandler(final AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        public void handleTimeout(AsyncResponse asyncResponse) {
            asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Operation time out.").build());
        }

    }
}
