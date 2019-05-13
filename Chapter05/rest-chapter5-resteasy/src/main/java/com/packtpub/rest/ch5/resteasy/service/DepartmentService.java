/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.resteasy.service;

import com.packtpub.rest.ch5.resteasy.model.Departments;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.cache.Cache;



/**
 * This class implements REST APIs for Department resource and uses
 * RESTEasy annotations for server side caching.
 *
 * @author Balachandar
 */
@Path("departments")
public class DepartmentService {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter5-resteasy-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(DepartmentService.class.getName());

    /**
     * Finds a department by id and caches the response
     *
     * @param id
     * @return Departments
     */
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Cache(maxAge=2000,mustRevalidate = false,noStore = true, proxyRevalidate = false, sMaxAge = 2000)
    public Departments findDepartment(@PathParam("id") Short id) {
        return entityManager.find(Departments.class, id);
    }

    /**
     * Find all departments from the data store and return compressed response
     *
     * @return List Of Departments
     */    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public List<Departments> findAllDepartmnets() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departments.class));
        List<Departments> depts = entityManager.createQuery(cq).getResultList();
        logger.log(Level.INFO, "Result:{0}", depts.toString());
        return depts;
    }    
 
}
