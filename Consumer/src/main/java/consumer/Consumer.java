package consumer;

import data.Constants;

/**
 * Created by nicob on 02.11.2016.
 * main class; starts consumer threads
 */

public class Consumer {

    private static String CURRENT_ORDER_NUMBER;

    public static void main(String[] args) {
        Thread amqpThread = new Thread(ActiveMQConsumer.getActiveMqConsumer(Constants.AMQP_TOPIC, Constants.AMQP_PORT));
        amqpThread.start();

        String kafkaServer = Constants.TESTING ? Constants.getServer() + ":" : "kafka:";
        Thread kafkaThread = new Thread(KafkaConsumer.getKafkaConsumer(kafkaServer + Constants.KAFKA_CONSUMER_PORT, Constants.KAFKA_TOPIC));
        kafkaThread.start();

        Thread directoryThread = new Thread(DirectoryListener.getDirectoryListener(Constants.FILE_PATH));
        directoryThread.start();
    }

    public static String getCURRENT_ORDER_NUMBER() {
        return CURRENT_ORDER_NUMBER;
    }

    public static void setCURRENT_ORDER_NUMBER(String curOrderNumber) {
        CURRENT_ORDER_NUMBER = curOrderNumber;
    }
}