package org.basic.common.bean;

import static org.basic.common.bean.CommonConstants.CONFIG_FILE_COMMON_SIMBOL;
import static org.basic.common.bean.CommonConstants.STRING_DELIMITER_EQUAL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.basic.common.util.ConfigFileMonitor;
import org.basic.common.util.SystemUtil;

public class CommonConfiguration extends Hashtable<String, String> {

    
    public String getConfigFile() {
    
        return fileFullPath;
    }

    class PrefixStack {

        private java.util.Stack<String> prefixStack = new java.util.Stack<String>();
        private StringBuilder prefix = new StringBuilder();

        public String getPrefix() {

            return prefix.toString();
        }

        public String pop() {

            if (prefixStack.isEmpty()) {
                return null;
            } else {
                String item = prefixStack.pop();
                if (item.equals(prefix.toString())) {
                    prefix = new StringBuilder();
                } else {
                    prefix = new StringBuilder(prefix.substring(0, prefix.indexOf(item.trim()) - 1));
                }
                return item;
            }
        }

        public String push(String item) {

            if (!SystemUtil.isNullOrBlank(prefix)) {
                prefix.append(".");
            }
            String itemString = item.trim();
            prefix.append(itemString);
            return prefixStack.push(itemString);
        }
    }

    private static final long serialVersionUID = 4112578634029877590L;

    final static CommonLogger logger = CommonLogger.getLogger(CommonConfiguration.class);

    /**
     * A property list that contains default values for any keys not found in this property list.
     * 
     * @serial
     */
    protected CommonConfiguration defaults;
    private String fileFullPath;

    /**
     * Creates an empty property list with no default values.
     * 
     * @param path
     */
    public CommonConfiguration(String path) {

        this(path, false);
    }

    public CommonConfiguration(String path, boolean supportLoadDynamically) {

        this.fileFullPath = path;
//        if (supportLoadDynamically) {
//            Thread configFileMonitorThread = new Thread(new ConfigFileMonitor(fileFullPath), "ConfigFileMonitor"
//                    + fileFullPath);
//            configFileMonitorThread.setDaemon(true);
//            configFileMonitorThread.start();
//        }
    }

    /**
     * Calls the <tt>Hashtable</tt> method <code>put</code>. Provided for parallelism with the <tt>getProperty</tt>
     * method. Enforces use of strings for property keys and values. The value returned is the result of the
     * <tt>Hashtable</tt> call to <code>put</code>.
     * 
     * @param key
     *            the key to be placed into this property list.
     * @param value
     *            the value corresponding to <tt>key</tt>.
     * @return the previous value of the specified key in this property list, or <code>null</code> if it did not have
     *         one.
     * @see #getProperty
     */
    public synchronized Object setProperty(String key, String value) {

        return put(key, value);
    }

    public synchronized String put(String key, String value) {

//        updateNbiConfig(key, value);
        return super.put(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public synchronized Object setProperty(String configFile, String key, String value) {

        return put(key, value);
    }

    private void updateNbiConfig(String key, String value) {

        StringBuilder fileCache = new StringBuilder();
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileFullPath)));

            PrefixStack strSection = new PrefixStack();
            boolean notFind = true;
            while ((line = reader.readLine()) != null) {
                if (isCommentLine(line) || "".equals(line)) {
                    fileCache.append(line).append("\n");
                    continue;
                }

                if (notFind) {
                    if (line.indexOf("{") != -1) {
                        int begin = line.lastIndexOf("{", line.length() - 1);
                        strSection.push(line.substring(0, begin));
                    } else if (line.indexOf("}") != -1) {
                        strSection.pop();
                    }
                    if (line.indexOf("=") > 0) {
                        StringTokenizer formator = new StringTokenizer(line, STRING_DELIMITER_EQUAL);
                        String tagkey = formator.nextToken().trim();
                        String tagvalue = "";
                        if (line.indexOf(STRING_DELIMITER_EQUAL) != line.length() - 1) {
                            tagvalue = formator.nextToken().trim();
                            if (tagvalue.lastIndexOf(';') == tagvalue.length() - 1) {
                                tagvalue = tagvalue.substring(0, tagvalue.length() - 1).trim();
                            }
                        }
                        if (strSection.getPrefix().concat(".").concat(tagkey).equals(key)) {
                            for (int i = 0; i < strSection.prefixStack.size(); ++i) {
                                fileCache.append("   ");
                            }
                            fileCache.append(tagkey).append("=").append(value).append("\n");
                            notFind = false;
                            continue;
                        }
                    }
                }
                fileCache.append(line).append("\n");
            }
        } catch (Exception e) {
            logger.log(CommonLogger.ERROR, "updateNbiConfig", "Faild update the item key=" + key + ",value=" + value
                    + " in config file " + fileFullPath, e);
        } finally {
            SystemUtil.closeQuitely(reader);
        }

        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(fileFullPath);
            writer.write(fileCache.toString().getBytes());
        } catch (Exception e) {
            logger.log(CommonLogger.ERROR, "updateNbiConfig", "Faild to write the item key=" + key + ",value=" + value
                    + " to config file " + fileFullPath, e);
        } finally {
            SystemUtil.closeQuitely(writer);
        }

    }

    /**
     * Alias for method <code>get</code>.
     * 
     * @param name
     *            the key of the value to be retrieved
     * @return the value corresbonding to <tt>key</tt>.
     */
    public String getValue(String name) {

        return this.get(name);
    }

    public String getValue(String name, String defaultValue) {

        String value = getValue(name);
        return SystemUtil.isNullOrBlank(value) ? defaultValue : value;
    }

    public void load(String filePath) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        load(br);
    }

    /**
     * Load the configuration file into this instance.
     * 
     * @param in
     *            the input stream.
     * @exception IOException
     *                if an error occurred when reading from the input stream.
     * @throws IllegalArgumentException
     *             if the input stream contains a malformed Unicode escape sequence.
     */
    public void load(InputStream in) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        load(br);
    }

    /**
     * Load the configuration file into this instance.
     * 
     * @param br
     *            the buffered reader.
     * @exception IOException
     *                if an error occurred when reading from the input stream.
     * @throws IllegalArgumentException
     *             if the input stream contains a malformed Unicode escape sequence.
     */

    public void load(BufferedReader br) throws IOException {

        String line = null;
        PrefixStack strSection = new PrefixStack();
        do {
            line = br.readLine();
            if (null == line)
                break;

            line = line.trim();

            if (line == "")
                continue;

            if (isCommentLine(line))
                continue;

            if (line.indexOf(STRING_DELIMITER_EQUAL) != -1) {
                StringTokenizer formator = new StringTokenizer(line, STRING_DELIMITER_EQUAL);
                String tagkey = formator.nextToken();
                String tagvalue = "";
                if (line.indexOf(STRING_DELIMITER_EQUAL) != line.length() - 1) {
                    tagvalue = formator.nextToken();
                    if (tagvalue.lastIndexOf(';') == tagvalue.length() - 1)
                        tagvalue = tagvalue.substring(0, tagvalue.length() - 1);
                }
                String prefix = strSection.getPrefix();
                put((prefix == null ? "" : (prefix + ".")) + tagkey.trim(), tagvalue.trim());
            } else if (line.indexOf("{") != -1) {
                int begin = line.lastIndexOf("{", line.length() - 1);
                strSection.push(line.substring(0, begin));
            } else if (line.indexOf("}") != -1) {
                strSection.pop();
            }

        } while (true);
    }

    private static boolean isCommentLine(String curStr) {

        if (SystemUtil.isNullOrBlank(curStr)) {
            return true;
        }
        StringTokenizer tokenizer = new StringTokenizer(curStr);
        try {
            String token = tokenizer.nextToken();
            return (token.trim().startsWith(CONFIG_FILE_COMMON_SIMBOL));
        } catch (NoSuchElementException e) {
        }

        return false;
    }

    public void reloadCommonConfiguration() throws IOException {

        clear();
        load(this.fileFullPath);
    }
}
