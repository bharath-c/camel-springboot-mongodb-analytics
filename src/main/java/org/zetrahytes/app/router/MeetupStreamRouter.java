package org.zetrahytes.app.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MeetupStreamRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("ahc-ws://stream.meetup.com/2/rsvps")
            .to("rabbitmq://localhost/meetupExchange?queue=rsvps");
    }

}
