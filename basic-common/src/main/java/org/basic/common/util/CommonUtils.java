package org.basic.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.TimeZone;

public class CommonUtils {
    public static final String IDM_CONF_HOME = System.getProperty("IDM_CONF_HOME") == null ? System.getProperty("IDM_CONF_HOME")
            : System.getProperty("IDM_CONF_HOME");
    public static final String PATH_SEPARATOR = System.getProperty("file.separator");
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final Charset ISO_8859_1_CHARSET = Charset.forName(ISO_8859_1);
    public static final String UTF8 = "UTF-8";
    public static final Charset UTF8_CHARSET = Charset.forName(UTF8);
    public static final String USASCII = "US-ASCII";
    public static final Charset USASCII_CHARSET = Charset.forName(USASCII);
    public static final String WINDOWS_URI_PATH_STRING = "\\";
	public static final String TOP_PATH = "..";
	public static final String CURRENT_PATH = ".";
	public static final char EXTENSION_SEPARATOR = '.';
    public static final char URI_PATH_CHAR = '/';
    public static final String URI_PATH_STRING = "/";
    
    public static final String STRING_EQUAL = "=";
    public static final String STRING_COLON = ":";
    public static final String STRING_SEMICOLON = ";";
    public static final String STRING_QUESTION = "?";
    public static final String STRING_AMPERSAND = "&";
    public static final String STRING_DOT = ".";
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    
    public static final String PROTOCOL_HOST_STRING_SEPARATOR = "://";
    public static final String QUERY_STRING_START_SEPARATOR = "?";
    public static final String QUERY_STRING_ENTRY_SEPARATOR = "&";
    public static final String QUERY_STRING_KV_SEPARATOR = "=";
    public static final String REQUEST_PARAM_TOKEN = "token";
    public static final String REQUEST_PARAM_LOGOUT_TOKEN = "logoutToken";
    public static final String REQUEST_PARAM_TOKEN_ID = "tokenId";
    public static final String REQUEST_PARAM_TENANT = "tenant";
    public static final String REQUEST_PARAM_ORIGINAL_URL = "original_url_before_logout";
    

    /**
     * Environment variable list
     */
	public static final String LOCAL_SUITE_REGISTRY = "localhost:5000";
	public static final String KUBE_REGISTRY_PROXY_NAME = "kube-registry-proxy.core";

	public static final String ENV_SIGNING_KEY = null;
	public static final String ENV_APISERVER_HOST = System.getenv("API_SERVER_HOST");
	public static final String ENV_APISERVER_PORT = System.getenv("API_SERVER_PORT");
	public static final String ENV_APISERVER_TOKEN_FILE = "/var/run/secrets/kubernetes.io/serviceaccount/token";
	
	public static final String ENV_ETCD_ENDPOINT = System.getenv("ETCD_ENDPOINT");
	public static final String ENV_ETCD_CLIENT_CRT_FILE = System.getenv("ETCD_TLS_PEM_FILE");
	public static final String ENV_ETCD_CLIENT_CRT_PWD = "changeit";
	
	public static final String ENV_IDM_SERVER_HOST = System.getenv("IDM_SERVER");
	public static final String ENV_IDM_SERVER_PORT = System.getenv("IDM_SVC_SERVICE_PORT");
	
	public static final String ENV_EXTERNAL_HOST = System.getenv("EXTERNAL_ACCESS_HOST");
	public static final String ENV_EXTERNAL_PORT = System.getenv("EXTERNAL_ACCESS_PORT");
	public static final String ENV_API_REGISTRY_HOST = System.getenv("REGISTRY_SERVER");
	public static final String ENV_API_REGISTRY_PORT =System.getenv("API_REGISTRY_PORT");
	
	public static final String ENV_AUTOPASS_SERVER_HOST = ENV_EXTERNAL_HOST;
	public static final String ENV_AUTOPASS_SERVER_PORT = "5814";

	public static final String ENV_VAULT_ENDPOINT = System.getenv("VAULT_ADDR");
	public static final String ENV_VAULT_SIGNING_KEY = System.getenv("VAULT_SIGNING_KEY");

	public static final String ENV_SUITE_REGISTRY = (System.getenv("SUITE_REGISTRY")==null
			||"".equals(System.getenv("SUITE_REGISTRY")))?LOCAL_SUITE_REGISTRY:System.getenv("SUITE_REGISTRY");

	public static final String ENDPOINT_API_SERVER = "https" + "://" + ENV_APISERVER_HOST + ":" + ENV_APISERVER_PORT;
	public static final String ENDPOINT_ETCD = "https" + "://" +System.getenv("K8S_MASTER_IP")+ ":" +"4001";
	public static final String ENDPOINT_IDM = "https" + "://" + ENV_IDM_SERVER_HOST + ":" + ENV_IDM_SERVER_PORT;
	public static final String ENDPOINT_AUTOPASS = "https" + "://" + ENV_AUTOPASS_SERVER_HOST; //+ EnvUtils.getAutopassserverport();
	public static final String ENDPOINT_API_REGISTRY = "https" + "://" + ENV_API_REGISTRY_HOST + ":" + ENV_API_REGISTRY_PORT;
	public static final String ENDPOINT_PROXY_REGISTRY = "https://" + KUBE_REGISTRY_PROXY_NAME + ":5000";
	public static final String ENDPOINT_VAULT = ENV_VAULT_ENDPOINT;
	

    /** UTF-8 character set */
    public static final String ENCRYPTED_VALUE_PREFIX = "ENC(";
    public static final String ENCRYPTED_VALUE_SUFFIX = ")";

    public static final String IDM_EXTERNAL_SECURITY_FILE="classpath:idm-security.properties";
    public static final String IDM_DEFAULT_SSL_PROTOCOL = "TLS";
    public static final String CRYPTO_FIPS_DEFAULT_PROVIDER = "JsafeJCE";
    public static final String CRYPTO_FIPS_DEFAULT_KEYSTORE_TYPE = "PKCS12";
    public static final String CRYPTO_FIPS_DEFAULT_KEYSTORE_FORMAT = "PKIX";
    public static final String CRYPTO_FIPS_DEFAULT_ENC_ALGORITHM = "AES";
    public static final String CRYPTO_FIPS_DEFAULT_ASYM_ALG = "RSA";
    public static final String CRYPTO_FIPS_DEFAULT_HASH_ALG = "SHA256";
    public static final int CRYPTO_FIPS_DEFAULT_ENC_ALG_KEY_SIZE = 128;
    public static final String CRYPTO_FIPS_DEFAULT_RANDOM_NUM_GEN_ALG = "HMACDRBG128";// Dual EC DRBG algorithm

    public static final String CRYPTO_DEFAULT_KEYSTORE_TYPE = "JKS";
    public static final String CRYPTO_DEFAULT_KEYSTORE_FORMAT = "SunX509";
    public static final String CRYPTO_DEFAULT_ENC_ALGORITHM = "PBEWithMD5AndDES";
    public static final String CRYPTO_DEFAULT_KEY = "gk8=347jG4;O<6";
    public static final String CRYPTO_DEFAULT_ASYM_ALG = "RSA";
    public static final String CRYPTO_DEFAULT_HASH_ALG = "SHA256";
    public static final TimeZone UTC_ZONE = TimeZone.getTimeZone("+00:00");
    public static final String IDM_CLIENT_INSTANCE_ATTRIBUTE_KEY = "__IDM_CLIENT_INSTANCE_OF_ATTRIBUTE_KEY__";
    public static final String IDM_SECURITY_CONTEXT = "__IDM_SECURITY_CONTEXT__";
    public static final String IDM_CLIENT_INSTANCE = "__IDM_CLIENT_INSTANCE__";
    public static final String LWSSO_COOKIE_KEY = "LWSSO_COOKIE_KEY";
    public static final String LWSSO_COOKIE_KEY_EXPIRATION = "__IDM_LWSSO_TOKEN_EXPIRATION__";
    public static final String IDM_X_AUTH_TOKEN = "__IDM_X_AUTH_TOKEN__";
    public static final String IDM_RESERVED_HEADER = "__IDM_RESERVED_HEADER__";
    public static final String IDM_SPECIFIC_SERVER_CONFIG = "__SPECIFIC_SERVER_CONFIG__";
    public static final String FILE_IDM_OPERATIONS = "classpath:/com/hp/ccue/identity/common/IdmOperations.xml";
    public static final String FILE_IDM_CONFIG = "classpath:IdmConfig.xml";
    public static final String MESSAGE_BUNDLE = "Messages";
    public static final String LOGOUT_KEY_BACK_APP_ON_LOGOUT = "backAppOnLogout";

    /**
     * Search for one {@code File} based on {@code String} argument file. Search rule are as below
     * <p>
     * 1) if file starts with "classpath:", it will search from the current classloader resource directory. if found,
     * return. otherwise
     * <p>
     * 2) if file starts with "WEB-INF", it will search from the current web-application. if found, return. otherwise
     * <p>
     * 3) it searches file from absolute path. if found, return. otherwise
     * <p>
     * 4) it searches file from the path specified by system environment "IDM_CONF_HOME"
     * 
     * @param file fileName to be looked fors
     * @return if
     */
    public static final File getFile(String file) {
        if (logger.isTraceEnabled()) {
            logger.trace("searching for file:" + file);
        }
        File f = new File(file);
        if (f.exists()) {
            logger.trace("Found the file: " + f);
            return f;
        }

        String fileUrl = null;
        if (file.replaceAll("/", "").replace("\\", "").startsWith("WEB-INF")) {
            try {                
                f = new File(CommonUtils.class.getClassLoader().getResource(PATH_SEPARATOR).toURI().getPath());
                if (logger.isTraceEnabled()) {
                    logger.trace("searching for file from web-inf:" + f.getPath());
                }
                fileUrl = f.getParentFile().getParentFile() + PATH_SEPARATOR + file.substring(file.indexOf("WEB-INF"));
                f = new File(fileUrl);
                if (f.exists()) {
                    logger.trace("Found the file from WEB-INF: " + f);
                    return f;
                }
            } catch (Exception e) {
                logger.info("Cannot find the file from WEB-INF: " + file);
            }
        }

        String classPathBase = null;
        if (file.startsWith("classpath:")) {
            try {
                classPathBase = CommonUtils.class.getClassLoader().getResource("//").toURI().getPath();
                if (logger.isTraceEnabled()) {
                    logger.trace("searching for file from classpath:" + classPathBase);
                }
                fileUrl = classPathBase + file.substring("classpath:".length()).trim();
                f = new File(fileUrl);
                if (f.exists()) {
                    logger.trace("Found the file from classpath: " + f);
                    return f;
                }
            } catch (Exception e) {
                logger.info("Cannot find the file from classpath: " + file);
            }
        }

        try {
            classPathBase = CommonUtils.class.getClassLoader().getResource("//").toURI().getPath();
            if (logger.isTraceEnabled()) {
                logger.trace("searching for file from classpath:" + classPathBase);
            }
            fileUrl = classPathBase + file;
            f = new File(fileUrl);
            if (f.exists()) {
                logger.trace("Found the file from classpath: " + f);
                return f;
            }
        } catch (Exception e) {
            logger.info("Cannot find the file from classpath: " + file);
        }

        if (IDM_CONF_HOME != null) {
            return new File(IDM_CONF_HOME + PATH_SEPARATOR + file);
        } else {
            logger.error("Failed to find the file from classpath: ",  f);
            return null;
        }
    }

    /**
     * Setup {@code FileInputstream} on {@code String} argument
     * 
     * @param fileName {@code String} argument representing one File path.
     * @return
     * @throws FileNotFoundException
     */
    public static final FileInputStream getFileInputStream(String fileName) throws FileNotFoundException {
        File idmConfigFile = getFile(fileName);
        logger.info("Search file {} by fileName={}", String.valueOf(idmConfigFile), fileName);
        return new FileInputStream(idmConfigFile);
    }

    /**
     * Parse the toString of one object as boolean. if toString of object argument is "true" or "enabled", ignoring
     * case, it return {@code true}
     * 
     * @param t a {@code String} containing one boolean representation to be parsed
     * @return the boolean represented by object argument
     */
    public static final <T> boolean isTrue(T t) {
        return null == t ? false
                : ("ENABLED".equalsIgnoreCase(t.toString()) || "TRUE".equalsIgnoreCase(t.toString())
                        || "YES".equalsIgnoreCase(t.toString()));
    }

    /**
     * Prints the message in standard out. it is used for "DESKTOP_CONSOLE" mode
     * 
     * @param msg
     */
    public static <T> void printInConsole(T msg) {
        System.out.println(String.valueOf(msg));
    }

    /**
     * Concatenate two arrays as one array
     * 
     * @param firstArray source array
     * @param secondArray array which will be append to firstArray
     * @return concatenated array
     */
    public static byte[] appendArrays(final byte[] firstArray, final byte[] secondArray) {

        final byte[] result = new byte[firstArray.length + secondArray.length];

        System.arraycopy(firstArray, 0, result, 0, firstArray.length);
        System.arraycopy(secondArray, 0, result, firstArray.length, secondArray.length);

        return result;

    }
}
