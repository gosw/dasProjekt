package consumer;

import data.Constants;

/**
 * Created by nicob on 02.11.2016.
 * main class; starts consumer threads
 */

public class Consumer {
    public static void main(String[] args) {
        Thread amqpThread = new Thread(ActiveMQConsumer.getActiveMqConsumer(Constants.AMQP_TOPIC, Constants.AMQP_PORT));
        amqpThread.start();

        Thread kafkaThread = new Thread(KafkaConsumer.getKafkaConsumer(Constants.getServer() + ":" + Constants.KAFKA_PORT, Constants.KAFKA_TOPIC));
        kafkaThread.start();

        Thread directoryThread = new Thread(DirectoryListener.getDirectoryListener(Constants.FILE_PATH));
        directoryThread.start();
    }
}