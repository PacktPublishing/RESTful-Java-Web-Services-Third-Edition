Code Samples for Chapter 7
==========================
This file contains instructions for running the sample project associated with Chapter 7 of RESTful Java Web Services.

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
- Extract the zip file to your local file system. You may see **rest-chapter7-service-doctools** folder in the extracted location.
- This is a maven parent project and it has four sub projects
    - <rest-chapter7-jaxrs2wadl> : This project demonstrates how to generate WADL api documentation file from JAX-RS resource class.  To learn about this project, refer (rest-chapter7-jaxrs2wadl/README.txt)   
    - <rest-chapter7-jaxrs2raml> : This project demonstrates how to generate RAML api documentation file from JAX-RS resource class. To learn about this project, refer (rest-chapter7-jaxrs2raml/README.txt)      
    - <rest-chapter7-raml2jaxrs> : This project demonstrates how to generate JAX-RS resource class(templates) from a RAML api doc file. To learn about this project, refer (rest-chapter7-raml2jaxrs/README.txt)       
    - <rest-chapter7-jaxrs2swagger> : This project demonstrates how to generate Swagger api documentation from JAX-RS resource class.  To learn about this project, refer (rest-chapter7-jaxrs2swagger/README.txt)  
      
- Open the rest-chapter7-service-doctools project in NetBeans IDE 8.0.2 or higher

    - In main tool bar,choose File > Open Project > rest-chapter7-service-doctools
    - In the project pane, expand rest-chapter7-service-doctools project and double click desired project to open in the IDE
    - **You will find a README.txt file under each project listed below rest-chapter7-service-doctools. Please go through this file to know how to run the respective project.**    


What next?
----------------------------
This project will help you to get a real feel of various api documentation tools that you learned in chapter 7. You can explore each tool  at your own pace. Also try enhancing the samples by adding more features that you find interesting, even you can try out API doc tools that are not listed here as well(in fact there are many such tools available today)  !