package org.basic.db.paging.sybase.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.basic.db.paging.sybase.ResultObject;

/**
 * 复制对象。
 * 
 * @author wangmeng 2-13-4-18
 * 
 */
public class CloneUtil {
  /**
   * 从数据库复制到集合 数据库字段必须和类字段一样
   * 
   * @param rs
   * @param dest
   * @param handler
   * @param pageSize
   * @return
   */
  public static List cloneResultSet2List(ResultSet rs, Class dest, CloneHandler handler, int pageSize) {
    List list = new ArrayList();
    try {
      int i = 0;
      while (rs.next() && i++ < pageSize) {
        Object t = dest.newInstance();
        copyResultSet2Obj(rs, t);
        if (handler != null) {
          handler.copy(rs, t);
        }
        list.add(t);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 克隆对象
   * 
   * @param src
   * @param dest
   * @param handler
   * @return
   */
  public static List cloneCollection2List(Collection src, Class dest, CloneHandler handler) {
    List list = new ArrayList();
    try {
      for (Object object : src) {
        Object t = dest.newInstance();
        copyFromObj2Obj(object, t);
        if (handler != null) {
          handler.copy(object, t);
        }
        list.add(t);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public static void copyFromObj2Obj(Object src, Object dest) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    Field[] fiels = src.getClass().getDeclaredFields();
    for (Field field : fiels) {
      String fName = field.getName();
      if (getGetOrSetMethod(src, fName, "get") == null) {
        continue;
      }
      copyFiled(src, dest, fName);
    }
  }

  public static void copyResultSet2Obj(ResultSet rs, Object dest) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException, SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    rs.getFetchSize();
    for (int i = 1; i <= meta.getColumnCount(); i++) {
      String fName = meta.getColumnName(i);
      Object obj = rs.getObject(fName);
      if (obj == null) {
        continue;
      }
      if (dest instanceof ResultObject) {
        ResultObject destObj = (ResultObject) dest;
        destObj.addData(fName, obj);
      } else {
        Method m = getGetOrSetMethod(dest, fName, "get");
        if (m == null) {
          continue;
        }
        Method setmethod = getGetOrSetMethod(dest, fName, "set");
        if (setmethod == null) {
          return;
        }
        setmethod.invoke(dest, obj);
      }
    }
  }

  private static void copyFiled(Object src, Object dest, String fieldName) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException {
    Method getmethod = getGetOrSetMethod(src, fieldName, "get");
    Method setmethod = getGetOrSetMethod(dest, fieldName, "set");
    if (setmethod == null || getmethod == null) {
      return;
    }
    setmethod.invoke(dest, getmethod.invoke(src));
  }

  private static Method getGetOrSetMethod(Object src, String fieldName, String getOrset) {
    Method[] methods = src.getClass().getMethods();
    String mName = getOrset + StringUtils.upperCaseFirstChar(fieldName);
    for (Method method : methods) {
      if (method.getName().equals(mName)) {
        return method;
      }
    }
    return null;
  }

  public static void main(String[] args) {
  }
}
