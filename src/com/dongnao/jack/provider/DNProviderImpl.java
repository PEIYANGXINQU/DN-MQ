package com.dongnao.jack.provider;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class DNProviderImpl implements DNProvider {
    
    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    
    ConnectionFactory connectionFactory;
    
    Connection connection;
    
    Session session;
    
    ThreadLocal<MessageProducer> tl = new ThreadLocal<MessageProducer>();
    
    ThreadLocal<Integer> counttl = new ThreadLocal<Integer>();
    
    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,
                    PASSWORD, BROKEURL);
            
            connection = connectionFactory.createConnection();
            
            connection.start();
            
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String disname) {
        try {
            Queue queue = session.createQueue(disname);
            
            MessageProducer messageProducer = null;
            
            if (tl.get() != null) {
                messageProducer = tl.get();
            }
            else {
                messageProducer = session.createProducer(queue);
                tl.set(messageProducer);
            }
            
            while (true) {
                
                Integer count = counttl.get();
                
                if (count == null) {
                    count = 0;
                }
                
                count += 1;
                TextMessage tm = session.createTextMessage(Thread.currentThread()
                        .getName()
                        + "我是XX平台，我需要发送短信：短信content" + count);
                System.out.println("我是XX平台，我需要发送短信：短信content" + count);
                counttl.set(count);
                messageProducer.send(tm);
                
                session.commit();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
