package com.dongnao.jack.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class DNConsumerImpl implements DNConsumer {
    
    private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
    
    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    
    private static String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    
    ConnectionFactory connectionFactory;
    
    Connection connection;
    
    Session session;
    
    ThreadLocal<MessageConsumer> tl = new ThreadLocal<MessageConsumer>();
    
    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,
                    PASSWORD, URL);
            
            connection = connectionFactory.createConnection();
            
            connection.start();
            
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getMessage(String disname) {
        try {
            Queue queue = session.createQueue(disname);
            
            MessageConsumer messageConsumer = null;
            
            if (tl.get() != null) {
                messageConsumer = tl.get();
            }
            else {
                messageConsumer = session.createConsumer(queue);
                tl.set(messageConsumer);
            }
            
            while (true) {
                TextMessage message = (TextMessage)messageConsumer.receive(1000);
                
                if (message != null) {
                    System.out.println("我是消费者：我接收到的消息为：" + message.getText());
                }
                else {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
