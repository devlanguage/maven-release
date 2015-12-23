1) Prequiste
 i) Install the http://jaist.dl.sourceforge.net/project/tcl/Tcl/8.6.4/tcl864-src.zip
     # cp tcl8.5.9-src.tar.gz /usr/local/src/
     # cd /usr/local/src/
     # tar -zxvf tcl8.5.9-src.tar.gz
     # cd tcl8.5.9/
     # cd unix/
     # ./configure --prefix=/usr/local/tcl/ --enable-shared
     # make; make install
     # ln -s /usr/local/tcl/bin/tclsh8.5 /usr/local/bin/tclsh8.5
 ii) Install the http://jaist.dl.sourceforge.net/project/tcl/Tcl/8.6.4/tk864-src.zip
    # cp tk8.5.9-src.tar.gz /usr/local/src/
    # cd /usr/local/src/
    # tar -zxvf tk8.5.9-src.tar.gz
    # cd tk8.5.9/
    # cd unix/
    # ./configure --prefix=/usr/local/tk/ --with-tcl=/usr/local/tcl/lib/ --enable-shared
    # make
    # make install
    # /usr/local/tk/bin/wish8.5
      /usr/local/tk/bin/wish8.5: error while loading shared libraries: libtcl8.5.so: cannot open shared object file: No such file or directory
    # ln -s /usr/local/tcl/lib/libtcl8.5.so /usr/local/tk/lib/libtcl8.5.so
    # /usr/local/tk/bin/wish8.5
    
iii) Install http://sourceforge.net/projects/expect/files/Expect/5.45/expect5.45.tar.gz    
    # cp expect-5.44.1.15.tar.gz /usr/local/src/
    # cd /usr/local/src/
    # tar -zxvf expect-5.44.1.15.tar.gz
    # cdexpect-5.44.1.15/
    # ./configure --prefix=/usr/local/expect/
    --withtcl=/usr/local/tcl/lib/ --with-tclinclude=/usr/local/src/tcl8.5.9/generic/
    --withtk=/usr/local/tk/lib/ --with-tkinclude=/usr/local/src/tk8.5.9/generic/ --enable-shared
    # make
    # make install
    # /usr/local/tcl/bin/expect
    expect1.1> exit
    #
    # /usr/local/tcl/bin/expectk
    /usr/local/tcl/bin/expectk: error while loading shared libraries: libtk8.5.so: cannot open shared object file: No such file or directory
    # ln -s /usr/local/tk/lib/libtk8.5.so
    /usr/local/tcl/lib/libtk8.5.so
    # /usr/local/tcl/bin/expectk
    Application initialization failed: version conflict for package "Tcl": have 8.5.9, need exactly 8.5
    % exit
    #    
iv) install the ruby:  wget http://cache.ruby-lang.org/pub/ruby/2.2/ruby-2.2.2.tar.gz
   # ./configure -prefix /usr/local/ruby
   # make ;make install
v)  install the ruby-gem: http://production.cf.rubygems.org/rubygems/rubygems-2.4.7.zip
   # cd rubygems-2.4.7
   # ./ruby setup.rb
   # make ;make install
vi) install the redis client for ruby: https://rubygems.global.ssl.fastly.net/gems/redis-3.2.1.gem
   gem install -file redis-3.2.1.gem
   gem install redis
6) wget http://ftp.gnu.org/gnu/autoconf/autoconf-2.68.tar.gz
   
       
2) Install redis http://download.redis.io/releases/redis-3.0.1.tar.gz
   % cd redis-3.0.1
   % make    
   % cd src
   % ./redis-server /path/to/redis.conf

t is possible to alter the Redis configuration passing parameters directly
s options using the command line. Examples:

   % ./redis-server --port 9999 --slaveof 127.0.0.1 6379
   % ./redis-server /etc/redis/6379.conf --loglevel debug

ll the options in redis.conf are also supported as options using the command

redis-benchmark -h hserver1 -p 6379 -t get -q -r 1000 -n 100000 -c 800

redis-trib create --replicas 1 172.29.16.174:6379 172.29.16.174:24791 172.29.16.174:24792  172.29.14.113:24793 172.29.14.113:24794 172.29.14.113:24795

Playing with Redis
------------------

You can use redis-cli to play with Redis. Start a redis-server instance,
then in another terminal try the following:

    % cd src
    % ./redis-cli
    redis> ping
    PONG
    redis> set foo bar
    OK
    redis> get foo
    "bar"
    redis> incr mycounter
    (integer) 1
    redis> incr mycounter
    (integer) 2
    redis>

You can find the list of all the available commands here:

    http://redis.io/commands