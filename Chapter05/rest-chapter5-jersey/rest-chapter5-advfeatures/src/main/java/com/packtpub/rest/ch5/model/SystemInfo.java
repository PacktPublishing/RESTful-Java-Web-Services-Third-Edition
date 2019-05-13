/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.model;

/**
 * This a demo resource to show case ResourceConfig features to add resources 
 * during deployment
 * In real life, populate attributes with real data.
 *
 * @author Jobinesh
 */
public class SystemInfo {

    private String hostName;
    private String processor;
    private String serverName;
    private int userSessions;
    private int threads;
    SystemInfo SystemInfo = null;

    private SystemInfo() {
        initWithDummyValues();
    }

    public static SystemInfo getInstance() {
        return new SystemInfo();
    }

    private void initWithDummyValues() {
        hostName = "localhost";
        processor = "Intel";
        serverName = "XXX";
        userSessions = 123233;
        threads = 334235235;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(int userSessions) {
        this.userSessions = userSessions;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

}
