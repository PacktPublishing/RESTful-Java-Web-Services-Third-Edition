/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.model;

import com.packtpub.rest.ch5.service.EmployeeResource;
import java.io.Serializable;
import java.net.URI;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;

/**
 * JPA model for DEPARTMENTS table
 * @author Jobinesh
 */
@Entity
@Table(name = "DEPARTMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findByDepartmentId", query = "SELECT d FROM Department d WHERE d.departmentId = :departmentId"),
    @NamedQuery(name = "Department.findByDepartmentName", query = "SELECT d FROM Department d WHERE d.departmentName = :departmentName"),
    @NamedQuery(name = "Department.findByManagerId", query = "SELECT d FROM Department d WHERE d.managerId = :managerId"),
    @NamedQuery(name = "Department.findByLocationId", query = "SELECT d FROM Department d WHERE d.locationId = :locationId")})
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "DEPARTMENT_ID")
    private Short departmentId;
    @Basic(optional = false)
   // @NotNull
   // @Size(min = 1, max = 30)
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @Column(name = "LOCATION_ID")
    private Short locationId;

    @InjectLink(resource = EmployeeResource.class,
            style = Style.ABSOLUTE,
            method = "query")
    @Transient        
    private URI employees;

    public Department() {
    }

    public Department(Short departmentId) {
        this.departmentId = departmentId;
    }

    public Department(Short departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentId != null ? departmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.departmentId == null && other.departmentId != null) || (this.departmentId != null && !this.departmentId.equals(other.departmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.packtpub.rest.ch5.model.Department[ departmentId=" + departmentId + " ]";
    }

    /**
     * @return the employees
     */
    public URI getEmployees() {
        return URI.create("departmentName");
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(URI employees) {
        this.employees = employees;
    }

}
