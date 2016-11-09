package database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import converter.JsonConverter;
import data.Constants;
import messages.ActiveMQMessage;
import messages.DirectoryMessage;
import messages.KafkaMessage;
import messages.Message;
import org.bson.Document;

/**
 * Created by nicob on 02.11.2016.
 * sends data to the mongo-DB
 */

public class DatabaseSender {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private static DatabaseSender instance;

    private DatabaseSender(){
        mongoClient = new MongoClient(Constants.MONGO_DB_ADDRESS, Constants.MONGO_DB_PORT);
        mongoDatabase = mongoClient.getDatabase(Constants.MONGO_DB_DATABASE);
    }

    public static DatabaseSender getDatabaseSender(){
        if (instance == null){
            instance = new DatabaseSender();
        }
        return instance;
    }

    public void insertMessage(Message message){
        String jsonString = JsonConverter.getInstance().toJsonString(message);
        //System.out.println(jsonString);
        Document document = Document.parse(jsonString);

        if (message instanceof ActiveMQMessage) {
            mongoDatabase.getCollection(Constants.MONGO_DB_COLLECTION_AMQP).insertOne(document);
        } else if (message instanceof DirectoryMessage) {
            mongoDatabase.getCollection(Constants.MONGO_DB_COLLECTION_DIR).insertOne(document);
        } else if (message instanceof KafkaMessage) {
            mongoDatabase.getCollection(Constants.MONGO_DB_COLLECTION_KAFKA).insertOne(document);
        }
    }
}