package org.basic.common.util;

import static org.basic.common.bean.CommonConstants.CONFIG_FILE_COMMON_CONFIG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.basic.common.bean.CommonConfiguration;
import org.basic.common.bean.CommonConstants;
import org.basic.common.bean.CommonLogger;

public class ConfigurationManager {

    private static final boolean loadFromClasspath = true;
    private static final boolean supportLoadDynamically = true;

    private static final String SYSMTE_CONFIG_DIR = CommonConstants.DEFAULT_CONFIG_FILE_DIR;
    private static final CommonLogger logger = CommonLogger.getLogger(ConfigurationManager.class);
    private static final Map<String, CommonConfiguration> CONFIG_MAP = new ConcurrentHashMap<String, CommonConfiguration>();

    private ConfigurationManager() {

    }

    public final static String getSystemConfigFile(String fileName) {

        String systemConfigDirectory = SYSMTE_CONFIG_DIR;
        if (System.getProperty("SYSMTE_CONFIG_DIR") != null) {
            systemConfigDirectory = System.getProperty("SYSMTE_CONFIG_DIR");
        }
        return systemConfigDirectory +"/"+ fileName;
    }

    public final static CommonConfiguration getCommonConfiguration() {

        CommonConfiguration basicConfig = CONFIG_MAP.get(CONFIG_FILE_COMMON_CONFIG);
        if (null == basicConfig) {
            synchronized (ConfigurationManager.class) {
                if (null == basicConfig) {
                    basicConfig = loadConfig(CONFIG_FILE_COMMON_CONFIG);
                    CONFIG_MAP.put(CONFIG_FILE_COMMON_CONFIG, basicConfig);
                }
            }
        }
        return basicConfig;
    }

    public final static void reloadCommonConfiguration() {

        CommonConfiguration basicConfig = CONFIG_MAP.get(CONFIG_FILE_COMMON_CONFIG);
        if (null != basicConfig) {
            synchronized (ConfigurationManager.class) {
                if (null != basicConfig) {
                    try {
                        basicConfig.reloadCommonConfiguration();
                    } catch (IOException e) {
                        logger.log(CommonLogger.SEVERE, "load", "Please Make sure config file " + basicConfig.getConfigFile() + " Correct",
                                e);
                    }
                }
            }
        }
    }

    private static CommonConfiguration loadConfig(String configFile) {

        CommonConfiguration commonConfig = null;
        if (loadFromClasspath) {
            commonConfig = loadFromClasspath(configFile);
        } else {
            commonConfig = loadFromSystempath(configFile);
        }
        return commonConfig;

    }

    private static CommonConfiguration loadFromSystempath(String configFile) {

        CommonConfiguration commonConfig = null;
        InputStream in = null;
        try {
            configFile = getSystemConfigFile(configFile);
            in = new FileInputStream(configFile);
            commonConfig = new CommonConfiguration(configFile, supportLoadDynamically);
            commonConfig.load(in);
        } catch (Exception e) {
            logger.log(CommonLogger.SEVERE, "load", "Please Make sure config file " + configFile + " Correct", e);
        } finally {
            SystemUtils.closeQuitely(in);
        }
        return commonConfig;
    }

    private static CommonConfiguration loadFromClasspath(String configFile) {

        CommonConfiguration commonConfig = null;
        InputStream in = null;
        try {
            in = CommonConfiguration.class.getClassLoader().getResourceAsStream(configFile);
            commonConfig = new CommonConfiguration(configFile, supportLoadDynamically);
            commonConfig.load(in);
        } catch (Exception e) {
            logger.log(CommonLogger.SEVERE, "load", "Please Make sure config file " + configFile + " Correct", e);
        } finally {
            SystemUtils.closeQuitely(in);
        }
        return commonConfig;
    }

    public static void main(String[] args) {

        System.out.println(ConfigurationManager.getSystemConfigFile("log4j.properties"));
        System.out.println(getCommonConfiguration().getValue("jms.imq.host"));

    }
}
