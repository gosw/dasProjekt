package consumer;

import com.google.common.collect.ImmutableMap;
import converter.JsonConverter;
import data.Constants;

import database.DatabaseSender;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import messages.KafkaMessage;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nicob on 02.11.2016.
 * class for consuming kafka messages
 */

public class KafkaConsumer implements Runnable {
    //class attributes
    private static KafkaConsumer instance;
    private String topicName;
    private ConsumerConfig consumerConfig;

    private KafkaConsumer(String address, String topic) {
        Properties properties = new Properties();
        putProperties(properties, address);
        this.consumerConfig = new ConsumerConfig(properties);
        this.topicName = topic;
    }

    private void putProperties(Properties properties, String address) {
        properties.put(Constants.BOOTSTRAP_SERVERS, address);
        properties.put(Constants.CONNECT_ZOOKEEPER, address);
        properties.put(Constants.GROUP_ID, "xyz");
        properties.put(Constants.CLIENT_ID,this.getClass().getSimpleName());
        properties.put(Constants.KEY_DESERIALIZE, StringDeserializer.class.getName());
        properties.put(Constants.VALUE_DESERIALIZE, StringDeserializer.class.getName());
        properties.put(Constants.PARTITION, "range");
    }

    public static KafkaConsumer getKafkaConsumer(String address, String topic){
        if (instance == null) {
            instance = new KafkaConsumer(address, topic);
        }
        return instance;
    }

    public void run() {
        //System.out.println("Starting the KafkaConsumer...");

        ConsumerConnector connector = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> messages = connector.createMessageStreams(ImmutableMap.of(topicName, 1));
        List<KafkaStream<byte[], byte[]>> messageStreams = messages.get(topicName);
        ExecutorService executorService = Executors.newFixedThreadPool(messageStreams.size());

        for (final KafkaStream<byte[], byte[]> messageStream : messageStreams) {
            executorService.submit(() -> {
                for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : messageStream) {
                    String jsonString = new String(messageAndMetadata.message()); //change messageStream to json-String
                    KafkaMessage message = JsonConverter.getInstance().getKafkaMessage(jsonString);
                    message.setValue(message.getValue());
//                    DatabaseSender.getDatabaseSender().insertMessage(message);
                    System.out.println(message.toString());
//                    FiniteMachine.handleMessage(sm, message);
                }
            });
        }

    }
}