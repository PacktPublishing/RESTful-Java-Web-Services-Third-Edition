Code Samples for Chapter 8
==========================
This file contains instructions for running the sample project associated with Chapter 8 of RESTful Java Web Services.

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
- Extract the zip file to your local file system. You may see **rest-chapter8-jaxrs** folder in the extracted location.
- This is a maven parent project and it has four sub projects
    - <rest-chapter8-service> : This project demonstrates custom ExceptionMapper, HTTP PATCH, SelectableEntityFilteringFeature. To learn about this project, refer (rest-chapter8-service/README.md)       
          
- Open the rest-chapter8-jaxrs project in NetBeans IDE 8.2 or higher

    - In main tool bar,choose File > Open Project > rest-chapter8-jaxrs
    - In the project pane, expand rest-chapter8-jaxrs project and double click desired project to open in the IDE(located inside the Modules element)
    - **You will find a README.md file under each project listed below rest-chapter8-jaxrs. Please go through this file to know how to run the respective project.**    


What next?
----------------------------
This project will help you to get a real feel of various best practices and design tips that we discussed in chapter8. You can explore each tip at your own pace. Also try enhancing the samples by adding more points that we discussed in chapter 8 