/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.service;

import com.packtpub.rest.ch4.model.Department;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * A REST resource class which demonstrates caching APIS in JAX-RS
 *
 * @author Jobinesh
 */
@Stateless
@Path("hr/cache")
public class HRServiceCache {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter4-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(HRServiceCache.class.getName());

    @GET
    @Path("etag/departments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDepartmentWithCacheValidationWithEtag(@PathParam("id") Short id, @Context Request request) {

        Department department = entityManager.find(Department.class, id);
        EntityTag etag = new EntityTag(Integer.toString(department.hashCode()));
        Response.ResponseBuilder builder = request.evaluatePreconditions(etag);

        //cached resource did change, send new one
        if (builder == null) {
            builder = Response.ok(department);
            builder.tag(etag);
            logger.log(Level.INFO, "object has changed, so building response with new one");
        } else {
            logger.log(Level.INFO, "object has not changed, passing message to client ");
        }

        return builder.build();
    }

    @GET
    @Path("departments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDepartmentWithCacheValidationWithLastModifiedDate(@PathParam("id") Short id, @Context Request request) {

        Department department = entityManager.find(Department.class, id);
        Date latModifiedDate = department.getModifiedDate();

        Response.ResponseBuilder builder = request.evaluatePreconditions(latModifiedDate);

        //cached resource did change, send new one
        if (builder == null) {
            builder = Response.ok(department);
            builder.lastModified(latModifiedDate);
            logger.log(Level.INFO, "object has changed, so building response with new one");
        } else {
            logger.log(Level.INFO, "object has not changed, passing message to client ");
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        builder.cacheControl(cc);
        return builder.build();

    }

    @PUT
    @Path("etag/departments/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Short id, @Context Request request, Department entity) {

        Department departmentEntityInDB = entityManager.find(Department.class, id);
        //In real life app, use better algorithsm for generating ETag
        EntityTag etag = new EntityTag(Integer.toString(departmentEntityInDB.hashCode()));
        Response.ResponseBuilder builder = request.evaluatePreconditions(departmentEntityInDB.getModifiedDate(), etag);
        // Client is not up to date (send back 412)
        if (builder != null) {
            return builder.status(Response.Status.PRECONDITION_FAILED).build();
        }
        entityManager.merge(entity);
        //In real life app, use better algorithsm for generating ETag
        EntityTag newEtag = new EntityTag(Integer.toString(entity.hashCode()));
        builder = Response.noContent();
        builder.lastModified(entity.getModifiedDate());
        builder.tag(newEtag);
        return builder.build();
    }

    @GET
    @Path("departments/{id}/holidays")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHolidayListForCurrentYear(@PathParam("id") Short deptId) {
        String holidayList = jsonify(getHolidayListForDepartment(deptId));

        ResponseBuilder response = Response.ok(holidayList);
        // Valid for current year 2015
        Calendar expirationDate = new GregorianCalendar(2015, 12, 31);
        response.expires(expirationDate.getTime());

        return response.build();

    }

    public ArrayList<String> getHolidayListForDepartment(Short id) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        ArrayList<String> holidayList = new ArrayList<String>(Arrays.asList(df.format(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime()), df.format(new GregorianCalendar(2015, Calendar.DECEMBER, 25).getTime())));
        logger.log(Level.INFO, holidayList.toString());
        return holidayList;
    }

    private String jsonify(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (String str : list) {
            if (sb.length() == 0) {
                sb.append("{ ");
            } else {
                sb.append(", ");
            }
            sb.append(str);
        }
        sb.append(" }");
        return sb.toString();
    }
}
