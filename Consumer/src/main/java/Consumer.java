import consumer.ActiveMQConsumer;
import consumer.DirectoryListener;
import consumer.KafkaConsumer;

import data.Constants;

/**
 * Created by nicob on 02.11.2016.
 */

public class Consumer {
    public static void main(String[] args) {
        KafkaConsumer.getKafkaConsumer(Constants.getServer() + ":" + Constants.KAFKA_PORT, Constants.KAFKA_TOPIC).run();
        DirectoryListener.getDirectoryListener(Constants.FILE_PATH).run();
        //ActiveMQConsumer.getActiveMqConsumer(Constants.AMQP_TOPIC, Constants.AMQP_PORT).run();
    }
}