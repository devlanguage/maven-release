package org.basic.db.oracle.connection;

import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.db.util.DbDao;
import org.basic.db.util.DbDaoFactory;

public class OracleDbConnectionTest {
    public static void main(String[] args) {
//jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcps)(HOST=15.119.80.177)(PORT=2484))(CONNECT_DATA=(SID=orcl)))
//        properties.setProperty("javax.net.ssl.trustStore", tmpFile.getAbsolutePath());
//        properties.setProperty("javax.net.ssl.trustStoreType", CertUtil.getConfigKeyStoreType());
//        properties.setProperty("oracle.net.ssl_version","1");
//        properties.setProperty("javax.net.ssl.trustStorePassword", ApiServerKeyStoreHelper.initOrGetTrustStorePassWord());
        
        String dbUrl =System.getProperty("spring.datasource.url");
        String dbUser =System.getProperty("spring.datasource.username");
        String dbPassword =System.getProperty("spring.datasource.password");
        
    }
}
