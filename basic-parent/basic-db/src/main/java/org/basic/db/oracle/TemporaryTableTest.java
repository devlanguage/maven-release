package org.basic.db.oracle;

import java.io.UnsupportedEncodingException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.basic.common.bean.DatabaseType;
import org.basic.common.util.DbUtils;

public class TemporaryTableTest extends OracleDbTest {

    public void testStrongReference() {

        Object referent = new Object();
        Object strongReference = referent;
        referent = null;
        System.gc();
        assert (null != strongReference);
    }
//软引用的对象在GC的时候不会被回收，只有当内存不够用的时候才会真正的回收，因此软引用适合缓存的场合，这样使得缓存中的对象可以尽量的再内存中待长久一点。
    public void testSoftReference() {

        String str = "test";
        SoftReference<String> softreference = new SoftReference<String>(str);
        str = null;
        System.gc();
        assert (null != softreference.get());
    }
//弱引用有利于对象更快的被回收，假如一个对象没有强引用只有弱引用，那么在GC后，这个对象肯定会被回收
    public void testWeakReference() {

        String str = "test";
        WeakReference<String> weakReference = new WeakReference<String>(str);
        str = null;
        System.gc();
        assert (null != weakReference.get());
    }

    public void testPhantomReference() {

        ReferenceQueue<String> referQueue = new ReferenceQueue<String>();
        String str = "test";
        PhantomReference<String> phantomReference = new PhantomReference<String>(str,referQueue);
        str = null;
        System.gc();
        assert (null != phantomReference.get());
    }
    

    public static void main(String[] args) {

        new TemporaryTableTest().startTest();
    }

    @Override
    public void startTest() {

        Connection conn1 = null, conn2 = null;
        Statement stmt1 = null, stmt2 = null;
        try {
            conn1 = dbDao.getConnection(DatabaseType.ORACLE);
            conn2 = dbDao.getConnection(DatabaseType.ORACLE);

            System.out.println(conn1.getAutoCommit());
            conn1.setAutoCommit(false);
            stmt1 = conn1.createStatement();
            stmt1.execute("insert into AA_TEMP_DEPARTMENT_TRANS(dep_id,dep_name) values(1, 'Test1')");
            stmt1.execute("select * from AA_TEMP_DEPARTMENT_TRANS");
            ResultSet rs = stmt1.getResultSet();
            DbUtils.printResultSet(rs);
            conn1.commit();
            
            stmt2 = conn1.createStatement();
            stmt2.execute("select * from AA_TEMP_DEPARTMENT_TRANS");
            rs = stmt2.getResultSet();
            DbUtils.printResultSet(rs);
            

            stmt2 = conn2.createStatement();
            stmt2.execute("select * from AA_TEMP_DEPARTMENT_TRANS");
            rs = stmt2.getResultSet();
            DbUtils.printResultSet(rs);

            conn1.close();
            conn2.close();
            
            conn1 =dbDao.getConnection(DatabaseType.ORACLE);
            stmt2 = conn1.createStatement();
            stmt2.execute("select * from AA_TEMP_DEPARTMENT_TRANS");
            rs = stmt2.getResultSet();
            DbUtils.printResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
