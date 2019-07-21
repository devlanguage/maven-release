package org.basic.db.oracle.collection;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.basic.common.bean.DatabaseType;
import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.common.util.DbUtils;
import org.basic.db.util.DbDao;
import org.basic.db.util.DbDaoFactory;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class OracleLobObjectTester {

    static DbDao dbDao = null;
    {
        try {
            dbDao = DbDaoFactory.createDbDao(PoolType.PROXOOL);
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        OracleLobObjectTester tester = new OracleLobObjectTester();
        tester.startTest();

    }

    private void startTest() {

        Connection conn = null;
        try {
            conn = dbDao.getConnection(DatabaseType.ORACLE);
            testLobObejct(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuitely(conn);
        }
    }

    /**
     * <pre>
     * create table db_person(
     *      psn_id NUMBER PRIMARY KEY, psn_name VARCHAR2(200),
     *      psn_photo BLOB, psn_description CLOB
     * ); 
     * create sequence db_person_seq;
     * 
     * CREATE OR REPLACE TRIGGER trig_db_person
     * before INSERT ON &quot;DB_PERSON&quot; FOR EACH ROW
     * BEGIN
     *   IF inserting THEN
     *      IF :NEW.PSN_ID IS NULL THEN
     *        SELECT db_person_seq.nextval INTO :NEW.PSN_ID FROM dual;
     *      END IF;
     *   END IF;
     * END;
     * 
     * </pre>
     * 
     * @param conn
     */

    public void testLobObejct(Connection conn) {

        // Test Submit Object
        System.out.println("Test Submit Object: ");
        try {
            String psnName = "TEST_" + 1;
            Statement stmt = conn.createStatement();
            // true if the first result is a ResultSet object; false if it is an update count or there are no results
            boolean result = stmt.execute("DELETE FROM db_person WHERE 1=1");

            // 使用EMPTY_CLOB()来初始化CLOB字段
            conn.setAutoCommit(false);
            stmt.execute("insert into db_person(psn_name, psn_photo, psn_description) VALUES('" + psnName
                    + "', empty_blob(), empty_clob())");

            ResultSet rs = stmt
                    .executeQuery("SELECT psn_id, psn_name, psn_photo, psn_description FROM db_person WHERE psn_name='"
                            + psnName + "'");
            while (rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2));
                oracle.sql.CLOB psnDescription = (CLOB) rs.getClob("psn_description");
                updateOutputStreamByFile(psnDescription.getAsciiOutputStream(), OracleLobObjectTester.class
                        .getResource("test01_description.txt").getFile());

                oracle.sql.BLOB psnPhoto = (BLOB) rs.getBlob("psn_photo");
                updateOutputStreamByFile(psnPhoto.getBinaryOutputStream(),
                        OracleLobObjectTester.class.getResource("test01_photo.bmp").getFile());
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    final static int BUFFER_SIZE = 512;

    public final static void updateOutputStreamByFile(OutputStream out, String file) {

        // char[] buffer = new char[BUFFER_SIZE];
        byte[] buffer = new byte[BUFFER_SIZE];
        int read = -1;
        try {
            // FileReader test01Description = new
            // FileReader(OracleLobObjectTester.class.getResource(
            // "test01_description.txt").getFile());
            FileInputStream test01Description = new FileInputStream(file);
            while ((read = test01Description.read(buffer, 0, BUFFER_SIZE)) != -1) {
                System.out.println(new String(buffer, 0, read));
                out.write(buffer, 0, read);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
