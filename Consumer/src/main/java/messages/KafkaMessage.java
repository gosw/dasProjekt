package messages;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by nicob on 02.11.2016.
 */

public class KafkaMessage implements Message {
    @JsonProperty("value")
    private Object value;
    private boolean booleanValue;
    private int intValue;
    private double doubleValue;
    @JsonProperty("status")
    private String status;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("timestamp")
    private long timeStamp;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;

        String stringValue = value.toString();
        if (stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("false")) {
            booleanValue = Boolean.valueOf(stringValue);
        }
        else if (stringValue.contains(".") || stringValue.contains(",")){
            doubleValue = Double.valueOf(stringValue);
        }
        else {
            intValue = Integer.valueOf(stringValue);
        }
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return "Kafka-Message: " + itemName + "; value: " + value + "; booleanValue: " + booleanValue + "; intValue: " + intValue +
                "; doubleValue: " + doubleValue + "; status: " + status + "; timeStamp: " + timeStamp;
    }
}