package sender;

import data.Constants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by nicob on 13.11.2016.
 * write messages to kafka
 */

public class KafkaProducer {
    private static KafkaProducer instance;
    private String topicName = Constants.PRODUCER_KAFKA_TOPIC;
    private Producer<String, String> producer;

    public static KafkaProducer getInstance() {
        if (instance == null) {
            instance = new KafkaProducer();
        }
        return instance;
    }

    private KafkaProducer() {
        Properties properties = new Properties();
        putProperties(properties);
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
    }

    private void putProperties(Properties properties) {
        String kafkaString = Constants.TESTING ? Constants.getServer() + ":" : "kafka:";
        properties.put("bootstrap.servers", kafkaString + Constants.KAFKA_PRODUCER_PORT);
//        properties.put("acks", "all");
//        properties.put("retries", 0);
//        properties.put("batch.size", 16384);
//        properties.put("linger.ms", 1);
//        properties.put("buffer.memory", 33554432);
        properties.put("metadata.broker.list", Constants.getServer() + ":" + Constants.KAFKA_PRODUCER_PORT);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    public void sendMessage(String data) {
        producer.send(new ProducerRecord<String, String>(topicName, data));
    }

}
