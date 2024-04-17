package com.pshah.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.pshah.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
Singleton
 */
public class ConfigManager {
    private static ConfigManager myconfigurationManager;
    private static Configuration myCurrentConfig;

    private ConfigManager() {
    }

    public static ConfigManager getInstance(){
        if(myconfigurationManager == null)
            myconfigurationManager = new ConfigManager();
        return myconfigurationManager;
    }
    /*
    Used to load a file by path provided
    throws ioException
     */
    public void loadConfigFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing config file, internal", e);
        }
        try {
            myCurrentConfig = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing config file, internal" , e);
        }

    }
    /*
    throws exception if no config loaded
     */
    public Configuration getCurrentConfiguration(){
        if (myCurrentConfig == null){
            throw new HttpConfigurationException("No current config set");
        }
        return myCurrentConfig;
    }
}

