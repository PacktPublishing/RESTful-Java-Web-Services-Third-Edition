/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service;

import com.packtpub.rest.ch5.model.Department;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;

/**
 * A standard resource class for department object used in this demo
 * @author Jobinesh
 */
@Stateless
@Path("departments")
public class DepartmentResource {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter5-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    @Context
    Configuration config;
    @Context
    Application app;
    private static final Logger logger = Logger.getLogger(DepartmentResource.class.getName());

    public DepartmentResource() {

    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Department entity) {
        entityManager.persist(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Short id, Department entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Short id) {
        Department entity = entityManager.find(Department.class, id);
        entityManager.remove(entityManager.merge(entity));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Department find(@PathParam("id") Short id) {
        return entityManager.find(Department.class, id);

    }

    @GET
    @Produces({"application/json"})
    public List<Department> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        return entityManager.createQuery(cq).getResultList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Department> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        javax.persistence.Query q = entityManager.createQuery(cq);
        q.setMaxResults(to - from + 1);
        q.setFirstResult(from);
        return q.getResultList();
    }

    @GET
    @Path("count")
    @Produces("text/plain")

    public int totalDepartmentCount() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Department> rt = cq.from(Department.class);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = entityManager.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
