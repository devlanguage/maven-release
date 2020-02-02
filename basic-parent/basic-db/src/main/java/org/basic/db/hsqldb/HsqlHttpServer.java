package org.basic.db.hsqldb;

public class HsqlHttpServer {
	
	public static String HSQL_FILE = "file:target/hsql/httpdb";
	public static String HSQL_DB_NAME = "test1";

	public static void main(String[] args) throws Exception {
//		 java -cp hsqldb-2.5.0.jar org.hsqldb.server.WebServer --database.0 file:webserver --dbname.0 test1 --address 0.0.0.0 --port 28081 --trace true
		args = new String[] { "--database.0", HSQL_FILE, "--dbname.0", HSQL_DB_NAME, //
				"--address", "0.0.0.0", "--port", "28081", "--remote_open", "true", //
				"--trace", "true" };
//		args = new String[] {"--help"};
		org.hsqldb.server.WebServer.main(args);

	}
}
