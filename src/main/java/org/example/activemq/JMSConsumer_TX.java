package org.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer_TX {

    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
    //消息队列名称
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        connection.start();
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（queue还是topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(queue);

        /**
         * 方式一
         *  同步阻塞方式receive()
         *  订阅者或者接收者调用 MessageConsumer的receive() 方法来接收消息，
         *  receive方法在能够接收到消息之前（或超时之前）将一直阻塞
         */
        while (true){
            TextMessage textMessage = (TextMessage) consumer.receive(4000L);
            if (textMessage != null){
                String text = textMessage.getText();
                System.out.println("**** 消费者接收到消息 ****："+ text + " properties " + textMessage.getStringProperty("c01"));
//                textMessage.acknowledge();
            }else {
                break;
            }
        }

        consumer.close();
        session.commit();
        session.close();
        connection.close();
    }

}
