FROM tomcat:9.0-jdk21-temurin

RUN rm -rf /opt/tomcat/webapps/*

COPY target/addressbook.war /opt/tomcat/webapps/addressbook.war

EXPOSE 8080

