package org.basic.db.hsqldb;

import org.basic.db.AbstractDatabaseServer;
import org.basic.db.domain.RdbDatabaseType;

public class HsqlHttpClient extends AbstractDatabaseServer {
//	jdbc:hsqldb:http://localhost:28081/test1
	private static String DB_URL = "jdbc:hsqldb:http://localhost:28081/test1";// Global: jdbc:hsqldb:mem; Per process:
	// jdbc:hsqldb:mem:. OR
	// jdbc:hsqldb:mem:XX
	private static String DB_USER = "SA";
	private static String DB_PWD = "";

	public HsqlHttpClient(RdbDatabaseType type, String dbUrl, String dbUser, String dbPwd) {
		super(type, dbUrl, dbUser, dbPwd);
	}

	public static AbstractDatabaseServer getDefault() {
		AbstractDatabaseServer tester = new HsqlHttpClient(RdbDatabaseType.hsqldb_http, DB_URL, DB_USER, DB_PWD);
		return tester;
	}

	public static void main(String[] args) throws Exception {
		AbstractDatabaseServer tester = HsqlHttpClient.getDefault();
		tester.queryAll();
	}
}
