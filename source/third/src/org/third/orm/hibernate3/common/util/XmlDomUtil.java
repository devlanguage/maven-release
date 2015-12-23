/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file org.basic.util.DomUtil.java is
 * created on Sep 22, 2007 11:24:23 AM
 */
package org.third.orm.hibernate3.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 */
public class XmlDomUtil {

    static DocumentBuilder builder = null;
    private final static Log logger = LogFactory.getLog(XmlDomUtil.class);

    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Document createDocument() {

        return builder.newDocument();
    }
    public final static Document createDocument(InputStream in) throws BasicException {

        Document doc = null;
        try {
            doc = builder.parse(in);
        } catch (Exception e) {
            throw SystemUtil.handleException(logger, e, "");
        }
        return doc;
    }
    public static Document createDocument(String rootNodeName) {

        Document doc = createDocument();
        doc.appendChild(doc.createElement(rootNodeName));
        return doc;
    }

    public static Document createDocumentByFile(String filename) throws FileNotFoundException,
            BasicException {

        return createDocument(new FileInputStream(filename));
    }

    public static Document createDocumentByString(String xmlFileContent) throws BasicException {

        ByteArrayInputStream ins = new ByteArrayInputStream(xmlFileContent.getBytes());
        return createDocument(ins);
    }

    public final static Element createRootNode(String rootNodeName) {
        return createDocument(rootNodeName).getDocumentElement();
    }

    public static boolean deleteNode(Node source, Node node) {

        // UtilityMgr.debug("Search " + node.getNodeName() + " in " + source.getNodeName() );
        if (!source.hasChildNodes()) {
            return false;
        }
        NodeList list = source.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.equals(node)) {

                n.getParentNode().removeChild(node);
                return true;
            } else {
                if (deleteNode(n, node)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public final static Element getNode(Element parent, String element) {

        assert !SystemUtil.isNullOrBlank(parent);
        NodeList nodeList = parent.getElementsByTagName(element);
        return nodeList == null ? null : (Element) nodeList.item(0);
    }

    public final static NodeList getNodeList(Element parent, String element) {

        assert !SystemUtil.isNullOrBlank(parent);
        return parent.getElementsByTagName(element);
    }

    public final static String getNodeValue(Element parent, String element) {

        assert !SystemUtil.isNullOrBlank(parent);

        Node firstChild = getNode(parent, element);
        return firstChild == null ? null : getNodeValue(firstChild);
    }

    public final static String getNodeValue(Node node) {

        assert !SystemUtil.isNullOrBlank(node);
        return node.getFirstChild() == null ? null : node.getFirstChild().getNodeValue();
    }

    public static Element getRootNode(Document doc) {

        return doc.getDocumentElement();
    }

    public static Element getRootNode(InputStream ins) throws BasicException {

        return getRootNode(createDocument(ins));
    }

    public static Element getRootNode(String xmlFileContent) throws BasicException {

        return getRootNode(createDocumentByString(xmlFileContent));
    }

    public static boolean isValid(String filename) {

        boolean bValid = false;
        try {
            builder.parse(filename);
            bValid = true;
        } catch (SAXException sxe) {
            bValid = false;
            System.err.println(sxe.getMessage());
        } catch (Exception e) {
            bValid = false;
            System.err.println(e.getMessage());
        }
        return bValid;
    }

    public final static void serialize(Element node, File file, boolean canonical)
            throws BasicException {

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            out.write(serialize(node, canonical).getBytes());
        } catch (FileNotFoundException e) {
            throw SystemUtil.handleException(logger, e, "Failed to find the file " + file);
        } catch (IOException e) {
            throw SystemUtil.handleException(logger, e,
                    "Failed to export the xml node value into the file " + file);
        }

    }

    public final static void serialize(Element node, String file, boolean canonical)
            throws BasicException {

        serialize(node, new File(file), canonical);
    }

    public static String serialize(Node node, boolean canonical) throws BasicException {

        String xmlString = "";
        try {
            DOMSource domSource = new DOMSource(node);
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(domSource, result);
            xmlString = sw.toString();

        } catch (Exception e) {
            throw SystemUtil.handleException(logger, e, "Failed to export the xml node as String ");
        }
        return xmlString;
    }

    public static String xmlToString(Document doc, boolean canonical) {

        String xmlString = "";
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(domSource, result);
            xmlString = sw.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlString;
    }

}
