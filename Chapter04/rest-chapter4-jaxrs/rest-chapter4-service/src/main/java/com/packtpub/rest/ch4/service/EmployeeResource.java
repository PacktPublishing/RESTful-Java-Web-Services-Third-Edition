/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.service;

import com.packtpub.rest.ch4.model.Employee;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This class that defines the subresource used in DepartmentResource
 * @author Balachandar
 */
public class EmployeeResource {

    Integer employeeId;

    public EmployeeResource(Integer employeeId) {
        this.employeeId = employeeId;
    }
//Resolves GET request to findEmployee() method

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Employee findEmployee() {
        Employee employee = findEmployeeEntity(employeeId);
        return employee;
    }

    private Employee findEmployeeEntity(Integer employeeId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
