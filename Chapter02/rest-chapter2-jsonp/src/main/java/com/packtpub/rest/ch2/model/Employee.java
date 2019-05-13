/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.model;

import java.util.Date;

/**
 * Employee class used in various examples in this project 
 * @author Jobinesh
 */
public class Employee {

    private String firstName;
    private String lastName;
    private String email;
    private Integer employeeId;
    private Date hireDate ;
    
    public Employee() {
    }

    public Employee(String firstName, String lastName, String email, Integer employeeId, Date hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeId = employeeId;
        this.hireDate = hireDate;
    }

 
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the doj
     */
    public Date getHireDate() {
        return hireDate;
    }

    /**
     * @param doj the doj to set
     */
    public void setHireDate(Date doj) {
        this.hireDate = doj;
    }

    /**
     * @return the employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Employee{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", employeeId=" + employeeId + ", doj=" + hireDate + '}';
    }

}
