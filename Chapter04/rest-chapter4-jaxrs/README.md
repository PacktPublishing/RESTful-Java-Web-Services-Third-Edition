Code Samples for Chapter 4
==========================
This file contains instructions for running the sample project associated with Chapter 4 of RESTful Java Web Services.

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

How to run this example ?
-------------------------
- If you are not familiar with NetBeans IDE and GlassFish, move back and refer to the section 'Building your first RESTful web service with JAX-RS' in Chapter 3 of this book. Please follow the detailed steps that you will find in this section and complete the sample application before trying out any other examples. This will make sure your IDE and GlassFish Server is configured properly to run all the examples given in this book.

- Extract the zip file to your local file system. You may see **rest-chapter4-jaxrs** folder in the extracted location.
- This is a maven project and it two sub projects
    - rest-chapter4-service : This project contains REST service implementation
    - rest-chapter4-client : This is the REST client implementation    
- Open the rest-chapter4-jaxrs project in NetBeans IDE 8.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter4-jaxrs
    - In the project pane, expand 'Modules' element in rest-chapter4-jaxrs project and double click rest-chapter4-service project to open in the IDE
	- Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step.
    - In rest-chapter4-service project, 
        - Go to src/main/resources folder and open META-INF/persistence.xml.  Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. To know how to defined data source in GlassFish, see this page: http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html. 
    
    - In the project pane, expand rest-chapter4-jaxrs project and double click on rest-chapter4-service and rest-chapter4-client project to open them in the IDE.
    - Go to the project rest-chapter4-service, Right click  and choose Run. Thia action deploys the REST services.
    - To test REST APIs, open  rest-chapter4-client project, and then select either HRServiceJAXRSClient.java or HRServiceAsynchJAXRSClient. Later is used for asynch invocation.
        - Change the BASE_URI string in java to reflect the server URL where you have deployed the application
        - Right click DepartmentServiceJAXRSClient.java and choose Run to test client APIs.  
        
Core classes used in this example
---------------------------------
- <rest-chapter4-service>
    - com.packtpub.rest.ch4.model	
		- Department : JPA entity mapped to DEPARTMENTS table
		- Employee : JPA entity mapped to EMPLOYEES table
	- com.packtpub.rest.ch4.ext	
		- CSVMessageBodyReader : Demonstrates custom entity body reader
		- CSVMessageBodyWriter : Demonstrates custom entity body writer
		- DynamicFeatureRegister: DynamicFeature implementation
	- com.packtpub.rest.ch4.validation
		- ValidDepartmentValidator: Custom bean validation constraint
		- DepartmentNotFoundExceptionMapper : Exception   mapper which maps DeprtmentNotFoundBusinessException exceptions to HTTP Response.
	- com.packtpub.rest.ch4.service
		- HRAsynchService : REST resource class which demonstrates asynch JAX-RS rest service
		- HRService : REST resource class which demonstrates the use of  bean validation, custom entity providers and dynamic feature
		- HRServiceCache : REST resource class which demonstrates  caching APIS in JAX-RS
		- AppConfig : Application configuration class, sub classed form javax.ws.rs.core.Application

- <rest-chapter4-client>
	- com.packtpub.rest.ch4.client
		- HRServiceJAXRSClient: Demonstrates javax.ws.rs.core.Form API for creating Department resource
		- HRServiceAsynchJAXRSClient: Demonstrates asynch APIs for invoking APIs	
		
Sample input for testing some of the core features
-----------------------------------------------------
- Testing CSVMessageBodyWriter
	- Resource Class: com.packtpub.rest.ch4.service.HRService 
	- URI : http://localhost:28080/rest-chapter4-service/webresources/hr/departments/batch
  	- HTTP Method : POST
  	- Content-Type: application/csv
  	- Payload : 
		departmentId,departmentName,locationId,managerId
		1001,"HR 1001",1700,101
		1002,"IT 1001",1500,205 
   	- See the image input_application_csv.png in this folder to get a feel of this test case

- Testing CSVMessageBodyReader
	- Resource Class: com.packtpub.rest.ch4.service.HRService  
	- URI : http://localhost:28080/rest-chapter4-service/webresources/hr/departments/batch
   	- HTTP Method : GET
   	- Content-Type: application/csv 
   	
- Testing @ValidDepartment validator
	- Resource Class: com.packtpub.rest.ch4.service.HRService  
	- URI : http://localhost:28080/rest-chapter4-service/webresources/hr/departments/
  	- HTTP Method : POST
  	- Content-Type: application/json
  	- Payload : {"departmentId":300,"departmentName":"Administration","locationId":1700,"managerId":101}
  	
- Testing JAX-RS Caching ( Cache-Control )
	- Resource Class: com.packtpub.rest.ch4.service.HRServiceCache  
	- Use any REST API testing tool such as Postman REST Client or REST Console to test caching feature
	- URI: http://localhost:28080/rest-chapter4-service/webresources/hr/cache/departments/10
	- HTTP Method: GET
	- Content-Type: application/json
	- Set request Header: if-modified-since:<Last-Modified header value from last response>
	
- Testing JAX-RS Caching ( ETag )
	- Resource Class: com.packtpub.rest.ch4.service.HRServiceCache   
	- Use any REST API testing tool such as Postman REST Client or REST Console to test caching feature
	- URI: http://localhost:28080/rest-chapter4-service/webresources/hr/cache/etag/departments/10
	- HTTP Method: GET
	- Content-Type: application/json
	- Set request Header: If-None-Match :<ETag from last response>

What next?
----------------------------
This project contains advanced JAX-RS features that you may learn in chapter 4. You can explore things based on your interest. Also try adding more more fractures of your choice