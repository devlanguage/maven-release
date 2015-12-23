/**
 * 
 */
package org.basic.xml.stax.axiom.person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;

/**
 * @author ygong
 * 
 */
public class OMElementToList {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 首先构建parser�?
        XMLStreamReader parser = XMLInputFactory.newInstance().createXMLStreamReader(
                OMElementToList.class.getResourceAsStream("Persons.xml"));
        // 还需要builder对象�?
        StAXOMBuilder builder = new StAXOMBuilder(parser);
        // get the root element
        // OMElement documentElement = builder.getDocumentElement();
        OMElement rootElement = builder.getDocument().getOMDocumentElement();
        System.out.println(rootElement);

        Iterator iterator = rootElement.getChildElements();
        List<Person> list = new ArrayList<Person>();
        while (iterator.hasNext()) {
            OMNode omNode = (OMNode) iterator.next();
            if (omNode.getType() == OMNode.ELEMENT_NODE) {
                OMElement omElement = (OMElement) omNode;
                if (omElement.getLocalName().toLowerCase().equals("person")) {
                    Person person = (Person) BeanUtil.processObject(omElement, Person.class, null,
                            true, new DefaultObjectSupplier());
                    list.add(person);
                }
            }
        }
        for (Person person : list) {
            System.out.println(person);
        }

    }

}
