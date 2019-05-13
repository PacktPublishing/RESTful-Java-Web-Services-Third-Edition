/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.hateoas.jersey;

import java.util.List;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

/**
 * A resource representation class to demonstrate HATEOAS annotation offering
 * from Jersey
 * such as InjectLink. @InjectLink can be used for generating Links in the REST
 * response.
 *
 * @author Jobinesh
 */
@XmlRootElement
public class DepartmentRepresentation {

    private Short departmentId;
    private String departmentName;
    private Integer managerId;
    private Short locationId;

    /* @InjectLink(
     value = "{id}/employees",
     style = Style.RELATIVE_PATH,
     bindings = @Binding(name = "id", value = "${instance.departmentId}"),
     rel = "employees"
     )
     @XmlJavaTypeAdapter(LinkAdaptor.class)
     @XmlElement(name = "link")
     Link links;*/
    
    //Specifies a link injection target in a returned representation bean.
    //May be used on fields of type String or URI. One of value() or resource() must be specified.
    @InjectLinks({
        @InjectLink(
                value = "{id}/employees",
                style = Style.RELATIVE_PATH,
                bindings = @Binding(name = "id", value = "${instance.departmentId}"),
                rel = "employees"
        ),
        @InjectLink(
                value = "{id}/employees/{managerId}",
                style = Style.RELATIVE_PATH,
                bindings = {
                    @Binding(name = "id", value = "${instance.departmentId}"),
                    @Binding(name = "managerId", value = "${instance.managerId}")},
                rel = "manager"
        )})
    
    @XmlJavaTypeAdapter(LinkAdaptor.class)
    @XmlElement(name = "links")
    List<Link> links;

    public DepartmentRepresentation() {

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
   
}
