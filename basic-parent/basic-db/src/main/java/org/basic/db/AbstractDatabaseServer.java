package org.basic.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.basic.common.util.SystemUtils;
import org.basic.db.domain.RdbDatabaseType;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.jdbc.DriverDataSource;

public abstract class AbstractDatabaseServer {

	protected String dbUrl = "";
	protected String dbUser;
	protected String dbPwd;
	protected RdbDatabaseType rdbDatabaseType;
	protected Flyway flyway;
	protected DataSource dataSource;

	public AbstractDatabaseServer(RdbDatabaseType type, String dbUrl, String dbUser, String dbPwd) {
		this.rdbDatabaseType = type;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
		initDatabase();
	}

	private void initDatabase() {
		dataSource = new DriverDataSource(Thread.currentThread().getContextClassLoader(), rdbDatabaseType.getDriver(), dbUrl, dbUser,
				dbPwd);
		flyway = Flyway.configure().dataSource(dataSource)
				.locations(AbstractDatabaseServer.class.getPackage().getName() + ".migration." + rdbDatabaseType.getType()).load();
		flyway.migrate();
	}

	public void queryAll() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id, firstname, lastname from customer");

			while (rs.next()) {
				System.out.println(String.format("id=%s, firstname=%s, lastname=%s", rs.getInt("id"),
						rs.getString("firstname"), rs.getString("lastname")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SystemUtils.closeQuitely(rs, stmt, conn);
		}

	}
}
