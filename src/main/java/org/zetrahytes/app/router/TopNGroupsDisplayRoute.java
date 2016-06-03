package org.zetrahytes.app.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;

@Component
public class TopNGroupsDisplayRoute extends RouteBuilder {
    /*
     Aggregate query:
        db.getCollection('rsvps').aggregate([
            { $match : { response : "yes"} }, // Not required, as we filter out declined invites in MeetupStreamRouter
            { $group : { _id : "$group.group_name", count : { $sum : 1 } } },
            { $sort : {count: -1} },
            { $limit : 10 }
        ])
     */
    String aggregateQuery = "[ { $group : { _id : \"$group.group_name\", count : {$sum : 1}} }, { $sort : {count: -1} }, { $limit : 10 } ]";
    
    @Override
    public void configure() throws Exception {
        from("timer://rsvpsaggregator?fixedRate=true&period=10000")
            .setBody().constant(aggregateQuery)
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
