package org.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer_topic {


    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
    // 主题名称，取消息必须和存消息的主题名称一致
    public static final String TOPIC_NAME = "topic-atguigu";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是二号消费者");

        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        connection.start();
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（queue还是topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(topic);

        //方式二   通过监听的方式来获取消息
        /**
         * 异步非阻塞方式（监听器 onMessage()）
         * 订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener) 注册一个消息监听器，
         * 当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。
         */
        consumer.setMessageListener((message) -> {
            if (null != message && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("**** 消费者接收到消息 ****："+ textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * queue 和 topic 对比
     *
     * 工作模式（queue）："负载均衡"模式，如果当前没有消费者，消息也不会丢弃；如果有多个消费者，
     *                    那么一条消息也只会发送给其中一个消费者，并且要求消费者ack信息
     * 工作模式（topic）："订阅-发布"模式，如果当前没有订阅者，消息将会被丢弃，如果有多个订阅者，
     *                    那么这些订阅者都会收到消息
     *
     * 有无状态（queue）：Queue数据默认会在mq服务器上已文件形式保存，比如Active MQ一般保存在$AMQ_HOME\data\kr-store\data下面，
     *                   也可以配置成DB存储
     * 有无状态（topic）：无状态
     *
     *
     * 传递完整性（queue）：消息不会被丢弃
     * 传递完整性（topic）：如果没有订阅者，消息会被丢弃
     *
     *
     * 处理效率（queue）：由于一条消息只发送给一个消费者，所以就算消费者再多，性能也不会有明显降低。
     *                   当然不同消息协议的具体性能也是有差异的
     * 处理效率（topic）：由于消息要按照订阅者的数量进行复制，所以处理性能会随着订阅者的增加而明显降低，
     *                   并且还要结合不同消息协议自身的性能差异
     */
}
