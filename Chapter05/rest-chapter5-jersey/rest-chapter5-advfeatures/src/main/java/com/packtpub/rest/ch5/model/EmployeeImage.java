/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The table for this entity does not come with HR schema by default. Here is
 * the script:
 * CREATE TABLE EMPLOYEE_IMAGES ( IMG_ID NUMBER(6, 0) NOT NULL ,
 * EMPLOYEE_ID NUMBER(6, 0) NOT NULL , IMAGE BLOB , DESCRIPTION VARCHAR2(100
 * BYTE) , CONSTRAINT EMPLOYEE_IMAGES_PK PRIMARY KEY ( IMG_ID ) ENABLE );
 *
 * @author Jobinesh
 */
@Entity
@Table(name = "EMPLOYEE_IMAGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeImage.findAll", query = "SELECT e FROM EmployeeImage e"),
    @NamedQuery(name = "EmployeeImage.findByImgId", query = "SELECT e FROM EmployeeImage e WHERE e.imgId = :imgId"),
    @NamedQuery(name = "EmployeeImage.findByEmployeeId", query = "SELECT e FROM EmployeeImage e WHERE e.employeeId = :employeeId"),
    @NamedQuery(name = "EmployeeImage.findByDescription", query = "SELECT e FROM EmployeeImage e WHERE e.description = :description")})
public class EmployeeImage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    // @NotNull
    @GeneratedValue
    @Column(name = "IMG_ID")
    private Integer imgId;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "EMPLOYEE_ID")
    private int employeeId;
    @Lob
    @Column(name = "IMAGE")
    private byte[] image;

    //@Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    public EmployeeImage() {
    }

    public EmployeeImage(Integer imgId) {
        this.imgId = imgId;
    }

    public EmployeeImage(Integer imgId, int employeeId) {
        this.imgId = imgId;
        this.employeeId = employeeId;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imgId != null ? imgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeImage)) {
            return false;
        }
        EmployeeImage other = (EmployeeImage) object;
        if ((this.imgId == null && other.imgId != null) || (this.imgId != null && !this.imgId.equals(other.imgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.packtpub.rest.ch5.model.EmployeeImage[ imgId=" + imgId + " ]";
    }

}
