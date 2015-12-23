EHCache Cluster
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"   
  xsi:noNamespaceSchemaLocation="ehcache.xsd">   
  <cacheManagerPeerProviderFactory   
  class="net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory"   
  properties="peerDiscovery=manual,   
  rmiUrls=//192.168.1.254:40000/UserCache" />   
    
  <cacheManagerPeerListenerFactory   
  class="net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory"   
  properties="hostName=192.168.1.126,port=40000,socketTimeoutMillis=120000" /> 
  
  

Terracotta与EHcache集群2010-03-22 15:33Terracotta与EHcache集群的另一种为集群多种类型的数据如:Cache、POJO、Session等(官方称定制安装)。

1.环境需求
●JDK1.5 或者更高
●Terracotta3.2 或者更高
●所有被集群的对象是可串行化(serializable)

2.配置ehcache.xml
●将${TERRACOTTA_HOME}/distributed-cache/ehcache.xsd、 
${TERRACOTTA_HOME}/distributed-cache/ehcache.xml复制到项目中的如:src下

●将ehcache.xml配置文件的ehcache元素修改成：
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:noNamespaceSchemaLocation="ehcache.xsd"
name="SSO4Cache" updateCheck="false"   monitoring="autodetect">

<defaultCache
maxElementsInMemory="10000"
eternal="false"
timeToIdleSeconds="120"
timeToLiveSeconds="120"
overflowToDisk="true"
diskSpoolBufferSizeMB="30"
maxElementsOnDisk="10000000"
diskPersistent="false"
diskExpiryThreadIntervalSeconds="120"
memoryStoreEvictionPolicy="LRU"
/>

<cache name="cn.com.commnet.cas.OBJECT_ECACHE"
maxElementsInMemory="1000"
eternal="false"
timeToIdleSeconds="300000"
timeToLiveSeconds="600000"
memoryStoreEvictionPolicy="LFU"
>
  <terracotta  clustered="true" valueMode="identity"/>
<!--表示该cache要加入到terracotta service中-->
</cache>

<cache name="cn.com.commnet.policyagent.GLOBAL_ECACHE"
maxElementsInMemory="1000"
eternal="false"
timeToIdleSeconds="300000"
timeToLiveSeconds="600000"
memoryStoreEvictionPolicy="LFU"
>
 <terracotta  clustered="true"/>  <!--表示该cache要加入到terracotta service中-->
</cache>

★valueMode 模式分两种1.serialization模式（默认）2.identity 模式(该模式只能在EHcache自定义安装下有效，快速安装下该模式无效)
1.serialization模式：在该模式下TC(terracotta  Client)从TS(terracotta  Service)缓存中获取一个元素得到了该元素的副本。所做的更改不影响元素在TS缓存或者其他TC元素副本。只有将在TS缓存中的元素覆盖其他TC也将更新。
2.identity 模式：TC从TS缓存中获取一个元素的引用。TC所做的更改将及时更新了每个TC节点上的元素引用。
★去除无效的 <terracottaConfig url="locahost:9510"/>、  <cacheManagerEventListenerFactory class="" properties=""/>、<diskStore path="java.io.tmpdir"/>

3.修改tc-config.xml文件
<modules>
<module name="tim-ehcache-1.7"/> <!--增加-->
<module name="tim-tomcat-6.0"/>
<module name="tim-quartz-1.7"/>
</modules>
并复制到没一个TS、TC上，后面操作与《Terracotta与EHcache集群(仅仅集群Cache)》一致
 
Terracotta与EHcache集群(仅仅集群Cache)2010-03-03 13:35   Terracotta与EHcache集群可以分为2种，一种为仅仅集群Cache(官方称快速安装)、另一种为集群多种类型的数据如:Cache、POJO、Session等(官方称定制安装)。

1.环境需求
●JDK1.5 或者更高
●Terracotta3.2 或者更高
●所有被集群的对象是可串行化(serializable)
2.安装分布式缓存
将下列jar文件拷贝到你程序的classpath下面，如果你是WEB项目则拷贝到WEB-INF\lib目录下。

${TERRACOTTA_HOME}/distributed-cache/ehcache-terracotta-<version>.jar 
${TERRACOTTA_HOME}/distributed-cache/ehcache-core-<ehcache-version>.jar 
3.配置分布式缓存
Ehcache的配置文件，默认为ehcache.xml， 需要在你项目的classpath中。如果你用的是WAR文件，需要将Ehcache配置文 件放入WEB-INF/lib 文件夹中。

添加Terracotta配置文件
为需要用Terracotta实现集群的cache，在ehcache.xml添加sub-element<terracotta/> 到cache的<cache>块中。举例如下： Xml代码
<cache name="foo" maxElementsInMemory="1000"   
       maxElementsOnDisk="10000" eternal="false" timeToIdleSeconds="3600"   
       timeToLiveSeconds="0" memoryStoreEvictionPolicy="LFU">   
  <!-- Adding the element <terracotta /> turns on Terracotta clustering for the cache foo. -->   
  <terracotta />   
</cache>   

<cache name="foo" maxElementsInMemory="1000"        maxElementsOnDisk="10000" eternal="false" timeToIdleSeconds="3600"        timeToLiveSeconds="0" memoryStoreEvictionPolicy="LFU">   <!-- Adding the element <terracotta /> turns on Terracotta clustering for the cache foo. -->   <terracotta /> </cache>

你还需要用<terracottaConfig>为应用服务器指出terracotta的 位置。
客户端需要加载配置从一个文件或者Terracotta服务器中。下面的例子展示了一个加载 于本地主机上的Terracotta服务器的配置文件。

Xml代码
<ehcache name="myCache">   
...   
<terracottaConfig url="localhost:9510" />   
...   
</ehcache>   

<ehcache name="myCache"> ... <terracottaConfig url="localhost:9510" /> ... </ehcache>

url属性的值需要包含Terracotta服务器的主机名和它的DSO端口号（默认为 9510）。

编辑不相容的配置
对于任何被集群的cache，你必须删除、disable、或者编辑那些在Terracotta集 群时不相容的配置元素。被集群的caches有个<terracotta>或 者<terracotta clustered=”true”>元素。
下面这些Ehcache配置属性或者元素需要删除或者disable。

  DiskStore-related attributes overflowToDisk and diskPersistent . 
Terracotta 服务器自动提供一个磁盘储存。

  Replication-related configuration elements, such as <cacheManagerPeerProviderFactory>, <cacheManagerPeerListenerFactory>, <bootstrapCacheLoaderFactory>, <cacheEventListenerFactory>. 
当一个变化发生在Terracotta集群的时候,所以包含变化元素和对象的节点都会更新。 Unlike the replication methods used to cluster Ehcache, cache event listeners are not (and do not need to be) notified of remote changes. Listeners are still aware of local changes.

Replication-related attributes such as replicateAsynchronously and replicatePuts . 
MemoryStoreEvictionPolicy属性需要被设置成LFU或者LRU。设置MemoryStoreEvictionPolicy为 FIFO将会引起IllegalArgumentException错误。

四、    启动集群 
1、启动Terracotta服务

UNIX/Linux 
[PROMPT] ${TERRACOTTA_HOME}/bin/start-tc-server.sh & 
Microsoft Windows 
[PROMPT] ${TERRACOTTA_HOME}\bin\start-tc-server.bat 
2、启动应用服务器
3、启动Terracotta开发控制台

UNIX/Linux 
[PROMPT] ${TERRACOTTA_HOME}/bin/dev-console.sh & 
Microsoft Windows 
[PROMPT] ${TERRACOTTA_HOME}\bin\dev-console.bat 
4、连接Terracotta集训
点击Terracotta开发控制台中的Connect…
5、点击集群导航窗口中的Ehcache节点查看Terracotta集群中的caches。

五、编辑Terracotta配置
这一步告诉你如何在不同的机器上启动客户端和服务器，和故障转移（高效）。你可以扩展Terracotta集 群和添加高效性，通过做以下的事情：
1、    将Terracotta服务器转移到单独的机器上。
2、    部署一个由多个Terracotta服务器组织的集群器。
3、    部署多个应用节点。

操作程序：
1、    关闭Terracotta集群器。
2、    建立Terracotta配置文件tc-config.xml包含像下面这样的 内容：

<?xml version="1.0" encoding="UTF-8"?>   
<!-- All content copyright Terracotta, Inc., unless otherwise indicated.   
     All rights reserved.   
-->   
<tc:tc-config xsi:schemaLocation="http://www.terracotta.org/schema/terracotta-4.xsd" xmlns:tc="http://www.terracotta.org/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">   
    
  <servers>   
    <!-- Sets where the Terracotta server can be found. Replace the value of   
         host with the server's IP address. -->   
    <server host="server.1.ip.address" name="Server1">   
      <data>%(user.home)/terracotta/server-data</data>   
      <logs>%(user.home)/terracotta/server-logs</logs>   
    </server>   
    <!-- If using a standby Terracotta server, also referred to as an ACTIVE-   
         PASSIVE configuration, add the second server here. -->   
    <server host="server.2.ip.address" name="Server2">   
      <data>%(user.home)/terracotta/server-data</data>   
      <logs>%(user.home)/terracotta/server-logs</logs>   
    </server>   
  </servers>   
  <!-- Sets where the generated client logs are saved on clients. -->     
  <clients>   
    <logs>%(user.home)/terracotta/client-logs</logs>   
  </clients>   
</tc:tc-config>  


<?xml version="1.0" encoding="UTF-8"?> <!-- All content copyright Terracotta, Inc., unless otherwise indicated.      All rights reserved. --> <tc:tc-config xsi:schemaLocation="http://www.terracotta.org/schema/terracotta-4.xsd" xmlns:tc="http://www.terracotta.org/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">     <servers>     <!-- Sets where the Terracotta server can be found. Replace the value of          host with the server's IP address. -->     <server host="server.1.ip.address" name="Server1">       <data>%(user.home)/terracotta/server-data</data>       <logs>%(user.home)/terracotta/server-logs</logs>     </server>     <!-- If using a standby Terracotta server, also referred to as an ACTIVE-          PASSIVE configuration, add the second server here. -->     <server host="server.2.ip.address" name="Server2">       <data>%(user.home)/terracotta/server-data</data>       <logs>%(user.home)/terracotta/server-logs</logs>     </server>   </servers>   <!-- Sets where the generated client logs are saved on clients. -->     <clients>     <logs>%(user.home)/terracotta/client-logs</logs>   </clients> </tc:tc-config>


3、    安装Terracotta3.1.1在每一台你在tc-config.xml中配置的服务器上。
4、    复制tc-config.xml文件到一个Terracotta服务器们可以进 入的位置。
5、    执行二、安装分布cache 和 三、配置分布式cache 在每一个你想要集群的应用节点上。
确定你安装了你的应用或者任何应用服务器在那些节点上。
6、    添加以下语句到Ehcache配置文件ehcache.xml中：

Xml代码
<!-- Add the servers that are configured in tc-config.xml. -->   
<terracottaConfig url="server.1.ip.address:9510,server.2.ip.address:9510" />   

<!-- Add the servers that are configured in tc-config.xml. --> <terracottaConfig url="server.1.ip.address:9510,server.2.ip.address:9510" />

7、    复制ehcache.xml到每一个应用节点，确保它在你应用的 classpath中。如果你用的是WAR文件，将这些JAR文件添加到WEB-INF/lib 文件夹中。
8、    启动Terracotta服务以下面的方法，将tc-config.xml中 的’Server1’换成任意你取的名字。

UNIX/Linux 
 [PROMPT] ${TERRACOTTA_HOME}/bin/start-tc-server.sh -f <path/to/tc-config.xml> -n Server1 & 
Microsoft Windows 
 [PROMPT] ${TERRACOTTA_HOME}\bin\start-tc-server.bat -f <path\to\tc-config.xml> -n Server1 & 

如果你配置了第2台服务器，启动那个服务用同样的方法在那台机器上，在 –n 标记后输入它的名字。第2台服务器成为热备份。任何你配置的其他服务器都可以开启变成备份服务器。
9、    打开所有的应用服务器。
10、打开Terracotta开发终端，监视集群器。

详见：http://www.terracotta.org/documentation/ga/product-documentation-2.html#388963632_pgfId-1131832      
 
 