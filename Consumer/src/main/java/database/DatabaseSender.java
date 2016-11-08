package database;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import converter.JsonConverter;
import data.Constants;
import messages.ActiveMQMessage;
import messages.Message;
import org.bson.Document;

/**
 * Created by nicob on 02.11.2016.
 */

public class DatabaseSender implements Runnable {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private static DatabaseSender instance;

    private DatabaseSender(){
        mongoClient = new MongoClient();
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
        Document document = Document.parse(jsonString);
        mongoDatabase.getCollection(Constants.MONGO_DB_COLLECTION).insertOne(document);
    }

    @Override
    public void run() {

    }
}