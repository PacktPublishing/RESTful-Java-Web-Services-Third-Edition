Code Samples for Chapter 5
==========================
This file contains instructions for running the sample project associated with Chapter 5 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.0.2 
- Glassfish Server 4.1 or newer(installed along with  NetBeans IDE)
- Maven 3.2.3. 
- Oracle Database 11.2 Express Edition or higher (Example uses HR Schema)

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix A section of this book

Before you begin
----------------
- If you are not familiar with NetBeans IDE and GlassFish, move back and refer to the section 'Building your first RESTful web service with JAX-RS' in Chapter 3 of this book. Please follow the detailed steps that you will find in this section and complete the sample application before trying out any other examples. This will make sure your IDE and GlassFish Server is configured properly to run all the examples given in this book.

How to use this example 
-------------------------
- Extract the zip file to your local file system. You may see **rest-chapter5-jersey** folder in the extracted location.
- This is a maven project and it three sub projects
    - <rest-chapter5-advfeatures> : This project contains REST service implementation demonstrating Server Sent Event, Chunk Output, Image(binary object) handling, model processor, monitoring capabilities.    
    - <rest-chapter5-hateoas> : This project contains RESTful web services demonstrating various HATEOAS features. 
    - <rest-chapter5-client> : This is the REST client implementation, which demonstrates Server Sent Events and Chunked Input features. You need to run the project  <rest-chapter5-advfeatures> in to deploy the required services to server before try running the client.
      
- Open the rest-chapter5-jersey project in NetBeans IDE 8.0.2 or higher

    - In main tool bar,choose File > Open Project > rest-chapter5-jersey
    - In the project pane, expand 'Modules' element in rest-chapter5-jersey project and double
      click rest-chapter5-advfeatures project to open in the IDE
   	- Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step.
    - In rest-chapter5-advfeatures project, 
        - Go to src/main/resources folder and open META-INF/persistence.xml.  Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. To know how to define data source in GlassFish, see this page: [http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html](http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html).
       
        - **You may also need to have an additional table(EMPLOYEE_IMAGES) to run thie example. You will find a script with name employeeimage.sql in the root folder. Run this script in your HR schema to generate the required table. This table is used by example that demonstrates image retrieval and update via REST API.**
        
    - Similarly, open rest-chapter5-hateoas and change the data source to reflect your local database as you have done for rest-chapter5-advfeatures project in last step.
   
    - To run project, right click rest-chapter5-advfeatures and choose Run. Thia action deploys the REST services. Do the same for rest-chapter5-hateoas to test APIs from this project
    - To test you can use any REST api tester or build a REST client
    

Core classes and corresponding REST APIs used in this example
-------------------------------------------------------------

- <rest-chapter5-advfeatures>

    - com.packtpub.rest.ch5.service.ApplicationConfig: This class demonstrates Jersey APIs for dynamically adding resource. You may also find some extended configuration offerings from Jersey.
    
        - REST API URI to test dynamic config fetaure: http://<server>:<port>/rest-chapter5-advfeatures/webresources/server/info , You will see server infomration in reponse to this call
        
    - com.packtpub.rest.ch5.service.EmployeeResource: This is a standard REST resource class representing employee object. In this class you may find examples for image handling,multipart form data and ChunkedOutput. 
       
        - URI to test image upload/Read: http://<server>:<port>/rest-chapter5-advfeatures/empImage.html
        - URI to test chunked Output: http://<server>:<port>/rest-chapter5-advfeatures/rest-chapter5-advfeatures/webresources/employees/chunk
               
    - com.packtpub.rest.ch5.service.monitor.ApplicationEventListenerImpl: This class is Jersey specific provider that listens to application events.
    
    - com.packtpub.rest.ch5.service.monitor.ContainerLifecycleListenerImpl: This class receive container life-cycle notification events.
    
    - com.packtpub.rest.ch5.service.monitor.RequestEventListenerImpl: The implementation of the interface will be called for request events when they occur
    
    - com.packtpub.rest.ch5.service.processor.JsonSchemaModelProcessorImpl: This class demostrates model processor capability which can be used for modifying resources dynamically. In this example it modifies all resources to return schema for URI path /schema.
    
        - REST API URI to test model processor: http://<server>:<port>/rest-chapter5-advfeatures/webresources/departments/schema
    
    - com.packtpub.rest.ch5.service.sse.SSEEnabledDeptResource: This class demonstrate Server Sent Event.
       
- <rest-chapter5-hateoas>

    - com.packtpub.rest.ch5.hateoas.jaxrs.JaxRSResource: This class demonstrates use of  javax.ws.rs.core.Link for programmatically building Links in JAX-RS resource.
        - REST API URI to test JAX-RS Link: http://<server>:<port>/rest-chapter5-hateoas/webresources/jaxrs/departments 
      - com.packtpub.rest.ch5.hateoas.jersey: This class demonstrates Jersey annotation for declaratively building Links in  JAX-RS resource.
          - REST API URI to test Jersey Link: http://<server>:<port>/rest-chapter5-hateoas/webresources/jersey/departments 
          
-<rest-chapter5-client>
    - com.packtpub.rest.ch5.client.DepartmentBroadcastingSSECleint: SSE client for http://localhost:28080/rest-chapter5-advfeatures/webresources/departments/events
    - com.packtpub.rest.ch5.client.EmployeeResourceClient: ChunkedInput client for http://localhost:28080/rest-chapter5-advfeatures/webresources/employees/chunks
      
How to test the APIs ?
-------------------------    
- Deploy the application to a container. Alternatively you can run(Right click on project and choose Run) the desired REST sample project as described in last section. This action will deploy the application in to integrated Glassfish
- To test REST APIs, right click and run both <rest-chapter5-advfeatures> and <rest-chapter5-hateoas> projects. This action will deploy the required services.
    - Some APIs can betested using any REST API test client(such as Postman or Rest Console)
    - To test rest-chapter5-client project, and choose existing client or build one if you want. You will not find client for all demo APIs in this project.
    - Change the BASE_URI string in java to reflect the server URL where you have deployed the application
    - Right click XXXClient.java and choose Run to test client APIs.
    
    
- **Following  REST API URIs will help you to test specifc features with a REST client(e.g Postman)**   


- REST API URI to test dynamic config fetaure: http://<server>:<port>/rest-chapter5-advfeatures/webresources/server/info , You will see server information in repose to this call, e.g: http://localhost:28080/rest-chapter5-advfeatures/webresources/server/info
- URI to test image upload/Read: http://<server>:<port>/rest-chapter5-advfeatures/empImage.html
- REST API URI to test model processor: http://<server>:<port>/rest-chapter5-advfeatures/webresources/departments/schema
- REST API URI to test JAX-RS Link: http://<server>:<port>/rest-chapter5-hateoas/webresources/jaxrs/departments 

- Testing Server Sent Event feature
    - REST API URI to subscribe to events: http://localhost:28080/rest-chapter5-advfeatures/webresources/departments/events. 
    - A quick way to test this  features is: deploy and run the service as first step(Right click and run <rest-chapter5-advfeatures> )
    - <rest-chapter5-client> project has test client for SSE and Chunked Input.
    - Change the BASE_URI string in java client to reflect the server URL where you have deployed the application
    - Now Run DepartmentBroadcastingSSECleint located in <rest-chapter5-client>
    - As last step update department using any REST client such as Postman and see event get published on all registered clients(check the output log in server)
        - REST API URI to update department which will eventually generate event for all subscribers is:http://localhost:28080/rest-chapter5-advfeatures/webresources/departments/events/300, and sample payload for update: {"departmentId":300,"departmentName":"Administration","employees":"departmentName","locationId":1700,"managerId":200}
- To test chunked input, open <rest-chapter5-client> in IDE
    -URI to test chunked Output: http://<server>:<port>/rest-chapter5-advfeatures/rest-chapter5-advfeatures/webresources/employees/chunk 
    - Change the BASE_URI string in java client to reflect the server URL where you have deployed the application    
    - Right click and run the EmployeeResourceClient.java  

What next?
----------------------------
This project contains Jersey features that you learned in chapter 5. You can explore each feature at your own pace. Also try enhancing the samples by adding more features that you find interesting !