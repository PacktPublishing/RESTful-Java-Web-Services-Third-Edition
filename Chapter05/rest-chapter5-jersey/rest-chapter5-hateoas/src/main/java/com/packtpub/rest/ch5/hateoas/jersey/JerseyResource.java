/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.hateoas.jersey;

import com.packtpub.rest.ch5.model.Departments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.beanutils.BeanUtils;

/**
 * A simple JAX-RS resource class. This REST resource class return 
 * list of DepartmentRepresentation on request. DepartmentRepresentation contains
 * @InjectLink annotation to generate hypermedia links in the resource representation. 
 * @author Jobinesh
 */
@Path("jersey/departments")
@Stateless
public class JerseyResource {

    @PersistenceContext(unitName = "HATEOAS_PU")
    private EntityManager entityManager;
   

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllAndUpdateResrcWithJerseyLinks() throws IllegalAccessException {
        
        System.out.println("Inside JerseyResource");
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
        
        System.out.println("Fetched All Departments");
        //  return Response.ok().links(getTransitionalLinks()).entity(entity).build();
        return Response.ok().entity(entity).build();
    }

    private void copyProperties(Object dest, Object src) {
        try {
            BeanUtils.copyProperties(dest, src);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
