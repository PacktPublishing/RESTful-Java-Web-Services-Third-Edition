/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.service;

import com.packtpub.rest.ch4.ext.RequestLogger;
import com.packtpub.rest.ch4.model.Department;
import com.packtpub.rest.ch4.validation.DeprtmentNotFoundBusinessException;
import com.packtpub.rest.ch4.validation.ValidDepartment;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

/**
 * A REST resource class which demonstrates bean validation, custom entity providers,
 * and dynamic feature
 * @author Jobinesh
 */
@Stateless
@Path("hr")
public class HRService {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter4-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(HRService.class.getName());

    public HRService() {

    }

    @POST
    @Path("departments")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@ValidDepartment Department entity) {
         entityManager.persist(entity);
        logger.log(Level.INFO, "create: " + entity.toString());
        
    }

    @POST
    @Path("departments/form")
    public void createDepartmnet(
            @FormParam("departmentId") short departmentId,
            @FormParam("departmentName") String departmentName) {
        
        logger.log(Level.INFO, "createDepartmnet: " + departmentId);
        
        Department entity = new Department();
        entity.setDepartmentId(departmentId);
        entity.setDepartmentName(departmentName);
        entityManager.persist(entity);
    }

    @POST
    @Path("departments/batch")
    @Consumes("application/csv")
    public void createInBatch(List<Department> entities) {

        logger.log(Level.INFO, entities.toString());
        for (Department entity : entities) {
            create(entity);
        }
    }

    @PUT
    @Path("departments/batch")
    @Consumes("application/csv")
    public void updateInBatch(List<Department> entities) {

        logger.log(Level.INFO, entities.toString());
        for (Department entity : entities) {
            entityManager.merge(entity);
        }
    }

    @PUT
    @Path("departments/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Short id, @Context Request request, Department entity) {
        entityManager.merge(entity);

    }

    @DELETE
    @Path("departments/{id}")
    public void remove(@PathParam("id") Short id) throws DeprtmentNotFoundBusinessException {
        Department department = entityManager.find(Department.class, id);
        if(department == null){
            throw new DeprtmentNotFoundBusinessException("Department is missing in store");
            
        }
        entityManager.remove(entityManager.merge(department));
    }

    @GET
    @Path("departments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findDepartment(@PathParam("id") @Min(value = 0, message = "Department Id must be a positive value") Short id, @Context Request request) {

        Department department = entityManager.find(Department.class, id);
        return department;
    }

    @GET
    @Path("departments/batch")
    @Produces("application/csv")

    public List<Department> findAllDepartmnetsInBatch() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
   
        return entityManager.createQuery(cq).getResultList();
    }

    @GET
    @Path("departments")
    @Produces(MediaType.APPLICATION_JSON)
    @RequestLogger
    public List<Department> findAllDepartmnets() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        List<Department> depts = entityManager.createQuery(cq).getResultList();
        logger.log(Level.INFO, "Result:" + depts.toString());
        return depts;
    }

    @GET
    @Path("departments/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        javax.persistence.Query q = entityManager.createQuery(cq);
        q.setMaxResults(to - from + 1);
        q.setFirstResult(from);
        return q.getResultList();
    }

    @GET
    @Path("departments/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer countREST() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Department> rt = cq.from(Department.class);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = entityManager.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @GET
    @Path("departments/{id}/manager")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findManagerForDepartment(@PathParam("id") Short deptId) {

        Department department = entityManager.find(Department.class, deptId);
        return department;
    }
}
