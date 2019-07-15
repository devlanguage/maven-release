package org.basic.io.xml.sax;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.basic.io.xml.UserJavaBeanForXML;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParseXml extends DefaultHandler {

    private static Stack<UserJavaBeanForXML> users = new Stack<UserJavaBeanForXML>();
    private UserJavaBeanForXML currentUser;
    private Stack<String> tags = new Stack<String>();

    public SAXParseXml() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals("user")) {
            currentUser = new UserJavaBeanForXML();
            users.add(currentUser);
        }
        tags.push(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String tag = (String) tags.peek();
        if (currentUser != null) {
            if (tag.equals("uuid")) {
                currentUser.setUuid(new String(ch, start, length));
            } else if (tag.equals("myname")) {
                currentUser.setMyname(new String(ch, start, length));
            } else if (tag.equals("sex")) {
                currentUser.setSex(new String(ch, start, length));
            } else if (tag.equals("myname")) {
                currentUser.setMyname(new String(ch, start, length));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("user")) {
            currentUser = null;
        }
        tags.pop();
    }

    private static final String XML_FILE = "org/basic/xml/Users.xml";

    public static InputStream getXmlFileStream() {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_FILE);
    }

    public static void main(String[] args) {

        long lasting = System.currentTimeMillis();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            SAXParseXml reader = new SAXParseXml();
            InputStream in = getXmlFileStream();
            sp.parse(new InputSource(in), reader);
            System.out.println(users);
            System.out.println("运行时间：" + (System.currentTimeMillis() - lasting) + " 毫秒");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}