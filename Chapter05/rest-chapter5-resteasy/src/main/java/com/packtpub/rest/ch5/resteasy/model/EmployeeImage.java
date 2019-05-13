/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.resteasy.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * EmployeeImage JPA entity
 * 
 * @author Jobinesh
 */

@Entity
@Table(name = "EMPLOYEE_IMAGES")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "EmployeeImage.findProfilePicEmployeeId", query = "SELECT e FROM EmployeeImage e WHERE e.employeeId = :employeeId")
})

public class EmployeeImage implements Serializable {
    

    @Id
    @Column(name = "IMG_ID")   
    private short imageId;
    
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYEE_ID")   
    private short employeeId;
    
    @Lob
    @Column(name="IMAGE")
    private byte[] employeePic;
    
    
    @Column(name = "DESCRIPTION")    
    private String description;
    
    
    public EmployeeImage(){
        
    }

    public EmployeeImage(short employeeId, byte[] employeePic, String description) {
        
        this.employeeId = employeeId;
        this.imageId = employeeId;
        this.employeePic = employeePic;
        this.description = description;
    }

    public short getImageId() {
        return imageId;
    }

    public void setImageId(short imageId) {
        this.imageId = imageId;
    }

    public short getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(short employeeId) {
        this.employeeId = employeeId;
    }

    public byte[] getEmployeePic() {
        return employeePic;
    }

    public void setEmployeePic(byte[] employeePic) {
        this.employeePic = employeePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    @Override
    public String toString() {
        return "com.packtpub.rest.ch5.resteasy.model.EmployeeImage[ employeeId=" + employeeId + " imageId=" + imageId + "]";
    }
    
}
