FROM ubuntu:16.04

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy simulation on docker and set permissions
COPY TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar /
RUN chmod u+x /TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# create directory for the temporary file
RUN mkdir -p /Data

ENTRYPOINT /entrypoint.sh