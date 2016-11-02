#!/bin/bash

# start spark
/spark/sbin/start-master.sh
/spark/sbin/start-slave.sh spark://spark:7077

# run endless loop to keep alive
/usr/bin/tail -f /dev/null