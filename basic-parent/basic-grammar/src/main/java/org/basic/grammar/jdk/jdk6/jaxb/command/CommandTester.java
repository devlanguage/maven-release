package org.basic.grammar.jdk.jdk6.jaxb.command;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;



/**
 * 
 */
public class CommandTester {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
//            JAXBContext ctx = JAXBContext.newInstance(Commands.class);
//            Source source = new StreamSource(new StringReader(
//                    PlatformConstants.NBI_PLATFORM_ADMIN_COMMANDS));
//            Commands commands = (Commands) ctx.createUnmarshaller().unmarshal(source);
//            System.out.println(commands.getCommands());
//            System.out.println(commands.commands);
//            
            System.out.println(PlatformConstants.NBI_PLATFORM_ADMIN_COMMANDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
