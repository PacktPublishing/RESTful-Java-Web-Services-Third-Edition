Code Samples for Chapter 7
==========================
This file contains instructions for running project **rest-chapter7-jaxrs2raml** associated with Chapter 7 of RESTful Java Web Services.

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

How to use this example 
-------------------------
   
- Open the rest-chapter7-jaxrs2raml project in NetBeans IDE 8.0.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter7-jaxrs2raml
    - In the project pane, expand rest-chapter7-jaxrs2raml project 
    - Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step. 
    - In rest-chapter7-jaxrs2raml project,        
        - Go to src/main/resources folder and open META-INF/persistence.xml.  Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. To know how to define data source in GlassFish, see this page: http://www.jobinesh.com/2015/09/configuring-jndi-data-source-for-oralce.html. 
         
    - To generate RAML file for the project, right click rest-chapter7-jaxrs2raml and choose Build. This action will build the REST resources and generates the RAML file. The RAML is generated via jaxrs-raml-maven-plugin specified in pom.xml
    - To view the RAML, open the file located at maven build target folder: <target>\generated-sources\jaxrs-raml\department-resource.raml


Core classes and corresponding REST APIs used in this example
-------------------------------------------------------------

- <rest-chapter7-jaxrs2raml>

    -  com.packtpub.rest.ch7.raml.ApplicationConfig: This class configures the JAX-RS components used in this example.
    
    - com.packtpub.rest.ch7.raml.DepartmentResource: This is a standard REST resource class representing department resource. In this example we are generating  RAML description for the department representation.
    

How to view RAML file for DepartmentResource ?
-------------------------    
 - Build the application using maven by executing the command mvn install. Alternatively you can right click on project and choose Build. This action will generates the RAML file.
 - To view the RAML, open the file located at maven build target folder: <target>\generated-sources\jaxrs-raml\department-resource.raml

What next?
----------------------------
You can try adding more REST APIs and JAX-RS annotations and see how these are getting represented in RAML.