/**
 * 
 */
package org.basic.xml.stax.axiom.person;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.utils.BeanUtil;

/**
 * @author ygong
 * 
 */
public class JavaObjectToOMElement {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Person man = new Person();
        man.setName("Warlaze");
        man.setAge(25);
        man.setAddress("Bei jing");
        man.setPhonenum("13900000000");

        List<Person> personList = new ArrayList<Person>();
        personList.add(man);
        personList.add(man);
        OMElement element = BeanUtil.getOMElement(new QName("persons"), personList.toArray(),
                new QName("person"), false, null);

        // javax.xml.stream.XMLStreamReader reader = BeanUtil.getPullParser(personList);
        // StreamWrapper parser = new StreamWrapper(reader);
        // StAXOMBuilder stAXOMBuilder = OMXMLBuilderFactory.createStAXOMBuilder(OMAbstractFactory
        // .getOMFactory(), parser);
        // OMElement element = stAXOMBuilder.getDocumentElement();
        System.out.println(element);

    }

}
