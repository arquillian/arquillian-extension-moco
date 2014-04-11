package org.jboss.arquillian.moco.client;

import java.util.Map;
import java.util.Properties;

public class MocoConfiguration {

    public static final String MOCO_PROPERTIES_FILENAME = "moco.properties";
    
    private static final int DEFAULT_PORT = 12306; 
    private static final String PORT_PROPERTY = "port";
    
    private int port;
    
    MocoConfiguration(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return port;
    }
    
    public String toProperties() {
        return PORT_PROPERTY + " = " + getPort();
    }
    
    public static MocoConfiguration fromProperties(Properties properties) {
        if(properties.containsKey(PORT_PROPERTY)) {
            return new MocoConfiguration(Integer.parseInt((String) properties.get(PORT_PROPERTY)));
        }
        
        return new MocoConfiguration(DEFAULT_PORT);
    }
    
    public static MocoConfiguration fromMap(Map<String, String> map) {
        
        if(map.containsKey(PORT_PROPERTY)) {
            return new MocoConfiguration(Integer.parseInt(map.get(PORT_PROPERTY)));
        }
        
        return new MocoConfiguration(DEFAULT_PORT);
        
    }
    
}
