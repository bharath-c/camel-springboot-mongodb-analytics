package org.zetrahytes.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MeetupStreamPersistenceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rabbitmq://localhost/meetupExchange?queue=rsvps")
            .convertBodyTo(String.class)
            .to("mongodb:mongoClient?database=meetup&collection=rsvps&operation=insert");
    }
    
}