Code Samples for Chapter 9
==========================
This file contains instructions for running project **rest-chapter9-odata-service** associated with Chapter 9 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
- Maven 3.2.3. 
- Oracle Database 11.2 Express Edition or higher (Example uses HR Schema)

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix A section of this book

How to use this example 
-------------------------
   
- Open the rest-chapter9-odata-service project in NetBeans IDE 8.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter9-odata-service
    - In the project pane, expand rest-chapter9-odata-service project 
    - Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step. 
    - In rest-chapter9-odata-serviceproject,     
        - Go to src/main/resources folder and open META-INF/persistence.xml. Modify the data source configuration for 'HR-PU' to match with Oracle data base (having HR schema) that you have. 
    -This project uses Apache Olingo for enabling OData service for your JPA model. Project setup details can be found here : [http://olingo.apache.org/doc/odata2/tutorials/CreateWebApp.html ](http://olingo.apache.org/doc/odata2/tutorials/CreateWebApp.html)    
	- To run project, right click rest-chapter9-odata-service and choose Run. This action deploys the REST services.
    - To test if OData services are enabled for JPA model present in the project, try accessing the following URI from any REST API cleint(or browser) : http://<server>:<port>/rest-chapter9-odata-service/odata/  
    - You should now see OData service collection details (based on JPA entity present in the project) as response  
   

Core classes and corresponding REST APIs used in this example
-------------------------------------------------------------

- <rest-chapter9-odata-servic>: This project contains JPA model classes and Olingo configuration class 
    - com.packtpub.rest.ch9.odata.service.ODataJPAServiceFactoryImpl: Implementation of ODataJPAServiceFactory. ODataJPAServiceFactory provides a means for initializing Entity Data Model (EDM) Provider and OData JPA Processors. See http://olingo.apache.org/doc/odata2/tutorials/CreateWebApp.html
    - com.packtpub.rest.ch9.odata.model.Department : JPA entity 
    - com.packtpub.rest.ch9.odata.model.Employee : JPA entity 
    - See web.xml to understand the configurations for enabling Apache Olingo in the project
    
How to test the APIs ?
-------------------------    
 - Deploy the application to a container. Alternatively you can run(Right click on project and choose Run) the desired REST sample project as described in last section. This action will deploy the application in to integrated Glassfish
 
    - You can try out all supported OData features on the deployes application:
    - For example following request URI with GET method will return trimmed department resource collection sorted on DepartmentName
    - http://<server>:<port>/rest-chapter9-odata-service/odata/Departments?$orderby=DepartmentName&$select=DepartmentName,ManagerId 
    - To test Create, Read, Update, Dlete on JPA model via OData service APIs over REST protocol, refer (rest-chapter9-odata-client/README.txt)      

.    

What next?
----------------------------
You can try adding more features from Apache Olingo. Refer [http://olingo.apache.org/doc/odata2/index.html](http://olingo.apache.org/doc/odata2/index.html)