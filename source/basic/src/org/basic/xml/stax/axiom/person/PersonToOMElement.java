/**
 * 
 */
package org.basic.xml.stax.axiom.person;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.llom.factory.OMXMLBuilderFactory;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.util.StreamWrapper;

/**
 * @author ygong
 * 
 */
public class PersonToOMElement {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Person man = new Person();
        man.setName("Warlaze");
        man.setAge(25);
        man.setAddress("Bei jing");
        man.setPhonenum("13900000000");
        javax.xml.stream.XMLStreamReader reader = BeanUtil.getPullParser(man);
        StreamWrapper parser = new StreamWrapper(reader);
        StAXOMBuilder stAXOMBuilder = OMXMLBuilderFactory.createStAXOMBuilder(OMAbstractFactory
                .getOMFactory(), parser);
        OMElement element = stAXOMBuilder.getDocumentElement();
        System.out.println(element);

    }

}
