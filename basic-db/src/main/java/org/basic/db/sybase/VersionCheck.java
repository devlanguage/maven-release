package org.basic.db.sybase;
/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:01:15 PM Nov 15, 2013
 *
 *****************************************************************************
 */


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on Nov 15, 2013, 2:01:15 PM
 */
public class VersionCheck {
    public static void main(String[] args) {
        VersionCheck c = new VersionCheck();
        try {
            c.check();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static final Pattern verPattern = Pattern.compile("\\(Build (\\w+)\\)", Pattern.CASE_INSENSITIVE);

    public synchronized void check() throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        System.out.println("About to check sp_version ...");
        try {
            conn = SybaseDbManager.getInstance().getConnection();
            String driverBuild=null;
            DatabaseMetaData meta = conn.getMetaData();
            String driverVersion = meta.getDriverVersion();
            System.out.println("jdbc driverVersion: "+driverVersion);
            Matcher m1 = verPattern.matcher(driverVersion);
            if (m1.find() == false) {
                System.out.println("Failed to parse driverVersion="+driverVersion);
            }
            else {
                driverBuild = m1.group(1);
                System.out.println("driverBuild="+driverBuild);
            }
            
            
            
            stmt = conn.createStatement();
            results = stmt.executeQuery("sp_version installjdbc");

            if (results.next()) {
                String spStatus = results.getString("STATUS");
                String spVersion = results.getString("VERSION");
                String spBuild = null;

                System.out.println("sp_version installjdbc:  "+spVersion);
                Matcher m = verPattern.matcher(spVersion);
                if (m.find() == false) {
                 System.out.println("Failed to parse spVersion=" + spVersion);
                } else {
                    spBuild = m.group(1);
                }
                System.out.println("spBuild=" + spBuild);
                System.out.println("spStatus=" + spStatus);
                if ((driverBuild != null) && (spBuild != null)) {
                    if (!driverBuild.equals(spBuild)) {
                        // For now, just make noise in log, do not fail the connection
                     System.out.println("Bad Stored Procedure Version=" + spVersion);
                    }
                }
                if (!spStatus.equalsIgnoreCase("Complete")) {
                    // For now, just make noise in log, do not fail the connection
                 System.out.println("Bad Stored Procedure Status=" + spStatus);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception ex) {
                    ex.printStackTrace();                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();                }
            }
        }
    }
}
