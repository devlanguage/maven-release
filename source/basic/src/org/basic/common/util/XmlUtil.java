/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file org.basic.util.DomUtil.java is created on Sep 22,
 * 2007 11:24:23 AM
 */
package org.basic.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.Random;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 */
public class XmlUtil {

    static DocumentBuilder builder = null;
    private final static Log logger = LogFactory.getLog(XmlUtil.class);

    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Failed to initialize the XML DOM tree", e);
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

    public static Document createDocumentBySystemPathFile(String filename) throws BasicException {

        Document doc = null;
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            doc = createDocument(inputStream);
        } catch (Exception e) {
            throw new BasicException(e);
        }
        return doc;
    }

    public static Document createDocumentByClassPathFile(String filename) throws BasicException {

        return createDocument(XmlUtil.class.getClassLoader().getResourceAsStream(filename));
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

    public final static void serialize(Element node, File file, boolean canonical) throws BasicException {

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            out.write(serialize(node, canonical).getBytes());
        } catch (FileNotFoundException e) {
            throw SystemUtil.handleException(logger, e, "Failed to find the file " + file);
        } catch (IOException e) {
            throw SystemUtil.handleException(logger, e, "Failed to export the xml node value into the file " + file);
        }

    }

    public final static void serialize(Element node, String file, boolean canonical) throws BasicException {

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

    public final static String nodeToString_01(Node node) {

        DOMSource source = new DOMSource(node);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(output);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return new String(output.toByteArray());
    }

    public final static String nodeToString(Node node, boolean containProcessInstruction) {

        DOMSource source = new DOMSource(node);
        StringWriter out = new StringWriter();
        StreamResult result = new StreamResult(out);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            out.flush();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        if (containProcessInstruction) {
            return out.toString();
        } else {
            String output = out.toString();
            String startTag = null, endTag = null;

            if (node instanceof Document) {
                startTag = ((Document) node).getDocumentElement().getNodeName();
            } else {
                startTag = node.getNodeName();
            }
            endTag = "</" + startTag + ">";
            startTag = "<" + startTag + ">";
            return output.substring(output.indexOf(startTag) + startTag.length() + 2, output.lastIndexOf(endTag));
        }
    }

    public static void printNodeInfo(Node node) {

        System.out.println(node.getNodeName() + " : " + node.getNodeValue());
    }

    public static void printNode(Node node) {

        short nodeType = node.getNodeType();
        switch (nodeType) {
            case Node.PROCESSING_INSTRUCTION_NODE:
                System.out.println("-----------PI start-----------");
                printNodeInfo(node);
                System.out.println("-----------PI end-----------");
                break;
            case Node.ELEMENT_NODE:
                System.out.println("-----------Element start-----------");
                printNodeInfo(node);
                System.out.println("-----------Element end-----------");
                NamedNodeMap attrs = node.getAttributes();
                int attrNum = attrs.getLength();
                for (int i = 0; i < attrNum; i++) {
                    Node attr = attrs.item(i);
                    System.out.println("-----------Attribute start-----------");
                    printNodeInfo(attr);
                    System.out.println("-----------Attribute end-----------");
                }
                break;
            case Node.TEXT_NODE:
                System.out.println("-----------Text start-----------");
                printNodeInfo(node);
                System.out.println("-----------Text end-----------");
                break;
            default:
                break;
        }
        Node child = node.getFirstChild();
        while (child != null) {
            printNode(child);
            child = child.getNextSibling();
        }
    }

    public final static void mergeXmlToFile(Node xmlNode, String file) {

        File output = new File(file);
        RandomAccessFile outputAccessor = null;

        if (output.exists()) {
            String rootNodeName = null;
            if (xmlNode instanceof org.w3c.dom.Document) {
                Document doc = (Document) xmlNode;
                rootNodeName = doc.getDocumentElement().getNodeName();
            } else {
                rootNodeName = xmlNode.getOwnerDocument().getDocumentElement().getNodeName();
            }

            String startTag = "<" + rootNodeName + ">";
            String endTag = "</" + rootNodeName + ">";
            BufferedReader fin = null;
            try {
                outputAccessor = new RandomAccessFile(file, "rw");
                String line = null;
                boolean notFileEnd = true;System.out.println(outputAccessor.getFilePointer());
                long lastPosition = -1;
                while ((line = outputAccessor.readLine()) != null && notFileEnd) {
                    
                    if (line.trim().startsWith(endTag)) {
                        outputAccessor.seek(lastPosition);
                        outputAccessor.write(nodeToString(xmlNode, false).getBytes());
                        outputAccessor.write(endTag.getBytes());
                        notFileEnd = false;
                    }
                    lastPosition =outputAccessor.getFilePointer();
//                    outputAccessor.write(line.getBytes());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SystemUtil.closeQuitely(outputAccessor);
            }
        } else {
            try {
                outputAccessor = new RandomAccessFile(file, "w");
                outputAccessor.write(nodeToString(xmlNode, false).getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SystemUtil.closeQuitely(outputAccessor);
            }
        }
    }

    public static void main(String[] args) {

        try {
            Document doc = createDocumentBySystemPathFile("C:/software/Java_Dev/eclipse_proj/java_proj/JavaBasic/user1.xml");
            mergeXmlToFile(doc, "output.xml");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
