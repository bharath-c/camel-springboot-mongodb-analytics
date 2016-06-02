package org.zetrahytes.app.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MeetupStreamRouter extends RouteBuilder {
    
    // an example for Message Filter pattern (See http://camel.apache.org/message-filter.html)
    @Override
    public void configure() throws Exception {
        from("ahc-ws://stream.meetup.com/2/rsvps")
            .filter().jsonpath("$[?(@.response == 'yes')]") // only accepted invites for now
            .to("rabbitmq://localhost/meetupExchange?queue=rsvps");
    }

}
