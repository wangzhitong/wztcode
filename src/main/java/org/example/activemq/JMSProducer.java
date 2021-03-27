package org.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * JMS 规范
 *      JMS中的角色
 *      JMS Provider：实现JMS接口和规范的消息中间件，即MQ服务器
 *      JMS Producer：消息生产者，创建和发送JMS消息的客户端应用
 *      JMS Consumer：消息消费者，接受和处理JMS消息的客户端应用
 *      JMS Message：消息
 *
 *   JMS Message结构
 *      1.消息头
 *          1.JMS Destination：消息发送的目的地，主要是指Queue和Topic
 *          2.JMS Delivery Mode：消息持久化和非持久化模式
 *              持久性的消息：应该被传送“一次仅仅一次”，这就意味着如果JMS提供者出现故障，该消息并不会丢失，
 *              它会在服务器恢复之后再次传递。非持久的消息：最多会传递一次，这意味着服务器出现故障，该消息将会永远丢失.
 *          3.JMS Expiration：消息过期时间
 *              可以设置消息在一定时间后过期，默认是永不过期。消息过期时间，等于Destination的send方法
 *              中的timeToLive值加上发送时刻的GMT时间值。如果timeToLive值等于0，则JMSExpiration被设为0，表示该消息永不过期。
 *              如果发送后，在消息过期时间之后还没有被发送到目的地，则该消息被清除。
 *          4.JMS Priority：消息优先级
 *               消息优先级，从0-9十个级别，0-4是普通消息5-9是加急消息。JMS不要求MQ严格按照这十个优先级发送消息但必
 *               须保证加急消息要先于普通消息到达。默认是4级。（比如优先级9加急消息不一定要早于优先级8的加急消息先发送，
 *               但是这两条加急消息一定比优先级4普通消息先发送。）
 *          5.JMS MessageID：消息唯一标识，由MQ产生
 *      2.消息体
 *          封装具体的消息数据，一共有5中消息体格式。发送和接受的消息体类型必须一致对应。
 *          5种消息体格式： TextMessage    普通字符串消息，包含一个String
 *                         MapMessage     一个Map类型的消息，key为String类型，值为java的基本类型
 *                         ByteMessage    二进制数组消息，包含一个byte[]
 *                         StreamMessage  Java数据流消息，用标准流操作来顺序的填充和读取
 *                         ObjectMessage  对象消息，包含一个可序列化的Java对象
 *      3.消息属性
 *          对消息的一种描述，达到识别、去重、重点标注等操作。
 *          textMessage.setStringProperty("c01","val01");消息生产者可以给消息设置自定义的属性，
 *          在消息接收方可以取到设置的消息属性textMessage.getStringProperty("c01");，做一些判断或其他处理
 *
 *    JMS的可靠性：
 *          持久性 producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 *          事务  connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
 *          签收  connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
 */
public class JMSProducer {

    // ActiveMQ服务地址
//    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61616";
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


    public static void main(String[] args) throws JMSException {
        //1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = mqConnectionFactory.createConnection();
        connection.start();
        //3.创建session回话
        //两个参数，一个是事务，一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
            // 设置消息持久化，如果不设置，默认是支持持久化的。DeliveryMode.NON_PERSISTENT 非持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //6.通过使用messageProducer生产3条消息，发送到MQ队列里面
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("jdbc_msg---" + i);
                // 7.1设置消息属性
            textMessage.setStringProperty("c01","val01");
            //8.通过messageProducer发送给mq
            producer.send(textMessage);
                //创建map类型消息
//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setString("mapKey","mapVal");
//            producer.send(mapMessage);
        }
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("****消息创建成功*****");
    }

}
