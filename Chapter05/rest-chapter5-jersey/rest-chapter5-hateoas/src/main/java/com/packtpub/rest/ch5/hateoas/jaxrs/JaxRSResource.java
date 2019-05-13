/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.hateoas.jaxrs;

import com.packtpub.rest.ch5.model.Departments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.beanutils.BeanUtils;

/**
 * A simple JAX-RS resource class. This REST resource class return 
 * list of DepartmentRepresentation on request. In this example, Links are
 * manually added to the resource representation via Link API .
 * @author Jobinesh
 */
@Path("jaxrs/departments")
@Stateless
public class JaxRSResource {

    @PersistenceContext(unitName = "HATEOAS_PU")
    private EntityManager entityManager;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllAndUpdateResrcWithJAXRSLinks() throws IllegalAccessException {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departments.class));
        List<Departments> deptOriginals = entityManager.createQuery(cq).getResultList();
        List<DepartmentRepresentation> deptForTransfer = new ArrayList<>();

        for (Departments src : deptOriginals) {
            DepartmentRepresentation dest = new DepartmentRepresentation();

            copyProperties(dest, src);

            deptForTransfer.add(dest);
        }
        GenericEntity<List<DepartmentRepresentation>> entity = new GenericEntity<List<DepartmentRepresentation>>(deptForTransfer) {
        };
        //  return Response.ok().links(getTransitionalLinks()).entity(entity).build();
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Short id) {

        Departments src = entityManager.find(Departments.class, id);
        DepartmentRepresentation entity = new DepartmentRepresentation();
        copyProperties(entity, src);
        return Response.ok().links(getLinks(entity.getManagerId())).entity(entity).build();

    }

    private Link[] getLinks(int departmnetId) {
        Link managerLink = Link.fromUri("{id}/employees")
                .rel("manager").build(departmnetId);
        return new Link[]{managerLink};
    }

    private void copyProperties(Object dest, Object src) {
        try {
            BeanUtils.copyProperties(dest, src);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
