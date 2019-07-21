package org.basic.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import javax.sql.RowSet;

import org.basic.common.bean.DatabaseType;
import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.db.util.DbDao;
import org.basic.db.util.DbDaoFactory;

public class PropertiesTest {

    private String name;

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public static void testProcedure1(Connection conn) throws SQLException {

        java.sql.CallableStatement cst = null;
        ResultSet rs = null;
        cst = conn.prepareCall("{call AAP_FOR_TEST(?)}");
        cst.setString(1, null);
        // cst.registerOutParameter(2, java.sql.Types.VARCHAR);
        cst.execute();
        // System.out.println(cst.getString(2));

    }

    public static void testProcedure2(Connection conn) throws SQLException {

        java.sql.CallableStatement cst = null;
        ResultSet rs = null;
        cst = conn.prepareCall("{call TMFNBI_PKG_TP_UTIL.GET_POTENTIAL_TPS(?, ?, ?, ?, ?)}");
        // tmfnbi_pkg_tp_util.GET_CURRENT_TPS('T7100_SONET','/shelf=2/slot=1/port=11','','/47/',v_tp_list);
        cst.setString(1, "T7100_SONET");
        cst.setString(2, "/shelf=2/slot=3/port=11");
        cst.setString(3, "");
        cst.setString(4, "");// "/47/"
        cst.registerOutParameter(5, java.sql.Types.ARRAY, "TMFNBI_PKG_TP_UTIL.TYPE_LAYER_RATE_ARRAY");// oracle.jdbc.OracleTypes.ARRAY

        cst.execute();
        rs = (java.sql.ResultSet) cst.getArray(5).getResultSet();

        while (rs.next()) {
            System.out.println(rs.getString(2));
        }
        // rs = (java.sql.ResultSet) cst.getObject(5);
        // printResultSet(rs);

    }

    public static void testProcedure3(Connection conn) throws SQLException {

        java.sql.CallableStatement cst = null;
        ResultSet rs = null;
        cst = conn.prepareCall("{call TMFNBI_PKG_TP_UTIL.GET_POTENTIAL_TPS(?, ?, ?, ?, ?)}");
        // tmfnbi_pkg_tp_util.GET_CURRENT_TPS('T7100_SONET','/shelf=2/slot=1/port=11','','/47/',v_tp_list);
        cst.setString(1, "T7100_SONET");
        cst.setString(2, "/shelf=2/slot=3/port=11");
        cst.setString(3, "");
        cst.setString(4, "");// "/47/"
        cst.registerOutParameter(5, java.sql.Types.REF_CURSOR);// oracle.jdbc.OracleTypes.CURSOR

        cst.execute();

        rs = (java.sql.ResultSet) cst.getObject(5);
        printResultSet(rs);

    }

    static DbDao dbDao = null;
    {
        try {
            dbDao = DbDaoFactory.createDbDao(PoolType.PROXOOL);
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = dbDao.getConnection(DatabaseType.ORACLE);

            // testQuery1(conn);
            // testProcedure1(conn);
            testProcedure1(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void testQuery1(Connection conn) throws SQLException {

        ResultSet rs = null;
        RowSet rowset = null;
        ResultSetMetaData rsmd = null;

        rs = conn.createStatement().executeQuery("select * from tmfnbi_rule_tp");
        rsmd = rs.getMetaData();
        printResultSet(rs);

    }

    /**
     * <pre>
     * 
     * 
     * public static final int java.sql.Types.LONGNVARCHAR: -16
     * public static final int java.sql.Types.NCHAR: -15
     * public static final int java.sql.Types.NVARCHAR: -9
     * public static final int java.sql.Types.ROWID: -8
     * public static final int java.sql.Types.BIT: -7
     * public static final int java.sql.Types.TINYINT: -6
     * public static final int java.sql.Types.BIGINT: -5
     * public static final int java.sql.Types.LONGVARBINARY: -4
     * public static final int java.sql.Types.VARBINARY: -3
     * public static final int java.sql.Types.BINARY: -2
     * public static final int java.sql.Types.LONGVARCHAR: -1
     * public static final int java.sql.Types.NULL: 0
     * public static final int java.sql.Types.CHAR: 1
     * public static final int java.sql.Types.NUMERIC: 2
     * public static final int java.sql.Types.DECIMAL: 3
     * public static final int java.sql.Types.INTEGER: 4
     * public static final int java.sql.Types.SMALLINT: 5
     * public static final int java.sql.Types.FLOAT: 6
     * public static final int java.sql.Types.REAL: 7
     * public static final int java.sql.Types.DOUBLE: 8
     * public static final int java.sql.Types.VARCHAR: 12
     * public static final int java.sql.Types.BOOLEAN: 16
     * public static final int java.sql.Types.DATALINK: 70
     * public static final int java.sql.Types.DATE: 91
     * public static final int java.sql.Types.TIME: 92
     * public static final int java.sql.Types.TIMESTAMP: 93
     * public static final int java.sql.Types.OTHER: 1111
     * public static final int java.sql.Types.JAVA_OBJECT: 2000
     * public static final int java.sql.Types.DISTINCT: 2001
     * public static final int java.sql.Types.STRUCT: 2002
     * public static final int java.sql.Types.ARRAY: 2003
     * public static final int java.sql.Types.BLOB: 2004
     * public static final int java.sql.Types.CLOB: 2005
     * public static final int java.sql.Types.REF: 2006
     * public static final int java.sql.Types.SQLXML: 2009
     * public static final int java.sql.Types.NCLOB: 2011
     * 
     * </pre>
     */
    public final static void printJavaSqlTypes() {

        java.lang.reflect.Field[] sqlTypes = java.sql.Types.class.getFields();
        Arrays.sort(sqlTypes, 0, sqlTypes.length, new Comparator<Field>() {

            public int compare(Field f1, Field f2) {

                try {
                    return f1.getInt("") - f2.getInt("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        for (java.lang.reflect.Field field : sqlTypes) {
            try {
                printConsole(field + ": " + field.getInt("") + "\n");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public final static void printRowSet(RowSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        boolean first = true;
        int columnSize = rsmd.getColumnCount();
        while (rs.next()) {
            if (first) {
                first = !first;
                for (int i = 1; i <= columnSize; i++) {
                    printConsole(format(rsmd.getColumnName(i)) + " | ");
                }
                printConsole("\n");
                for (int i = 1; i <= columnSize; i++) {
                    printConsole(format("====================================") + " | ");
                }
                printConsole("\n");
            }

            for (int i = 1; i <= columnSize; i++) {
                printConsole(format(String.valueOf(rs.getObject(rsmd.getColumnName(i)))) + " | ");
            }
            printConsole("\n");
        }

    }

    public final static void printResultSet(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        boolean first = true;
        int columnSize = rsmd.getColumnCount();
        while (rs.next()) {
            if (first) {
                first = !first;
                for (int i = 1; i <= columnSize; i++) {
                    printConsole(format(rsmd.getColumnName(i)) + " | ");
                }
                printConsole("\n");
                for (int i = 1; i <= columnSize; i++) {
                    printConsole(format("====================================") + " | ");
                }
                printConsole("\n");
            }

            for (int i = 1; i <= columnSize; i++) {
                printConsole(format(String.valueOf(rs.getObject(rsmd.getColumnName(i)))) + " | ");
            }
            printConsole("\n");
        }

    }

    public final static int MAX_COLUMN_LENGTH = 25;
    public static String MAX_COLUMN_STRING = "";
    static {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_COLUMN_LENGTH; i++) {
            sb.append(' ');
        }
        MAX_COLUMN_STRING = sb.toString();
    }

    public final static String format(String t) {

        return (t + MAX_COLUMN_STRING).substring(0, MAX_COLUMN_STRING.length() - 2);
    }

    public final static <T> void printConsole(T t) {

        System.out.print(t);
    }
}
