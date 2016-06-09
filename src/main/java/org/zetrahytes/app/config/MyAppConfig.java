package org.zetrahytes.app.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class MyAppConfig {
    
    @Value("${mongodb.host}")
    private String mongoDbHost;
    
    @Value("${mongodb.port}")
    private int mongoDbPort;
    
    @Bean
    MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(mongoDbHost, mongoDbPort);
    }
}
