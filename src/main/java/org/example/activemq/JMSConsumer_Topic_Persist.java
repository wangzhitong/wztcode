package org.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer_Topic_Persist {

    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
    //消息队列名称
    public static final String TOPIC_NAME = "topic-atguigu";

    /**
     * 一定要先运行一次消费者，等于向MQ注册，类似订阅了这个主题。
     * 然后运行生产者发送消息，无论消费者是否在线，都会接收到，不在线的话，
     * 下次在线的时候，会把之前没有接收到的消息全部接收。
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        connection.setClientID("...z3");
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建持久化的消息订阅者
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "remark");
        connection.start();
        Message message = subscriber.receive();
        while (null != message){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("**** 收到的持久化topic："+textMessage.getText());
            message = subscriber.receive();
        }

        subscriber.close();
        session.close();
        connection.close();
        System.out.println("****TOPIC_NAME 消息发布到MQ完成*****");
    }
}
