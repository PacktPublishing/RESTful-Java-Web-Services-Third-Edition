Code Samples for Chapter 7
==========================
This file contains instructions for running project **rest-chapter7-raml2jaxrs** associated with Chapter 7 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
- Maven 3.2.3. 
- Oracle Database 11.2 Express Edition or higher (Example uses HR Schema)

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix  section of this book

Before you begin
----------------
- If you are not familiar with NetBeans IDE and GlassFish, move back and refer to the section 'Building your first RESTful web service with JAX-RS' in Chapter 3 of this book. Please follow the detailed steps that you will find in this section and complete the sample application before trying out any other examples. This will make sure your IDE and GlassFish Server is configured properly to run all the examples given in this book.

How to use this example 
-------------------------
   
- Open the rest-chapter7-raml2jaxrs project in NetBeans IDE 8.2 or higher
    - In main tool bar,choose File > Open Project > rest-chapter7-raml2jaxrs
    - In the project pane, expand rest-chapter7-raml2jaxrs project   
    - You will find department-resource.raml in the ${basedir}/src/main/resources/raml folder of the project. We will generate JAX-RS resource classes using  department-resource.raml as input.
    - To generate RAML file for the project, right click rest-chapter7-raml2jaxrs and choose Build. This action will generates JAX-RS resource component from the RAML file. The RAML is generated via raml-jaxrs-maven-plugin specified in pom.xml
    - To view the JAX-RS, open the maven build target folder and navigate to <target>\generated-sources\raml-jaxrs\com\packtpub\rest\ch7\service\resource. You will see JAX-RS resource class files (along with supporting classes) generated in this folder. 



What next?
----------------------------
A quick RAML tutorial is here: http://raml.org/docs.html
You can try modifying RAML file manually and try generating the source as explained above.