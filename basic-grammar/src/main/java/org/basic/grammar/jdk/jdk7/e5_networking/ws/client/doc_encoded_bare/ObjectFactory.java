package org.basic.grammar.jdk.jdk7.e5_networking.ws.client.doc_encoded_bare;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.basic.jdk6.ws.client.doc_encoded_bare package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListUserResponse_QNAME = new QName("http://www.wstest.org/hello", "listUserResponse");
    private final static QName _ListUser_QNAME = new QName("http://www.wstest.org/hello", "listUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.basic.jdk6.ws.client.doc_encoded_bare
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserArray }
     * 
     */
    public UserArray createUserArray() {
        return new UserArray();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wstest.org/hello", name = "listUserResponse")
    public JAXBElement<UserArray> createListUserResponse(UserArray value) {
        return new JAXBElement<UserArray>(_ListUserResponse_QNAME, UserArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wstest.org/hello", name = "listUser")
    public JAXBElement<String> createListUser(String value) {
        return new JAXBElement<String>(_ListUser_QNAME, String.class, null, value);
    }

}
