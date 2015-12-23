/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ws.pullxml.hello.PullXmlHelloTest.java is created on 2008-5-20
 */
package org.basic.xml.stax.axiom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMOutputFormat;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

/**
 * 
 */
public class PullXmlHelloTest {

    public final static String FILE_ROOT = "source/resource/";

    /**
     * @param args
     */
    public static void main(String[] args) {

        PullXmlHelloTest test = new PullXmlHelloTest();
        try {
            // test.testRead();
            test.testWrite();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void testWrite() throws FileNotFoundException, XMLStreamException,
            FactoryConfigurationError {

        // 通常通过OMFactory来构造XML文档中的element，下面是????示例代码??
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMDocument doc = factory.createOMDocument();
        // 创建两个名空??
        OMNamespace nsPerson = factory.createOMNamespace(
                "http://pullxml.test/person", "p");
        OMNamespace nsAddress = factory.createOMNamespace(
                "http://pullxml.test/person/address", "addr");

        // 创建rood节点
        OMElement personElement = factory.createOMElement("person", nsPerson);
        OMAttribute nameAttribute = factory.createOMAttribute("name", nsPerson,
                "ZhangSan");
        personElement.addAttribute(nameAttribute);
        personElement.addAttribute("age", "23", null);
        personElement.addAttribute("age", "45", nsPerson);
        OMElement addressesElement = factory.createOMElement("address",
                nsAddress);
        OMElement addressXiAn = factory.createOMElement("address", nsAddress);
        addressXiAn.addChild(factory.createOMText("Xi'An City"));
        addressesElement.addChild(addressXiAn);
        OMElement addressLanZhou = factory
                .createOMElement("address", nsAddress);
        addressLanZhou.addChild(factory.createOMText("兰州"));
        addressesElement.addChild(addressLanZhou);
        personElement.addChild(addressesElement);
        doc.addChild(personElement);

        // 写入到XmlFile
        OMOutputFormat oof = new OMOutputFormat();
        oof.setXmlVersion("1.0");
        oof.setCharSetEncoding("GBK");
        // 方法??二：
        // default OM output format: version=1.0, encoding=utf-8
        doc.serialize(new FileOutputStream(FILE_ROOT
                + "pull_root_write_001.xml"));
        doc.serialize(new FileOutputStream(FILE_ROOT
                + "pull_root_write_002.xml"), oof);

        // 方法?? 四：
        oof.setDoOptimize(true);
        doc.serializeAndConsume(new FileOutputStream(FILE_ROOT
                + "pull_root_write_003.xml"));
        doc.serializeAndConsume(new FileOutputStream(FILE_ROOT
                + "pull_root_write_004.xml"), oof);

        // 方法五： 构建writer做输出器??
        XMLStreamWriter writer = XMLOutputFactory.newInstance()
                .createXMLStreamWriter(
                        new FileOutputStream(FILE_ROOT
                                + "pull_root_write_005.xml"));
        personElement.serialize(writer);// Cache on
        writer.flush();

        // Detach
        doc.serialize(System.out);
        System.out.println("\n\n");

        System.err.println("Detach\n");
        addressesElement.detach();
        doc.serialize(System.out);
        System.out.println("\n\n");

        addressesElement.serialize(System.out);
        // // ele.detach();
        // ele.serialize(System.out); // 即使detach()，依然会输出ele
        // doc.serialize(System.out); // 如果detach()，就不会有ele到document里??

    }

    private void testRead() throws FileNotFoundException, XMLStreamException,
            FactoryConfigurationError {

        // 首先构建parser??
        XMLStreamReader parser = XMLInputFactory
                .newInstance()
                .createXMLStreamReader(
                        PullXmlHelloTest.class
                                .getResourceAsStream("pull_root_read_test.xml"));
        // 还需要builder对象??
        StAXOMBuilder builder = new StAXOMBuilder(parser);
        // get the root element
        // OMElement documentElement = builder.getDocumentElement();
        OMDocument doc = builder.getDocument();
        // dump the out put to console with caching
        doc.serialize(System.out);
        System.out.println();
        OMElement firstAddressElement = doc.getOMDocumentElement()
                .getFirstChildWithName(
                        new QName("http://pullxml.test/person/address",
                                "address"));

        // OMElement有一系列的get方法来获得内容??
        firstAddressElement.serialize(System.out); // cache on, 强制构建OMTre
        firstAddressElement.serializeAndConsume(System.out); // cache off, 不强制构建OMTre
        System.out.println();
        System.out.println();

        // will NOT build the OMTree in the memory.
        // So you are at your own risk of losing information.
        String firstAddressString = firstAddressElement.toStringWithConsume();
        // call toString, will build the OMTree in the memory.
        System.out.println(firstAddressString);

        // 关于serialize和serializeAndConsume，前者会强制构建OMTree，或者则不会?? // 关于detach，它只影响OMElement本身和OMTree的关系，并不影响OMElement本身??
        // // 与之对应的还有一个build方法，build会强制build整个OMTree出来?? //
        // 这两个方法??常用在处理OMElement与OMTree的关系上。从输入流构建出OMElement(build)以及把OMElement从输入流断开(detach)，以便放到输出流。输入流和输出流是不同的OMTree??
        // 
    }

}
