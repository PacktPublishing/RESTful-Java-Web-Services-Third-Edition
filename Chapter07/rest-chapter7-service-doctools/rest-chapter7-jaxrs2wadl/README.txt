Code Samples for Chapter 7
==========================
This file contains instructions for running project **rest-chapter7-jaxrs2wadl** associated with Chapter 7 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
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
   
- Open the rest-chapter7-jaxrs2wadl project in NetBeans IDE 8.0.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter7-jaxrs2wadl
    - In the project pane, expand rest-chapter7-jaxrs2wadl project 
    - Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step. 
    - In rest-chapter7-jaxrs2wadl project,        
    	- Go to src/main/resources folder and open META-INF/persistence.xml.  Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. To know how to define data source in GlassFish, see this page: http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html.  
    	
    - To run project, right click rest-chapter7-jaxrs2wadl and choose Run. This action deploys the REST services.
    - To access the WADL , http://<server>:<port>/rest-chapter7-jaxrs2wadl/webresources/application.wadl
    e.g: http://localhost:28080/rest-chapter7-jaxrs2wadl/webresources/application.wadl

Core classes and corresponding REST APIs used in this example
-------------------------------------------------------------

- <rest-chapter7-jaxrs2wadl>

    -  com.packtpub.rest.ch7.wadl.ApplicationConfig: This class configures the JAX-RS components used in this example.
    
    - com.packtpub.rest.ch7.wadl.DepartmentResource: This is a standard REST resource class representing department resource. In this example we are generating  WADL description for the department representation
    

How to view WADL file for DepartmentResource ?
-------------------------    
 - Deploy the application to a container. Alternatively you can right click on project and choose Run. This action will deploy the application in to integrated Glassfish
 - To access the WADL , http://<server>:<port>/rest-chapter7-jaxrs2wadl/webresources/application.wadl
    e.g: http://localhost:28080/rest-chapter7-jaxrs2wadl/webresources/application.wadl

What next?
----------------------------
You can try adding more REST APIs and JAX-RS annotations and see how these are getting represented in WADL.