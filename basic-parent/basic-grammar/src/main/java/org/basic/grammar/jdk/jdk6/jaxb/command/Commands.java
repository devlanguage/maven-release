package org.basic.grammar.jdk.jdk6.jaxb.command;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 */
@XmlRootElement
public class Commands {

    // @XmlElement(name="command")
    private List<CommandMap> commandMap;

    
    @XmlJavaTypeAdapter(CommandMapAdapter.class)
    public java.util.HashMap commands;

    /**
     * @return get method for the field commandSet
     */

    public List<CommandMap> getCommands() {

        return this.commandMap;
    }
    

    //    
    // /**
    // * @param commandSet the commandSet to set
    // */
    // public void setCommands(List<Command> commandSet) {
    //    
    // this.commandSet = commandSet;
    // }

}
