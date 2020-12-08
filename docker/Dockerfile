# Alpine Linux with OpenJDK JRE
FROM openjdk:latest

# Copy war file
COPY events-portal-0.0.1-SNAPSHOT.jar /eventsportal.war

# run the app
CMD ["/usr/bin/java", "-jar", "/eventsportal.war"]