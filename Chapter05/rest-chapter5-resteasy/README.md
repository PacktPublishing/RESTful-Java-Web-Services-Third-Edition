Code Samples for Chapter 5
==========================
This file contains instructions for running the sample project associated with Chapter 5 of RESTful Java Web Services.

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
- Extract the zip file to your local file system. You may see **rest-chapter5-resteasy** folder in the extracted location.
- This is a maven project 
    - <rest-chapter5-resteasy> : This project contains REST service & Client implementation demonstrating RESTEasy features of caching and GZIP Compression.    
      
- Open the rest-chapter5-resteasy project in NetBeans IDE 8.2 or higher

    - In main tool bar,choose File > Open Project > rest-chapter5-resteasy
    - In the project pane, expand 'Modules' element in rest-chapter5-resteasy project
	- Note that this example uses HR schema that usually comes with Oracle as sample schema. So we need to change the connection details to reflect your local settings as explained in next step.
        - Go to src/main/resources folder and open META-INF/persistence.xml.  Modify the jta-data-source entry to match with Oracle data base that you have. You can either choose a an already existing data source(which connects to HR schema in Oracle DB) in GlassFish server or define a new JNDI data source in GlassFish and set it as 'jta-data-source' in persistence.xml. 
    - To run project, right click rest-chapter5-resteasy and choose Run. This action deploys the REST services.
    - To test you can use REST client program - DepartmentServiceClient
    

Core classes and corresponding REST APIs used in this example
-------------------------------------------------------------

- <rest-chapter5-resteasy>

    - com.packtpub.rest.ch5.resteasy.service.RestAppConfig: This class demonstrates Jersey APIs for dynamically adding resource. You may also find some extended configuration offerings from Jersey.
    - com.packtpub.rest.ch5.resteasy.client.DepartmentServiceClient: This class demonstrates RESTEasy features of client side caching and invoking the target service.
    - com.packtpub.rest.ch5.resteasy.service.DepartmentService: This class demonstrates RESTEasy features of server side caching and GZIP compression.
    - com.packtpub.rest.ch5.resteasy.service.EmployeeImageResource: This class demonstrates RESTEasy features of file upload/download.
    
      
How to test the APIs ?
-------------------------    
- Deploy the application to a container. Alternatively you can run(Right click on project and choose Run) the desired REST sample project as described in last section. This action will deploy the application in to integrated Glassfish
- To test REST APIs, right click and run both <rest-chapter5-resteasy> project. This action will deploy the required services.
    - Some APIs can betested using any REST API test client(such as Postman or Rest Console)
    - Right click DepartmentServiceClient.java and choose Run to test client APIs.
    - For testing File Upload/Download Service, create 'EMPLOYEE_IMAGES' table using the 'EMPLOYEE-IMAGE.sql' in resources folder.
    
    
- **Following  REST API URIs will help you to test specifc features with a REST client(e.g Postman)**   

- REST API URI to test the DepartmentService: http://<server>:<port>/rest-chapter5-resteasy/webresources/departments/id/10,http://<server>:<port>/rest-chapter5-resteasy/webresources/departments/all 
- REST API URI to test the EmployeeImageResource: http://<server>:<port>/rest-chapter5-resteasy/webresources/employee/image/100,http://<server>:<port>/rest-chapter5-resteasy/index.html

What next?
----------------------------
This project contains RESTEasy features that you learned in chapter 5. You can explore each feature at your own pace. Also try enhancing the samples by adding more features that you find interesting !