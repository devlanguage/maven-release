package org.basic.db.flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.FlywayCallback;

public class FlywayPGTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://shcssolindb01.hpeswlab.net:5432/postgres?user=postgres@testazure18&password=Admin_1234";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--dbUrl")) {
                url = args[++i];
            }
        }

        try {
  
            url = "jdbc:postgresql://shcssolindb01.hpeswlab.net:5432/shcemersonvm01?user=shcemersonvm01&password=shcemersonvm01&search_path=shcemersonvm01";
            url="jdbc:postgresql://shcGeorgeCTs1w1.hpeswlab.net:5432/dan?user=postgres&password=postgres";
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            
           Statement stmt= dataSource.getConnection().createStatement();
//           stmt.executeUpdate("update boson_schema_vesion set version='1.4.0.1', script='V1.4.0.1__init_deployment_table.sql' where installed_rank=2");
            
            Flyway flyway = new Flyway();
            flyway.setEncoding("UTF-8");
            flyway.setLocations("db.migrate.postgres");
            flyway.setSqlMigrationPrefix("V");
            flyway.setSqlMigrationSeparator("__");
            flyway.setDataSource(dataSource);
//            flyway.setBaselineOnMigrate(true);
            flyway.setTable("boson_schema_vesion");
            flyway.setBaselineOnMigrate(true);
//            flyway.baseline();
            
//            flyway.baseline();
            flyway.migrate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
