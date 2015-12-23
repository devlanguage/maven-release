/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.common.util.MessageUtil.java is created on Sep 26, 2007 11:25:26 AM
 */
package org.third.orm.hibernate3.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.third.orm.hibernate3.common.bean.CommonConstants;
import org.third.orm.hibernate3.common.bean.FileType;
import org.third.orm.hibernate3.common.bean.RelativePath;

public class MessageUtil {

    private static final Log logger = LogFactory.getLog(MessageUtil.class);
    private static Properties MESSAGE_LIST;
    // private final static Logger logger = Logger.getLogger(MessageUtil.class);

    static {
        MESSAGE_LIST = new Properties();
        try {
            MESSAGE_LIST.load(MessageUtil.class.getClassLoader().getResourceAsStream(
                    CommonConstants.COMMON_MESSAGES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final static String getMessage(String key) {

        return MESSAGE_LIST.getProperty(key);
    }

    public final static String getMessage(String key, String defaultValue) {

        return MESSAGE_LIST.getProperty(key, defaultValue);
    }

    public final static void addMessage(String key, String defaultValue) {

        // MESSAGE_LIST.
        MESSAGE_LIST.put(key, defaultValue);

    }

    public final static void addMessages(Map<Object, Object> messageEntries) {

        MESSAGE_LIST.putAll(messageEntries);
    }

    /**
     * Add the message into the message repository from specified message file. For example.
     * addMessages("org/basic/common/config.properties", null, null);<br>
     * addMessages("org/basic/common/config.xml", FileType.XML, null);<br>
     * addMessages("c:/configpath/config.xml", FileType.XML,
     * RelativePath.OPERATE_SYSTEM_ABSOLUTE_PATH);<br>
     * Notes:These are message file format example: <br>
     * 
     * <pre>
     * &lt;!--config.properties:--&gt;
     *      key1 = value1
     *      key2 = value2
     * 
     * &lt;!--config.xml:--&gt;
     * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
     * &lt;!DOCTYPE properties SYSTEM &quot;http://java.sun.com/dtd/properties.dtd&quot;&gt;
     * &lt;properties&gt;
     *      &lt;comment&gt;comments&lt;/comment&gt;
     *      &lt;entry key=&quot;key1&quot;&gt;value1&lt;/entry&gt;
     *      &lt;entry key=&quot;key2&quot;&gt;value2&lt;/entry&gt;
     * &lt;/properties&gt;
     * </pre>
     * 
     * @param messageFile
     *            message file path.
     * @param fileType
     *            file type of message file, by default, fileType is {@link FileType#PROPERTIES}
     * @param relativePath
     *            how to find out the message file, by default, fileType is
     *            {@link RelativePath#CLASS_PATH}
     * @throws BasicException
     */
    public final static void addMessages(String messageFile, FileType fileType,
            RelativePath relativePath) throws BasicException {

        fileType = fileType == null ? FileType.PROPERTIES : fileType;
        relativePath = relativePath == null ? RelativePath.CLASS_PATH : relativePath;

        InputStream ins = null;
        Properties messageEntries = new Properties();
        try {
            switch (relativePath) {
                case CLASS_PATH: {
                    ins = StreamUtil.getInputStreamFromClassPath(messageFile);
                    break;
                }

                case OPERATE_SYSTEM_ABSOLUTE_PATH: {
                    ins = StreamUtil.getInputStreamFromAbosulateClassPath(messageFile);
                    messageEntries.load(StreamUtil
                            .getInputStreamFromAbosulateClassPath(messageFile));
                    MESSAGE_LIST.putAll(messageEntries);
                    break;
                }
            }
            switch (fileType) {
                case PROPERTIES: {
                    messageEntries.load(ins);
                    break;
                }
                case XML: {
                    messageEntries.loadFromXML(ins);
                    break;
                }
                default: {
                    throw new BasicException("Unvalid the file type. pls refer to "
                            + FileType.class.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            SystemUtil.handleException(logger, e, "Failed to load the message file [FilePath="
                    + messageFile + "");
        } finally {
            StreamUtil.close(ins);
        }
        MESSAGE_LIST.putAll(messageEntries);
    }

    public final static void removeMessage(Object key) {

        MESSAGE_LIST.remove(key);
    }
}
