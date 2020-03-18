package org.basic.db.postgresql;

import org.basic.db.AbstractDatabaseServer;
import org.basic.db.domain.RdbDatabaseType;

public class PostgresqlServer extends AbstractDatabaseServer {
	public PostgresqlServer(RdbDatabaseType type, String dbUrl, String dbUser, String dbPwd) {
		super(type, dbUrl, dbUser, dbPwd);
	}

	private static RdbDatabaseType DB_TYPE = RdbDatabaseType.postgresql;
	private static String DB_URL = "jdbc:postg3resql://h7.test.com:5432/test1";// Global: jdbc:hsqldb:mem; Per process:
																		// jdbc:hsqldb:mem:. OR jdbc:hsqldb:mem:XX
	private static String DB_USER = "test1";
	private static String DB_PWD = "test1";

	public static AbstractDatabaseServer getDefault() {
		AbstractDatabaseServer tester = new PostgresqlServer(DB_TYPE, DB_URL, DB_USER, DB_PWD);
		return tester;
	}

	public static void main(String[] args) throws Exception {
		AbstractDatabaseServer tester = PostgresqlServer.getDefault();
		tester.queryAll();
	}

}
