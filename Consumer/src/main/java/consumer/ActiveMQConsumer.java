package consumer;

import converter.JsonConverter;
import data.Constants;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import messages.ActiveMQMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Created by nicob on 02.11.2016.
 */

public class ActiveMQConsumer implements Runnable {

    private Connection connection;
    private String topicName;

    private static ActiveMQConsumer instance;

    private ActiveMQConsumer(String topicName, int port){
        this.topicName = topicName;
        String amqpServer = "tcp://" + Constants.getServer() + ":" + port;
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory(amqpServer);

        try {
            connection = amqConnectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static ActiveMQConsumer getActiveMqConsumer(String topicName, int port){
        if (instance == null){
            instance = new ActiveMQConsumer(topicName, port);
        }
        return instance;
    }

    public void run() {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);
            MessageConsumer messageConsumer = session.createConsumer(destination);

            messageConsumer.setMessageListener(amqpMessage -> {
                if (amqpMessage instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) amqpMessage;
                    String text = "";

                    try {
                        text = textMessage.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                    //ActiveMQMessage message = JsonConverter.getInstance().getActiveMqMessage(text);
                    System.out.println(text);
                }
                else {
                    System.out.println("Message received: " + amqpMessage);
                }
            });

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}