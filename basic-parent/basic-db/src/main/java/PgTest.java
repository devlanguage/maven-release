
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class PgTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://h7.test.com:5432/postgres?user=postgres@testazure18&password=Admin_1234";
        String user = "postgres", password = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--dbUrl")) {
                url = args[++i];
            } else if (args[i].equals("--dbUser")) {
                user = args[++i];
            } else if (args[i].equals("--dbPwd")) {
                password = args[++i];
            }
        }

        try {
            Connection conn = DriverManager.getConnection(url);
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select current_timestamp");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(url + "," + user + "," + password);
            Connection conn = DriverManager.getConnection(url, user, password);
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select current_timestamp");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
