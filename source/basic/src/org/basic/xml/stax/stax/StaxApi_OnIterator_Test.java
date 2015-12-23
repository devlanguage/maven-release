package org.basic.xml.stax.stax;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

public class StaxApi_OnIterator_Test {

    final static String file_name = "peoples.xml";

    public static void main(String[] args) {

        javax.xml.stream.XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        javax.xml.stream.XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // configure it to create readers that coalesce adjacent character sections
        inputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
//            input = new FileInputStream(StaxCursor_Test.class.getResource(file_name).getPath());
//            testStaxCursorRead(input, inputFactory);
//            input.close();
//            input = new FileInputStream(StaxCursor_Test.class.getResource(file_name).getPath());
//            testStaxInteratorRead(input, inputFactory);

            output = new FileOutputStream("user.xml");
            testStaxCursorWrite(output, outputFactory);
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
//              w.writeDTD("<!DOCTYPE html " +   
//                          "PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +   
//                          "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");   
//              w.writeCharacters("\n");   
              w.writeStartElement("uri", "html","df");   
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

        XMLEventReader interatorReader = null;
        try {
            interatorReader = xmlFac.createXMLEventReader(input);
            while (interatorReader.hasNext()) {
                XMLEvent event = interatorReader.nextEvent();
                System.out.println(event);
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            interatorReader.close();
        }

    }

    private static void testStaxCursorRead(FileInputStream input, XMLInputFactory xmlFac) throws XMLStreamException {

        XMLStreamReader cursorReader = null;
        try {
            cursorReader = xmlFac.createXMLStreamReader(input);
            int event = cursorReader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamReader.START_DOCUMENT:
                        System.out.println("Start Document.");
                        break;
                    case XMLStreamReader.START_ELEMENT:
                        System.out.println("Start Element: " + cursorReader.getName());
                        for (int i = 0, n = cursorReader.getAttributeCount(); i < n; ++i) {
                            System.out.println("Attribute: " + cursorReader.getAttributeName(i) + "="
                                    + cursorReader.getAttributeValue(i));
                        }
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

                if (!cursorReader.hasNext())
                    break;

                event = cursorReader.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursorReader.close();
        }

    }
}
