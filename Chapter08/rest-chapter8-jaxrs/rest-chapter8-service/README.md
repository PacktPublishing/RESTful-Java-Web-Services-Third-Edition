Code Samples for Chapter 8
==========================
This file contains instructions for running project **rest-chapter8-service** associated with Chapter 8 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
- Glassfish Server 4.1 or newer(installed along with  NetBeans IDE)
- Maven 3.2.3. 
- Oracle Database 11.2 Express Edition or higher (Example uses HR Schema)

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix section of this book


Before you begin
----------------
- If you are not familiar with NetBeans IDE and GlassFish, move back and refer to the section 'Building your first RESTful web service with JAX-RS' in Chapter 3 of this book. Please follow the detailed steps that you will find in this section and complete the sample application before trying out any other examples. This will make sure your IDE and GlassFish Server is configured properly to run all the examples given in this book.

How to use this example 
-------------------------
   
- Open the rest-chapter8-service project in NetBeans IDE 8.0.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter8-service
    - In the project pane, expand rest-chapter8-service project 
    - Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step. 
    - In rest-chapter8-service project,        
        - Go to src/main/resources folder and open META-INF/persistence.xml.  Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. To know how to define data source in GlassFish, see this page: http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html. 
         
    - To run project, right click rest-chapter8-servic and choose Run. This action deploys the REST services.
   

Core classes and corresponding REST APIs used in this example
-------------------------------------------------------------

- <rest-chapter8-service>

    -  com.packtpub.rest.ch8.service.ApplicationConfig: This class configures the JAX-RS components used in this example.    
    - com.packtpub.rest.ch8.service.DepartmentResource: This is a standard REST resource class representing department resource. You can access this via URI http://<server>:<port>/rest-chapter8-service/webresources/departments
    -  com.packtpub.rest.ch8.service.EmployeeResource: This is a standard REST resource class representing employee resource. You can access this via URI http://<server>:<port>/rest-chapter8-service/webresources/employees
    -  com.packtpub.rest.ch8.service.exception.ValidationExceptionMapper: Custom ExceptionMapperclass for handling ValidationException
    
    
How to test the APIs ?
-------------------------    
 - Deploy the application to a container. Alternatively you can run(Right click on project and choose Run) the desired REST sample project as described in last section. This action will deploy the application in to integrated Glassfish
 
    - A quick way to test the API is to use any REST test client(such as Postman or Rest Console)
    - To test SelectableEntityFilteringFeature, try accessing the following resource API:
    	-  http://<server>:<port>/rest-chapter8-service/webresources/departments/10?select=departmentName
    	-  The select=departmentName causes server to rerun only departmentName
    - To test the media header versioning, issue a GET request with following properties using any of your favourite REST test client.
    	- Request URI: http://<server>:<port>/rest-chapter8-service/webresources/departments
    	- Accept : application/vnd.packtpub.v2+xml
    	- Request Method: Get
    	- Now when you send the request, runtime will pickup method annotated with @Produces("application/vnd.packtpub.v2+xml")  to read values
   
	- To test validation and exception mapper, try accessing the following API with any REST test client(such as Postman or Rest Console): http://<server>:<port>/rest-chapter8-service/webresources/departments?offset=-1
	E.g:  http://localhost:8080/rest-chapter8-service/webresources/departments?offset=-1
	You will see an error message as repsonse: The offset parameter must be greater than 0
    The error is due to the @Min validation set for the method parameter. The com.packtpub.rest.ch8.service.exception.ValidationExceptionMapper provider maps the error to entity body

	- To test pagination try accessing resource with offset and limit paremeters. e.g:http://localhost:28080/rest-chapter8-service/webresources/departments?offset=0&limit=4

What next?
----------------------------
This project will help you to get a real feel of various best practises and design tips that we discussed in chapter8. You can explore each tip at your own pace. Also try enhancing the samples by adding more points that we discussed in chapter 8 