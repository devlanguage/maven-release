package org.basic.db.oracle;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.basic.common.bean.DatabaseType;
import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.common.util.DbUtil;
import org.basic.db.util.DbDao;
import org.basic.db.util.DbDaoFactory;

/**
 * 
 */
public class LobSample {

    static DbDao dbDao = null;
    {
        try {
            dbDao = DbDaoFactory.createDbDao(PoolType.PROXOOL);
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }
    public final static String logo = "resources/waterfall.jpg";
    public final static String desc = "resources/BranchHistory.txt";

    public final static String oracle_charset = "ISO-8859-1";
    public final static String utf8_charset = "UTF-8";
    public final static String java_charset = "GB2312";

    public static void main(String[] args) {

        LobSample tester = new LobSample();
        try {
            tester.testCharset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // tester.startTest();

    }

    private void testCharset() throws Exception {

        FileInputStream fin = new FileInputStream("resources/BranchHistory.txt");
        byte[] bs = new byte[5];
        fin.read(bs, 0, 4);
        printBytes(bs);
        fin.close();
        // String source = "涨a";
        // byte[] out1, out2, out3, out4;
        // out1 = source.getBytes(oracle_charset);
        // out2 = source.getBytes(java_charset);
        // out3 = new String(source.getBytes(java_charset),
        // oracle_charset).getBytes(oracle_charset);
        // // System.out.println(new String(out, oracle_charset));
        // printBytes(out1);
        // printBytes(out2);
        // printBytes(out3);
    }

    void printBytes(byte[] bs) {

        for (byte b : bs) {
            System.out.print("=" + b);
        }
        System.out.println();
    }

    private void startTest() {

        Connection conn = null;
        try {
            conn = dbDao.getConnection(DatabaseType.ORACLE);
            // updateLob(conn);
            insertLob(conn);
            queryLob(conn);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeQuitely(conn);

        }
    }

    private void queryLob(Connection conn) throws Exception {

        PreparedStatement pstmt = conn.prepareStatement("select * from department where dep_id=5");
        ResultSet rs1 = pstmt.executeQuery();
        int LENGTH = 1024;
        char[] buffer1 = new char[LENGTH];
        byte[] buffer2 = new byte[LENGTH];
        int received = -1;
        StringBuffer sb = new StringBuffer();
        while (rs1.next()) {
            System.out.print("dep_name=" + rs1.getString("dep_name"));
            oracle.sql.CLOB description = (oracle.sql.CLOB) rs1.getClob("description");
            Reader reader = description.getCharacterStream();
            while ((received = reader.read(buffer1, 0, LENGTH)) != -1) {
                sb.append(new String(buffer1, 0, received));
            }
            System.out.println(", description=" + new String(sb.toString().getBytes(oracle_charset), java_charset));

            sb.delete(0, sb.length());
            oracle.sql.BLOB logo = (oracle.sql.BLOB) rs1.getBlob("logo");
            InputStream input = logo.getBinaryStream();
            while ((received = input.read(buffer2, 0, LENGTH)) != -1) {
                sb.append(new String(buffer2, 0, received));
            }
            System.out.println(", logo=" + new String(sb.toString().getBytes(oracle_charset), java_charset));
        }
    }

    private void insertLob(Connection conn) throws Exception {

        PreparedStatement pstmt = conn
                .prepareStatement("insert into department(dep_id,dep_name, logo, description) values(?, ?, empty_blob(), empty_clob())");
        // pstmt.setInt(1, 5);
        // pstmt.setString(2, "R&D");
        // pstmt.executeUpdate();
        // pstmt.close();

        // pstmt.addBatch("delete from department where dep_id = 5");
        // pstmt.executeBatch();

        pstmt = conn.prepareStatement("select * from department where dep_id = ? for update");
        pstmt.setInt(1, 5);
        ResultSet rs1 = pstmt.executeQuery();
        rs1.next();
        oracle.sql.CLOB description = (oracle.sql.CLOB) rs1.getClob("description");
        oracle.sql.BLOB logo = (oracle.sql.BLOB) rs1.getBlob("logo");

        Writer writer1 = description.getCharacterOutputStream();
        // writer1.append("23");
        writer1.write(new String("张三从来没有".getBytes(java_charset), oracle_charset).toCharArray());
        writer1.flush();
        pstmt.close();

        OutputStream output = logo.getBinaryOutputStream();
        output.write("这是Logo图片".getBytes(oracle_charset));
        output.close();

        // description.setBytes("just test".getBytes(oracle_charset));
        // logo.setBytes("waterfall.jpg".getBytes(oracle_charset));

    }

}
