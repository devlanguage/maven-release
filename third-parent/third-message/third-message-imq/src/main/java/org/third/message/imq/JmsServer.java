package org.third.message.imq;


/**
 * <pre>
 * JMS应用之openMQ(openJMS) 常用命令
 * 
 * sun OpenMQ也称openjms。它是众多jms中的佼佼者，无论是性能方面还是平台搭建方面！性能上能随意满足4000+的并发访问。平台上就更不用说了，sun的东西就是实用。OpenMQ很好的实现了jms规范，而且它能很好的实现集群。要说有缺点唯一它没有一个可视化的后台管理。现在OpenMQ已经集成在GlassFish应用服务器下，下面分享一些基本的openMQ管理命令(Linux环境)……
 * 
 * 1  启起MQ :
 * 单台服务器
 * /opt/mq/bin/imqbrokerd -tty -name myBroker -port 7677 -Dimq.autocreate.queue=false -      Dimq.jms.max_threads=512 -vmargs "-Xms3G -Xmx3G" &
 * 
 * jms集群
 * /opt/mq/bin/imqbrokerd -tty -name myBroker -port 7677 -cluster host1:7677,
 * host2:7677,hostN:7677 -Dimq.cluster.masterbroker=hostA:7677 -
 * Dimq.jms.max_threads=4096 -Dimq.system.max_count=-1 -Dimq.message.max_size=-1 - 
 * Dimq.autocreate.queue.maxNumBackupConsumers=-1 -vmargs "-Xms3584m -Xmx3584m" &
 * 
 * 2  消息队列管理:
 * 
 * 查看某个队列有多少链接
 * /opt/mq/bin/imqcmd -b hostXX:7677 -t q -n huozi(队列名，下同)  metrics dst
 * 
 * 动态查看某队列的信息
 * /opt/mq/bin/imqcmd -b hostXX:7677 -t q -n huozi  metrics dst
 * 
 * 查看整个jms的运行属性
 * /opt/mq/bin/imqcmd -u admin  -b hostXX:7677 query bkr
 * 
 * 查看消息队列中的消息
 * /opt/mq/bin/imqcmd  -u admin -b hostXX:7677 list msg -t q -n huozi -nocheck
 * 
 * 查看所有队列状态
 * /opt/mq/bin/imqcmd   -u admin -b hostXX:7677  list dst
 * 
 * 查看某个队列的的状态
 * /opt/mq/bin/imqcmd -b hostXX:7677 -t q -n  huozi
 * 
 * 查询某个消息在某个队列中的状态
 * /opt/mq/bin/imqcmd -b hostXX:7677 query msg -t q -n huozi -msgID "ID:1625-127.0.0.1  
 * (ef:f5:f4:5c:46:69)-60828-1282050952257" -nocheck
 * 
 * 删除某个队列中的消息
 * /opt/mq/bin/imqcmd -b hostXX:7677 destroy msg -t q -n huozi -msgID "ID:205-127.0.0.1
 * (83:c2:1d:63:77:b1)-44516-1282050052264" -nocheck
 * 
 * 查看队列的 ip 连接
 * /opt/mq/bin/imqcmd -u admin -b hostXX:7677 -n huozi list  cxn
 * 
 * 查看某个连接
 * /opt/mq/bin/imqcmd -u admin -b hostXX:7677 -n 4955132286115188480  query  cxn
 * 
 * 清除消息队列
 * /opt/mq/bin/imqcmd -b hostXX:7677 -t q -n huozi purge dst
 * 
 * 查看队列消息是否已被清除
 * /opt/mq/bin/imqcmd -b hostXX:7677 -u admin list dst
 * 
 * 删除某个连接
 * /opt/mq/bin/imqcmd -u admin -b hostXX:7677 -n 4955132286115188480  destroy cxn
 * </pre>
 */
public class JmsServer {
    // "D:\Java\Jdk\jre\bin\java" "-server" "-cp"
    // "D:\Java\OpenMQ\lib\imqbroker.jar;D:\Java\OpenMQ\lib\imqutil.jar;D:\Java\OpenMQ\lib\jsse.jar;D:\Java\OpenMQ\lib\jnet.jar;
    // D:\Java\OpenMQ\lib\jcert.jar;D:\Java\OpenMQ\lib\..\..\share\lib\jdmkrt.jar;D:\Java\OpenMQ\lib\..\..\share\lib\mfwk_instrum_tk.jar;D:\Java\OpenMQ\lib\ext"
    // "-Dimq.home=D:\Java\OpenMQ" "-Dimq.varhome=D:\Java\OpenMQ\var" "-Xms16m" "-Xmx192m"
    // com.sun.messaging.jmq.jmsserver.Broker
    public static void main(String[] args) {
        // OpenMQ依靠IMQ_VARHOME环境变量指定var目录，如果没有的话就直接定位到安装目录中的var目录；
        // 2. OpenMQ依靠IMQ_JAVAHOME指定JDK，或者通过命令行参数-javahome指定。
    }
}
