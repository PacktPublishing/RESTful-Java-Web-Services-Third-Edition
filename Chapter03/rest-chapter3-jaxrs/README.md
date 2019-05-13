Code Samples for Chapter 3
==========================
This file contains instructions for running the sample project associated with Chapter 3 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8 or newer
- NetBeans IDE 8.2 or newer
- Glassfish Server 4.1 or newer(installed along with  NetBeans IDE)
- Maven 3.2.3 or newer
- Oracle Database 11.2 Express Edition or newer (Example uses HR Schema)

Detailed instructions for setting up all the required tools for running the
examples used in this book are discussed in Appendix section of this book

Core classes used in this example
---------------------------------
- <rest-chapter3-service>
    - com.packtpub.rest.ch3.model   
        - Departments : JPA entity mapped to DEPARTMENTS table
    - com.packtpub.rest.ch3.service
        - DepartmentService: REST resource class which demonstrates CRUD operations on department resource
        - RestAppConfig : Application configuration class, sub classed form javax.ws.rs.core.Application
- <rest-chapter3-client>
    - com.packtpub.rest.ch3.client
       -  DepartmentServiceJAXRSClient: JAX-RS client for accessing RESTful web APIs exposed by <rest-chapter3-service>

How to run this example ?
-------------------------
- If you are not familiar with NetBeans IDE and GlassFish, move back and refer to the section 'Building your first RESTful web service with JAX-RS' in Chapter 3 of this book. Please follow the detailed steps that you will find in this section and complete the sample application before trying out any other examples. This will make sure your IDE and GlassFish Server is configured properly to run all the examples given in this book.

- Extract the zip file to your local file system. You may see **rest-chapter3-jaxrs** folder in the extracted location.
- This is a maven project and it has two sub projects
    - rest-chapter3-service : This project contains REST service implementation
    - rest-chapter3-client : This is the REST client implementation   
- Open the rest-chapter3-jaxrs project in NetBeans IDE 8.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter3-jaxrs
    - In the project pane, expand 'Modules' element in rest-chapter3-jaxrs project and then double click rest-chapter3-service project to open in the IDE
	- Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings
    as explained in next step.
    - In rest-chapter3-service project,
        - Go to src/main/resources folder and open META-INF/persistence.xml file. Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. To know how to defined data source in GlassFish, see this page: http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html
        - This example uses HR schema as data store that usually comes with Oracle DB as sample schema
    - In the project pane, expand rest-chapter3-jaxrs project and double click rest-chapter3-client project to open in the IDE.
    - Right click rest-chapter3-service and choose Run. This action deploys the REST services. 
		- Do not worry about the warning message seen at server startup, they do not impact the functioning of the service
    - To test the API from browser try the following URI http://<server>:<port>/rest-chapter3-service/webresources/departments
    - To test REST APIs, open  rest-chapter3-client project, and then select DepartmentServiceJAXRSClient.java.
        - Change the BASE_URI string in DepartmentServiceJAXRSClient.java to reflect the server URL where you have deployed the application
        - Right click DepartmentServiceJAXRSClient.java and choose Run to test client APIs. This client exercises GET 
 
What next?
----------------------------
Both rest-chapter3-service and rest-chapter3-client contains basic examples for REST API server and client implementations.
Go through the source and try adding more more features to these examples  as and when you learn new JAX-RS APIs. 

