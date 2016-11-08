package data;

/**
 * Created by nicob on 02.11.2016.
 */

public class Constants {
    //Kafka-Port
    public static final int KAFKA_PORT = 1001;

    //Kafka-Topic
    public static final String KAFKA_TOPIC = "prodData";

    //Kafka-Attributes
    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static final String CONNECT_ZOOKEEPER = "zookeeper.connect";
    public static final String GROUP_ID = "group.id";
    public static final String CLIENT_ID = "client.id";
    public static final String KEY_DESERIALIZE = "key.deserializer";
    public static final String VALUE_DESERIALIZE = "value.deserializer";
    public static final String PARTITION = "partition.assignment.strategy";

    //Path to ERP file
    public static final String FILE_PATH = "C:\\Users\\nicob\\dockerDir";

    //ActiveMQ-Attributes
    public static final int AMQP_PORT = 32774;
    public static final String AMQP_TOPIC = "m_orders";

    //Mongo-DB
    public static final String MONGO_DB_ADDRESS = "localhost";
    public static final int MONGO_DB_PORT = 27017;
    public static final String MONGO_DB_DATABASE = "dummyDb";
    public static final String MONGO_DB_COLLECTION = "dummyColl";


    public static String getServer() {
        return (System.getProperty("os.name").toLowerCase().matches("(.*)windows(.*)"))
                ? "192.168.99.100" : "127.0.0.1";
    }
}