package org.basic.io.xml.dom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.basic.io.xml.UserJavaBeanForXML;
import org.basic.io.xml.dom.dom4j.CreateXMLForDOM4j;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class CreateXML {

  /**
   * @param args
   */

  // Dom创建xml
  public static String createXMLForDOM(List<UserJavaBeanForXML> list) {

    String xmlStr = null;
    try {
      if (null != list && !list.isEmpty()) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        // 文档对象创建一个 根 元素<users>
        Element users = doc.createElement("users");
        doc.appendChild(users);
        for (UserJavaBeanForXML user : list) {
          // 创建<users>的子元素<user>
          Element userElt = doc.createElement("user");
          users.appendChild(userElt);

          // 创建<user>的子元素<id>
          Element idElt = doc.createElement("uuid");
          userElt.appendChild(idElt);

          // 创建子元素<id>的文本值
          // 第一种方式
          // Text idTxt = doc.createTextNode(String.valueOf(c.getId()));
          // idElt.appendChild(idTxt);
          // 第二种方式
          idElt.setTextContent(String.valueOf(user.getUuid()));

          // 增加一个user的"标识"属性--uuid 并且给出值为1-----------------------------------------------------------------
          Attr id = doc.createAttribute("uuid");
          userElt.setAttributeNode(id);
          id.setValue("1");

          // 创建<city>的子元素<name>
          Element nameElt = doc.createElement("myname");
          userElt.appendChild(nameElt);

          // 创建子元素<name>的文本值
          Text nameTxt = doc.createTextNode(user.getMyname());
          nameElt.appendChild(nameTxt);
          // nameElt.setTextContent(po.getName());

          // 创建<user>的子元素<sex>
          Element sexElt = doc.createElement("sex");
          userElt.appendChild(sexElt);

          // 创建子元素<sex>的文本值
          Text sexEtx = doc.createTextNode(user.getSex());
          sexElt.appendChild(sexEtx);

          // 创建<user>的子元素<age>
          Element ageElt = doc.createElement("age");
          userElt.appendChild(ageElt);

          // 创建子元素<sex>的文本值
          Text ageEtx = doc.createTextNode(user.getAge() + "");
          ageElt.appendChild(ageEtx);

        }
        xmlStr = getDomXml(doc);
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    System.out.println("DOM:" + xmlStr);
    return xmlStr;
  }

  private static String getDomXml(Node doc) {

    String xmlStr = null;
    try {
      // 以 Document Object Model（DOM）树的形式充当转换 Source 树的持有者
      DOMSource source = new DOMSource(doc);

      // 用来生成XML文件
      // 要生成文件需构造PrintWriter的writer,
      // DOM中这种方式name的值没有写进去,由于编码的问题
      // PrintWriter writerXml = new PrintWriter(new FileOutputStream ("city-dom.xml"));
      // 用OutputStreamWriter加了编码就OK了
      PrintWriter writerXml = new PrintWriter(new OutputStreamWriter(new FileOutputStream("city-jdom.xml"), "utf-8"));
      Result resultXml = new StreamResult(writerXml);
      // 实现此接口的对象包含构建转换结果树所需的信息
      // Result resultXml = new StreamResult(new FileOutputStream("city-dom.xml"));

      // 用来得到XML字符串形式
      // 一个字符流，可以用其回收在字符串缓冲区中的输出来构造字符串。
      StringWriter writerStr = new StringWriter();
      Result resultStr = new StreamResult(writerStr);

      // 此抽象类的实例能够将源树转换为结果树。
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      // 设置编码
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      // 是否缩进
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      // 将 XML Source 转换为 Result
      transformer.transform(source, resultXml);
      transformer.transform(source, resultStr);

      // 获取XML字符串
      xmlStr = writerStr.getBuffer().toString();
    } catch (TransformerException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return xmlStr;
  }

  // SAX方式生成XML
  public static String saxToXml(List<UserJavaBeanForXML> list) {

    String xmlStr = null;
    try {
      // 用来生成XML文件
      // 要生成文件需构造PrintWriter的writer
      // PrintWriter writerXml = new PrintWriter("city-sax.xml");
      // Result resultXml = new StreamResult(writerXml);
      // 实现此接口的对象包含构建转换结果树所需的信息
      Result resultXml = new StreamResult(new FileOutputStream("city-sax.xml"));

      // 用来得到XML字符串形式
      // 一个字符流，可以用其回收在字符串缓冲区中的输出来构造字符串
      StringWriter writerStr = new StringWriter();
      // 构建转换结果树所需的信息。
      Result resultStr = new StreamResult(writerStr);

      // 创建SAX转换工厂
      SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
      // 转换处理器，侦听 SAX ContentHandler
      // 解析事件，并将它们转换为结果树 Result
      TransformerHandler th = sff.newTransformerHandler();
      // 将源树转换为结果树
      Transformer transformer = th.getTransformer();
      // 设置字符编码
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      // 是否缩进
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      // 设置与用于转换的此 TransformerHandler 关联的 Result
      // 注：这两个th.setResult不能同时启用
      // th.setResult(resultXml);
      th.setResult(resultStr);

      th.startDocument();
      AttributesImpl attr = new AttributesImpl();
      th.startElement("", "", "users", attr);
      if (null != list && !list.isEmpty()) {
        for (int i = 0; i < list.size(); i++) {
          th.startElement("", "", "user", attr);

          th.startElement("", "", "uuid", attr);
          String id = String.valueOf(list.get(i).getUuid());
          th.characters(id.toCharArray(), 0, id.length());
          th.endElement("", "", "uuid");

          th.startElement("", "", "myname", attr);
          String name = String.valueOf(list.get(i).getMyname());
          th.characters(name.toCharArray(), 0, name.length());
          th.endElement("", "", "myname");

          th.startElement("", "", "sex", attr);
          String sex = String.valueOf(list.get(i).getSex());
          th.characters(sex.toCharArray(), 0, sex.length());
          th.endElement("", "", "sex");

          th.startElement("", "", "age", attr);
          String age = String.valueOf(list.get(i).getAge());
          th.characters(age.toCharArray(), 0, age.length());
          th.endElement("", "", "age");

          th.endElement("", "", "user");
        }
      }

      th.endElement("", "", "users");
      th.endDocument();
      xmlStr = writerStr.getBuffer().toString();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("SAX:" + xmlStr);
    return xmlStr;
  }

  /**
   * StAX生成XML，它是The Streaming API for XML简称 JDK1.6的新增 ********
   * 
   * @param list
   * @return
   */

  public static String stAXToXml(List<UserJavaBeanForXML> list) {

    String xmlStr = null;
    try {
      if (null != list && !list.isEmpty()) {
        StringWriter writerStr = new StringWriter();

        PrintWriter writerXml = new PrintWriter(new OutputStreamWriter(new FileOutputStream("city-StAX.xml"), "utf-8"));

        // 定义用于获取 XMLEventWriter 和 XMLStreamWriter 的工厂抽象实现
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        // 指定如何写XML，注：以下两行只能启用一行
        // XMLStreamWriter xmlsw = xof.createXMLStreamWriter(writerStr);
        XMLStreamWriter xmlsw = xof.createXMLStreamWriter(writerXml);

        // 写入XML文档声明
        xmlsw.writeStartDocument("UTF-8", "1.0");
        xmlsw.writeStartElement("users");
        // 写入注释到xml文档
        xmlsw.writeComment("省和城市信息");
        for (UserJavaBeanForXML user : list) {
          xmlsw.writeStartElement("user");

          // 添加<id>节点
          xmlsw.writeStartElement("id");
          xmlsw.writeCharacters(String.valueOf(user.getUuid()));
          // 结束<id>节点
          xmlsw.writeEndElement();
          // 添加<name>节点
          xmlsw.writeStartElement("name");
          xmlsw.writeCharacters(user.getMyname());
          // 结束<name>节点
          xmlsw.writeEndElement();

          xmlsw.writeEndElement();
        }
        // 结束<cities>节点
        xmlsw.writeEndElement();
        // 结束 XML 文档
        xmlsw.writeEndDocument();
        xmlsw.flush();
        xmlsw.close();

        xmlStr = writerStr.getBuffer().toString();
        writerStr.close();
      }
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("StAX:" + xmlStr);
    return xmlStr;
  }

  public static void main(String[] args) {

    UserJavaBeanForXML user1 = new UserJavaBeanForXML();
    UserJavaBeanForXML user2 = new UserJavaBeanForXML();
    user1.setUuid("1");
    user1.setMyname("ydd");
    user1.setSex("male");
    user1.setAge(26);
    user2.setUuid("2");
    user2.setMyname("shwin");
    user2.setSex("male");
    user2.setAge(25);
    List<UserJavaBeanForXML> userList = new ArrayList<UserJavaBeanForXML>();
    userList.add(user1);
    userList.add(user2);
    createXMLForDOM(userList);
    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    saxToXml(userList);
    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    // stAXToXml(userList);

    // CreateXMLForJDOM.jdomToXml(userList);
    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

    CreateXMLForDOM4j.dom4jToXml(userList);
  }

}