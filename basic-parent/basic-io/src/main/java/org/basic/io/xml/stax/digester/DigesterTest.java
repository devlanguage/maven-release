package org.basic.io.xml.stax.digester;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.digester.xmlrules.DigesterRuleParser;

public class DigesterTest {

    public static void main(String[] args) {

        Digester xmlParser = DigesterLoader.createDigester(DigesterTest.class
                .getResource("student_teacher.xml"));
        
    }
}
