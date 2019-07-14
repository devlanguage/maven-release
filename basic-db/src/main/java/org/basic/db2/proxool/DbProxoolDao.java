package org.basic.db2.proxool;

import static org.basic.common.bean.CommonConstants.DB_ORACLE_DRIVER_CLASS;
import static org.basic.common.bean.CommonConstants.DB_ORACLE_PASSWORD;
import static org.basic.common.bean.CommonConstants.DB_ORACLE_URL;
import static org.basic.common.bean.CommonConstants.DB_ORACLE_USER;
import static org.basic.common.bean.CommonConstants.POOL_PROXOOL_max_count;
import static org.basic.common.bean.CommonConstants.POOL_PROXOOL_min_conn;
import static org.basic.common.bean.CommonConstants.POOL_PROXOOL_sleep_time;
import static org.basic.common.bean.CommonConstants.POOL_PROXOOL_test_sql;
import static org.basic.common.bean.CommonConstants.POOL_PROXOOL_throttle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.basic.common.bean.CommonConfiguration;
import org.basic.common.bean.CommonLogger;
import org.basic.common.bean.DatabaseType;
import org.basic.common.util.ConfigurationManager;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;

public class DbProxoolDao extends DbDao {

    public final static CommonLogger logger = CommonLogger.getLogger(DbProxoolDao.class);

    public final static String NBI_PROXOOL_CONNECTION_ALIAS = "TEST_PROXOOL_CONNECTION_ALIAS";
    public final static String NBI_PROXOOL_CONNECTION_NAME = "proxool." + NBI_PROXOOL_CONNECTION_ALIAS;

    @Override
    public Connection getConnection(DatabaseType dbType) throws SQLException {

        logger.log(CommonLogger.TRACE, "getConnection", "ActiveConnectionNum=" + getActiveConnectionCount());
        return DriverManager.getConnection(NBI_PROXOOL_CONNECTION_NAME);
    }

    @Override
    public void init() {

        CommonConfiguration config = ConfigurationManager.getCommonConfiguration();
        java.util.Properties info = new java.util.Properties();
        info.setProperty("proxool.simultaneous-build-throttle", config.get(POOL_PROXOOL_throttle));
        info.setProperty("proxool.minimum-connection-count", config.get(POOL_PROXOOL_min_conn));
        info.setProperty("proxool.maximum-connection-count", config.get(POOL_PROXOOL_max_count));
        info.setProperty("proxool.house-keeping-test-sql", config.get(POOL_PROXOOL_test_sql));
        info.setProperty("proxool.house-keeping-sleep-time", config.get(POOL_PROXOOL_sleep_time));

        info.setProperty("user", config.get(DB_ORACLE_USER));
        info.setProperty("password", config.get(DB_ORACLE_PASSWORD));

        String proxoolUrl = NBI_PROXOOL_CONNECTION_NAME + ":" + config.get(DB_ORACLE_DRIVER_CLASS) + ":"
                + config.get(DB_ORACLE_URL);// proxool.alias:driver:url
        try {
            org.logicalcobwebs.proxool.ProxoolFacade.registerConnectionPool(proxoolUrl, info);
        } catch (ProxoolException e) {
            e.printStackTrace();
        }

    }

    public void close() {

        try {
            // org.logicalcobwebs.proxool.ProxoolFacade.killAllConnections(proxoolAlias, "", false);
            org.logicalcobwebs.proxool.ProxoolFacade.shutdown(1);
        } catch (Exception e) {
            //
        }
    }

    int getActiveConnectionCount() {

        try {
            return ProxoolFacade.getSnapshot(NBI_PROXOOL_CONNECTION_ALIAS, true).getActiveConnectionCount();
        } catch (Exception e) {
            logger.log(CommonLogger.WARN, "getActiveConnectionCount",
                    "Failed to retrieve the db connection pool informaiton", e);
        }
        return -1;
    }

    int getAvailableConnectionCount() {

        try {
            return ProxoolFacade.getSnapshot(NBI_PROXOOL_CONNECTION_ALIAS, true).getAvailableConnectionCount();
        } catch (Exception e) {
            logger.log(CommonLogger.SEVERE, "getAvailableConnectionCount",
                    "Failed to retrieve the db connection pool informaiton", e);
        }
        return -1;
    }

    int getMaximumConnectionCount() {

        try {
            return ProxoolFacade.getSnapshot(NBI_PROXOOL_CONNECTION_ALIAS, true).getMaximumConnectionCount();
        } catch (Exception e) {
            logger.log(CommonLogger.SEVERE, "getMaximumConnectionCount",
                    "Failed to retrieve the db connection pool informaiton", e);
        }
        return -1;
    }

    String getConnectionInfo() {

        StringBuilder sb = new StringBuilder();
        try {
            SnapshotIF snapshot = ProxoolFacade.getSnapshot(NBI_PROXOOL_CONNECTION_ALIAS, true);
            sb.append("[ActiveConnectionCount=" + snapshot.getActiveConnectionCount());
            sb.append(",AvailableConnectionCount=" + snapshot.getAvailableConnectionCount());
            sb.append(",MaximumConnectionCount=" + snapshot.getMaximumConnectionCount());
            sb.append("]");
            return sb.toString();
        } catch (Exception e) {
            logger.log(CommonLogger.SEVERE, "getConnectionInfo", "Failed to retrieve the db connection pool detail", e);
        }
        return "Failed to retrieve the db connection pool informaiton";
    }

}
