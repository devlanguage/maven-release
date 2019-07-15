package org.third.orm.hibernate3.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.PooledConnection;

import oracle.jdbc.oci.OracleOCIConnection;
import oracle.jdbc.pool.OracleConnectionCache;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.pool.OracleOCIConnectionPool;

/**
 * 
 */
public class OracleConnection {

    static Connection conn = null;
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            // DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

            conn = openConnectionFromDM();
            conn = openConnectionFromDS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection openConnectionFromDM() throws SQLException {

        return DriverManager.getConnection("jdbc:oracle:thin:@sunshapp40:1521:EMS7100A", "ygong1", "tellabs");
    }

    private static Connection openConnectionFromDS() throws SQLException {

        OracleDataSource myDataSource = new OracleDataSource(); // 创建数据源
        myDataSource.setServerName("sunshapp40");
        myDataSource.setDatabaseName("EMS7100A");
        myDataSource.setDriverType("thin");
        myDataSource.setNetworkProtocol("tcp"); // 设置属性
        myDataSource.setPortNumber(1521);
        myDataSource.setUser("ygong1");
        myDataSource.setPassword("tellabs");
        // 连接数据库，可以重新指定用户名和密码，以不同的身份连接查询；
        return myDataSource.getConnection(/* "username", "password" */);
    }

    private static Connection openConnCache() throws SQLException {

        /*
         * 使用连接缓存---------------------------------------------------------------------------
         */
        /*
         * 1.创建OracleConnectionCacheImpl的对象,他实现了OracleConnectionCacheClass接口 这个接口扩展了OracleDataSource类，可以使用这个类的所有属性和方法
         * 创建的最简单的方法是使用默认的构造器
         */
        OracleConnectionPoolDataSource myOCCI = new OracleConnectionPoolDataSource();
        // myOCCI.setCacheInactivityTimeout(10l);// -设置连接池存储的不活动的连接结束时间.
        // myOCCI.setCacheTimeToLiveTimeout(10); // -设置连接池存储的活动的连接结束时间.
        // myOCCI.setCacheFixedWaitTimeout(10); // -设置连接池存储的固定的连接结束时间.
        // myOCCI.setCacheFixedWaitIdleTime(25);// -设置连接池存储的固定的连接停歇时间.

        myOCCI.setServerName("sunshapp40");
        myOCCI.setDatabaseName("EMS7100A");
        myOCCI.setPortNumber(1521);
        myOCCI.setDriverType("thin");
        myOCCI.setUser("ygong1");
        myOCCI.setPassword("tellabs");
        // 还可以使用现有的OraclePoolDataSource对象为它设置属性
        // OracleConnectionCacheImpl myOCCI=new OracleConnectionCacheImpl(myOracle);
        // 或使用如下方法
        // myOCCI.setConnectionPoolDataSource(myOracle);

        // 2.请求、使用和关闭连接实例
        Connection myConnection = myOCCI.getConnection();

        /*
         * PooledConnection对象代表物理数据库连接，默认情况下，调用myOCCI.getConnection()方法时， myocci检查缓存中是否存在Pooledconnection对象，没有就创建一个
         * 然后，检查pooledconnection对象是否有空闲的连接实例，没有就创建 并返回PooledConnection对象的连接实例
         */
        // 在这里可以请求另一个连接实例
        // Connection myConnection2=myOCCI.getConnection();
        // // 关闭连接实例
        // myConnection.close();
        //
        // // 程序结束前，应该关闭OracleConnectionCacheImpl对象,同时也就关闭了所有PooledConnection对象
        // myOCCI.close();
        return myConnection;

    }

    private static java.sql.Connection openConnPoolDS() throws SQLException {

        OracleConnectionPoolDataSource myOracle = new OracleConnectionPoolDataSource();
        // 创建连接缓冲池数据源oracleConnectionPoolDataSource的对象
        // 实现了ConnectionPoolDataSource接口
        // 2.使用此对象设置物理数据库连接的属性
        myOracle.setServerName("localhost");
        myOracle.setDatabaseName("myOracle");
        myOracle.setPortNumber(1521);
        myOracle.setDriverType("thin");

        myOracle.setUser("user");
        myOracle.setPassword("password");

        // 3.创建缓冲的连接对象，它代表物理数据库连接，可以使用它来请求数据库连接实例

        PooledConnection myPooledConnection = myOracle.getPooledConnection();
        // PooledConnection myPooledConnection= myOracle.getPooledConnection("user","password");
        // 建立一次连接，可以多次请求连接实例，最后再关闭连接对象

        // 4.请求、使用、和最终关闭连接实例
        Connection myConnection = myPooledConnection.getConnection();
        return myConnection;
        // 使用
        // myConnection.close();
        //
        // // 5.程序结束之前，关闭PooledConnection对象
        // myPooledConnection.close();

    }

    private static void openConnByOCI() throws SQLException {

        /*
         * 使用OCI驱动程序实现连接缓冲池--------------------------------------------------------------- 优点：可以动态配置物理连接数量
         * 每个连接实例可以可以有不同的用户名密码，可以使用一个缓冲池访问不同的用户模式 能够有多个缓冲连接，THIN只有一个数据库连接
         */
        OracleOCIConnectionPool myOOCP = new OracleOCIConnectionPool();

        /*
         * myOOCP.set…… …… ……
         */
        // 以下可选的可以程序运行时动态设定的属性
        /*
         * Properties myProperties=new Properties(); myProperties.put(OracleOCIConnectionPool.CONNPOOL_MIN_LIMIT,"5");
         * my …… .CONNPOOL_MAX_LIMIT,"10"); …………… _INCREMENT,"2"); _TIMEOUT,"30"); _NOWAIT,"true");
         * myOOCP.setPoolconfig(myProperties);
         */
        // 建立连接实例
        OracleOCIConnection myConnection = (OracleOCIConnection) myOOCP.getConnection();

        myConnection.close();

        myOOCP.close();

    }

    public static void main(String[] args) {

        OracleConnection c = new OracleConnection();

        try {
            conn = c.openConnectionFromDS();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select sysdate from dual");
            while (rs.next()) {
                System.out.println(rs.getDate(1));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
