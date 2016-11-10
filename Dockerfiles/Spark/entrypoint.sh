!/bin/bash

# start spark
/spark/sbin/start-master.sh
/spark/sbin/start-slave.sh spark://spark:7077

# set spark memory
cp /spark/conf/spark-defaults.conf.template /spark/conf/spark-defaults.conf
echo "spark.driver.memory5g" >> /spark/conf/spark-defaults.conf

# run endless loop to keep alive
/usr/bin/tail -f /dev/null