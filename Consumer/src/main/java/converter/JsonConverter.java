package converter;

import messages.ActiveMQMessage;
import messages.DirectoryMessage;
import messages.KafkaMessage;
import messages.Message;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by nicob on 02.11.2016.
 */

public class JsonConverter {
    private static JsonConverter ourInstance = new JsonConverter();

    private ObjectMapper mapper = new ObjectMapper();

    public static JsonConverter getInstance() {
        return ourInstance;
    }

    private JsonConverter() {
    }

    public KafkaMessage getKafkaMessage(String jsonString){
        try {
            return mapper.readValue(jsonString, KafkaMessage.class);
        } catch (JsonMappingException jsonMapEx){
            jsonMapEx.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActiveMQMessage getActiveMqMessage(String jsonString){
        try {
            return mapper.readValue(jsonString, ActiveMQMessage.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DirectoryMessage getDirectoryMessage(String jsonString){
        try {
            return mapper.readValue(jsonString, DirectoryMessage.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJsonString(Message message){
        try {
            return mapper.writeValueAsString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
