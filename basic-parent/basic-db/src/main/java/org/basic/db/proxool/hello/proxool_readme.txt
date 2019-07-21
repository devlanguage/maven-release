今天把我做的WEB工程用压力测试工具测试测试了一下，WEB工程中用到了Proxool连接池，一测试，才发现simultaneous-build-throttle这个参数的重要。 
　　这个参数的英文解释如下： 
simultaneous-build-throttle: 
This is the maximum number of connections we can be building at any one time. That is, the number of new connections that have been requested but aren't yet available for use. Because connections can be built using more than one thread (for instance, when they are built on demand) and it takes a finite time between deciding to build the connection and it becoming available we need some way of ensuring that a lot of threads don't all decide to build a connection at once. (We could solve this in a smarter way - and indeed we will one day) Default is 10. 
　　是指在任一时刻，可以（同时）建立的最大连接数，也就是说，就是已经请求的、但还没可用的新连接数量。因为连接可以用多线程建立，从决定要建立连接到连接可用是需要一定时间的，所以我们需要一些方式来避免大量的线程想同时建立连接。（我们本应该找一个更聪明的方式来解决这个问题，总有一天我们会找到的）默认值是10 

　　当我使用140个用户，进行压力测试时，发现偶尔，会有多于10个要求同时建立连接的请求，当请求数量超过限定的数值时，会出现连接失败的情况。 

　　因此结论就是，当数据库并发连接可能会比较高的应用，这个值应该适当的设大一点。 

再介绍一下其它重要几个参数，以前对这个几参数的作用似懂非懂，今天又花了点时间结合压力测试的结果琢磨了一下，感觉有点豁然开朗： 

maximum-active-time: 
If the housekeeper comes across a thread that has been active for longer than this then it will kill it. So make sure you set this to a number bigger than your slowest expected response! Default is 5 minutes. 
　　如果一个线程活动时间超过这个数值，线程会被杀死。所以要确保这个数值设置得比最慢的响应时间长。默认是5分钟。守护进程会把连接池中多余的可用线程（未用的、超过这个时间的）杀死，最终保留的连接数量就是minimum-connection-count规定的数量。守护进程会根据house-keeping-sleep-time参数设置的时间隔定时检查。 

maximum-connection-lifetime: 
The maximum amount of time that a connection exists for before it is killed (milliseconds). Default is 4 hours. 
　　指一个连接最大的存活时间（毫秒为单位），超过这个时间，连接会被杀死。默认值是4小时。 

overload-without-refusal-lifetime: 
This helps us determine the pool status. If we have refused a connection within this threshold (milliseconds) then we are overloaded. Default is 60　seconds. 
　　这个参数帮助我们确定连接池的状态，如果在这个时间阀值内（单位为毫秒）拒绝了一个连接，就认为是过载了。默认值是60。 
