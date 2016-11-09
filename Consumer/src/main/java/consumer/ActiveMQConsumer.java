package consumer;

import converter.XmlConverter;
import data.Constants;

import javax.jms.*;

import messages.ActiveMQMessage;
import org.apache.activemq.*;

/**
 * Created by nicob on 02.11.2016.
 */

public class ActiveMQConsumer implements Runnable {
    Session session = null;
    Connection connection = null;
    String topicName = "";

    private static ActiveMQConsumer instance;

    private ActiveMQConsumer(String topicName, int port) {
        this.topicName = topicName;
        String amqpServer = "tcp://" + Constants.getServer() + ":" + port;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(amqpServer);
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static ActiveMQConsumer getActiveMqConsumer(String topicName, int port) {
        if (instance == null) {
            instance = new ActiveMQConsumer(topicName, port);
        }
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Bin im ActiveMQ consumer.Consumer");
        try {
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(topicName + "?consumer.dispatchAsync= false");

            MessageConsumer messageConsumer = session.createConsumer(destination);

            while (true) {
                messageConsumer.setMessageListener(message -> {
                    System.out.println("Bin im Message-Listener");
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = "";

                        try {
                            text = textMessage.getText();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }

                        ActiveMQMessage mqMessage = XmlConverter.getActiveMqMessage(text);
                        System.out.println(mqMessage);
                    } else {
                        System.out.println(message);
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Bin im ActiveMQ consumer.Consumer fertig");
    }
}