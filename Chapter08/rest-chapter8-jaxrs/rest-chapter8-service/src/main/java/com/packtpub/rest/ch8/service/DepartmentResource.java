/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch8.service;

import com.packtpub.rest.ch8.jpa.model.Department;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Department JAX-RS resource class
 *
 * @author Jobinesh
 */
@Stateless
@Path("departments")
@Produces({"application/xml", "application/vnd.packtpub.v1+xml"})
@Consumes({"application/xml", "application/vnd.packtpub.v1+xml"})
public class DepartmentResource extends JPAResource<Department> {

    @PersistenceContext(unitName = "HR-PU")
    private EntityManager em;

    public DepartmentResource() {
        super(Department.class);
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("HR-PU");
        //em = emf.createEntityManager();
    }

    @POST
    @Override
    public void create(Department entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    public void edit(@PathParam("id") Short id, Department entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Short id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    public Department find(@PathParam("id") Short id) {
        return super.find(id);
    }

    @GET
    public List<Department> findDepartmnetsInRange(@QueryParam("offset") @DefaultValue("1") @Min(value = 0, message = "{department.from.minval}") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {

        List<Department> depts = super.findRange(new int[]{offset, offset + limit});
        System.out.println("findDepartmnetsInRange:" + depts);
        return depts;
    }

//    @POST
//    public Response createDepartment(Department entity,
//            @Context UriInfo uriInfo) {
//        create(entity);
//        Short deptId = entity.getDepartmentId();
//        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
//        builder.path(deptId.toString());
//        return Response.created(builder.build()).build();
//    }
    @GET
    @Produces({"application/vnd.packtpub.v2+xml"})
    public List<Department> findDepartmnetsInRangeV1(@QueryParam("offset") @DefaultValue("1") @Min(value = 0, message = "{department.from.minval}") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {

        return super.findRange(new int[]{offset, offset + limit});

    }

    @GET
    @Path("count")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
