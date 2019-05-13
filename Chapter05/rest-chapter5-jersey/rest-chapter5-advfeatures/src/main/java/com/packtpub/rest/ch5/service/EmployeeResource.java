/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service;

import com.packtpub.rest.ch5.model.Employee;
import com.packtpub.rest.ch5.model.EmployeeImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;
import org.eclipse.persistence.queries.CursoredStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.ChunkedOutput;

/**
 * A standard REST resource class representing employee object. In this class
 * you may find examples for image handling,multipart form data and
 * ChunkedOutput.
 *
 * @author Jobinesh
 */
@Stateless
@Path("employees")
public class EmployeeResource {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter5-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(EmployeeResource.class.getName());
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public EmployeeResource() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        entityManager.persist(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Employee entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        Employee entity = entityManager.find(Employee.class, id);
        entityManager.remove(entity);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Employee find(@PathParam("id") Integer id) {
        return entityManager.find(Employee.class, id);
    }

    /**
     * Return all employees in chunks using ChunkedOutput return type
     *
     * @return
     */
    @GET
    @Path("chunk")
    @Produces({MediaType.APPLICATION_JSON})
    public ChunkedOutput<List<Employee>> findAllEmployeesInChunk() {
        final ChunkedOutput<List<Employee>> output = new ChunkedOutput<List<Employee>>() {
        };
        executorService.execute(new LargeCollectionResponseType(output));

        // the output will be probably returned even before
        // a first chunk is written by the new thread
        return output;
    }

    class LargeCollectionResponseType implements Runnable {

        ChunkedOutput output;
        EntityManager entityManagerLocal = null;
        CursoredStream cursoredStream = null;
        final int PAGE_SIZE = 50;

        LargeCollectionResponseType(ChunkedOutput output) {
            this.output = output;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.packtpub_rest-chapter5-service_war_1.0-SNAPSHOTPU");
            entityManagerLocal = emf.createEntityManager();
            Query empQuery = entityManagerLocal.createNamedQuery("Employee.findAll");
            empQuery.setHint("eclipselink.cursor", true);
            cursoredStream = (CursoredStream) empQuery.getSingleResult();
        }

        public void run() {
            try {
                boolean hasMore = true;
                do {
                    List<Employee> chunk = (List<Employee>) getNextBatch(cursoredStream, PAGE_SIZE);
                    hasMore = (chunk != null && chunk.size() > 0);
                    if (hasMore) {
                        output.write(chunk);
                    }
                } while (hasMore);
            } catch (IOException e) {
                // IOException thrown when writing the
                // chunks of response: should be handled
                e.printStackTrace();
            } finally {
                try {
                    output.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
        }

        private List<Employee> getNextBatch(CursoredStream cursoredStream, int pagesize) {
            List emps = null;
            if (!cursoredStream.atEnd()) {
                emps = cursoredStream.next(pagesize);
            } else {
                emps = Collections.<Employee>emptyList();
            }
            return emps;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Employee> findAll() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq
                .select(cq.from(Employee.class
                ));
        return entityManager.createQuery(cq)
                .getResultList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Employee> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq
                .select(cq.from(Employee.class
                ));
        javax.persistence.Query q = entityManager.createQuery(cq);

        q.setMaxResults(to
                - from + 1);
        q.setFirstResult(from);

        return q.getResultList();
    }

    @GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public int countREST() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Employee> rt = cq.from(Employee.class
        );
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = entityManager.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Store the image in to database passed by the client
     *
     * @param request
     * @param in
     * @param fileDetail
     * @param empId
     * @throws Exception
     */
    @POST
    @Path("{empId}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImage(@Context HttpServletRequest request, @FormDataParam("empImgFile") InputStream in,
            @FormDataParam("empImgFile") FormDataContentDisposition fileDetail, @PathParam("empId") Integer empId) throws Exception {

        if (fileDetail == null || fileDetail.getFileName() == null) {
            logger.info("file name is null");
            throw new WebApplicationException("File name is null");
        }
        logger.info("Receiving file " + fileDetail.getFileName() + " size " + request.getContentLength());

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            int size = request.getContentLength();
            byte[] buffer = new byte[size];
            int len;
            while ((len = in.read(buffer, 0, 10240)) != -1) {
                bos.write(buffer, 0, len);
            }

            byte[] imgBytes = bos.toByteArray();
            //If image exists update it, otherwise create one
            EmployeeImage empImg = entityManager.find(EmployeeImage.class,
                    empId);
            if (empImg
                    == null) {
                empImg = new EmployeeImage();
                empImg.setImgId(empId);
                empImg.setEmployeeId(empId);
                empImg.setImage(imgBytes);
                empImg.setDescription(fileDetail.getFileName());
                entityManager.persist(empImg);
            } else {
                empImg.setImgId(empId);
                empImg.setEmployeeId(empId);
                empImg.setImage(imgBytes);
                empImg.setDescription(fileDetail.getFileName());
                entityManager.merge(empImg);
            }
        }
        logger.info(
                "Received file " + fileDetail.getFileName());

    }

    /**
     * Get the image from database and return to the caller
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GET
    @Path("/{empId}/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImage(final @PathParam("empId") Integer id) throws Exception {

        StreamingOutput outputImage = new StreamingOutput() {

            @Override
            public void write(OutputStream output) throws IOException {
                EmployeeImage empImg = entityManager.find(EmployeeImage.class, id);
                logger.info(
                        "File " + empImg.getDescription() + " requested");
                byte[] buf = empImg.getImage();

                logger.info(
                        "File size " + buf.length);
                output.write(buf);

                output.flush();

                logger.info(
                        "File " + empImg.getDescription() + "successfully downloaded in ");
            }

        };
        
        ResponseBuilder response = Response.ok((Object) outputImage);
        response.header("Content-Disposition", "inline;filename=image.jpeg");
        
        return response.build(); 

    }

}
