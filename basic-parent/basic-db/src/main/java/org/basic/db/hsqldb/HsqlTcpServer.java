package org.basic.db.hsqldb;

/**
 * <pre>
 no value for argument:-help
Usage: java org.hsqldb.server.Server [options]

+-----------------+-------------+----------+------------------------------+
|     OPTION      |    TYPE     | DEFAULT  |         DESCRIPTION          |
+-----------------+-------------+----------+------------------------------|
| --help          | -           | -        | displays this message        |
| --address       | name|number | any      | server inet address          |
| --port          | number      | 9001/544 | port at which server listens |
| --database.i    | [type]spec  | 0=test   | name of database i           |
| --dbname.i      | alias       | -        | url alias for database i     |
| --silent        | true|false  | true     | false => display all queries |
| --trace         | true|false  | false    | display JDBC trace messages  |
| --tls           | true|false  | false    | TLS/SSL (secure) sockets     |
| --no_system_exit| true|false  | false    | do not issue System.exit()   |
| --remote_open   | true|false  | false    | can open databases remotely  |
| --props         | filepath    |          | file path of properties file |
+-----------------+-------------+----------+------------------------------+

The server looks for a 'server.properties' file in the current directory and
loads properties from it if it exists.
Command line options override those loaded from the 'server.properties' file.
 server.port=9001
 server.database.0=test
 server.dbname.0=...
 ...
 server.database.n=...
 server.dbname.n=...
 server.silent=true

See the HSQLDB User Guide for further details.
 * 
 * </pre>
 */
public class HsqlTcpServer {
	public static String HSQL_FILE = "file:target/hsql/tcpdb";
	public static String HSQL_DB_NAME = "test1";

	public static void main(String[] args) throws Exception {
//		 java -cp hsqldb-2.5.0.jar org.hsqldb.server.Server --database.0 file:tcpserver --dbname.0 test1 --address 0.0.0.0 --port 28080 --remote_open true --trace true
		args = new String[] { "--database.0", HSQL_FILE, "--dbname.0", HSQL_DB_NAME, //
				"--address", "0.0.0.0", "--port", "28080", "--remote_open", "true", //
				"--trace", "true" };
//		args = new String[] {"--help"};
		org.hsqldb.server.Server.main(args);

	}
}
