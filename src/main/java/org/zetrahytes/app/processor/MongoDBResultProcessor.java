package org.zetrahytes.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;

@Component
public class MongoDBResultProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        BasicDBList resultList = exchange.getIn().getBody(BasicDBList.class);
        resultList.forEach(System.out::println); // Java 8
        System.out.println("=====================");
    }

}
