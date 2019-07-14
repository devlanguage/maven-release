package org.basic.db.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbSocketTest {

    private static final String separator = "#";

    public static void main(String[] args) {
        DbSocketTest t = new DbSocketTest();
        Connection conn = null;
        try {
             conn =  OracleConnectionPool.getInstance().getConnection();
            t.test(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            OracleConnectionPool.getInstance().closeConnectionSliently(conn);
        }

    }

    public void test(Connection con) {

        StringBuffer msgBuffer = new StringBuffer();
        StringBuffer msgIdListBuffer = new StringBuffer();
        // StringBuffer msgBuffer = new StringBuffer();
        // StringBuffer msgIdListBuffer = new StringBuffer();
        String id = null;
        String contentSql = "select id,msg1,msg2,source_type,OPERATION_TYPE,user from NBI_ALL_VIEW_4 order by id asc";
        String sqlRemoveTrashMsg = "delete from NBI_ALL_VIEW_4 where id in (";
        PreparedStatement contentStmt = null;

        long lastSendTime = System.currentTimeMillis();
        long lastCheckTime = System.currentTimeMillis();
        
        boolean hasNoData = false;
        ResultSet rs = null;
        try {
            contentStmt=  con.prepareStatement(contentSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // int k = 0;
            while (true) {
                // k++;
                // ResultSet rs = countStmt.executeQuery();
                // rs.next();
                // int count = rs.getInt(1);
                // rs.close();
                // if (count == 0) {
                // hasNoData = true;
                // lastCheckTime = System.currentTimeMillis();
                // } else {
                // hasNoData = false;
                rs = contentStmt.executeQuery();
                if (rs.next()) {
                    hasNoData = false;
                    rs.beforeFirst();
                } else {
                    hasNoData = true;
                    lastCheckTime = System.currentTimeMillis();
                }
                msgIdListBuffer.delete(0, msgIdListBuffer.length());
                int i = 0;
                while (rs.next()) {
                    i++;
                    id = rs.getString("id");
                    msgIdListBuffer.append(id).append(",");
                    msgBuffer.delete(0, msgBuffer.length());
                    msgBuffer.append(id).append(separator).append(rs.getString("source_type"))
                            .append(separator).append(rs.getString("operation_type")).append(
                                    separator).append("4.2#0#").append(
                                    rs.getString("msg1") == null ? 0 : rs.getString("msg1")
                                            .length()).append(separator).append(
                                    rs.getString("msg2") == null ? 0 : rs.getString("msg2")
                                            .length()).append("#0#0@@").append(
                                    rs.getString("msg1") == null ? "" : rs.getString("msg1"))
                            .append("####").append(
                                    rs.getString("msg2") == null ? "" : rs.getString("msg2"))
                            .append("~~").append(rs.getString("user"));

//                    addNbiTrace("Oracle_Socket send: " + msgBuffer.toString(), con);

                    // out.write(msgBuffer.toString());
                    // out.flush();
                    if (i % 500 == 0) {
                        String idList = msgIdListBuffer.toString();
                        idList = idList.substring(0, idList.length() - 1);
                        // stmt.executeUpdate(sqlRemoveTrashMsg + idList + ")");
                        msgIdListBuffer.delete(0, msgIdListBuffer.length());
                    }
                }

                rs.close();

                if (i % 500 != 0) {
                    String idList = msgIdListBuffer.toString();
                    idList = idList.substring(0, idList.length() - 1);
                    // stmt.executeUpdate(sqlRemoveTrashMsg + idList + ")");
                }
                lastSendTime = lastCheckTime;
                lastCheckTime = System.currentTimeMillis();
                con.commit();
                System.out.println(lastCheckTime - lastSendTime);
                // }
                if (lastCheckTime - lastSendTime >= 100) {
                    if (hasNoData) {
                        if (lastCheckTime - lastSendTime >= 1000 * 3600 * 24) {
                            return;
                        } else {
                            // out.write(emptyMsg);
                            // out.flush();
                            lastSendTime = System.currentTimeMillis();
                            lastCheckTime = System.currentTimeMillis();
                        }
                    }
                }
                Thread.sleep(50);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("ERROR! Failure! \nException Message: " + e.getMessage());
            return;
        } finally {
            try {
                // countStmt.close();
                contentStmt.close();
                // stmt.close();
            } catch (Exception e) {
                // do nothing
            }
            // onExceptionOrTimeout();
        }
    }
}
