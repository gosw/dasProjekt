package messages;

/**
 * Created by nicob on 07.11.2016.
 * allows generic transfer for messages
 */

public class Message {
    private String orderNumber;

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}