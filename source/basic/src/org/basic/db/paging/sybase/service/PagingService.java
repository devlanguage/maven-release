package org.basic.db.paging.sybase.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.basic.db.paging.sybase.model.Page;
import org.basic.db.paging.sybase.util.CacheUtil;
import org.basic.db.paging.sybase.util.CloneUtil;
import org.basic.db.paging.sybase.util.JDBCUtil;

public class PagingService {

  /**
   * jdbc分页add by wangmeng 2013-4-18 要求单表，无子查询，无关联查询
   * 
   * @param sql
   *          执行sql语句
   * @param cls
   *          封装数据表
   * @param id
   *          id列名
   * @param startNum
   *          从哪条开始。0...n
   * @param pageSize
   *          每页条数
   * @return
   */
  public synchronized Page findPageBySql(final String sql, Class cls, final String id, int startNum, final int pageSize) {
    final Page page = new Page();// 分页信息记录总数和当前页数据
    Connection con = null;
    try {
      String execsql = sql;
      String sql2 = sql.toLowerCase();
      long btime = System.currentTimeMillis();
      long etime;
      con = JDBCUtil.getConnection();
      PreparedStatement stmt;
      ResultSet rs;
      String counthql = sql2; // 计算count(*)的SQL
      int cacount = CacheUtil.getTotalSize(sql);// 读取总数缓存
      int total = 0;// 返回count数
      if (cacount == -1) {// 没有缓存
        if (counthql.indexOf("order") > -1) {
          counthql = "select count(*) " + counthql.substring(counthql.indexOf("from"), counthql.lastIndexOf("order"));
        } else {
          counthql = "select count(*) " + counthql.substring(counthql.indexOf("from"), counthql.length());
        }

        System.out.println(counthql);

        btime = System.currentTimeMillis();
        stmt = con.prepareStatement(counthql);
        rs = stmt.executeQuery();
        rs.next();
        page.setTotalCount(rs.getInt(1));
        total = rs.getInt(1);
        CacheUtil.setTotal(sql, total);
      } else {
        total = (Integer) cacount;
        page.setTotalCount(total);
      }
      if (total <= 0) {
        return page;
      }
      etime = System.currentTimeMillis();
      System.out.println("countsql处理时间：" + (etime - btime));

      btime = System.currentTimeMillis();
      if (total < 0) {// 小数据量处理
        System.out.println(execsql);
        stmt = con.prepareStatement(execsql);
        rs = stmt.executeQuery();
        int var = 0;
        while (var++ < startNum && rs.next())
          ;
        List list = CloneUtil.cloneResultSet2List(rs, cls, null, pageSize);
        page.setData(list);
      } else {// 大数据量处理
        String t = sql2.substring(sql2.indexOf("from") + 5);// 获取表名
        String idsql = "select " + id + " from " + t.trim().split(" ")[0] + " "; // 先查询id位置的sql
        if (sql2.contains(" where")) {// 拼where子句
          if (sql2.contains("order by")) {
            idsql += sql2.substring(sql2.indexOf(" where"), sql.indexOf("order by"));
          } else {
            idsql += sql2.substring(sql2.indexOf(" where"));
          }
        }
        int orderIndex = sql2.indexOf("order by");
        final String cachidsql = idsql;
        if (orderIndex == -1) {// 无排序可以增加缓存进行快速查找
          if (CacheUtil.isInitIndex(sql)) {// 有缓存使用缓存
            Entry<Integer, Object> entry = CacheUtil.getFloorEntry(sql, startNum);
            if (entry == null) {

            }
            startNum -= entry.getKey();
            if (idsql.contains("where")) {
              idsql += " and " + id + " >= " + entry.getValue();
            } else {
              idsql += "where " + id + " >= " + entry.getValue();
            }
            idsql = "select top " + (startNum + pageSize) + idsql.substring(idsql.indexOf("select") + 6);
          } else {// 没缓存增加
            new Thread() {
              @Override
              public synchronized void run() {
                if (CacheUtil.isPlaceholder(sql)) {
                  return;
                }
                CacheUtil.setPlaceholder(sql);
                try {
                  Map map = new HashMap();
                  System.out.println("缓存开始");
                  long b1 = System.currentTimeMillis();
                  Connection c = JDBCUtil.getConnection();
                  ResultSet rs = c.prepareStatement(cachidsql + " order by " + id).executeQuery();
                  int i = 0;
                  int cap = CacheUtil.getIndexSize(sql);
                  while (rs.next()) {
                    if (i % cap == 0) {
                      map.put(i, rs.getInt(1));
                    }
                    i++;
                  }
                  CacheUtil.initPageIndex(sql, map);
                  System.out.println("缓存结束" + (System.currentTimeMillis() - b1));
                  JDBCUtil.closeConnection(c);
                } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
            }.start();
          }
          idsql += " order by " + id;
        }
        System.out.println(idsql);

        stmt = con.prepareStatement(idsql);
        rs = stmt.executeQuery();
        int var = 0;
        while (var++ < startNum && rs.next())
          ;
        int i = 0;
        List ids = new ArrayList();
        while (rs.next() && i++ < pageSize) {// 把缓存数据取出来
          ids.add(rs.getObject(1));
        }
        etime = System.currentTimeMillis();
        System.out.println("idsql处理时间：" + (etime - btime));
        btime = System.currentTimeMillis();
        StringBuilder sbsql = new StringBuilder();
        if (orderIndex == -1) {// 无排序使用id>=?方式
          sbsql.append(id).append(">=").append(ids.get(0));
          execsql = "select top " + pageSize + execsql.substring(execsql.toLowerCase().indexOf("select") + 6);
        } else {// 有排序使用id=? or id=?
          sbsql.append(" (");
          for (int j = 0; j < ids.size(); j++) {
            if (sbsql.indexOf("(" + id) != -1) {
              sbsql.append(" or ");
            }
            sbsql.append(id + " = ").append(ids.get(j));
          }
          sbsql.append(")");
        }
        if (!execsql.toLowerCase().contains(" where ")) {
          execsql +=  " where " + sbsql.toString();
        } else {
          execsql += " and " + sbsql.toString();
        }
        System.out.println(execsql);
        // stmt = con.prepareStatement(execsql);
        // rs = stmt.executeQuery();
        List list = execQuery(execsql, cls, pageSize);
        etime = System.currentTimeMillis();
        System.out.println("查询数据时间： " + (etime - btime));
        page.setData(list);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtil.closeConnectionByThread(con);
    }
    return page;
  }

  private List execQuery(String sql, Class cls, int pageSize) throws Exception {

    List date = null;
    Connection con = JDBCUtil.getConnection();
    PreparedStatement stmt = con.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    date = CloneUtil.cloneResultSet2List(rs, cls, null, pageSize);

    JDBCUtil.closeConnection(con);
    return date;
  }

}
