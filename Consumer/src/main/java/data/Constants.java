package data;

/**
 * Created by nicob on 02.11.2016.
 * collection of constant values
 */

public class Constants {

    //kafka attributes
    public static final int KAFKA_PORT = 1001;
    public static final String KAFKA_TOPIC = "prodData";
    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static final String CONNECT_ZOOKEEPER = "zookeeper.connect";
    public static final String GROUP_ID = "group.id";
    public static final String CLIENT_ID = "client.id";
    public static final String KEY_DESERIALIZE = "key.deserializer";
    public static final String VALUE_DESERIALIZE = "value.deserializer";
    public static final String PARTITION = "partition.assignment.strategy";

    //path to erp file
    public static final String FILE_PATH = "C:\\Users\\nicob\\dockerDir";

    //activemq attributes
    public static final int AMQP_PORT = 32774;
    public static final String AMQP_TOPIC = "m_orders";

    //mongo-DB attributes
    public static final String MONGO_DB_ADDRESS = "localhost";
    public static final int MONGO_DB_PORT = 3001;
    public static final String MONGO_DB_DATABASE = "dummyDb";
    public static final String MONGO_DB_COLLECTION_AMQP = "amqp_collection";
    public static final String MONGO_DB_COLLECTION_DIR = "dir_collection";
    public static final String MONGO_DB_COLLECTION_KAFKA = "kafka_collection";

    /**
     * checks the os and determines server address
     */
    public static String getServer() {
        return (System.getProperty("os.name").toLowerCase().matches("(.*)windows(.*)"))
                ? "192.168.99.100" : "127.0.0.1";
    }
}