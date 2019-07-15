
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.basic.common.util.SystemUtil;

public class Configuration extends Hashtable<String, String> {
	
	private static final long serialVersionUID = 4112578634029877590L;
    
    public final static String ORACLE_URL = "oracle.url";
	
	/**
	 * Indicate the comment sign, '#'.
	 */	
    public final static char COMMENT_SYMBOL = '#';
    
    /**
     * A property list that contains default values for any keys not
     * found in this property list.
     *
     * @serial
     */
    protected Configuration defaults;

    /**
     * Creates an empty property list with no default values.
     */
    public Configuration() {
    	this(null);
    }

    /**
     * Creates an empty property list with the specified defaults.
     *
     * @param   defaults   the defaults.
     */
    public Configuration(Configuration defaults) {
    	this.defaults = defaults;
    }

    /**
     * Calls the <tt>Hashtable</tt> method <code>put</code>. Provided for
     * parallelism with the <tt>getProperty</tt> method. Enforces use of
     * strings for property keys and values. The value returned is the
     * result of the <tt>Hashtable</tt> call to <code>put</code>.
     *
     * @param key the key to be placed into this property list.
     * @param value the value corresponding to <tt>key</tt>.
     * @return     the previous value of the specified key in this property
     *             list, or <code>null</code> if it did not have one.
     * @see #getProperty
     */
    public synchronized Object setProperty(String key, String value) {
        return put(key, value);
    }

    /**
     * Alias for method <code>get</code>.
     * 
     * @param name the key of the value to be retrieved
     * @return the value corresbonding to <tt>key</tt>.
     */
    public String getValue(String name) {
		return this.get(name);
	}

    public String getValue(String name, String defaultValue) {

        String value = getValue(name);
        return SystemUtil.isNullOrBlank(value) ? defaultValue : value;
    }
    
    /**
     * Load the configuration file into this instance.
     * 
     * @param      in   the input stream.
     * @exception  IOException  if an error occurred when reading from the
     *             input stream.
     * @throws	   IllegalArgumentException if the input stream contains a
     * 		   malformed Unicode escape sequence.
     */
    public void load(InputStream in) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
    	load(br);
    }
	
    /**
     * Load the configuration file into this instance.
     * 
     * @param      br   the buffered reader.
     * @exception  IOException  if an error occurred when reading from the
     *             input stream.
     * @throws	   IllegalArgumentException if the input stream contains a
     * 		   malformed Unicode escape sequence.
     */
    public void load(BufferedReader br) throws IOException {
    	String line = null;
		String strSection = null;
		
    	do {
            line = br.readLine();
            if( null == line)
                break;
            
            line = line.trim();

            if(line == "")
                continue;

            if(isCommentLine(line))
                continue;
            
            if (line.indexOf('=') != -1) {
            	StringTokenizer formator = new StringTokenizer(line, "=");
                String tagkey = formator.nextToken();
                String tagvalue = "";
            	if(line.indexOf('=') != line.length() - 1) {
            		tagvalue = formator.nextToken();
            		if(tagvalue.lastIndexOf(';') == tagvalue.length() - 1)
            			tagvalue = tagvalue.substring(0, tagvalue.length() - 1);
            	}
                put((strSection == null ? "" : (strSection + ".")) + tagkey.trim(), tagvalue.trim());
            } else if (line.indexOf('{') != -1) {
            	int begin = line.lastIndexOf("{",line.length()-1);
            	strSection = line.substring(0, begin);
            } else if (line.indexOf("};") != -1) {
            	strSection = null;
            }
            
//            StringTokenizer formator = new StringTokenizer(line,"=");
//            try {
//            } catch(NoSuchElementException validerr) {
//            	int begin = line.lastIndexOf("{",line.length()-1);
//
//				if( begin >0 ) {
//    	        	bSection  = true;
//            	    strSection = line.substring(0, begin);
//            	    continue;
//				}
//
//				begin = line.lastIndexOf("};",line.length()-1);
//            	
//				if(begin >=0 ) {
//					if(bSection == true) {
//						sections.put(strSection.trim(), tags.clone());
//						tags.clear();
//						bSection = false;
//					}
//					continue;
//				}
//            }
        } while(true);
    }
	
    private boolean isCommentLine(String curStr) {
        StringTokenizer tokenizer = new StringTokenizer(curStr);
        try {
            String token = tokenizer.nextToken();
            return (token.charAt(0) == COMMENT_SYMBOL);
        } catch(NoSuchElementException e) {
        }

        return false;
    }
}
