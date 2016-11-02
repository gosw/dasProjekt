#!/bin/bash

# start kafka
/kafka/bin/zookeeper-server-start.sh /kafka/config/zookeeper.properties &
/kafka/bin/kafka-server-start.sh /kafka/config/server.properties &

# run endless loop to keep alive
/usr/bin/tail -f /dev/null