/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.hateoas.jaxrs;

import com.packtpub.rest.ch5.hateoas.jersey.LinkAdaptor;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jobinesh
 */
@XmlRootElement
public class DepartmentRepresentation {

    private Short departmentId;
    private String departmentName;
    private Integer managerId;
    private Short locationId;

    Link employeesLink;

    public DepartmentRepresentation() {
        System.out.println("--");
    }

    public DepartmentRepresentation(Short departmentId, String departmentName, Integer managerId, Short locationId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.locationId = locationId;
    }

    public Short getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Short getLocationId() {
        return locationId;
    }

    public void setLocationId(Short locationId) {
        this.locationId = locationId;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(LinkAdaptor.class)
    public Link getEmployeesLink() {
        employeesLink = Link.fromUri("{id}/employees")
                .rel("employees").build(getDepartmentId());

        return employeesLink;
    }

    public void setEmployeesLink(Link employeesLink) {
        this.employeesLink = employeesLink;
    }

}
