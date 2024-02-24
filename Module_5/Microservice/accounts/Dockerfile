#start with a base imag containing java runtime
FROM openjdk:17-jdk-slim

#nformation around who maintains the image
MAINTAINER chaudhary.com

#add the applicaton's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#excute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]