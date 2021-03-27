package org.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProduce_Topic_Persist {

    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
    //消息队列名称
    public static final String TOPIC_NAME = "topic-atguigu";

    public static void main(String[] args) throws JMSException {
        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);  //设置持久化
        connection.start();
        //6.通过使用messageProducer生产3条消息，发送到MQ队列里面
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg_persist---" + i);
            //8.通过messageProducer发送给mq
            producer.send(textMessage);
        }
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("****TOPIC_NAME 消息发布到MQ完成*****");
    }
}
