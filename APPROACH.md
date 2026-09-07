(Also see [README.md](./README.md))

# Considerations/Approach

## Database
Having studied the application requirements before starting, I decided to make a Spring Boot API\
with a Postgres database at the backend. For this type of data, I think a NoSQL/document database may work\
better for the type of data and potential volume of sensor readings as opposed to rows in a relational database.\
For the sake of the proof of concept (POC) application, and my lack of experience in using NoSQL with Spring Boot, I decided to\
stick with Postgres for the implementation.

## Data Structure
After completing my implementation, looking at the response of the GET endpoint, I felt it could be normalised more,\
I was initially debating on making some of the response structure a Map rather than Lists, but my thought was that\
the user/calling service will only request what they want, as the query parameters are quite robust.\
See [alternate-normalised-structure.jsonc](alternate-normalised-structure.jsonc) for a proposed, more normalised structure.

The existing data structure is intentional in having a different list item for each stat returned, I originally thought I might\
return them all, and have nulls for the ones that weren't requested, but I think that is bad API design.

## Error handling
I have implemented just one exception handler in [RestResponseEntityExceptionHandler.java](src/main/java/com/weather/app/controller/advice/RestResponseEntityExceptionHandler.java).\
In terms of other error handling, I didn't want to assume any other requirements for this, and ultimately considered it out of scope.\
At first I was considering checking if sensors exist before we create a statistic under them, I decided this was out of scope and we\
didn't know enough about the application/environment to assume.

This app was said to be "a service that receives weather data from various sensors". Depending on whether this app sits right in front of the sensors,\
or if it is called down the line of microservices, the error handling may change in the app.

I have not included any error handling around the database/connection. In apps that call external services, error handling is a must there. But for\
cases like this, depending on the requirements, the database being down in this app, or having issues, means potentially the product\
itself is down and needs to be fixed critically (assuming this app is at the back of the whole system). I don't consider handling around this to be\
necessary for what I know from the requirements.

The same goes for other error handling I considered but did not implement, such as checking if sensors exist, check sensor is not a negative\
number etc. For what we know about the requirements and where this app sits in a system architecture, we don't know enough to say\
if another app would handle that, or if such bad data is even possible based on the sensors. I believe that error handling for the\
"sake of it" can get out of hand, and out of scope.

I did not implement any null checks, arithmetic exception handling on the querying side either. Assuming modern microservice architecture,\
this app should be the only gateway to the Postgres instance in this case. We are already validating the metrics coming in the POST request,\
so I don't think its necessary also to be handling this when you query it back.


## Incomplete / Won't Do (Productionize)

* I have intentionally not provided full test coverage as the requirements stated. I have tried to include enough testing to\
cover the different techniques for both unit and integration. I have omitted some tests that are more rudimentary and would\
not showcase anything more  in my opinion, for example, the stat calculation tests in [StatServiceImplTest.java](src/test/java/com/weather/app/service/impl/StatServiceImplTest.java).\
The //todos left in the tests were intentionally left in. I would also include at least one more integration test for the bad request on the GET endpoint.
* I have left the `ddl-auto: update` in [application.yaml](src/main/resources/application.yaml) as it is. The schema will be created when the application is started\
this is obviously bad practice / dangerous for a microservice, I intended to implement Liquibase migrations as an enhancement.\
The [DataConfig.java](src/main/java/com/weather/app/common/config/DataConfig.java) seeding is also only included for convenience’s sake.
* I have not implemented any containerization for the app, I consider this an enhancement for a proof of concept. Typically I would add\
the `jib-maven-plugin` instead of a Dockerfile
* I have not implemented logging outside of one in the data seeding. Typically I would use Slf4J with logback.