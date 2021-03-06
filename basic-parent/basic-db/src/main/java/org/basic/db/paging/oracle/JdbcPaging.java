package org.basic.db.paging.oracle;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.basic.common.util.DbUtils;
import org.basic.common.util.SystemUtils;

/**
 * Created on Jan 30, 2014, 10:55:15 AM
 */
public class JdbcPaging {
	static {
		try {
			String driverClass = "oracle.jdbc.OracleDriver";
			DriverManager.registerDriver((Driver) Class.forName(driverClass).newInstance());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final static Connection getOracleConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:oracle:thin:@sunshapp49:1521:EMS7100G", "west", "tellabs");

	}

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = getOracleConnection();
			long start = System.currentTimeMillis();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
					ResultSet.CLOSE_CURSORS_AT_COMMIT);
			ResultSet rs = stmt.executeQuery("select * from west.FM_ARCHIVE_ALARM");
			rs.absolute(200000);
			int pageSize = 10;
			while (rs.next() && pageSize-- >= 0) {
				System.out.println(rs.getString(1));
			}
			long end = System.currentTimeMillis();
			System.out.println("Take time: " + (end - start) / 1000 + " seconds");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				SystemUtils.closeQuitely(conn);
			}
		}

	}
}
