package org.basic.grammar.jdk.jdk7.e3_jdbc4_1;

/**
 * <pre>
 * 3. JDBC 4.1
 * 
 * 3.1.可以使用try-with-resources自动关闭Connection, ResultSet, 和 Statement资源对象 
 * 
 * 3.2. RowSet 1.1：引入RowSetFactory接口和RowSetProvider类，可以创建JDBC driver支持的各种 row sets，
 * 这里的rowset实现其实就是将sql语句上的一些操作转为方法的操作，封装了一些功能。
 * 
 * 3.3. JDBC-ODBC驱动会在jdk8中删除 
 * 
 *     try (Statement stmt = con.createStatement()) { 
 *      RowSetFactory aFactory = RowSetProvider.newFactory();
 *       CachedRowSet crs = aFactory.createCachedRowSet();
 *       
 *      RowSetFactory rsf = RowSetProvider.newFactory("com.sun.rowset.RowSetFactoryImpl", null);
 *     WebRowSet wrs = rsf.createWebRowSet();
 *     createCachedRowSet 
 *     createFilteredRowSet 
 *     createJdbcRowSet 
 *     createJoinRowSet 
 *     createWebRowSet
 * 
 * </pre>
 * 
 * @author ygong
 *
 */
public class Jdbc41Test {

}
