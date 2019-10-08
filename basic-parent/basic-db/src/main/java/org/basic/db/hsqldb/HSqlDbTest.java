package org.basic.db.hsqldb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import org.basic.common.util.StreamUtils;

/**
 * <pre>
 Memory-Only  
    jdbc:hsqldb:mem:dbname 
 In-Process
    jdbc:hsqldb:file:/E:/hsqldb/data/dbname
    jdbc:hsqldb:file:/opt/db/dbname
    jdbc:hsqldb:file:dbname
 Server Mode
    java -classpath hsqldb.jar org.hsqldb.server.Server –database.0 testdb –dbname.0 testdbName
    jdbc:hsqldb:hsql://localhost:port/dbname
 Web Server Mode
    java -classpath ../lib/hsqldb.jar org.hsqldb.WebServer –database.0 testdb –dbname.0 testdbname
 Servlet Mode
 * 
 * </pre>
 *
 */
public class HSqlDbTest {
	private static String HSQLDB_HOME = "C:\\software\\db\\hsqldb-2.5.0";
	private static String HSQLDB_JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

	public static void main(String[] args) throws Exception {
		testHsqlStandalone();
		testHsqlServer();
	}

	/**
	 * <pre>
	 * CREATE TABLE PUBLIC.PUBLIC.ORGNIZATION (
		ID VARCHAR(32) NOT NULL,
		NAME VARCHAR(1024),
		DESCRIPTION VARCHAR(5000000)
	  );
	 * 
	 * </pre>
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	private static void testHsqlStandalone() throws SQLException, IOException {
		Properties properties = new Properties();
		properties.setProperty("user", "admin");
		properties.setProperty("password", "hsqldb");
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:" + HSQLDB_HOME + "/test1/test0", properties);
		Connection conn2 = DriverManager.getConnection("jdbc:hsqldb:file:" + HSQLDB_HOME + "/test1/test0", properties);
		Connection conn3 = DriverManager.getConnection("jdbc:hsqldb:file:" + HSQLDB_HOME + "/test1/test0", properties);

		PreparedStatement pstmt = conn.prepareStatement("Insert into ORGNIZATION(id, name,DESCRIPTION) values(?, ?,?)");
		
		for (int i = 0; i < 1; i++) {
			pstmt.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
			pstmt.setString(2, "Provider");
			pstmt.setString(3, StreamUtils.streamToString(HSqlDbTest.class.getResourceAsStream("test1.txt")));
			pstmt.addBatch();
			if (i > 0 && i % 100 == 0) {
				pstmt.executeBatch();
			}
		}

		Statement stmt1 = conn2.createStatement();
		ResultSet rs = stmt1.executeQuery("Select top 1 * from ORGNIZATION");
		while (rs.next()) {
			System.out.println(rs.getObject(1) + ",  " + rs.getObject(2));
		}
		
		Statement stmt2 = conn3.createStatement();
		ResultSet rs2 = stmt2.executeQuery("Select top 1 * from ORGNIZATION");
		while (rs2.next()) {
			System.out.println(rs2.getObject(1) + ",  " + rs2.getObject(2));
		}

	}

	/**
	 * <pre>
	C:\software\db\hsqldb-2.5.0>java -cp hsqldb-2.5.0.jar org.hsqldb.Server -database.0 db1/suitedb -dbname.0 sdb
	[Server@2f2c9b19]: Startup sequence initiated from main() method
	[Server@2f2c9b19]: Could not load properties from file
	[Server@2f2c9b19]: Using cli/default properties only
	[Server@2f2c9b19]: Initiating startup sequence...
	[Server@2f2c9b19]: Server socket opened successfully in 31 ms.
	[Server@2f2c9b19]: Database [index=0, id=0, db=file:db1/suitedb, alias=sdb] opened successfully in 359 ms.
	[Server@2f2c9b19]: Startup sequence completed in 390 ms.
	[Server@2f2c9b19]: 2019-07-27 15:08:07.269 HSQLDB server 2.5.0 is online on port  	9001
	[Server@2f2c9b19]: To close normally, connect and execute SHUTDOWN SQL
	[Server@2f2c9b19]: From command line, use [Ctrl]+[C] to abort abruptly
	 * 
	 * java -cp hsqldb.jar org.hsqldb.util.DatabaseManager
	 * jdbc:hsqldb:hsql://localhost/sdb
	 * </pre>
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	private static void testHsqlServer() throws SQLException, IOException {
		Properties properties = new Properties();
		properties.setProperty("user", "admin");
		properties.setProperty("password", "hsqldb");
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:" + HSQLDB_HOME + "/test1/test0", properties);
		PreparedStatement pstmt = conn.prepareStatement("Insert into ORGNIZATION(id, name,DESCRIPTION) values(?, ?,?)");
		pstmt.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
		pstmt.setString(2, "Provider");
		pstmt.setString(3, StreamUtils.streamToString(HSqlDbTest.class.getResourceAsStream("test1.txt")));
		pstmt.execute();

		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("Select * from ORGNIZATION");
		while (rs.next()) {
			System.out.println(rs.getObject(1) + ",  " + rs.getObject(2));
		}

	}

}
