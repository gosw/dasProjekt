# Pull base image
FROM ubuntu:16.04

# Install Java.
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean
	
WORKDIR \\Dockerdir	
	
#ADD Jar-File 
ADD TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar \\Dockerdir\\tmp\\TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar

#RUN Jar-File
RUN java -jar \\Dockerdir\\tmp\\TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar  -o \\Dockerdir\\tmp -d 1000 -amqp tcp://192.168.99.100:32768 -kafka 192.168.99.100:1000 -topic prodData
