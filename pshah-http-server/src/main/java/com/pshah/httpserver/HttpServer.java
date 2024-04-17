package com.pshah.httpserver;

import com.pshah.httpserver.config.ConfigManager;
import com.pshah.httpserver.config.Configuration;
import com.pshah.httpserver.core.ServerListenerThread;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.sun.org.slf4j.internal.Logger;

import java.io.IOException;

/*
 * Driver class
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main (String[] args) {
        LOGGER.info("Starting Server ...");

        ConfigManager.getInstance().loadConfigFile("src/main/resources/http.json");
        Configuration conf = ConfigManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port:" + conf.getPort());
        System.out.println("Using Webroot:" + conf.getWebroot());
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e){
            e.printStackTrace();
            //TODO: handle later
        }
    }
}
