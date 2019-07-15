package org.basic.net.c20_jmx.jdmk.chat.socket;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SocketProc {

    private static final String separator = "#";

    private static final String emptyMsg = "0000#T#1#4.2#0#0#0#0#0@@#~~";

    private static Connection con = null;

    private static Socket socket = null;

    private static BufferedWriter out = null;

    private static String remoteIp = null;
    
    private static int remotePort = 0;
    private static String remoteViewName = "";

    private static final String serverRegSql = "update tmfnbi_server_reg_tab set socket_normal = ? where ip = ? and port = ? and view_name = ?";
    private static final String insertExceptionSql = "insert into tmfnbi_exeception_error_info values(?, ?, ?, sysdate, ?)";

    private static PreparedStatement serverRegStmt = null;

    private static PreparedStatement insertExceptionStmt = null;

    public static void socket_proc(String ip, int port, String viewName) {

        remoteIp = ip;
        remotePort = port;
        remoteViewName = viewName;

        String countSql = "select count(id) from " + viewName;
        String contentSql = "select id,msg1,msg2,source_type,OPERATION_TYPE from "

        + viewName + " order by id asc";

        String delSql = "delete from " + viewName + " WHERE id <= ?";

        PreparedStatement countStmt = null;

        PreparedStatement contentStmt = null;

        Statement stmt = null;

        try {

            con = DriverManager.getConnection("jdbc:default:connection:");

            serverRegStmt = con.prepareStatement(serverRegSql);

            insertExceptionStmt = con.prepareStatement(insertExceptionSql);

            countStmt = con.prepareStatement(countSql);

            contentStmt = con.prepareStatement(contentSql);

            stmt = con.createStatement();

            serverRegStmt.setInt(1, 1);
            serverRegStmt.setString(2, ip);
            serverRegStmt.setInt(3, port);
            serverRegStmt.setString(4, remoteViewName);

            serverRegStmt.executeUpdate();

            con.commit();

            socket = new Socket(ip, port);

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Thread.sleep(100);

        } catch (Exception e) {

            System.err.println("ERROR! Failure! Please check it! \nException Message: "

            + e.getMessage());

            insertExceptionInfo(e);

            onExceptionOrTimeout();

            return;

        }

        StringBuffer msg = null;

        StringBuffer idString = null;

        String id = null;

        long lastSendTime = System.currentTimeMillis();

        long lastCheckTime = System.currentTimeMillis();

        boolean hasNoData = false;

        try {

            int k = 0;

            while (true) {

                k++;

                ResultSet rs = countStmt.executeQuery();

                rs.next();

                int count = rs.getInt(1);

                rs.close();

                if (count == 0) {

                    hasNoData = true;

                    lastCheckTime = System.currentTimeMillis();

                } else {

                    hasNoData = false;

                    rs = contentStmt.executeQuery();

                    idString = new StringBuffer();

                    int i = 0;

                    while (rs.next()) {

                        i++;

                        id = rs.getString("id");

                        idString.append(id).append(",");

                        msg = new StringBuffer();

                        msg.append(id).append(separator).append(

                        rs.getString("source_type"));

                        msg.append(separator).append(

                        rs.getString("operation_type")).append(

                        separator);

                        msg.append("4.2#0#").append(rs.getString("msg1") == null ?

                        0 : rs.getString("msg1").length());

                        msg.append(separator).append(rs.getString("msg2") == null ?

                        0 : rs.getString("msg2").length());

                        msg.append("#0#0@@").append(
                                rs.getString("msg1") == null ? "" : rs.getString("msg1"))

                        .append("####");

                        msg.append(rs.getString("msg2") == null ? "" : rs.getString("msg2"))
                                .append("~~");

                        out.write(msg.toString());

                        out.flush();

                        if (i % 500 == 0) {

                            String idList = idString.toString();

                            idList = idList.substring(0, idList.length() - 1);

                            stmt.executeUpdate(delSql + idList + ")");

                            idString = new StringBuffer();

                        }

                    }

                    rs.close();

                    if (i % 500 != 0) {

                        String idList = idString.toString();

                        idList = idList.substring(0, idList.length() - 1);

                        stmt.executeUpdate(delSql + idList + ")");

                    }

                    lastSendTime = lastCheckTime;

                    lastCheckTime = System.currentTimeMillis();

                    con.commit();

                }

                if (lastCheckTime - lastSendTime >= 10000) {

                    if (hasNoData) {

                        if (lastCheckTime - lastSendTime >= 1000 * 60 * 60) {

                            return;

                        } else {

                            out.write(emptyMsg);

                            out.flush();

                            lastSendTime = System.currentTimeMillis();

                            lastCheckTime = System.currentTimeMillis();

                        }

                    }

                }

                Thread.sleep(100);

            }

        } catch (Throwable e) {

            System.err.println("ERROR! Failure! \nException Message: "

            + e.getMessage());

            insertExceptionInfo(e);

            return;

        } finally {

            try {

                countStmt.close();

                contentStmt.close();

                stmt.close();

            } catch (Exception e) {

                // do nothing

            }

            onExceptionOrTimeout();

        }

    }

    private static void insertExceptionInfo(Throwable e) {

        try {

            insertExceptionStmt.setString(1, remoteIp);

            insertExceptionStmt.setInt(2, remotePort);

            insertExceptionStmt.setString(3, remoteViewName);

            insertExceptionStmt.setString(4, e.getMessage());

            insertExceptionStmt.executeUpdate();

            con.commit();

        } catch (SQLException e1) {

            e1.printStackTrace();

        }

    }

    private static void onExceptionOrTimeout() {

        try {

            socket.close();

        } catch (Exception e) {

            // do nothing

        }

        try {

            if (!con.isClosed()) {

                serverRegStmt.setInt(1, 0);

                serverRegStmt.setString(2, remoteIp);

                serverRegStmt.setInt(3, remotePort);

                serverRegStmt.setString(4, remoteViewName);

                serverRegStmt.executeUpdate();

                con.commit();

                serverRegStmt.close();

                con.close();

            }

        } catch (Exception ex) {

            // do nothing

        }

    }
}
