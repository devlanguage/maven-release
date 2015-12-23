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

        // ͨ��ͨ��OMFactory������XML�ĵ��е�element��������????ʾ������??
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMDocument doc = factory.createOMDocument();
        // ������������??
        OMNamespace nsPerson = factory.createOMNamespace(
                "http://pullxml.test/person", "p");
        OMNamespace nsAddress = factory.createOMNamespace(
                "http://pullxml.test/person/address", "addr");

        // ����rood�ڵ�
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
        addressLanZhou.addChild(factory.createOMText("����"));
        addressesElement.addChild(addressLanZhou);
        personElement.addChild(addressesElement);
        doc.addChild(personElement);

        // д�뵽XmlFile
        OMOutputFormat oof = new OMOutputFormat();
        oof.setXmlVersion("1.0");
        oof.setCharSetEncoding("GBK");
        // ����??����
        // default OM output format: version=1.0, encoding=utf-8
        doc.serialize(new FileOutputStream(FILE_ROOT
                + "pull_root_write_001.xml"));
        doc.serialize(new FileOutputStream(FILE_ROOT
                + "pull_root_write_002.xml"), oof);

        // ����?? �ģ�
        oof.setDoOptimize(true);
        doc.serializeAndConsume(new FileOutputStream(FILE_ROOT
                + "pull_root_write_003.xml"));
        doc.serializeAndConsume(new FileOutputStream(FILE_ROOT
                + "pull_root_write_004.xml"), oof);

        // �����壺 ����writer�������??
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
        // ele.serialize(System.out); // ��ʹdetach()����Ȼ�����ele
        // doc.serialize(System.out); // ���detach()���Ͳ�����ele��document��??

    }

    private void testRead() throws FileNotFoundException, XMLStreamException,
            FactoryConfigurationError {

        // ���ȹ���parser??
        XMLStreamReader parser = XMLInputFactory
                .newInstance()
                .createXMLStreamReader(
                        PullXmlHelloTest.class
                                .getResourceAsStream("pull_root_read_test.xml"));
        // ����Ҫbuilder����??
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

        // OMElement��һϵ�е�get�������������??
        firstAddressElement.serialize(System.out); // cache on, ǿ�ƹ���OMTre
        firstAddressElement.serializeAndConsume(System.out); // cache off, ��ǿ�ƹ���OMTre
        System.out.println();
        System.out.println();

        // will NOT build the OMTree in the memory.
        // So you are at your own risk of losing information.
        String firstAddressString = firstAddressElement.toStringWithConsume();
        // call toString, will build the OMTree in the memory.
        System.out.println(firstAddressString);

        // ����serialize��serializeAndConsume��ǰ�߻�ǿ�ƹ���OMTree�������򲻻�?? // ����detach����ֻӰ��OMElement�����OMTree�Ĺ�ϵ������Ӱ��OMElement����??
        // // ��֮��Ӧ�Ļ���һ��build������build��ǿ��build����OMTree����?? //
        // ����������??�����ڴ���OMElement��OMTree�Ĺ�ϵ�ϡ���������������OMElement(build)�Լ���OMElement���������Ͽ�(detach)���Ա�ŵ����������������������ǲ�ͬ��OMTree??
        // 
    }

}
