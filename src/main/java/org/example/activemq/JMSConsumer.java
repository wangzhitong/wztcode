package org.example.activemq;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer {

    // ActiveMQ服务地址
//     public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
    // 连接本地Java用Broker创建的activemq实例
//    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    //消息队列名称
//    public static final String QUEUE_NAME = "queue01";

    // 使用nio协议
//    public static final String ACTIVEMQ_URL = "nio://192.168.52.134:61618";
    //消息队列名称
//    public static final String QUEUE_NAME = "transport";

    // 使用auto+nio协议
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61608";
    //消息队列名称
    public static final String QUEUE_NAME = "nio_auto_jdbc";

    public static void main(String[] args) throws JMSException, IOException {
        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        connection.start();
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
                System.out.println("**** 消费者接收到消息 ****："+ text);
            }else {
                break;
            }
        }

        /**
         * 方式二   通过监听的方式来获取消息
         *
         * 异步非阻塞方式（监听器 onMessage()）
         * 订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener) 注册一个消息监听器，
         * 当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。
         */
//        consumer.setMessageListener((message) -> {
//            if (null != message && message instanceof TextMessage){
//                TextMessage textMessage = (TextMessage) message;
//                try {
//                    String c01 = textMessage.getStringProperty("c01");
//                    System.out.println("**** 消费者接收到消息 ****："+ textMessage.getText());
//                    System.out.println("****消费者接收到消息属性 ****：" + c01);
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (null != message && message instanceof MapMessage){
//                MapMessage mapMessage = (MapMessage) message;
//                try {
//                    System.out.println("**** 消费者接收到消息 ****："+ mapMessage.getString("mapKey"));
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * 问题：
     *
     * 1.先生产消息，然后启动两个消费者，那个先启动，那个就消费消息，后启动的消费者不能消费。
     *
     * 2.先启动两个消费者，再生产消息，那么两个消费者会一人消费一半。
     */
}
