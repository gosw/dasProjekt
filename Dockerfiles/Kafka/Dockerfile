FROM ubuntu:16.04

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# install kafka
ADD http://mirrors.ae-online.de/apache/kafka/0.10.0.1/kafka_2.11-0.10.0.1.tgz /kafka.tgz
RUN tar -xzf /kafka.tgz && mv /kafka_2.11-0.10.0.1 /kafka

# copy entrypoint script on docker and set permissions 
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# expose kafka port
EXPOSE 2181
EXPOSE 9092

ENTRYPOINT /entrypoint.sh