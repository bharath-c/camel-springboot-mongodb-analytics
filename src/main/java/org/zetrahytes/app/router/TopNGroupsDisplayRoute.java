package org.zetrahytes.app.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Component
public class TopNGroupsDisplayRoute extends RouteBuilder {
    /*
     Aggregate query:
        db.getCollection('rsvps').aggregate([
            { $match : { response : "yes"} },
            { $group : { _id : "$group.group_name", count : { $sum : 1 } } },
            { $sort : {count: -1} },
            { $limit : 10 }
        ])
     */
    @Override
    public void configure() throws Exception {
        from("timer://rsvpsaggregator?fixedRate=true&period=10000")
            .setBody().constant("[{ $match : { response : \"yes\" } }, { $group : { _id : \"$group.group_name\", count : {$sum : 1}} }, { $sort : {count: -1} }, { $limit : 10 }]")
            .process(new Processor() { // transform the query to something MongoDB understands
                @Override
                public void process(Exchange exchange) throws Exception {
                    String payload = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody((DBObject) JSON.parse(payload));
                }
            })
            .to("mongodb:mongoClient?database=meetup&collection=rsvps&operation=aggregate")
            .process(new Processor() { // read the result from MongoDB
                @Override
                public void process(Exchange exchange) throws Exception {
                    BasicDBList payload = exchange.getIn().getBody(BasicDBList.class);
                    System.out.println("=====================");
                    for (Object obj : payload) {
                        System.out.println(obj.toString());
                    }
                    System.out.println("=====================");
                }
            });
    }
    
}
