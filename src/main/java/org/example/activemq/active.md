#1.JMS 消息规范
    ##JMS的可靠性
    sad 
    as 
##### 持久性 ： 包括持久的Queue和持久的Topic。
##### Transaction（事务性）：

##### 持久性

> 区块
> sad
> > asd

* 列表
    * 列表
        *  阿萨德 sad
            ads asd asd 
            
+ 阿萨德
#2.ActiveMQ 传输协议
##2.1协议种类
*   TCP
```text
TCP是Broker默认配置的协议，默认监听端口是61616。
在网络传输数据前，必须要先序列化数据，消息是通过一个叫wire protocol的来序列化成字节流。默认情况下，ActiveMQ把wire protocol叫做OpenWire，它的目的是促使网络上的效率和数据快速交互。
TCP连接的URI形式如：tcp://HostName:port?key=value&key=value，后面的参数是可选的。
TCP传输的的优点：
    TCP协议传输可靠性高，稳定性强
    高效率：字节流方式传递，效率很高
    有效性、可用性：应用广泛，支持任何平台
```
*   NIO
```text
NIO协议和TCP协议类似，但NIO更侧重于底层的访问操作。它允许开发人员对同一资源可有更多的client调用和服务器端有更多的负载。
一般情况下，大量的Client去连接Broker是被操作系统的线程所限制的。因此，NIO的实现比TCP需要更少的线程去运行，所以建议使用NIO协议。
NIO连接的URI形式：nio://hostname:port?key=value&key=value
```
*   AMQP （了解）
```text
一个提供统一消息服务的应用层标准高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。基于此协议的客户端与消息中间件可传递消息，
并不受客户端/中间件不同产品，不同开发语言等条件限制。
```
*   STOMP（了解）
```text
STOMP是流文本定向消息协议，是一种为MOM(Message Oriented Middleware，面向消息中间件)设计的简单文本协议。
```
*   SSL（了解）
```text
SSL传输允许客户端使用TCP上的SSL连接到远程ActiveMQ代理。SSL传输允许客户端使用TCP套接字上的SSL连接到远程ActiveMQ代理。
连接的URL形式： ssl://hostname:port?key=value
```
*   MQTT（了解）
```text
MQTT，即消息队列遥测传输，是IBM开发的一个即时通讯协议，有可能成为物联网的重要组成部分。该协议支持所有平台，几乎可以把所有联网物品和外部连接起来，被用来当作传感器和致动器(比如通过Twitter让房屋联网)的通信协议。
```
*   VM（了解）
```text
VM本身不是协议。VM传输允许客户机在VM内部彼此连接，而不需要网络通信的开销。所使用的连接不是套接字连接，而是使用直接方法调用来启用高性能嵌入式消息传递系统。
第一个使用VM连接的客户机将引导一个嵌入式代理。后续的连接将连接到同一代理。一旦所有到代理的VM连接都关闭了，嵌入式代理将自动关闭。
```

##配置
*   打开activemq安装目录的conf/activemq.xml，找到transportConnectors标签，内容如下
```xml
<transportConnectors>
	<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
    <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
由配置可知MQ的各种协议URL配置格式以及默认端口。  
除了TCP协议外，其他协议的name属性值和协议本身的名称一致，为什么TCP协议的name属性值为openwire，因为ActiveMQ的默认消息协议引用就是openwire,所以默认协议TCP和默认端口61616由此而来。  
ActiveMQ出厂默认配置是BIO网络IO模型，若要NIO网络IO模型，需要在transportConnectors标签下加入以下配置，配置如下：
```xml
<transportConnector name="nio" uri="nio://127.0.0.1:61618?trace=true" />
```
>当然NIO默认端口可以自定义，如果你不特别指定ActiveMQ的网络监听端口，那么这些端口都讲使用BIO网络IO模型（OpenWIre、STOMP、AMQP）。所以为了首先提高单节点的网络吞吐性能，我们需要明确指定ActiveMQ网络IO模型。
##NIO协议配置
由ActiveMQ安装目录所在的/conf/activemq.xml的配置文件可知，ActiveMQ默认出厂配置并不是NIO网络模型，而是BIO网络模型，若想使用NIO网络模型，需要transportConnectors标签加入以下配置，
端口可以自定义（如果不指定端口，默认使用BIO网络IO模型端口，比如OpenWire、STOMP、AMQP等）：
```xml
<transportConnector name="nio" uri="nio://127.0.0.1:61618?trace=true" />
```
>配置完成后重启activemq.  
NIO配置文件修改后，连接ActiveMQ的url由原来的tcp://127.0.0.1:61616改成nio://127.0.0.1:61618即可。
##NIO配置增强
当我们没有配置NIO时，端口使用的是以TCP为基础的BIO+TCP模式，当配置了NIO并且URL格式以“nio”开头后，
此时端口使用的是以TCP协议为基础的NIO网络模型，即NIO+TCP
> 怎么能让端口既支持NIO网络模型，有支持多个协议（不仅仅是TCP协议）
* 配置auto关键字
```xml
  <transportConnectors>
        <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
        <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>

       <!-- <transportConnector name="nio" uri="nio://0.0.0.0:61618?trace=true"/> -->
        <transportConnector name="auto+nio" uri="auto+nio://0.0.0.0:61608?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;
org.apache.activemq.transport.nio.SelectorManager.corePoolSize=20&amp;org.apache.activemq.transport.nio.SelectorManager.maximumPoolSize=50"/>
    </transportConnectors>
```
>   在配置文件中<transportConnectors>增加一行配置（端口可自行指定），但不能配置其他NIO协议。
```xml  
<transportConnector name="auto+nio" uri="auto+nio://0.0.0.0:61608?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;
 org.apache.activemq.transport.nio.SelectorManager.corePoolSize=20&amp;org.apache.activemq.transport.nio.SelectorManager.maximumPoolSize=50"/>
```
>   配置完成重启activemq。  
>   修改连接地址，注意修改端口
```java
public class JMSConsumer {
  // 使用auto+nio协议 tcp: 或 nio 都支持
    public static final String ACTIVEMQ_URL = "tcp://192.168.52.134:61608";
    //消息队列名称
    public static final String QUEUE_NAME = "nio_auto";
    //.....
}
```
>   由上一步可知，使用auto关键字配置了61608端口，当我们在61608端口使用指定的协议名称时，activemq会自动使用该协议的NIO网络模型，
>   不再是原来的BIO网络模型，由此达到NIO网络模型支持多协议的作用。
#3.ActiveMQ 持久化
###3.1 持久化方式
*   AMQ（了解）
```text
AMQ 消息存储是一种基于文件存储形式，它具有写入速度快和容易恢复的特点。消息存储在一个个文件中文件的默认大小为32M，
当一个文件中的消息已经全部被消费，那么这个文件将被标识为可删除，在下一个清除阶段，这个文件被删除。AMQ适用于ActiveMQ5.3之前的版本
```
*   KahaDB（重点）  
    KahaDB消息存储是基于日志文件的存储方式，它是5.4版本之后默认存储方式。
    在Activemq安装目录的conf/activemq.xml文件配置了ActiveMQ的默认持久化方式
    ```xml
    <persistenceAdapter>
        <kahaDB directory="${activemq.data}/kahadb"/>
    </persistenceAdapter>
    ```
    > 其中，directory属性值配置了KahaDB持久化方式日志所在目录，即/data/kahadb。
    这个目录下包含4个文件 db.data db.redo db-1.log lock
    
    kahaDB可用于任何场景，提高了性能和恢复能力。消息存储使用一个事务日志和仅仅用一个索引文件来存储它所有的地址。
    事务日志用于保存持久化数据，索引文件作为索引执行事务日志。
    
    KahaDB是一个专门针对消息持久化的解决方案，他对典型的消息使用模型进行优化。数据被准假到data logs中。
    当不在需要log文件中的数据的时候，log文件会被丢弃。
    *   db-【number】.log(事务日志)  
    ```text
    KahaDB存储消息到预定大小的数据纪录文件中，文件名为db-number.log。当数据文件已满时，一个新的文件会随之创建，
    number数值也会随之递增，它随着消息数量的增多，如每32M一个文件，文件名按照数字进行编号，如db-1.log，db-2.log······。
    当不再有引用到数据文件中的任何消息时，文件会被删除或者归档。
    ```
    *   db.data(索引文件)
    ```text
    该文件包含了持久化的BTree索引，索引了消息数据记录中的消息，它是消息的索引文件，本质上是B-Tree（B树），
    使用B-Tree作为索引指向db-number.log里面存储消息。
    ```
    *   db.free
    ```text
    记录当前db.data文件里面那些页面是空闲的，文件具体内容是所有空闲页的ID，
    方便下次记录数据时，从空闲页面开始建立索引，保证索引的连续性并且没有碎片。
    ```
    *   db.redo
    ```text
    用来进行消息恢复，如果kahaDB消息存储在强制退出后启动，用于恢复BTree索引
    ```
    *   lock
    ```text
    文件锁，表示当前kahaDB读写权限的broker
    ```
*   LevelDB（了解）
LevelDB文件系统是从ActiveMQ5.8之后引进的，它和KahaDB很相似，也是基于文件的本地数据库存储形式，但是它提供比KahaDB更快的持久性，
但它不再使用自定义B-Tree实现来索引预写日志，而是使用基于LevelDB的索引。相对于KahaDB它具有如下更好的优点：  
    *   快速更新（无需进行随机磁盘更新）
    *   并发读取
    *   使用硬链接快速索引快照
```xml
 <persistenceAdapter>
 		<!--<kahaDB directory="${activemq.data}/kahadb"/> -->
        <levelDB directory="${activemq.data}/leveldb"/>
 </persistenceAdapter>
```
*   JDBC（重点）  
JDBC持久化方式顾名思义就是将数据持久化到数据库中（如mysql），实现步骤如下：
    *   添加mysql驱动jar包到ActiveMQ安装目录的lib文件夹下
    *   在ActiveMQ的配置文件中的<persistenceAdapter>标签下添加以下内容
    ```xml
    <persistenceAdapter>
            <!--<kahaDB directory="${activemq.data}/kahadb"/> -->
            <jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="true" />
    </persistenceAdapter>
    ```
    >   datasource属性指定将要引用的持久化数据库bean名称（后面会配置一个name=mysql-ds的bean 标签）。  
        createTablesOnStartup属性值为true，表示在启动activemq的时候创建数据库和表，默认不写也为true，
        但这样每次启动都会去创建表，违背了持久性。所以一般第一次启动时设置为true，之后将值改为false。

    *   在配置文件中添加数据库连接池配置  
    配置连接池之前先在数据库中建立一个对应的数据库
    ```xml
    <broker>
    </broker>
    <bean id="mysql-ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <!-- 数据库驱动名称，此处是mysql驱动名称-->
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <!-- 连接数据库url，ip换成自己数据库所在的机器ip，数据库名为新建立数据库的名称-->
        <property name="url" value="jdbc:mysql://192.168.3.59:3306/activemq_db?relaxAutoCommit=true&amp;serverTimezone=GMT"/>
        <!-- 连接数据库用户名-->
        <property name="username" value="root"/>
        <!-- 连接数据库密码-->
        <property name="password" value="123456"/>
        <property name="poolPreparedStatements" value="true"/>
    </bean>
    <import resource="jetty.xml"/>
    ```
    >  注意：ActiveMQ默认使用DBCP连接池，并且自带了DBCP连接池相关jar包，如果想要换成C3P0等连接池，需要自行引入相关jar包。其中bean的id属性值一定要和上面的dataSource属性值一样。  
       bean 标签的位置要放在 &lt;broker&gt;标签之后&lt;import&gt;标签之前，否则会报错。
    > > 以上配置完成后，启动activemq，有可能出现连接失败的问题，【java.io.IOException: Cannot create PoolableConnectionFactory (null,  message from server: "Host '192.168.58.134' is not allowed to connect to this MySQL server"】
    ）我们需要在mysql中做一下修改：
    ```sql
    mysql > grant all privileges on db_name.* to usr_name@'%' identified by 'pwd';
    mysql > flush privileges ;
    ```
    >   其中，db_name 是数据库名， usr_name 用户名， pwd 密码。'%' 为通配符。此方式是开放所有IP的root访问权限，如果是正式环境还是建议用 开放指定IP的方式。
    *   启动ActiveMQ  
    启动成功后数据库中会生成相应的三张表：  
    
    activemq_acks  
    >   订阅关系表，如果是持久化Topic，订阅者和服务器的订阅关系保存在这个表。
   
    activemq_lock  
    >   在集群环境下才有用，只有一个Broker可以获取消息，称为Master Broker，其他的只能作为备份等待，Master Broker不可用时，其他的才可能成为下一个Master Broker。这个表用于记录哪个Broker是当前的Master Broker。
    
    activemq_msgs  
    >   消息表 Queue和Topic的消息都存在里面。
    *   测试  
    前提：  必须producer.setDeliveryMode(DeliveryMode.PERSISTENT);设置为持久化。否则消息只存在于内存中。  
   
    ```text
    当生产者将消息发送到queue中，由于我们采用的是mysql持久化方式，在activemq_msgs表中会插入，生产者发送的消息，
    当启动消费者进行消费消息后，这些消息将从表中删除。
    
    使用topic方式测试：  
    一定先启动订阅者，表示该topic存在订阅者，否则发布的topic是不会持久化到数据库中，换句话说不存在订阅者的topic就是废消息，没必要持久化到JDBC中。  
    在activemq_msgs中可以看到发布的消息，topic于queue不同的是，topic被消费的消息不会在数据表中删除。
    ```
*   JDBC+ ActiveMQ Journal（日志）（重点）  
    由于用jdbc的方式，在数据流大的情况下要频繁操作数据库，对数据库压力会过大，所以引入了ActiveMQ Journal（高速缓存），
    使每次消息过来之后在ActiveMQ和JDBC之间加一层高速缓存，使用高速缓存写入技术，大大提高了性能。
    ```text
    如：当有消息过来后，消息不会立马持久化到数据库中，而是先保存到缓存中，被消费的消息也是先从缓存中读取，经过了指定的时间之后，
    才把缓存中的数据持久化到数据库中。如果是queue，则只持久化未被消费的消息。
    ```
    用法：在配置文件中将persistenceAdapter替换成以下内容
    ```xml
    <!-- <persistenceAdapter> -->
    <!--    <kahaDB directory="${activemq.data}/kahadb"/>-->
    <!--      <jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="true" />
    </persistenceAdapter> -->

    <persistenceFactory>
        <journalPersistenceAdapterFactory
                journalLogFiles="5"
                journalLogFileSize="32768"
                useJournal="true"
                useQuickJournal="true"
                dataSource="#mysql-ds"
                dataDirectory="../activemq-data" />
    </persistenceFactory>
    ```
    > dataSource属性值指定将要引用的持久化数据库的bean名称，dataDirectory属性指定了Journal相关的日志保存位置。成功启动后则会发现多出一个activemq-data文件夹。
    
    配置成功后，重启ActiveMQ服务。启动队列生产生产消息，消息不会立即同步到数据库中，如果过了一段时间后队列的消息还没被消费，才会自动持久化到数据库中。
### 3.2总结
ActiveMQ消息持久化机制有：       

    AMQ 基于日志文件  
    KahaDB 基于日志文件，从ActiveMQ5.4开始默认使用  
    JDBC 基于第三方数据库  
    Replicated LevelDB Store 从5.9开始提供了LevelDB和Zookeeper的数据复制方法，用于Master-slave方式的首选数据复制方案。  
 
在配置关系型数据库作为ActiveMQ的持久方案时，需要注意的坑：  
    
数据库jar包
>   记得拷贝相关的jar文件放到activemq的lib目录下，mysql-jdbc驱动的jar包和对应的数据库里连接池jar包。

createTablesOnStartUp属性
>   在jdbcPersistenceAdapter标签中设置了 createTablesOnStartUp属性为true时，在第一次启动ActiveMQ时，
>服务节点会自动创建所需要的的数据表，启动完成后记得将属性值修改为false。

下划线问题
>   出现这个异常“java.long.IllegalStateException:BeanFactory not initialized or already closed”
>   这是因为你的操作系统的机器名中有“_”符号，修改名称后重启即可。

#ActiveMQ之zookeeper集群



     
