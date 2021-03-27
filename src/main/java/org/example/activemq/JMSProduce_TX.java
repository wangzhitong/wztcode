package org.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProduce_TX {

    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
    //消息队列名称
    public static final String QUEUE_NAME = "queue01";


    /**
     * 事务：
     *      在没有开启事务的情况下，生产者将消息发送到mq队列中，消费者接收到消息，消息就出队列结束了。
     *
     *      在开启事务的情况下，生产者调用send()方法，发送消息后，一定要commit()，否则消息不会进入到队列中，
     *      消费者开启事务后，接收到消息后也一定要执行commit，否则消息不会出队列，消费者可以一直接收到消息，
     *      会出现多次重复消费。
     *
     *  签收：（手动签收和自动签收用的最多）
     *      Session.AUTO_ACKNOWLEDGE 自动签收
     *      Session.CLIENT_ACKNOWLEDGE 手动签收
     *          手动签收，在收到消息后需要调用 textMessage.acknowledge(); 确认手动签收，
     *          否则会出现消息无法出队列，重复消费的问题。
     *      Session.DUPS_OK_ACKNOWLEDGE  可以允许部分重复签收
     *
     *
     *  签收和事务的关系：
     *      在事务性会话中，当一个事务被成功提交则消息被自动签收。如果事务回滚，则消息会被再次传送。
     *      非事务性会话中，消息何时被确认取决于创建会话时的应答模式（自动签收和手动签收）
     *
     * connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
     * 如果开启事务（true），默认就是自动签收，即使设置了手动签收，最终也会执行自动签收。只要最后执行commit就可以
     * 如果没有开启事务（false），设置了手动签收，就要在接收到消息后，执行textMessage.acknowledge(); 签收
     *
     */
    public static void main(String[] args) throws JMSException {
        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        connection.start();
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（具体是队列queue还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 设置消息持久化，如果不设置，默认是支持持久化的。DeliveryMode.NON_PERSISTENT 非持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //6.通过使用messageProducer生产3条消息，发送到MQ队列里面
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg_tx---" + i);
            // 7.1设置消息属性
            textMessage.setStringProperty("c01","val01");
            //8.通过messageProducer发送给mq
            producer.send(textMessage);
        }
        //9.关闭资源
        producer.close();
        session.commit(); //提交事务
        session.close();
        connection.close();
        System.out.println("****消息创建成功*****");
    }
}
