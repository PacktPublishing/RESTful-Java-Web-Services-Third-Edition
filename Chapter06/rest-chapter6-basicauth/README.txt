Code Samples for Chapter 6
==========================
This file contains instructions for running the sample project associated with Chapter 6 of RESTful Java Web Services.

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
- <rest-chapter6-service>
    - com.packtpub.rest.ch6.model   
        - Departments : JPA entity mapped to DEPARTMENTS table
    - com.packtpub.rest.ch6.service
        - HRService: REST resource class which is used to fetch enterprise department details for valid JWT Auth token
        - AppConfig : Application configuration class, sub classed form javax.ws.rs.core.Application
    - com.packtpub.rest.ch6.filter
        - JWTAuthValidationFilter: JAX-RS ContainerRequestFilter used to perform validation of JWT Auth token
        - JWTTokenRequired : JAX-RS Name Binding for applying JWT Authentication annotation
    - com.packtpub.rest.ch6.util
        - JWTAuthHelper: Helper class with required functions for implementing JWT Authentication
        
- <rest-chapter6-client>
    - com.packtpub.rest.ch6.client
       -  HRServiceJWTAuthClient: JAX-RS client for accessing RESTful web APIs exposed by <rest-chapter6-service> with JWT Auth token

How to run this example ?
-------------------------
- If you are not familiar with NetBeans IDE and GlassFish, move back and refer to the section 'Building your first RESTful web service with JAX-RS' in Chapter 3 of this book. Please follow the detailed steps that you will find in this section and complete the sample application before trying out any other examples. This will make sure your IDE and GlassFish Server is configured properly to run all the examples given in this book.

- Extract the zip file to your local file system. You may see **rest-chapter6-jwt** folder in the extracted location.
- This is a maven project and it has two sub projects
    - rest-chapter6-jwtservice : This project contains REST service implementation
    - rest-chapter6-jwtclient : This is the REST client implementation   
- Open the rest-chapter6-jwt project in NetBeans IDE 8.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter6-jwt
    - In the project pane, expand 'Modules' element in rest-chapter6-jwt project and then double click rest-chapter6-service project to open in the IDE
	- Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings
    as explained in next step.
    - In rest-chapter6-jwtservice project,
        - Go to src/main/resources folder and open META-INF/persistence.xml file. Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. 
        - This example uses HR schema as data store that usually comes with Oracle DB as sample schema
    - In the project pane, expand rest-chapter6-jwt project and double click rest-chapter6-jwtclient project to open in the IDE.
    - Right click rest-chapter6-jwtservice and choose Run. This action deploys the REST services.
    - To test REST APIs, open  rest-chapter6-client project, and then select HRServiceJWTAuthClient.java.
        - Change the BASE_URI string in HRServiceJWTAuthClient.java to reflect the server URL where you have deployed the application
        - Right click HRServiceJWTAuthClient.java and choose Run to test client APIs. This client exercises GET with JWT Auth token.
 