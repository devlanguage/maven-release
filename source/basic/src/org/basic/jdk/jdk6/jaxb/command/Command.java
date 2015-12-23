/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jdk6.xml.jaxb.command.Command.java is created on 2007-9-14 下午04:47:50
 */
package org.basic.jdk.jdk6.jaxb.command;

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
