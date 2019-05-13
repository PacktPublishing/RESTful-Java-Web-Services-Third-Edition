/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.service;

import com.packtpub.rest.ch6.ext.RequestLogger;
import com.packtpub.rest.ch6.model.Department;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

/**
 * A REST resource class which is used to fetch enterprise department details
 *  
 * @author Balachandar
 */
@Stateless
@Path("hr")
public class HRService {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter6-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(HRService.class.getName());

    public HRService() {

    }

    @GET
    @Path("departments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findDepartment(@PathParam("id") @Min(value = 0, message = "Department Id must be a positive value") Short id, @Context Request request) {

        Department department = entityManager.find(Department.class, id);
        return department;
    }

    @GET
    @Path("departments")
    @Produces(MediaType.APPLICATION_JSON)
    @RequestLogger
    public List<Department> findAllDepartmnets() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        List<Department> depts = entityManager.createQuery(cq).getResultList();
        if(depts != null)
            logger.log(Level.INFO, "Result:{0}", depts.toString());
        return depts;
    }


}
