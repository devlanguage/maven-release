package org.third.orm.ibatis.account.dao;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.basic.common.util.XmlUtil;
import org.third.orm.ibatis.account.dao.hibernate.HibernateDaoLocator;
import org.third.orm.ibatis.account.dao.ibatis.IbatisDaoLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class AccountDaoLocator {

    static enum DaoLocatorType {
        hibernate, ibatis
    }

    static CommonLogger logger = CommonLogger.getLogger(AccountDaoLocator.class);

    private static final String DAO_CONFIG_FILE = "DaoConfig.xml";
    private static DaoConfiguration daoConfig = null;
    static {
        Document daoConfigs;
        try {
            daoConfigs = XmlUtil.createDocument(AccountDaoLocator.class.getResourceAsStream(DAO_CONFIG_FILE));

            NodeList daoConfigList = XmlUtil.getNodeList(daoConfigs.getDocumentElement(), DaoConfiguration.ELEMENT_DAO_CONFIG);
            daoConfig = new DaoConfiguration();
            for (int i = 0; i < daoConfigList.getLength(); ++i) {
                Element daoConfigElement = (Element) daoConfigList.item(i);
                String dcDefault = daoConfigElement.getAttribute(DaoConfiguration.ATTR_DC_DEFAULT);

                daoConfig.setImplementor(daoConfigElement.getAttribute(DaoConfiguration.ATTR_DC_CLASS));
                daoConfig.setName(daoConfigElement.getAttribute(DaoConfiguration.ATTR_DC_NAME));

                Element configFileElement = XmlUtil.getNode(daoConfigElement, DaoConfiguration.ELEMENT_CONFIG_FILE);
                daoConfig.setLoadFrom(XmlUtil.getNodeValue(configFileElement, DaoConfiguration.ELEMENT_CF_LOAD_FROM));
                daoConfig.setConfigFile(XmlUtil.getNodeValue(configFileElement, DaoConfiguration.ELEMENT_CF_PATH));

                if (Boolean.parseBoolean(dcDefault)) {
                    break;
                }
            }
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }

    public AccountDaoLocator() {
        try {
            DaoLoader daoLoader = (DaoLoader) Class.forName(daoConfig.getImplementor()).newInstance();
            daoLoader.init(daoConfig, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static AccountDaoLocator daoLocator;

    public synchronized static AccountDaoLocator getInstance() {

        if (daoLocator == null) {
            daoLocator = new IbatisDaoLocator();
        }
        return daoLocator;
    }

    public synchronized static AccountDaoLocator getInstance(DaoLocatorType locatorType) {

        switch (locatorType) {
            case hibernate:
                daoLocator = new HibernateDaoLocator();
                break;
            default:
                daoLocator = new IbatisDaoLocator();
                break;
        }
        return daoLocator;
    }

    public abstract AccountDao getAccountDao();

    public abstract UserDao getUserDao();

    public abstract SequenceDao getSequenceDao();

    public abstract UserInfoDao getUserInfoDao();

}