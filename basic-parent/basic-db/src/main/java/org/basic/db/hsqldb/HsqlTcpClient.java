package org.basic.db.hsqldb;

import org.basic.db.AbstractDatabaseServer;
import org.basic.db.RdbDatabase;

public class HsqlTcpClient extends AbstractDatabaseServer {
	private static String DB_URL = "jdbc:hsqldb:hsql://localhost:28080/test1";// Global: jdbc:hsqldb:mem; Per process:
// jdbc:hsqldb:mem:. OR
// jdbc:hsqldb:mem:XX
	private static String DB_USER = "SA";
	private static String DB_PWD = "";

	public HsqlTcpClient(RdbDatabase type, String dbUrl, String dbUser, String dbPwd) {
		super(type, dbUrl, dbUser, dbPwd);
	}

	public static void main(String[] args) throws Exception {
		HsqlTcpClient tester = new HsqlTcpClient(RdbDatabase.hsqldb, DB_URL, DB_USER, DB_PWD);
		tester.queryAll();
	}
}
