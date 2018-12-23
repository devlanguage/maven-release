import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Created on Mar 14, 2014, 3:09:04 PM
 */
public class DBTest {
    public static void main(String[] args) {
        java.sql.Connection conn = null;
        try {
            OracleDataSource ods = new OracleDataSource();
            ods = new OracleDataSource();
            ods.setServerName("sunshapp42");
            ods.setDatabaseName("shapp42");
            ods.setPortNumber(1521);
            ods.setDriverType("thin");

            boolean inmMode = true;
            if (inmMode == true) {
                // Manu's code keeps dxxdata and schema user in synch. OSR gave
                // us the dxxdata user's password which should be the same. We
                // connect as the schema user because that is who owns the tables.
                ods.setUser("hli1");
            } else {
                ods.setUser("hli1");
            }
            // ods.setUser("hli1");
            ods.setPassword("tellabs");
            conn = ods.getConnection();
            System.out.println("==" + conn + ", " + conn.isClosed());

            boolean isCache = true;
            if (isCache) {
                ods.setConnectionCachingEnabled(true);
                ods.setConnectionCacheName("Test_Cache");
            } else {
                ods.setConnectionCachingEnabled(false);
            }
            OracleConnectionCacheManager connMgr = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
            Properties properties = new Properties();

            properties.setProperty("MinLimit", "1");
            properties.setProperty("MaxLimit", ""+23);
            properties.setProperty("InitialLimit", "1");
            properties.setProperty("ConnectionWaitTimeout", "20");              
            
            String cacheName="cache_asdfasfasdf";
            connMgr.createCache(cacheName, ods, properties);
            
            conn = DriverManager.getConnection("jdbc:oracle:thin:@sunshapp42:1521:shapp42", "hli1", "tellabs");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select table_name from user_tables");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
