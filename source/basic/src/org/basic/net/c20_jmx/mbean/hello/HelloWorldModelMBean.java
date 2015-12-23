/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 4:51:44 PM Feb 21, 2014
 *
 *****************************************************************************
 */
package org.basic.net.c20_jmx.mbean.hello;

/**
 * Created on Feb 21, 2014, 4:51:44 PM
 */
import java.lang.reflect.Constructor;

import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

import org.basic.net.c20_jmx.mbean.BaseMBean;

/**
 * <pre>
 *  ModelMBean
 *        然而，普通的动态 Bean通常缺乏一些管理系统所需要的支持：比如持久化MBean的状态、日志记录、缓存等等。
 * 如果让用户去一一实现这些功能确实是件枯燥无聊的工作。为了减轻用户的负担，JMX提供商都会提供不同的 ModelBean实现。
 * 其中有一个接口是 Java 规范中规定所有厂商必须实现的：javax.management.modelmbean.RequiredModelBean。
 * 
 * 通过配置Descriptor信息，我们可以定制这个ModelBean，指定哪些 MBean状态需要记入日志、如何记录以及是否缓存某些属性、缓存多久等等。
 * 对于Descriptor，在MBean中相当于附带的一些信息，这些信息在MBean实现中可以作为一种策略，以此增强MBean的功能。
 * 动态MBean以及标准MBean的MBeanInfo都已经包括了Descriptor，但是在逻辑实现中没用到此对象。在ModelMBean中，
 * Descriptor作用非常大，持久化、日志、缓存等的策略等相关信息都是在Descriptor中定义的。开发人员可以增加相关属性到Descriptor中，来对应用功能进行扩展
 *        
 *   使用ModelMBean中，有两步很重要，
 *   第一步设置动态MBean元数据：setModelMBeanInfo(...)，MBeanServer会利用这些元数据管理MBean。
 *   第二步设置ModelMBean需要管理的对象：setManagerdResourece(...)，
 *   
 *   第一步的元数据其实也就是被管理对象的元数据。
 *   这二步都是可以在运行时候动态的配置的，对于ModelMBeanInfo和Resource等相关信息可以在xml文件中进行配置。所以对于ModelMBean的实现，可以很好的利用xml等工具。
 * </pre>
 * 
 * Created on Feb 21, 2014, 4:52:42 PM
 */

public class HelloWorldModelMBean extends RequiredModelMBean implements BaseMBean {

    public HelloWorldModelMBean() throws Exception {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.management.modelmbean.RequiredModelMBean#getMBeanInfo()
     */
    @Override
    public MBeanInfo getMBeanInfo() {

        // return super.getMBeanInfo();
        try {
            return (MBeanInfo) buildModelMBeanInfo();
        } catch (RuntimeOperationsException | MBeanException e) {
            return super.getMBeanInfo();
        }
    }

    public static RequiredModelMBean createModelBean() throws RuntimeOperationsException, MBeanException, InstanceNotFoundException,
            InvalidTargetObjectTypeException {
        // 模型MBean信息
        ModelMBeanInfo info = buildModelMBeanInfo();
        // 模型MBean
        RequiredModelMBean modelMBean = new RequiredModelMBean(info);
        // 目前只支持ObjectReference，将来可能会支持ObjectReference", "Handle", "IOR", "EJBHandle",
        // or "RMIReference，
        // RMIReference从名字上可以看出，如果支持的话，那么以后就可以支持远程MBean引用
        modelMBean.setManagedResource(new HelloWorld(), "ObjectReference");
        return modelMBean;
    }

    protected static ModelMBeanInfo buildModelMBeanInfo() throws RuntimeOperationsException, MBeanException {
        // --
        // attributes
        // ------------------------------------------------------------------
        ModelMBeanAttributeInfo[] attributes = new ModelMBeanAttributeInfo[1];

        // 设置属性
        Descriptor nameDesc = new DescriptorSupport();
        nameDesc.setField("name", "hello");
        nameDesc.setField("value", "----dfdf---");
        nameDesc.setField("displayName", "myname");
        nameDesc.setField("setMethod", "setHello");
        nameDesc.setField("getMethod", "getHello");
        nameDesc.setField("descriptorType", "attribute");
        attributes[0] = new ModelMBeanAttributeInfo("hello", "java.lang.String", "name say hello to", true, true, false, nameDesc);

        // --
        // operations
        // -------------------------------------------------------------------
        ModelMBeanOperationInfo[] operations = new ModelMBeanOperationInfo[2];
        String className = HelloWorld.class.getName();

        // getName method
        Descriptor getDesc = new DescriptorSupport(new String[] { "name=getHello", "descriptorType=operation", "class=" + className,
                "role=operation" });
        operations[0] = new ModelMBeanOperationInfo("getHello", "get hello  ", null, null, MBeanOperationInfo.ACTION, getDesc);

        Descriptor setDesc = new DescriptorSupport(new String[] { "name=setHello", "descriptorType=operation", "class=" + className,
                "role=operation" });
        operations[1] = new ModelMBeanOperationInfo("setHello", "set hello  ", new MBeanParameterInfo[] { new MBeanParameterInfo("a",
                "java.lang.String", " a method's arg ") }, null, MBeanOperationInfo.ACTION, setDesc);
        // 构造 printHello()操作的信息
        ModelMBeanOperationInfo print1Info = new ModelMBeanOperationInfo(//
                "printHello", //
                null, //
                null, //
                "void", //
                MBeanOperationInfo.INFO, //
                null //
        );
        // 构造printHello(String whoName)操作信息
        ModelMBeanOperationInfo print2Info;
        MBeanParameterInfo[] param2 = new MBeanParameterInfo[1];
        param2[0] = new MBeanParameterInfo("whoName", "java.lang.String", "say hello to who");
        print2Info = new ModelMBeanOperationInfo(//
                "printHello", //
                null,//
                param2,//
                "void", //
                MBeanOperationInfo.INFO, //
                null//
        );
        // constructors
        ModelMBeanConstructorInfo[] constructors = new ModelMBeanConstructorInfo[1];
        Constructor<?>[] ctors = HelloWorld.class.getConstructors();

        constructors[0] = new ModelMBeanConstructorInfo("default constructor", ctors[0], null);
        
        // 设置一个Descriptor策略，这样RequiredModelMBean改变 Attribute值得时候会记录日志
        // 当然RequiredModelMBean还需要addAttributeChangeNotificationListener，注册一个监听器
        Descriptor globalDescriptor = new DescriptorSupport(new String[] { "name=HelloWorldModelMBean", "displayName=globaldescriptor",
                "descriptorType=mbean", "log=T", "logfile=hello.log" });
        // mmbeanInfo.setMBeanDescriptor(globalDescriptor);

        // ModelMBeanInfo
        ModelMBeanInfo mmbeanInfo = new ModelMBeanInfoSupport(className, // MBean类
                "Simple implementation of model bean.",// 描述文字
                attributes, // 所有的属性信息（数组）
                null,// 所有的构造函数信息
                operations,// 所有的操作信息（数组）
                null,// 所有的通知信息
                globalDescriptor);


        return mmbeanInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.basic.jmx.mbean.BaseMBean#getMBanName()
     */
    @Override
    public String getMBeanName() {
        return this.getClass().getName();
    }

}