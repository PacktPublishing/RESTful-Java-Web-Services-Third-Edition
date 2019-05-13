<!--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.

    The contents of this file are subject to the terms of either the GNU
    General Public License Version 2 only ("GPL") or the Common Development
    and Distribution License("CDDL") (collectively, the "License").  You
    may not use this file except in compliance with the License.  You can
    obtain a copy of the License at
    http://glassfish.java.net/public/CDDL+GPL_1_1.html
    or packager/legal/LICENSE.txt.  See the License for the specific
    language governing permissions and limitations under the License.

    When distributing the software, include this License Header Notice in each
    file and include the License file at packager/legal/LICENSE.txt.

    GPL Classpath Exception:
    Oracle designates this particular file as subject to the "Classpath"
    exception as provided by Oracle in the GPL Version 2 section of the License
    file that accompanied this code.

    Modifications:
    If applicable, add the following below the License Header, with the fields
    enclosed by brackets [] replaced by your own identifying information:
    "Portions Copyright [year] [name of copyright owner]"

    Contributor(s):
    If you wish your version of this file to be governed by only the CDDL or
    only the GPL Version 2, indicate your decision by adding "[Contributor]
    elects to include this software in this distribution under the [CDDL or GPL
    Version 2] license."  If you don't indicate a single choice of license, a
    recipient has the option to distribute your version of this file under
    either the CDDL, the GPL Version 2 or to extend the choice of license to
    its licensees as provided above.  However, if you add GPL Version 2 code
    and therefore, elected the GPL Version 2 license, then the option applies
    only if the new code is made subject to such option by the copyright
    holder.

-->

**
This sample is inspired by oauth2-client-google-webapp example that you will find in Jersey official example: https://github.com/jersey/jersey
**

Code Samples for Chapter 6
==========================
This file contains instructions for running the sample project associated with Chapter 6 of RESTful Java Web Services.
This example demonstrates how to use  Jersey client APIs for accessing OAuth 2.0 protected resource. The client used in this example invokes Google Tasks API which is protected using OAuth 2.0.


General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
- Maven 3.2.3. 
- Jetty

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix section of this book
 
Prerequisites: Obtaining consumer key and consumer secret for accessing Google Tasks API 
-----------------------------------------------------------------------------------
This client exmaple access Google Tasks APIs.  You need to register your own application
with Google in order to obtain consumer key and consumer secret you have to use
to configure this client.
Detailed steps are here: https://developers.google.com/console/help/new
Aletrnatively you can watch this video : https://www.youtube.com/watch?v=Jg__b_WM5gg
Here is the summary of steps for your quick reference:


1.	You need to have Google account and register into the Google API console
<https://code.google.com/apis/console>. Register a new project.

2.	Under 'APIs and  auth' menu on left, Serach for 'Tasks API'. 
3.	Choose Tasks API and Enable it
4.	Clikc on Credential menu item on left side and choose Create New Client Id
5.	Choose Web Application as a type and define the redirect uri. This you can define under  	'Authorized RedirectsURIs'. As you will probably run
	the app on the localhost, the URI may look for example like the
	following:
	"http://localhost:28080/rest-chapter6-oauth2-webclient/api/oauth2/authorize".
	The URI must point to the OAuthFinalizerResource.authorize resource (so,
	it ends with /api/oauth2/authorize). If the URI is incorrectly entered
	into the API console, the authorization request will fail and the user
	will get an appropriate error message from Google (then go and fix the
	URI in the console).

6. Your new application will get and 'Client ID' and 'Client Secret'. You will need these
keys to setup the application on index.html.

Running the Example
-------------------
-	We will not use GlassFish for running this example as there is a weird bug associated with 	Jackson libraries. So we use Jetty for trying out this example.

	Run the example as follows:

	> mvn clean package jetty:run -Djetty.port=28080

	This deploys current example using Jetty.  
Follow the steps:

1.	Goto http://localhost:28080/rest-chapter6-oauth2-webclient/index.html
2.	Enter Client ID and Client Secret of your application and click OK
3.	This action will call /setup API (see OAuthInitializerResource class), which create ClientIdentifier using the client id and secret and saves it for later use. 
3.	Then you will be redirected to the Google Authorization URI. 
4.  Grant an access to your application. You will then be
	redirected back to "/api/oauth2/authorize" (see OAuthFinalizerResource class) and authorization process will be finished. Immediately, you will be redirected back to the
	"/api/tasks" (See GoogleTaskResource). This time, you will see the API response in JSON format.

What next?
----------------------------
You can try other Google APIs by modifying the client, Also beautify the page to display response in HTML form (instead of raw JSON content as is)