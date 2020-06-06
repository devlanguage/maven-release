package org.basic.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        System.out.println("最小内存" + totalMemory / (1024 * 1024) + "兆");
        System.out.println("最大内存" + maxMemory / (1024 * 1024) + "兆");

        try {
            String url = args[0];
            String user = "oidc";
            String password = "oidc";
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select 23");
            while (rs.next()) {
                System.out.println(rs.getObject(1));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
