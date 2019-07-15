package org.basic.db.oracle.collection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.basic.common.bean.DatabaseType;
import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.common.util.DbUtil;
import org.basic.db2.proxool.DbDao;
import org.basic.db2.proxool.DbDaoFactory;

public class OracleArrayObjectTester {

    static DbDao dbDao = null;
    {
        try {
            dbDao = DbDaoFactory.createDbDao(PoolType.PROXOOL);
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        OracleArrayObjectTester tester = new OracleArrayObjectTester();
        tester.startTest();

    }

    private void startTest() {

        Connection conn = null;
        try {
            conn = dbDao.getConnection(DatabaseType.ORACLE);
            testObjectArray(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeQuitely(conn);
        }
    }

    /**
     * <pre>
     * create or replace TYPE
     *      type_department IS OBJECT(
     *      dep_id VARCHAR2(20),
     *      dep_name VARCHAR2(200)
     * );
     * create or replace TYPE
     *      type_department_table IS TABLE OF type_department;
     * PROCEDURE ADD_DEPARTMENTS(
     *      p_department_list type_department_table
     * );
     * </pre>
     * 
     * @param conn
     */

    public void testObjectArray(Connection conn) {

        // Test Submit Object
        System.out.println("Test Submit Object: ");
        try {
            CallableStatement stmt = conn.prepareCall("{call PKG_ORACLE_TEST.ADD_DEPARTMENT(?)}");
            Map<String, Class<?>> sqlToJavaMap = conn.getTypeMap();
            sqlToJavaMap.put("TYPE_DEPARTMENT", SqlTypeDepartment.class);
            SqlTypeDepartment dep = new SqlTypeDepartment("1", "Test Department");
            stmt.setObject(1, dep);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // test submitting Object Array
        System.out.println("test the submit Object Array: ");
        try {
            CallableStatement stmt = conn.prepareCall("{call PKG_ORACLE_TEST.ADD_DEPARTMENTS(?)}");

            // Object[] depArray = new Object[] { new Object[] { "1", "HR Department_1" } };
            Object[] depArray = new Object[] { new SqlTypeDepartment("1", "HR Department_2") };
            oracle.jdbc.OracleConnection oracleConn = (oracle.jdbc.OracleConnection) conn;

            oracle.sql.ArrayDescriptor descriptor = oracle.sql.ArrayDescriptor.createDescriptor(
                    "TYPE_DEPARTMENT_TABLE", oracleConn);
            oracle.sql.ARRAY formattedDepArray = new oracle.sql.ARRAY(descriptor, oracleConn, depArray);
            stmt.setArray(1, formattedDepArray);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // test retrieving the Department by dep_id
        System.out.println("test retrieving the Department by dep_id: ");
        try {
            CallableStatement stmt = conn.prepareCall("{?=call PKG_ORACLE_TEST.get_department(?)}");
            // oracle.sql.StructDescriptor sd = new StructDescriptor("TYPE_DEPARTMENT", conn);
            // oracle.sql.STRUCT s = new STRUCT(sd,conn,new Object[]{});
            stmt.registerOutParameter(1, java.sql.Types.STRUCT, "TYPE_DEPARTMENT");
            stmt.setString(2, "2");
            stmt.execute();
            System.out.println(stmt.getObject(1));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // test retrieving the Object Array, Return Cursor
        System.out.println("test retrieving the  Object Array: ");
        try {
            CallableStatement stmt = conn.prepareCall("{?=call PKG_ORACLE_TEST.LIST_DEPARTMENTS()}");
            stmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                System.out.println("dep_id=" + rs.getInt(1) + ", dep_name=" + rs.getString(2));
            }

            stmt = conn
                    .prepareCall("delete from department where dep_id IN (select dep_id from (select dep_id, rownum totalCount from department) where totalCount>3)");
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
