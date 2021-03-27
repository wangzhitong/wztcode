package org.example.activemq;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {

    /**
     * 用创建一个activemq的实例，相当于一个微服务，我们不需要再到liunx上安装mq。
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //创建Broker实例
        BrokerService brokerService = new BrokerService();
        brokerService.setPopulateJMSXUserID(true);
        //绑定地址
        brokerService.addConnector("tcp://localhost:61616");
        //启动服务
        brokerService.start();
    }
}
