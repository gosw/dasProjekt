package converter;

import messages.ActiveMQMessage;
import messages.DirectoryMessage;
import messages.KafkaMessage;
import messages.Message;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by nicob on 02.11.2016.
 * maps json-strings to message objects and backwards
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DirectoryMessage getDirectoryMessage(String jsonString){
        try {
            return mapper.readValue(jsonString, DirectoryMessage.class);
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
