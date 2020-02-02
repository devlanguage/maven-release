package org.basic.db;

public enum RdbDatabase {
	hsqldb("hsqldb", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:hsql://"), //
	hsqldb_http("hsqldb", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:http://"), //
	hsqldb_mem("hsqldb", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:mem:"), //
	hsqldb_file("hsqldb", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:file:"), //
	h2("h2", "org.h2.Driver", "jdbc:h2:"), //
	sqlite("sqlite", "org.sqlite.JDBC", "jdbc:sqlite:"), //
	sqlite_android("sqlite", "org.sqldroid.SQLDroidDriver", "jdbc:sqlite:"), //
	sqldroid("sqldroid", "org.sqldroid.SQLDroidDriver", "jdbc:sqldroid:"), //

	tc("tc", "org.testcontainers.jdbc.ContainerDatabaseDriver", "jdbc:tc:"), //

	redshift("redshift", "com.amazon.redshift.jdbc42.Driver", "jdbc:redshift:"), //
	snowflake("snowflake", "net.snowflake.client.jdbc.SnowflakeDriver", "jdbc:h2:"), //
	firebird("firebird", "org.firebirdsql.jdbc.FBDriver", "jdbc:firebird:"), //
	firebirdsql("firebirdsql", "org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:"), //
	// jdbc:informix-sqli://«host?»:1533/«database?»:INFORMIXSERVER=«server?»
	informix("firebirdsql", "com.informix.jdbc.IfxDriver", "jdbc:informix-sqli:"),

	oracle("oracle", "oracle.jdbc.OracleDriver", "jdbc:oracle:oci8:@"), // jdbc:oracle:oci8:@«database?»
	oracle_thin("oracle", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@"), // jdbc:oracle:thin:@«host?»:1521:«database?»
	db2("db2", "com.ibm.db2.jdbc.app.DB2Driver", "jdbc:db2:"), // jdbc:db2:«database?»
	db2_thin("db2", "com.ibm.db2.jdbc.net.DB2Driver", "jdbc:db2://"), // jdbc:db2://«host?»:6789/«database?»
//	private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//	private static final String MYSQL_GOOGLE_JDBC_DRIVER = "com.mysql.jdbc.GoogleDriver";
//	private static final String MYSQL_LEGACY_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	mysql("mysql", "com.mysql.cj.jdbc.Driver", "jdbc:mysql:"), //
	mysql_google("mysql", "com.mysql.jdbc.GoogleDriver", "jdbc:mysql:"), //
	mysql_legacy("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql:"), //

	sqlserver("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver:"), //
	sqlserver_JTDS("sqlserver", "net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:"), //

	postgresql("postgresql", "org.postgresql.Driver", "jdbc:postgresql:"), ////
	mariadb("mariadb", "org.mariadb.jdbc.Driver", "jdbc:mariadb:"), //
	sybase("sybase", "com.sybase.jdbc4.jdbc.SybDriver", "jdbc:sybase:"), //
	saphana("sybase", "com.sap.db.jdbc.Driver", "jdbc:sap:"), //
//
	derby("derby", "org.apache.derby.jdbc.ClientDriver", "jdbc:derby://"), //
	derby_embedded("derby", "org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:");

	private String type;
	private String driver;
	private String urlPrefix;

	private RdbDatabase(String type, String driver, String urlPrefix) {
		this.type = type;
		this.driver = driver;
		this.urlPrefix = urlPrefix;
	}

	public String getType() {
		return this.type;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}
}
