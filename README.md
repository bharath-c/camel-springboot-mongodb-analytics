# camel-springboot-sample
Simple analytics app using Apache Camel, Spring Boot and MongoDB

This app has three camel routes:

**MeetupStreamRouter:**

+ Listens for JSON notifications from Meetup's Websocket-based RSVP stream using Camel's AHC-WS (Async Http Client Websocket Client) component.
+ Filters out notifications based on invite response using jsonpath (Only the accepted invites move beyond this step)
+ Sends it to RabbitMQ exchange

**MeetupStreamPersistenceRoute:**
+ Listens for new notifications from RabbitMQ
+ Saves the json in MongoDB

**TopNGroupsDisplayRoute:**
+ Using an aggregate query, this route queries MongoDB every 10 secs for the top 10 Meetup groups based on the number of accepted invites and displays it
