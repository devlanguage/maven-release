package org.basic.db.hsqldb;

import org.basic.db.AbstractDatabaseServer;
import org.basic.db.domain.RdbDatabaseType;

public class HsqlTcpClient extends AbstractDatabaseServer {
	private static String DB_URL = "jdbc:hsqldb:hsql://localhost:28080/test1";// Global: jdbc:hsqldb:mem; Per process:
// jdbc:hsqldb:mem:. OR
// jdbc:hsqldb:mem:XX
	private static String DB_USER = "SA";
	private static String DB_PWD = "";

	public HsqlTcpClient(RdbDatabaseType type, String dbUrl, String dbUser, String dbPwd) {
		super(type, dbUrl, dbUser, dbPwd);
	}

	public static AbstractDatabaseServer getDefault() {
		AbstractDatabaseServer tester = new HsqlTcpClient(RdbDatabaseType.hsqldb, DB_URL, DB_USER, DB_PWD);
		return tester;
	}

	public static void main(String[] args) throws Exception {
		AbstractDatabaseServer tester = HsqlTcpClient.getDefault();
		tester.queryAll();
	}
}
