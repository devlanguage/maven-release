package org.basic.io.xml.stax.stax;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * <pre>
 * StAX包括两套处理XML的API，分别提供了不同程度的抽象。它们是：基于指针的API和基于迭代器的API。
 * 
 * 我们先来了解基于指针的API。它把XML作为一个标记（或事件）流来处理，应用程序可以检查解析器的状态，获得解析的上一个标记的信息，然后再处理下一个标记，依次类推。
 * 
 * 在开始API探索之前，我们首先创建一个名为users.xml的XML文档用于测试，它的内容如下：
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
 * <company>
 *     <depart title="Develop Group">
 *         <user name="Tom" age="28" gender="male" >Manager</user>
 *         <user name="Lily" age="26" gender="female" />
 *     </depart>
 *     <depart title="Test Group">
 *         <user name="Frank" age="32" gender="male" >Team Leader</user>
 *         <user name="Bob" age="45" gender="male" />
 *         <user name="Kate" age="25" gender="female" />
 *     </depart>
 * </company>
 * 
 * 可以让我们使用基于指针的API的接口是javax.xml.stream.XMLStreamReader（很遗憾，你不能直接实例化它），要得到它的实例，我们需要借助于javax.xml.stream.XMLInputFactory类。根据JAXP的传统风格，这里使用了抽象工厂（Abstract Factory）模式。如果你对这个模式很熟悉的话，就能够在脑海中想象出我们将要编写的代码的大致框架了。
 * 
 * 首先，获得一个XMLInputFactory的实例。方法是：
 * 
 * XMLInputFactory factory = XMLInputFactory.newInstance();
 * 
 * 或者：
 * 
 * XMLInputFactory factory = XMLInputFactory.newFactory();
 * 
 * 这两个方法是等价的，它们都是创建了一个新的实例，甚至实例的类型都是完全一致的。因为它们的内部实现都是：
 * 
 * {
 *     return (XMLInputFactory) FactoryFinder.find("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl");
 * }
 * 
 * 接下来我们就可以创建XMLStreamReader实例了。我们有这样一组方法可以选择：
 * 
 * XMLStreamReader createXMLStreamReader(java.io.Reader reader) throws XMLStreamException;
 * 
 * XMLStreamReader createXMLStreamReader(javax.xml.tranform.Source source) throws XMLStreamException;
 *     
 * XMLStreamReader createXMLStreamReader(java.io.InputStream stream) throws XMLStreamException;
 * 
 * XMLStreamReader createXMLStreamReader(java.io.InputStream stream, String encoding) throws XMLStreamException;
 * 
 * XMLStreamReader createXMLStreamReader(String systemId, java.io.InputStream stream) throws XMLStreamException;
 * 
 * XMLStreamReader createXMLStreamReader(String systemId, java.io.Reader reader) throws XMLStreamException;
 * 
 * 这些方法都会根据给定的流创建一个XMLStreamReader实例，大家可以依据流的类型、是否需要指定解析XML的编码或者systemId来选择相应的方法。
 * 
 * 在这里，我们对systemId稍作说明，并简单解释一下它与publicId的区别。
 * 
 * systemId和publicId是XML文档里DOCTYPE元素中经常出现的两个属性。它们都是对外部资源的引用，用以指明引用资源的地址。systemId是直接引用资源，publicId是间接定位外部资源。具体一点说是这样：
 * 
 * systemId：外部资源（大多是DTD文件）的URI。比如本地文件file:///user/dtd/users.dtd或者网络某个地址的文件http://www.w3.org/dtd/users.dtd。
 * 
 * publicId：相当于一个名字，这个名字代表了一个外部资源。比如，我们规定"W3C HTML 4.0.1"这个字符串对应"http://www.w3.org/dtd/users.dtd"这个资源。那么，publicId="W3C HTML 4.0.1"和systemId="http://www.w3.org/dtd/users.dtd"的作用就是一样的。
 * 
 * 好了，我们接着用以上列出的第一个接口来创建一个XMLStreamReader实例：
 * 
 * try {
 *     XMLStreamReader reader = factory.createXMLStreamReader(new FileReader("users.xml"));
 * } catch (FileNotFoundException e) {
 *     e.printStackTrace();
 * } catch (XMLStreamException e) {
 *     e.printStackTrace();
 * }
 * 
 * 要遍历XML文档，需要用到XMLStreamReader的下面几个方法：
 * 
 * int getEventType();
 * 
 * boolean hasNext() throws XMLStreamException;
 * 
 * int next() throws XMLStreamException;
 * 
 * getEventType()方法返回XMLStreamConstants接口中定义的一个标记常量，表示当前指针所指向标记（或事件）的类型。根据当前事件类型的不同，应用程序可以做出不同的处理。标记常量的类型和含义如下：
 * 
 * START_DOCUMENT：文档的开始
 * END_DOCUMENT：文档的结尾
 * START_ELEMENT：元素的开始
 * END_ELEMENT：元素的结尾
 * PROCESSING_INSTRUCTION：处理指令
 * CHARACTERS：字符（文本或空格）
 * COMMENT：注释
 * SPACE：可忽略的空格
 * ENTITY_REFERENCE：实体的引用
 * ATTRIBUTE：元素的属性
 * DTD：DTD
 * CDATA：CDATA块
 * NAMESPACE：命名空间的声明
 * NOTATION_DECLARATION：标记的声明
 * ENTITY_DECLARATION：实体的声明
 * 
 * next()方法将指针移动到下一个标记，它同时返回这个标记（或事件）的类型。此时若接着调用getEventType()方法则返回相同的值。
 * 
 * hasNext()用于判断是否还有下一个标记。只有当它返回true时才可以调用next()以及其它移动指针的方法。
 * 
 * 看了上面几个方法的介绍，大家就会发现使用XMLStreamReader遍历XML文档是非常容易的，因为它的用法和每个人都熟悉的Java迭代器（Iterator）是一样的。下面我们就用已经掌握的这几个方法对上文中给出的XML文档做一个测试。希望你还记得它的内容，如果忘记了，请翻回去重新浏览一下。
 * 
 * XMLStreamReader的某些方法，无论当前标记（或事件）是什么类型的，都可以被调用。它们的定义和作用如下：
 * String getVersion();//获得XML文档中的版本信息
 * String getEncoding();//获得XML文档中的指定编码
 * javax.xml.namespace.NamespaceContext getNamespaceContext();//获得当前有效的命名空间上下文，包含前缀、URI等信息
 * String getNamespaceURI();//获得当前有效的命名空间的URI
 * javax.xml.stream.Location getLocation();//获得当前标记的位置信息，包含行号、列号等
 * boolean hasName();//判断当前标记是否有名称，比如元素或属性
 * boolean hasText();//判断当前标记是否有文本，比如注释、字符或CDATA
 * boolean isStartElement();//判断当前标记是否是标签开始
 * boolean isEndElement();//判断当前标记是否是标签结尾
 * boolean isCharacters();//判断当前标记是否是字符
 * boolean isWhiteSpace();//判断当前标记是否是空白
 * 
 * 
 * </pre>
 * 
 */

public class StaxApi_OnCursor_Test {

  final static String file_name = "peoples.xml";

  public static void main(String[] args) {

    javax.xml.stream.XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    javax.xml.stream.XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    // configure it to create readers that coalesce adjacent character sections
    inputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
    FileInputStream input = null;
    FileOutputStream output = null;
    try {
      input = new FileInputStream(StaxApi_OnCursor_Test.class.getResource(file_name).getPath());
      testStaxCursorRead(input, inputFactory);
      input.close();
      System.out.println("-==============================================");
      input = new FileInputStream(StaxApi_OnCursor_Test.class.getResource(file_name).getPath());
      testStaxInteratorRead(input, inputFactory);
      input.close();

      // output = new FileOutputStream("user.xml");
      // testStaxCursorWrite(output, outputFactory);
    } catch (Exception e1) {
      e1.printStackTrace();
    }

    // XMLEventReader interatorReader = xmlFac.createXMLStreamReader(input);
  }

  static final String XHTML_NS = "http://www.w3.org/1999/xhtml";
  static final QName HTML_TAG = new QName(XHTML_NS, "html");
  static final QName HEAD_TAG = new QName(XHTML_NS, "head");
  static final QName TITLE_TAG = new QName(XHTML_NS, "title");
  static final QName BODY_TAG = new QName(XHTML_NS, "body");

  private static void testStaxCursorWrite(FileOutputStream output, XMLOutputFactory outputFactory)
      throws XMLStreamException {

    // XMLEventWriter w = outputFactory.createXMLEventWriter(System.out);
    XMLEventFactory ef = XMLEventFactory.newInstance();
    XMLStreamWriter w = outputFactory.createXMLStreamWriter(System.out);
    try {
      w.writeStartDocument();
      w.writeCharacters("\n");
      // w.writeDTD("<!DOCTYPE html " +
      // "PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
      // "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
      // w.writeCharacters("\n");
      w.writeStartElement("uri", "html", "df");
      w.writeDefaultNamespace(XHTML_NS);
      w.writeAttribute("lang", "en");
      w.writeCharacters("\n");
      w.writeStartElement(XHTML_NS, "head");
      w.writeStartElement(XHTML_NS, "title");
      w.writeCharacters("Test");
      w.writeEndElement();
      w.writeEndElement();
      w.writeCharacters("\n");
      w.writeStartElement(XHTML_NS, "body");
      w.writeCharacters("This is a test.");
      w.writeEndElement();
      w.writeEndElement();
      w.writeEndDocument();
    } finally {
      w.close();
    }

  }

  private static void testStaxInteratorRead(FileInputStream input, XMLInputFactory xmlFac) throws XMLStreamException {

    XMLEventReader iteratorReader = null;
    try {
      iteratorReader = xmlFac.createXMLEventReader(input);
      while (iteratorReader.hasNext()) {
        XMLEvent event = iteratorReader.nextEvent();
        // 如果事件对象是元素的开始
        if (event.isStartElement()) {
          // 转换成开始元素事件对象
          StartElement start = event.asStartElement();
          // 打印元素标签的本地名称
          System.out.print(start.getName().getLocalPart());
          // 取得所有属性
          Iterator attrs = start.getAttributes();
          while (attrs.hasNext()) {
            // 打印所有属性信息
            Attribute attr = (Attribute) attrs.next();
            System.out.print(":" + attr.getName().getLocalPart() + "=" + attr.getValue());
          }
          System.out.println();
        }
      }
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } finally {
      iteratorReader.close();
    }

  }

  private static void testStaxCursorRead(FileInputStream input, XMLInputFactory xmlFac) throws XMLStreamException {

    XMLStreamReader cursorReader = null;
    try {
      cursorReader = xmlFac.createXMLStreamReader(input);

      while (cursorReader.hasNext()) {
        int event = cursorReader.getEventType();
        switch (event) {
          case XMLStreamReader.START_DOCUMENT:
            System.out.println("Start Document.");
            break;
          case XMLStreamReader.START_ELEMENT:
            System.out.println("Start Element: " + cursorReader.getName());
            for (int i = 0, n = cursorReader.getAttributeCount(); i < n; ++i) {
              System.out.println("\tAttribute: " + cursorReader.getAttributeName(i) + "="
                  + cursorReader.getAttributeValue(i));
            }
            System.out.println();
            break;
          case XMLStreamReader.CHARACTERS:
            if (cursorReader.isWhiteSpace()) {
              break;
            }
            System.out.println("Text: " + cursorReader.getText());
            break;
          case XMLStreamReader.END_ELEMENT:
            System.out.println("End Element:" + cursorReader.getName());
            break;
          case XMLStreamReader.END_DOCUMENT:
            System.out.println("End Document.");
            break;
        }
        event = cursorReader.next();
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      cursorReader.close();
    }

  }
}
