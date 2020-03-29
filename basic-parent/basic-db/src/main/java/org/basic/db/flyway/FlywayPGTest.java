package org.basic.db.flyway;

import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

public class FlywayPGTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://h7.test.com:5432/postgres?user=postgres@testazure18&password=Admin_1234";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--dbUrl")) {
                url = args[++i];
            }
        }

        try {

            url = "jdbc:postgresql://h7.test.com:5432/shcemersonvm01?user=shcemersonvm01&password=shcemersonvm01&search_path=shcemersonvm01";
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(url);

            Statement stmt = dataSource.getConnection().createStatement();
//           stmt.executeUpdate("update boson_schema_vesion set version='1.4.0.1', script='V1.4.0.1__init_deployment_table.sql' where installed_rank=2");

            FluentConfiguration flywayConf = Flyway.configure();
            flywayConf.encoding("UTF-8");
            flywayConf.locations("db.migrate.postgres");
            flywayConf.sqlMigrationPrefix("V");
            flywayConf.sqlMigrationSeparator("__");
            flywayConf.dataSource(dataSource);
//            flyway.setBaselineOnMigrate(true);
            flywayConf.table("boson_schema_vesion");
            flywayConf.baselineOnMigrate(true);
//            flyway.baseline();

//            flyway.baseline();
            flywayConf.load().migrate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
