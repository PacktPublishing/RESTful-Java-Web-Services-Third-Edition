/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this imageFile, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.resteasy.service;

import com.packtpub.rest.ch5.resteasy.model.EmployeeImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * This class used to upload or download an employee profile picture
 *
 * @author Balachandar
 */
@Path("employee")
@Stateless
public class EmployeeImageResource {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter5-resteasy-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(EmployeeImageResource.class.getName());

    @GET
    @Path("/image/{id}")
    @Produces("image/png")
    /*
    * Download the employee profile picture for the given employee id
    */
    public Response getProfilePicture(@PathParam("id") Short empId) {

        try {

            //Step-1: Fetch the employee profile picture from datastore
            EmployeeImage profile = entityManager.find(EmployeeImage.class, empId);

            //Ste-2: 
            String fileName = empId + "-image.png";
            File imageFile = new File(fileName);
            try (FileOutputStream imageFOS = new FileOutputStream(imageFile)) {
                imageFOS.write(profile.getEmployeePic());
                imageFOS.flush();
            }

            ResponseBuilder responseBuilder = Response.ok((Object) imageFile);
            responseBuilder.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            return responseBuilder.build();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Fetching Employee Profile Picture", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error Fetching Employee Profile Picture").build();
        }

    }

    
    @POST
    @Consumes("multipart/form-data")
    @Path("/addimage")
    /*
    * Upload the employee profile picture for the given employee id
    */
    public Response addImage(MultipartFormDataInput form) {

        try {
            //Step-1: Read the Form Contents
            Map<String, List<InputPart>> formContents = form.getFormDataMap();
            List<InputPart> imagePart = formContents.get("profilePicture");

            if (imagePart == null) {
                return Response.status(400).entity("Invalid Content Uploaded").build();
            }
            byte[] profilePic = null;

            for (InputPart inputPart : imagePart) {

                profilePic = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
            }
            
            //Step-2: Validate the presence of mandatory content and
            //if invalid content reply as Bad Data Request
            if (profilePic == null || profilePic.length<1 || formContents.get("employeeId") == null) {
                return Response.status(Status.BAD_REQUEST).entity("Invalid Content Uploaded").build();
            }

            String empId = formContents.get("employeeId").get(0).getBodyAsString();
            String desc = "";
            if (formContents.get("imageDesc") != null
                    && formContents.get("imageDesc").get(0) != null) {
                desc = formContents.get("imageDesc").get(0).getBodyAsString();
            }

            //Step-3:Persist the uploaded image to datastore
            EmployeeImage empImgEntity = new EmployeeImage(
                    Short.parseShort(empId),
                    profilePic, desc);
            entityManager.persist(empImgEntity);

            return Response.status(Status.CREATED).entity("Saved Employee Profile Picture").build();

        } catch (IOException | NumberFormatException e) {
            logger.log(Level.SEVERE, "Error Saving Employee Profile Picture", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error Saving Employee Profile Picture").build();

        }

    }

}
