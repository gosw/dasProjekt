!/bin/bash

# start simulation
java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -o /Data -amqp tcp://activemq:61616 -d 5000 -kafka kafka:9092 -topic prodData &

# start consumer
#java -jar consumer.jar &

# run endless loop to keep alive
/usr/bin/tail -f /dev/null