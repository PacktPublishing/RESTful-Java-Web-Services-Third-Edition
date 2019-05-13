/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.oauth1.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * This class demonstrates using OAuth1.0 for consuming Twitter API
 * 
 * @author Jobinesh
 */
public class OAuth1TwitterClient {

    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
    private static final String FRIENDS_TIMELINE_URI = "https://api.twitter.com/1.1/statuses/home_timeline.json";
    //private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE_NAME = "twitterclient.properties";
    private static final String PROPERTY_ACCESS_TOKEN = "accesstoken";
    private static final String PROPERTY_ACCESS_TOKEN_SECRET = "acesstokenSecret";
    private static final String PROPERTY_CONSUMER_KEY = "consumerKey";
    private static final String PROPERTY_CONSUMER_SECRET = "consumerSecret";
    private Properties clientCredetnials;

    public String readTweets(String twitterAPI) {
        // retrieve client token and secret from the property file
        clientCredetnials = loadClientPorperties(PROPERTIES_FILE_NAME);
        ConsumerCredentials consumerCredentials = new ConsumerCredentials(clientCredetnials.getProperty(PROPERTY_CONSUMER_KEY),
                clientCredetnials.getProperty(PROPERTY_CONSUMER_SECRET));
        Feature filterFeature;
        if (clientCredetnials.getProperty(PROPERTY_ACCESS_TOKEN) == null) {
            // To get Access Token perfom the Authorization Flow first,
            // let the user approve our app and get Access Token.
            final OAuth1AuthorizationFlow authorizationFlow = OAuth1ClientSupport.builder(consumerCredentials)
                    .authorizationFlow(
                            "https://api.twitter.com/oauth/request_token",
                            "https://api.twitter.com/oauth/access_token",
                            "https://api.twitter.com/oauth/authorize")
                    .build();
            //This demo asks user to do the following steps manually.
            //Real life web app will automate this tasks
            final String authorizationUri = authorizationFlow.start();
            System.out.println("Enter the following URI into a web browser and authorize me:");
            System.out.println(authorizationUri);
            System.out.print("Enter the authorization code: ");
            final String verifier;
            try {
                verifier = IN.readLine();
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
            final AccessToken accessToken = authorizationFlow.finish(verifier);

            // store access token for next application execution
            clientCredetnials.setProperty(PROPERTY_ACCESS_TOKEN, accessToken.getToken());
            clientCredetnials.setProperty(PROPERTY_ACCESS_TOKEN_SECRET, accessToken.getAccessTokenSecret());

            // get the filter feature that will configure the client with consumer credentials and
            // received access token. This will be used with client to call API
            filterFeature = authorizationFlow.getOAuth1Feature();
        } else {
            //Access tokens are already available from last execution
            final AccessToken storedToken = new AccessToken(clientCredetnials.getProperty(PROPERTY_ACCESS_TOKEN),
                    clientCredetnials.getProperty(PROPERTY_ACCESS_TOKEN_SECRET));
            // build a new filter feature from the stored consumer credentials and access token
            filterFeature = OAuth1ClientSupport.builder(consumerCredentials).feature()
                    .accessToken(storedToken).build();
        } 
        // create a new Jersey client and register filter feature that will add OAuth signatures and
        // JacksonFeature that will process returned JSON data.
        final Client client = ClientBuilder.newBuilder()
                .register(filterFeature)
                .register(JacksonFeature.class)
                .build();

        // make requests to protected resources
        // (no need to care about the OAuth signatures)
        final Response response = client.target(twitterAPI).request().get();
        if (response.getStatus() != 200) {
            String errorEntity = null;
            if (response.hasEntity()) {
                errorEntity = response.readEntity(String.class);
            }
            throw new RuntimeException("Request to Twitter was not successful. Response code: "
                    + response.getStatus() + ", reason: " + response.getStatusInfo().getReasonPhrase()
                    + ", entity: " + errorEntity);
        }

        // persist the current consumer key/secret and token/secret for future use
        storeSettings();

        final String tweets = response.readEntity(String.class);

        System.out.println("Tweets:\n");
        System.out.println(tweets);
        return tweets;
    }

    /**
     * Main method that demos OAuth 1 to read twitter API.
     * <p/>
     * Execute this method to demo
     *
     * @param args Command line arguments.
     * @throws Exception Thrown when error occurs.
     */
    public static void main(final String[] args) throws Exception {

        String tweets = new OAuth1TwitterClient().readTweets(FRIENDS_TIMELINE_URI);
        System.out.println(tweets);

    }

    private Properties loadClientPorperties(String fileName) {
        Properties properties = new Properties();
        FileInputStream st = null;
        try {
            st = new FileInputStream(fileName);
            properties.load(st);
        } catch (final IOException e) {
            // ignore
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (final IOException ex) {
                    // ignore
                }
            }
        }

        for (final String name : new String[]{PROPERTY_CONSUMER_KEY, PROPERTY_CONSUMER_SECRET,
            PROPERTY_ACCESS_TOKEN, PROPERTY_ACCESS_TOKEN_SECRET}) {
            final String value = System.getProperty(name);
            if (value != null) {
                properties.setProperty(name, value);
            }
        }

        if (properties.getProperty(PROPERTY_CONSUMER_KEY) == null
                || properties.getProperty(PROPERTY_CONSUMER_SECRET) == null) {
            System.out.println("No consumerKey and/or consumerSecret found in twitterclient.properties file. "
                    + "You have to provide these as properties. See README.md for more information.");
            System.exit(1);
        }
        return properties;
    }

    private void storeSettings() {
        FileOutputStream st = null;
        try {
            st = new FileOutputStream(PROPERTIES_FILE_NAME);
            clientCredetnials.store(st, null);
        } catch (final IOException e) {
            // ignore
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (final IOException ex) {
                // ignore
            }
        }
    }

}
