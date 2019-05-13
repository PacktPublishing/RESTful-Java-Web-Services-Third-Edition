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
This sample is inspired by oauth-client-twitter  example that you will find in Jersey official example: https://github.com/jersey/jersey
**

Code Samples for Chapter 6
==========================
This file contains instructions for running the sample project associated with Chapter 6 of RESTful Java Web Services.
This example teaches you Jersey client APIs for accessing OAuth 1.0 protected resource. The client used in this example invokes Twitter API which is protected using OAuth 1.0.


General instructions
--------------------
The examples used in this book are built using the following:

- Java SE Development Kit 8
- NetBeans IDE 8.2 
- Maven 3.2.3. 

Detailed instructions for setting up all the required tools for running the 
examples used in this book are discussed in Appendix section of this book
 
Prerequisites: Obtaining consumer key and consumer secret for accessing Twitter API 
-----------------------------------------------------------------------------------
This client exmaple access Twitter APIs.  You need to register your own application
with Twitter in order to obtain consumer key and consumer secret you have to use
to configure this client. You can do it as follows:
	
1.  Go to [Twitter Developers Page](http://dev.twitter.com/). You need
    to sign in to the Twitter (if you are new to Twitter you need to
    sign up). Then scroll down to the end of the page. You will see TOOLS | Manage Your Apps menu item at the end. Click "Manage Your Apps". Alternatively you directly navogate to https://apps.twitter.com/

2. In the Application Management page(https://apps.twitter.com), create a new application. 
	Fill out the "Create an application" form - you have to pick a
    unique application name (e.g. RestfulWebSample), enter some description,
    enter something for app website (e.g. http://jersey.java.net),
    Accept terms by selecting **Yes, I agree** in "Twitter Content"
    checkbox, enter captcha. finally click **Create your twitter application** button.

3.  You will be presented with consumer key, consumer secret and other
    details for your registered application. You will use these keys for configuring the client application as explained in next section.

If you still have doubts, se this video : https://www.youtube.com/watch?v=KDdNMrueGQs

How to run this example 
-------------------------
- 	Extract the zip file to your local file system. You may see **rest-chapter6-oauth1-client** folder in the extracted location.    
- 	Open the rest-chapter6-oauth1-client project in NetBeans IDE 8.0.2 or higher
- 	In the root folder, locate twitterclient.properties file, open it in any editor and 	overwrite the values for consumerKey and consumerSecret with values that is assigned to 	your application(See section: Obtaining consumer key and consumer secret for accessing 	Twitter API).
- 	Move back to NetBeans project, right click OAuth1TwitterClient and choose Run
- 	If you are running for first time, application will ask you to 
	"Enter the following URI into a web browser and authorize me:"
- 	Copy the URI and enter in to a browser. You will will be presented with access code.
- 	Enter the access code in console 
- 	The above action executes client, which call the OAuth 1 protected Twitter API: 
	https://api.twitter.com/1.1/statuses/home_timeline.json"
- 	You will see the result printed in NetBeans console
- 	Here is sample output:
	>> Enter the following URI into a web browser and authorize me:
	>> https://api.twitter.com/oauth/authorize?oauth_token=5B8p2gAAAAAAhAVnAAABTy1Wvvk
	>> Enter the authorization code: 7325483
	>> Tweets:

What next?
----------------------------
You can try other Twitter APIs by modifying the client. Alos try building a web client.