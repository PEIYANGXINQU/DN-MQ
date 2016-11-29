package com.dongnao.jack.topic.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class DNConsumerImpl implements DNConsumer, MessageListener {
    
    private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
    
    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    
    private static String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    
    ConnectionFactory connectionFactory;
    
    Connection connection;
    
    Session session;
    
    MessageConsumer messageConsumer;
    
    ThreadLocal<MessageConsumer> tl = new ThreadLocal<MessageConsumer>();
    
    public void onMessage(Message paramMessage) {
        
        TextMessage message = (TextMessage)paramMessage;
        
        try {
            System.out.println("我是消费者：我接收到的消息为：" + message.getText());
        }
        catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
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
            Topic topic = session.createTopic(disname);
            
            MessageConsumer messageConsumer = null;
            
            if (tl.get() == null) {
                messageConsumer = session.createConsumer(topic);
                messageConsumer.setMessageListener(new listener1());
                tl.set(messageConsumer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public class listener1 implements MessageListener {
        
        public void onMessage(Message paramMessage) {
            TextMessage message = (TextMessage)paramMessage;
            
            try {
                System.out.println("我是消费者：我接收到的消息为：" + message.getText());
            }
            catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public class listener2 implements MessageListener {
        
        public void onMessage(Message paramMessage) {
            TextMessage message = (TextMessage)paramMessage;
            
            try {
                System.out.println("我是消费者2：我接收到的消息为：" + message.getText());
            }
            catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
