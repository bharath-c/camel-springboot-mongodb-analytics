package org.zetrahytes.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zetrahytes.app.processor.MongoDBResultProcessor;

/*
Aggregate query:
   db.getCollection('rsvps').aggregate([
       { $match : { response : "yes"} }, // Not required, as we filter out declined invites in MeetupStreamRouter
       { $group : { _id : "$group.group_name", count : { $sum : 1 } } },
       { $sort : {count: -1} },
       { $limit : 10 }
   ])
*/

@Component
public class TopNGroupsDisplayRoute extends RouteBuilder {
    
    @Autowired
    private MongoDBResultProcessor mongoDBResultProcessor;
    private static final String AGGREGATE_QUERY = "[ { $group : { _id : \"$group.group_name\", count : {$sum : 1}} }, { $sort : {count: -1} }, { $limit : 10 } ]";
    
    @Override
    public void configure() throws Exception {
        from("timer://rsvpsaggregator?fixedRate=true&period=10000")
            .setBody().constant(AGGREGATE_QUERY)
            .to("mongodb:mongoClient?database=meetup&collection=rsvps&operation=aggregate")
            .process(mongoDBResultProcessor);
    }
    
}
