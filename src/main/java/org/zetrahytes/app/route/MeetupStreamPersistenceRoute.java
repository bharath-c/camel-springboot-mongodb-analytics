package org.zetrahytes.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MeetupStreamPersistenceRoute extends RouteBuilder {
    
    /* 
     * Meetup RSVPs are read from RabbitMQ and forwarded to both MongoDB and Elasticsearch. 
     * Why both? Just trying out to see if Kibana dashboard can be built using the data indexed by Elasticsearch
     * 
     * camel-elasticsearch: Configured as a transport client that connects to a locally running elasticsearch
     * cluster named 'my-elasticsearch-cluster' using the transport ip (127.0.0.1) and port (9300).
     * 9300 is the default port, so it's skipped in the URI.
     * (See https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/transport-client.html)
     */
    @Override
    public void configure() throws Exception {
        from("rabbitmq://localhost/meetupExchange?queue=rsvps")
            .convertBodyTo(String.class)
            .multicast().to(
                "mongodb:mongoClient?database=meetup&collection=rsvps&operation=insert",
                "elasticsearch://my-elasticsearch-cluster?operation=INDEX&indexName=meetup&indexType=rsvps&ip=127.0.0.1"
             );
    }
    
}