package org.basic.grammar.jvm;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author feiye
 */
public class JdkLogTest {

    private final static Logger logger = LoggerManager.getLogger(JdkLogTest.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        // logger.finest("This is Finest Level");
        // logger.finer("This is Finest Level");
        // logger.fine("This is Finest Level");
        // logger.config("This is Finest Level");
        // logger.info("This is Finest Level");
        // logger.warning("This is Finest Level");
        // logger.log(Level.SEVERE, "This is Finest Level");

        String str = "Z--h-a-g-n-s-a-n";
        char delimiter = '-';
        // List<String> elements = StringUtil.split("-Z--h-a-g-n-s-a-n", '-');
        ArrayList parts = new ArrayList();
        int currentIndex;
        int previousIndex;
        for (previousIndex = 0; (currentIndex = str.indexOf(delimiter, previousIndex)) > 0; previousIndex = currentIndex + 1) {
            String part = str.substring(previousIndex, currentIndex).trim();
            parts.add(part);
        }

        parts.add(str.substring(previousIndex, str.length()).trim());
        System.out.println(parts);
    }
}
