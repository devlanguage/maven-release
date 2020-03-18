package org.basic.jdk.core.jdk.jdk6.jaxb.command;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * 
 */
@XmlRootElement
public class Command {

    @XmlAttribute
    private String name;
    @XmlValue
    private String description;
//    @XmlElement
//    private String name;
//    @XmlElement
//    private String description;
//    @XmlElement(name = "response_message")
//    private String response;

}
