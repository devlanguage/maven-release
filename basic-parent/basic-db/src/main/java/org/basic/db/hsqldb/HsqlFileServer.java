package org.basic.db.hsqldb;

import org.basic.db.AbstractDatabaseServer;
import org.basic.db.RdbDatabase;

public class HsqlFileServer extends AbstractDatabaseServer {
	public HsqlFileServer(RdbDatabase type, String dbUrl, String dbUser, String dbPwd) {
		super(type, dbUrl, dbUser, dbPwd);
	}

	private static RdbDatabase DB_TYPE = RdbDatabase.hsqldb_file;
	private static String DB_URL = "jdbc:hsqldb:file:target/hsql/test2";// Global: jdbc:hsqldb:mem; Per process:
																		// jdbc:hsqldb:mem:. OR jdbc:hsqldb:mem:XX
	private static String DB_USER = "sa1";
	private static String DB_PWD = "sdf";

	public static void main(String[] args) throws Exception {
		HsqlFileServer tester = new HsqlFileServer(DB_TYPE, DB_URL, DB_USER, DB_PWD);
		tester.queryAll();
	}
}
