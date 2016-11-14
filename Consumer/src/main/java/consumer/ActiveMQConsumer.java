package consumer;

import converter.XmlConverter;
import data.Constants;

import javax.jms.*;

import messages.ActiveMQMessage;
import org.apache.activemq.*;
import sender.DatabaseSender;

/**
 * Created by nicob on 02.11.2016.
 * consumer for activemq messages
 */

public class ActiveMQConsumer implements Runnable {
    Session session;
    Connection connection;
    String topicName;

    private static ActiveMQConsumer instance;


    private ActiveMQConsumer(String topicName, int port) {
        this.topicName = topicName;
        String amqpServer = Constants.TESTING ? "tcp://" + Constants.getServer() + ":" + port :
                                "failover:tcp://activemq:" + port;
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
        try {
            //start connection
            connection.start();

            //create a session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //determine destination
            Destination destination = session.createTopic(topicName + "?consumer.dispatchAsync= false");

            //create consumer
            MessageConsumer messageConsumer = session.createConsumer(destination);

            //receive and convert message
            while (true) {
                messageConsumer.setMessageListener(message -> {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = "";

                        try {
                            text = textMessage.getText();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }

                        ActiveMQMessage mqMessage = XmlConverter.getInstance().getActiveMqMessage(text);
                        Consumer.setCURRENT_ORDER_NUMBER(mqMessage.getOrderNumber());
                        if (!Constants.TESTING) {
                            DatabaseSender.getDatabaseSender().insertMessage(mqMessage);
                        }
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
    }
}