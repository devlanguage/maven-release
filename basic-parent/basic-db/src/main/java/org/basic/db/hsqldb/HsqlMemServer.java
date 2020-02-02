package org.basic.db.hsqldb;

import org.basic.db.AbstractDatabaseServer;
import org.basic.db.RdbDatabase;

public class HsqlMemServer extends AbstractDatabaseServer {
	private static String DB_URL = "jdbc:hsqldb:mem:xxx";// Global: jdbc:hsqldb:mem; Per process: jdbc:hsqldb:mem:. OR
															// jdbc:hsqldb:mem:XX

	private static String DB_USER = "sa1";
	private static String DB_PWD = "sdf";

	public HsqlMemServer(RdbDatabase type, String dbUrl, String dbUser, String dbPwd) {
		super(type, dbUrl, dbUser, dbPwd);
	}

	public static void main(String[] args) throws Exception {
		HsqlMemServer tester = new HsqlMemServer(RdbDatabase.hsqldb_mem, DB_URL, DB_USER, DB_PWD);
		tester.queryAll();
	}

}
