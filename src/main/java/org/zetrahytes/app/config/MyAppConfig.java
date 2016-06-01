package org.zetrahytes.app.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class MyAppConfig {
    
    @Bean
    MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient("localhost", 27017);
    }
}
