package org.basic.db.derby;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyAdmin {

    public static void backUpDatabase(Connection conn) throws SQLException {

        // Get today's date as a string:
        java.text.SimpleDateFormat todaysDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String backupdirectory = "c:/mybackups/" + todaysDate.format((java.util.Calendar.getInstance()).getTime());
        CallableStatement cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
        cs.setString(1, backupdirectory);
        cs.execute();
        cs.close();
        System.out.println("backed up database to " + backupdirectory);
    }

    public static void backUpDatabaseWithFreeze(Connection conn) throws SQLException {

        Statement s = conn.createStatement();
        s.executeUpdate("CALL SYSCS_UTIL.SYSCS_FREEZE_DATABASE()");
        // copy the database directory during this interval
        s.executeUpdate("CALL SYSCS_UTIL.SYSCS_UNFREEZE_DATABASE()");
        s.close();
    }
}
