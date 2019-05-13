/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.service;

import com.packtpub.rest.ch4.ext.RequestLogger;
import com.packtpub.rest.ch4.model.Department;
import com.packtpub.rest.ch4.validation.ValidDepartment;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This class that defines the subresource concept
 *
 * @author Balachandar
 */
@Path("departments")
public class DepartmentResource {

    //Sub-resource locator method
    @PersistenceContext(unitName = "com.packtpub_rest-chapter4-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(DepartmentResource.class.getName());

    
    @POST
    @Path("departments")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@ValidDepartment Department entity) {
         entityManager.persist(entity);
        logger.log(Level.INFO, "create: {0}", entity.toString());
        
    }

    @POST
    @Path("departments/form")
    public void createDepartmnet(
            @FormParam("departmentId") short departmentId,
            @FormParam("departmentName") String departmentName) {
        Department entity = new Department();
        entity.setDepartmentId(departmentId);
        entity.setDepartmentName(departmentName);
        entityManager.persist(entity);
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

    @Path("{id}/manager")
    public EmployeeResource
            findManagerForDepartment(@PathParam("id") Short deptId) {
//Find the department for id
        Department department = findDepartmentEntity(deptId);
//Create the instance of Employee object
//This instance will be used for further resolving request
        EmployeeResource employeeResource = new EmployeeResource(department.getManagerId());
        return employeeResource;
    }

    private Department findDepartmentEntity(Short deptId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
