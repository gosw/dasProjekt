FROM ubuntu:16.04

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# install spark
ADD http://www-eu.apache.org/dist/spark/spark-2.0.1/spark-2.0.1-bin-hadoop2.7.tgz /spark.tgz
RUN tar -xzf /spark.tgz && mv /spark-2.0.1-bin-hadoop2.7 /spark

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# expose spark
EXPOSE 8080
EXPOSE 7077

ENTRYPOINT /entrypoint.sh