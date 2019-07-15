Modify JACORB charset:
    by default, jacorb take file.encoding as default charset. so, just add the jvm parameter:
        -Dfile.encoding=
1. Modify jaco.tpl.bat
    @echo off
    set JACORB_HOME=..
    SET CP= %CLASSPATH%;
    FOR %%i IN (%JACORB_HOME%\lib\*.jar, .) DO CALL :addpath %%i
    
    set CLASSPATH=%CP%
    rem call java interpreter
    java  -cp %CLASSPATH% -Djacorb.home=%JACORB_HOME% -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton %*
    
    :addpath
    SET CP=%CP%;%1
    GOTO :EOF
2）idltemplate.bat更名为idl.bat（idl文件编译器），编辑如下：
    @echo off
    set JACORB_HOME=..
    SET CP= %CLASSPATH%;
    FOR %%i IN (%JACORB_HOME%\lib\*.jar, .) DO CALL :addpath %%i
    
    set CLASSPATH=%CP%
    rem call java interpreter
    %JAVA_HOME%\bin\java    -cp  %CLASSPATH%  org.jacorb.idl.parser %*
    
    :addpath
    SET CP=%CP%;%1
    GOTO :EOF  
      
    Usage: idl [-h|-help][-v|-version][-Dsymbol[=value]][-Idir][-U<symbol>][-W debug_level ][-all][-forceOverwrite][-ami_callback][-ami_polling]
    [-backend classname][-d <Output Dir>][-unchecked_narrow][-i2jpackage x:y][-i2jpackagefile <filename>][-cldc10][-ir][-nofinal][-noskel][-nost
    ub][-diistub][-sloppy_forward][-sloppy_names][-permissive_rmic][-genEnhanced][-syntax][-in inputfile] <filelist>
    idl.bat C:\software\Java_Dev\eclipse_proj\java_proj\JavaBasic\java\basic\src\org\basic\corba\jacorb\demo.idl
    
3）ns.bat以及ntfy.bat，编辑，将jaco前加上路径，即D:\JacORB\bin\jaco
    $ ns [-Djacorb.naming.ior filename=<filename>] [-DOAPort=port] [-Djacorb.naming.time out=<timeout>]
    You can also start the Java interpreter explicitly by typing
        $ jaco jacorb.naming.NameServer [-Djacorb.naming.ior filename=<filename>] [-DOAPort=port] [-Djacorb.naming.time out=<timeout>]
    
    $ ntfy [-printIOR] [-printCorbaloc] [-writeIOR filename] [-registerName nameID[.nameKind]] [-port oaPort] [-channels channels] [-help]
        -printIOR print the IOR to STDOUT
        -printCorbaloc print the Corbaloc to STDOUT
        -writeIOR ﬁlename write the IOR to a ﬁle
        -registerName nameId[.nameKind] make a Name Service entry for the EventChannelFactory.
        The Notiﬁcation Service will resolve the Name Service
        by invoking
        resolve initial references("NameService").
        Ensure that your environment is set up properly.
        -port oaport start the Notiﬁcation Service on the speciﬁed port.
        -channels channels create a number of EventChannels.
4） set OAPort=4711
         ns.bat  -DOAPort=9527
1.Feeding this ﬁle into the IDL compiler
  $ idl -d ./generated server.idl
  
  
  
  
  Corba算是比较古老的分布式系统了，因为工作需要，需要访问Corba的服务器（也是用JacORB）实现的一套系统，其实就是TMF814相关规范的Corba实现，以下是摸索的点滴，在此稍作记录，以防忘记。 
  
访问服务器的几种方式 [list=1] 
IOR的方式，就是将服务信息导出为字符串，直接传给客户端相关代码 
ORBInitRef.NameService，比如：corbaloc::172.31.0.140:13000/NameService 
JacORB的几个特殊属性 Java代码 
prop.put( "org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB" );   
prop.put( "org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton" );          
 
 双向IIOP的访问首先需要设置属性： Java代码 
prop.put( "org.omg.PortableInterceptor.ORBInitializerClass.bidir_init",   
            "org.jacorb.orb.giop.BiDirConnectionInitializer");  

其次激活特定的POA Manager，代码如下： 
Java代码 
Any any = orb.create_any();   
BidirectionalPolicyValueHelper.insert(any, BOTH.value);   
Policy[] policies = new Policy[1];   
 policies[0] = orb.create_policy(   
 BIDIRECTIONAL_POLICY_TYPE.value, any);   
  
org.omg.PortableServer.POA poa = root_poa.create_POA(   
        "BiDirPOA", root_poa.the_POAManager(), policies);   
poa.the_POAManager().activate();  
  