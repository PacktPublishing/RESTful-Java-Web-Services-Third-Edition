Code Samples for Chapter 2
==========================
This file contains instructions for running the sample project associated with Chapter 2 of RESTful Java Web Services.

General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
- Maven 3.2.3. 

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix section of this book

Dependencies for this project
----------------------------
Dependencies for the using each library are listed in the following links:

- Java EE: https://javaee.github.io/
- Jackson: https://github.com/FasterXML/jackson-docs/wiki/Using-Jackson2-with-Maven	 
- Gson: https://sites.google.com/site/gson/gson-user-guide/using-gson-with-maven2

How to run this example ?
-------------------------
- Extract the zip file to your local file system. You may see **rest-chapter2-jsonp** folder in the extracted location.
- Open the rest-chapter2-jsonp project in NetBeans IDE 8.2 or higher
	- In main tool bar,choose File > Open Project > rest-chapter2-jsonp 	
- The source is structured in the following packages 
     - com.packtpub.rest.ch2.model : contains entity and helper class required for this project
    - com.packtpub.rest.ch2.jsonp : conatins JSR 353, JSR 367 & JSR 374 related JSON-P processing examples
    - com.packtpub.rest.ch2.jackson : Contains Jackson related examples
    - com.packtpub.rest.ch2.gson : Contains Gson related examples
	-- All the classes in the above mentioned packages are runnable (have main method) and you can try running each to exercise the APIs
    - <rest-chapter2-jsonp>/src/main/resources contains json files used in the examples 
    
- Once everything is setup properly, you can try running desired file from NetBeans IDE(Right click on the file > Run File)
 