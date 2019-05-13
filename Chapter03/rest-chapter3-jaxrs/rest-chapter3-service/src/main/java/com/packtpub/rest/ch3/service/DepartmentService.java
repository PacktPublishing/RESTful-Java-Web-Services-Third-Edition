/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch3.service;

import com.packtpub.rest.ch3.model.Departments;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * This class implements REST APIs for Department resource
 *
 * @author Jobinesh
 */
@Path("/departments")
@Stateless
public class DepartmentService {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter3-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(DepartmentService.class.getName());
    

    /**
     * Returns list of departments
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departments> findAllDepartments() {
        //Find all departments from the data store
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departments.class));
        List<Departments> departments = entityManager.createQuery(cq).getResultList();
        return departments;
    }

    /**
     * Creates Departments entity
     *
     * @param entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createDepartment(Departments entity) {
        entityManager.persist(entity);
    }

    /**
     * Get total departments count
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces("text/plain")
    public int countREST() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Departments> rt = cq.from(Departments.class);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = entityManager.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    /**
     * Creates a department. This method demonstrates @FormParam
     *
     * @param departmentId
     * @param departmentName
     */
    @POST
    @Path("form")
    public void createDepartmnet(
            @FormParam("departmentId") short departmentId,
            @FormParam("departmentName") String departmentName) {
        Departments entity = new Departments();
        entity.setDepartmentId(departmentId);
        entity.setDepartmentName(departmentName);
        entityManager.persist(entity);
    }

    /**
     * Creates a department This method demonstrates @BeanParam
     *
     * @param deptBean
     */
    @POST
    @Path("form/bean")
    public void createDepartmnet(@BeanParam DepartmentFormBean deptBean) {
        createDepartmnet(deptBean.getDepartmentId(),
                deptBean.getDepartmentName());
    }

    /**
     * Modifies department
     *
     * @param id
     * @param entity
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editDepartment(@PathParam("id") Short id, Departments entity) {
        entityManager.merge(entity);
    }

    /**
     * Deletes a department
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void removeDepartment(@PathParam("id") Short id) {

        Departments entity = entityManager.find(Departments.class, id);
        entityManager.remove(entityManager.merge(entity));

    }

    /**
     * Finds a department by name
     *
     * @param name
     * @return
     */
    @GET
    @Path("{name: [a-zA-Z][a-zA-Z_0-9]}")
    @Produces(MediaType.APPLICATION_JSON)
    public Departments findDepartmentByName(@PathParam("name") String name) {
        Query queryDepartmentsByName = entityManager.createNamedQuery("Departments.findByDepartmentName");
        queryDepartmentsByName.setParameter("departmentName", name);
        Departments department = (Departments) queryDepartmentsByName.getSingleResult();
        return department;
    }

    /**
     * Finds a department by id
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Departments findDepartment(@PathParam("id") Short id) {
        return entityManager.find(Departments.class, id);
    }

    /**
     * Returns list of departments
     *
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("query")
    public List<Departments> findAllDepartmentsWithQueryParam(@QueryParam("name") String name) {
        //Find all departments from the data store
        Query queryDepartmentsByName = entityManager.createNamedQuery("Departments.findByDepartmentName");
        queryDepartmentsByName.setParameter("departmentName", name );
        List<Departments> departments = queryDepartmentsByName.getResultList();
        logger.log(Level.INFO, departments.toString());
        return departments;
    }

    /**
     * Returns list of departments. This method demonstrates @MatrixParam
     *
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("matrix")
    public List<Departments> findAllDepartmentsWithMatrixParam(@MatrixParam("name") String name) {
        //Find all departments from the data store
        Query queryDepartmentsByName = entityManager.createNamedQuery("Departments.findByDepartmentName");
        queryDepartmentsByName.setParameter("departmentName", name );
        List<Departments> departments = queryDepartmentsByName.getResultList();

        logger.log(Level.INFO, departments.toString());

        return departments;
    }

    /**
     * Simple hello world API
     *
     * @return
     */
    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld() {
        return "hello world";
    }

    //Usage of QueryParam for injecting class field
    @QueryParam("locationId")
    Short deptLocationId;

    /**
     * Returns list of departments by location
     *
     * @param locationId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryByLocation")
    public List<Departments> findAllDepartmentsByLocation() {
        //Find all departments from the data store
        Query queryDepartmentsByLocation = entityManager.createNamedQuery("Departments.findByLocationId");
        queryDepartmentsByLocation.setParameter("locationId", deptLocationId);
        List<Departments> departments = queryDepartmentsByLocation.getResultList();
        logger.log(Level.INFO, departments.toString());
        return departments;
    } 


}
