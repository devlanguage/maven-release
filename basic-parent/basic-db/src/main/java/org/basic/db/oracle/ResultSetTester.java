package org.basic.db.oracle;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Map;

import org.basic.common.bean.DatabaseType;
import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.common.util.DbUtils;
import org.basic.db.PropertiesTest;
import org.basic.db.oracle.types.SqlTypeAddress;
import org.basic.db.util.DbDao;
import org.basic.db.util.DbDaoFactory;

/**
 * 
 */
public class ResultSetTester {

    static DbDao dbDao = null;
    {
        try {
            dbDao = DbDaoFactory.createDbDao(PoolType.PROXOOL);
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }

    public ResultSetTester() {

        // DatabaseManager.getInstance()
    }

    public static void main(String[] args) {

        ResultSetTester tester = new ResultSetTester();
        tester.startTest();

    }

    private void startTest() {

        Connection conn = null;
        try {
            conn = dbDao.getConnection(DatabaseType.ORACLE);
            // testDatabaseMetaData(conn);
            // testStatement(conn);
            // testPreparedStatement(conn);
            // testResult1(conn);
            testResult2(conn);
            // testArray(conn);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuitely(conn);

        }
    }

    private void testResult2(Connection conn) {

        try {
            Statement stmt = conn.createStatement(//
                    ResultSet.TYPE_FORWARD_ONLY, // TYPE_FORWARD_ONLY, TYPE_SCROLL_INSENSITIVE,
                    // TYPE_SCROLL_SENSITIVE
                    ResultSet.CONCUR_READ_ONLY);// , // CONCUR_READ_ONLY, CONCUR_UPDATABLE
            // ResultSet.HOLD_CURSORS_OVER_COMMIT); // HOLD_CURSORS_OVER_COMMIT, CLOSE_CURSORS_AT_COMMIT
            ResultSet rs = stmt.executeQuery("select * from NM_GEOENTITY");
            ;
            PropertiesTest.printResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testDatabaseMetaData(Connection conn) throws Exception {

        DatabaseMetaData dbmd = conn.getMetaData();
        System.out.println("DbInfo:\n" + dbmd.getDatabaseProductVersion());
        // System.out.println(DatabaseManager.printResultSet(dbmd.getTypeInfo()));
        System.out.println("\tgetTables:\n" + DbUtils.printResultSet(dbmd.getTables(null, "YGONG1", "%", null)));
        System.out.println("\tgetTableTypes:\n" + DbUtils.printResultSet(dbmd.getTableTypes()));
        System.out.println("\t getSchemas:\n" + DbUtils.printResultSet(dbmd.getSchemas()));
        System.out.println("String Functions: " + dbmd.getStringFunctions());
        System.out.println("Number Functions: " + dbmd.getNumericFunctions());
    }

    // Statement: ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
    // ResultSet DatabaseMetaData.getResultSetHoldability()
    private void testStatement(Connection conn) throws Exception {

        Statement stmt = conn.createStatement(//

                ResultSet.TYPE_FORWARD_ONLY, // TYPE_FORWARD_ONLY, TYPE_SCROLL_INSENSITIVE,
                // TYPE_SCROLL_SENSITIVE
                ResultSet.CONCUR_READ_ONLY, // CONCUR_READ_ONLY, CONCUR_UPDATABLE
                ResultSet.HOLD_CURSORS_OVER_COMMIT); // HOLD_CURSORS_OVER_COMMIT, CLOSE_CURSORS_AT_COMMIT
        // stmt.addBatch("insert into department(dep_id, dep_name) values(seq_department.nextval,
        // 'Finance')");
        // stmt.addBatch("insert into department(dep_id, dep_name) values(seq_department.nextval,
        // 'Marketing')");
        // stmt.addBatch("delete from department where dep_name = 'Finance' or
        // dep_name='Marketing'");
        // stmt.executeBatch();
        // stmt.clearBatch();

        // dependon on the real database
        // stmt.executeUpdate("insert into department(dep_name) values('Finance')",
        // Statement.RETURN_GENERATED_KEYS);

        String dep = "";
        try {
            // for(Charset a: Charset.availableCharsets().values()){
            // System.out.println(a.displayName());
            // }
            dep = new String("345".getBytes("gb2312"), "ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        stmt.executeUpdate("update department set dep_name='" + dep + "' where dep_id=1");

        ResultSet rs = stmt.executeQuery("select dep.* from department dep");
        // // System.out.println(stmt.getMoreResults());//false
        System.out.println(DbUtils.printResultSet(rs));
        // System.out.println(stmt.getUpdateCount());// false

    }

    private void testPreparedStatement(Connection conn) throws Exception {

        PreparedStatement pstmt = conn.prepareStatement("select * from department where dep_id <> ?");
        pstmt.setInt(1, 1);
        pstmt.executeQuery();
        // pstmt.executeQuery("select * from department");
        ResultSet rs1 = null;
        rs1 = pstmt.getResultSet();
        System.out.println(DbUtils.printResultSet(rs1));
        // while (pstmt.getMoreResults() && pstmt.getUpdateCount() != -1) {
        // rs1 = pstmt.getResultSet();
        // System.out.println(DatabaseManager.printResultSet(rs1));
        // }

        pstmt = conn.prepareStatement("update department set address = ? where dep_id = ?");
        pstmt.setString(1, "xi'an");
        // pstmt.setArray(2, new int[]{});

        // Clob description = pstmt.get;
        // pstmt.setClob(1, description);
        pstmt.setInt(2, 1);
        pstmt.execute();

    }

    // FETCH_FORWARD, FETCH_REVERSE, FETCH_UNKNOWN
    private void testResult1(Connection conn) throws Exception {

        Statement stmt = conn.createStatement(//

                ResultSet.TYPE_SCROLL_INSENSITIVE, // TYPE_FORWARD_ONLY, TYPE_SCROLL_INSENSITIVE,
                // TYPE_SCROLL_SENSITIVE
                ResultSet.CONCUR_UPDATABLE, // CONCUR_READ_ONLY, CONCUR_UPDATABLE
                ResultSet.HOLD_CURSORS_OVER_COMMIT); // HOLD_CURSORS_OVER_COMMIT,CLOSE_CURSORS_AT_COMMIT

        ResultSet rs1 = stmt.executeQuery("select dep.* from department dep");
        String result1 = DbUtils.printResultSet(rs1);
        rs1.absolute(1);
        System.out.println("current_row=" + rs1.getRow());
        rs1.updateString(2, "HR");
        rs1.updateRow();

        rs1.moveToInsertRow();
        System.out.println("current_row=" + rs1.getRow());

        rs1.absolute(2);
        System.out.println("current_row=" + rs1.getRow());
        rs1.updateString(2, rs1.getString(2) + "HR");
        rs1.updateRow();

        rs1.moveToInsertRow();
        System.out.println("current_row=" + rs1.getRow());

        rs1.updateInt(1, 12);
        rs1.updateString(2, "NewDepartment-2");
        rs1.insertRow();
        System.out.println(result1);
        System.out.println("current_row=" + rs1.getRow());

        ResultSet rs2 = stmt.executeQuery("select dep.* from department dep");
        String result2 = DbUtils.printResultSet(rs2);
        System.out.println(result2);
    }

    // FETCH_FORWARD, FETCH_REVERSE, FETCH_UNKNOWN
    private void testArray(Connection conn) throws Exception {

        Map<String, Class<?>> sqlToJavaMap = conn.getTypeMap();
        sqlToJavaMap.put("ADDRESS", SqlTypeAddress.class);
        // sqlToJavaMap.put("schemaName.ADDRESS", Address.class);

        Statement stmt = conn.createStatement(//

                ResultSet.TYPE_SCROLL_INSENSITIVE, // TYPE_FORWARD_ONLY, TYPE_SCROLL_INSENSITIVE,
                // TYPE_SCROLL_SENSITIVE
                ResultSet.CONCUR_UPDATABLE, // CONCUR_READ_ONLY, CONCUR_UPDATABLE
                ResultSet.HOLD_CURSORS_OVER_COMMIT); // HOLD_CURSORS_OVER_COMMIT,CLOSE_CURSORS_AT_COMMIT

        ResultSet rs1 = stmt.executeQuery("select * from employee");
        ResultSetMetaData rsmd = rs1.getMetaData();
        while (rs1.next()) {
            System.out.print(rsmd.getColumnName(1) + ":" + rs1.getString(1));
            System.out.print("," + rsmd.getColumnName(2) + ":" + rs1.getString(2));
            System.out.print("," + rsmd.getColumnName(3) + ":" + rs1.getString(3));
            System.out.print("," + rsmd.getColumnName(4) + ":" + rs1.getString(4));
            System.out.print("," + rsmd.getColumnName(5) + ":[");

            // Array addressList = rs1.getArray(5);
            // Map<String, Class<?>> map = new HashMap<String, Class<?>>();
            // addressList.getArray(map);
            Object[] os = (Object[]) rs1.getArray(5).getArray();
            for (Object o : os) {
                SqlTypeAddress s = (SqlTypeAddress) o;
                System.out.print(s);
            }
        }
    }

}
