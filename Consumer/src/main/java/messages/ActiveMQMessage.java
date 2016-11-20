package messages;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by nicob on 02.11.2016.
 * message class for erp data from activemq
 */

public class ActiveMQMessage extends Message {
    @JsonProperty("customerNumber")
    private int customerNumber;
    @JsonProperty("materialNumber")
    private int materialNumber;
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("timeStamp")
    private Date timeStamp;


    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public void setMaterialNumber(int materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "AMQP-Message: " + "customerNumber: " + customerNumber + "; materialNumber: " + materialNumber +
               "; orderNumber: " + orderNumber + "; timeStamp: " + timeStamp;
    }
}