
/**
 * <pre>
 *  &lt;DaoConfig name=&quot;hibernateDao&quot; class=&quot;org.ibatistest.account.dao.hibernate.HibernateDaoLoader&quot;
 *     default=&quot;true&quot;&gt;
 *     &lt;ConfigFile&gt;
 *       &lt;LoadFrom&gt;classpath&lt;/LoadFrom&gt;
 *       &lt;Path&gt;/org/ibatistest/account/dao/hibernate/hibernate.cfg.xml&lt;/Path&gt;
 *     &lt;/ConfigFile&gt;
 *   &lt;/DaoConfig&gt;
 * 
 *   &lt;DaoConfig name=&quot;IbatisDao&quot; class=&quot;org.ibatistest.account.dao.ibatis.IbatisDaoLoader&quot;&gt;
 *     &lt;ConfigFile&gt;
 *       &lt;LoadFrom&gt;system&lt;/LoadFrom&gt;
 *       &lt;Path&gt;/org/ibatistest/account/dao/ibatis/ibatis_daoConfig.xml&lt;/Path&gt;
 *     &lt;/ConfigFile&gt;
 *   &lt;/DaoConfig&gt;
 * </pre>
 */
public class DaoConfiguration {

    public final static String ELEMENT_DAO_CONFIG = "DaoConfig";
    public final static String ATTR_DC_NAME = "name";
    public final static String ATTR_DC_DEFAULT = "default";
    public final static String ATTR_DC_CLASS = "class";
    public final static String ELEMENT_CONFIG_FILE = "ConfigFile";
    public final static String ELEMENT_CF_LOAD_FROM = "LoadFrom";
    public final static String ELEMENT_CF_PATH = "Path";

    public final static String LOAD_FROM_SYSTEM = "system";
    public final static String LOAD_FROM_CLASS_PATH = "classpath";

    protected String name;
    protected String implementor;
    protected String loadFrom;
    protected String configFile;

    /**
     * @return get method for the field clazz
     */
    public String getImplementor() {

        return this.implementor;
    }

    /**
     * @param clazz
     *            the clazz to set
     */
    public void setImplementor(String clazz) {

        this.implementor = clazz;
    }

    /**
     * @return get method for the field configFile
     */
    public String getConfigFile() {

        return this.configFile;
    }

    /**
     * @param configFile
     *            the configFile to set
     */
    public void setConfigFile(String configFile) {

        this.configFile = configFile;
    }

    /**
     * @return get method for the field loadFrom
     */
    public String getLoadFrom() {

        return this.loadFrom;
    }

    /**
     * @param loadFrom
     *            the loadFrom to set
     */
    public void setLoadFrom(String loadFrom) {

        this.loadFrom = loadFrom;
    }

    /**
     * @return get method for the field name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

}
