package org.basic.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.util.DOMConstants;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

class JaxbSchemaOutputResolver extends SchemaOutputResolver {

	private File f;

	public JaxbSchemaOutputResolver(String fileName) {
		f = new File(fileName);
	}

	public JaxbSchemaOutputResolver(String dir, String fileName) {
		f = new File(dir, fileName);
	}

	@Override
	public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

class ClientCertificateValidatorConfig {

	public final static String CERTIFICATE_PARSER_CLASS_ATT_NAME = "class";
	public final static String USER_NAME_ATTRIBUTE_NAME_ATT_NAME = "attributeName";
	public final static String USER_NAME_HEADER_NAME_ATT_NAME = "headerName";

	public final static String USER_NAME_ATTRIBUTE_NAME_DEF_VAL = "cn";

	/**
	 * denotes a fully qualified class name that implements the interface
	 * {@link com.hp.ccue.identity.hpssoImpl.validators.clientCertificate.UserIdentifierRetriever}
	 * if null, the default one is used
	 */
	private String certificateParserClass;
	/**
	 * Denotes the name of the attribute that holds the user name. The attribute is
	 * part ofthe certificate to be parsed by {@link #certificateParserClass}
	 */
	private String userNameAttributeName;
	/**
	 * Denotes the header name that holds the client certificate
	 */
	private String userNameHeaderName;

	public ClientCertificateValidatorConfig() {
	}

	@Override
	public ClientCertificateValidatorConfig clone() throws CloneNotSupportedException {

		return (ClientCertificateValidatorConfig) super.clone();

	}

	@XmlAttribute(name = "class")
	public String getCertificateParserClass() {

		return certificateParserClass;
	}

	public void setCertificateParserClass(String certificateParserClass) {

		this.certificateParserClass = certificateParserClass;
	}

	@XmlAttribute(name = "attributeName")
	public String getUserNameAttributeName() {

		return userNameAttributeName;
	}

	public void setUserNameAttributeName(String userNameAttributeName) {

		this.userNameAttributeName = userNameAttributeName;
	}

	@XmlAttribute(name = "headerName")
	public String getUserNameHeaderName() {

		return userNameHeaderName;
	}

	public void setUserNameHeaderName(String userNameHeaderName) {

		this.userNameHeaderName = userNameHeaderName;
	}
}

@XmlRegistry
class JaxbObjectFactory {

	@XmlElementDecl(name = "ClientCertificate")
	public JAXBElement<ClientCertificateValidatorConfig> createClientCertificate(ClientCertificateValidatorConfig cac) {

		return new JAXBElement<ClientCertificateValidatorConfig>(new QName("ClientCertificate"),
				ClientCertificateValidatorConfig.class, cac);
	}

}

public class XmlUtils {
	private static javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory;
	static DocumentBuilder builder = null;
	private final static Logger logger = Logger.getLogger(XmlUtils.class.getName());

	static {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			documentBuilderFactory.setExpandEntityReferences(false);
			documentBuilderFactory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
			builder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		javax.xml.parsers.SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			spf.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
			javax.xml.parsers.SAXParser sp = spf.newSAXParser();
			org.xml.sax.XMLReader xmlReader = sp.getXMLReader();
		} catch (ParserConfigurationException | SAXException e) {
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
			SystemUtils.handleException(logger, e, "");
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

		return createDocument(XmlUtils.class.getClassLoader().getResourceAsStream(filename));
	}

	public static Document createDocumentByString(String xmlFileContent) throws BasicException {

		ByteArrayInputStream ins = new ByteArrayInputStream(xmlFileContent.getBytes());
		return createDocument(ins);
	}

	public final static Element createRootNode(String rootNodeName) {

		return createDocument(rootNodeName).getDocumentElement();
	}

	public static boolean deleteNode(Node source, Node node) {

		// UtilityMgr.debug("Search " + node.getNodeName() + " in " +
		// source.getNodeName() );
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

		assert !SystemUtils.isNullOrBlank(parent);
		NodeList nodeList = parent.getElementsByTagName(element);
		return nodeList == null ? null : (Element) nodeList.item(0);
	}

	public final static NodeList getNodeList(Element parent, String element) {

		assert !SystemUtils.isNullOrBlank(parent);
		return parent.getElementsByTagName(element);
	}

	public final static String getNodeValue(Element parent, String element) {

		assert !SystemUtils.isNullOrBlank(parent);

		Node firstChild = getNode(parent, element);
		return firstChild == null ? null : getNodeValue(firstChild);
	}

	public final static String getNodeValue(Node node) {

		assert !SystemUtils.isNullOrBlank(node);
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

	public static final String nodeToString(org.w3c.dom.Element element) throws IOException {
		String resultXmlStr;

		Document doc = element.getOwnerDocument();
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter(DOMConstants.DOM_WELL_FORMED_PARAM, false);

		resultXmlStr = serializer.writeToString(element);
		return resultXmlStr;
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
		StringWriter output = new StringWriter();
		StreamResult result = new StreamResult(output);
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			output.flush();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return output.toString();
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
				boolean notFileEnd = true;
				System.out.println(outputAccessor.getFilePointer());
				long lastPosition = -1;
				while ((line = outputAccessor.readLine()) != null && notFileEnd) {

					if (line.trim().startsWith(endTag)) {
						outputAccessor.seek(lastPosition);
						outputAccessor.write(nodeToString(xmlNode, false).getBytes());
						outputAccessor.write(endTag.getBytes());
						notFileEnd = false;
					}
					lastPosition = outputAccessor.getFilePointer();
//                    outputAccessor.write(line.getBytes());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				SystemUtils.closeQuitely(outputAccessor);
			}
		} else {
			try {
				outputAccessor = new RandomAccessFile(file, "rw");
				outputAccessor.write(nodeToString(xmlNode, false).getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				SystemUtils.closeQuitely(outputAccessor);
			}
		}
	}

	public static void main(String[] args) {

		try {
			Document doc = createDocument();

			Element usersEle = doc.createElement("users");
			Comment usersComment = doc.createComment("Define user list");
			usersEle.appendChild(usersComment);

			Element userZhangEle = doc.createElement("user");
			userZhangEle.setAttribute("id", "001");
			userZhangEle.setAttribute("name", "test1");
			usersEle.appendChild(userZhangEle);

			Element userWangEle = doc.createElement("user");
			userWangEle.setAttribute("id", "001");
			userWangEle.setAttribute("name", "test1");
			usersEle.appendChild(userWangEle);

			doc.appendChild(usersEle);
			System.out.println(nodeToString(usersEle));

			mergeXmlToFile(doc, "output.xml");
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
