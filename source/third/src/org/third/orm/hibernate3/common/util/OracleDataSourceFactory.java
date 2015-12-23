package org.third.orm.hibernate3.common.util;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;

import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.pool.OracleOCIConnectionPool;
import oracle.jdbc.xa.client.OracleXADataSource;

public class OracleDataSourceFactory implements ObjectFactory {
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Fri_Jul_31_19:30:28_PDT_2009";
  public static final boolean TRACE = false;
  public static final boolean PRIVATE_TRACE = false;

  public Object getObjectInstance(Object object, Name name, Context context, Hashtable hashtable) throws Exception {
    Reference reference = (Reference) object;
    Object object_0_ = null;
    String string = reference.getClassName();
    Properties properties = new Properties();
    OracleDataSource oracledatasource;
    if (string.equals("oracle.jdbc.pool.OracleDataSource") || string.equals("oracle.jdbc.xa.client.OracleXADataSource")) {
      if (string.equals("oracle.jdbc.pool.OracleDataSource"))
        oracledatasource = new OracleDataSource();
      else
        oracledatasource = new OracleXADataSource();
      Object object_1_ = null;
      StringRefAddr stringrefaddr;
      if ((stringrefaddr = (StringRefAddr) reference.get("connectionCachingEnabled")) != null) {
        String string_2_ = (String) stringrefaddr.getContent();
        if (string_2_.equals("true"))
          oracledatasource.setConnectionCachingEnabled(true);
      }
      if ((stringrefaddr = (StringRefAddr) reference.get("connectionCacheName")) != null)
        oracledatasource.setConnectionCacheName((String) stringrefaddr.getContent());
      if ((stringrefaddr = (StringRefAddr) reference.get("connectionCacheProperties")) != null) {
        String string_3_ = (String) stringrefaddr.getContent();
        Properties properties_4_ = extractConnectionCacheProperties(string_3_);
        oracledatasource.setConnectionCacheProperties(properties_4_);
      }
      if ((stringrefaddr = ((StringRefAddr) reference.get("fastConnectionFailoverEnabled"))) != null) {
        String string_5_ = (String) stringrefaddr.getContent();
        if (string_5_.equals("true"))
          oracledatasource.setFastConnectionFailoverEnabled(true);
      }
      if ((stringrefaddr = (StringRefAddr) reference.get("onsConfigStr")) != null)
        oracledatasource.setONSConfiguration((String) stringrefaddr.getContent());
    } else if (string.equals("oracle.jdbc.pool.OracleConnectionPoolDataSource"))
      oracledatasource = new OracleConnectionPoolDataSource();
    else if (string.equals("oracle.jdbc.pool.OracleOCIConnectionPool")) {
      oracledatasource = new OracleOCIConnectionPool();
      String string_6_ = null;
      String string_7_ = null;
      String string_8_ = null;
      String string_9_ = null;
      String string_10_ = null;
      String string_11_ = null;
      String string_12_ = null;
      Object object_13_ = null;
      Object object_14_ = null;
      String string_15_ = null;
      StringRefAddr stringrefaddr;
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_min_limit")) != null)
        string_6_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_max_limit")) != null)
        string_7_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_increment")) != null)
        string_8_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_active_size")) != null)
        string_9_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_pool_size")) != null)
        string_10_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_timeout")) != null)
        string_11_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("connpool_nowait")) != null)
        string_12_ = (String) stringrefaddr.getContent();
      if ((stringrefaddr = (StringRefAddr) reference.get("transactions_distributed")) != null)
        string_15_ = (String) stringrefaddr.getContent();
      properties.put("connpool_min_limit", string_6_);
      properties.put("connpool_max_limit", string_7_);
      properties.put("connpool_increment", string_8_);
      properties.put("connpool_active_size", string_9_);
      properties.put("connpool_pool_size", string_10_);
      properties.put("connpool_timeout", string_11_);
      if (string_12_ == "true")
        properties.put("connpool_nowait", string_12_);
      if (string_15_ == "true")
        properties.put("transactions_distributed", string_15_);
    } else
      return null;
    if (oracledatasource != null) {
      Object object_16_ = null;
      StringRefAddr stringrefaddr;
      if ((stringrefaddr = (StringRefAddr) reference.get("url")) != null)
        oracledatasource.setURL((String) stringrefaddr.getContent());
      if (((stringrefaddr = (StringRefAddr) reference.get("userName")) != null)
          || (stringrefaddr = (StringRefAddr) reference.get("u")) != null
          || ((stringrefaddr = (StringRefAddr) reference.get("user")) != null))
        oracledatasource.setUser((String) stringrefaddr.getContent());
      if (((stringrefaddr = (StringRefAddr) reference.get("passWord")) != null)
          || ((stringrefaddr = (StringRefAddr) reference.get("password")) != null))
        oracledatasource.setPassword((String) stringrefaddr.getContent());
      if (((stringrefaddr = (StringRefAddr) reference.get("description")) != null)
          || ((stringrefaddr = (StringRefAddr) reference.get("describe")) != null))
        oracledatasource.setDescription((String) stringrefaddr.getContent());
      if (((stringrefaddr = (StringRefAddr) reference.get("driverType")) != null)
          || ((stringrefaddr = (StringRefAddr) reference.get("driver")) != null))
        oracledatasource.setDriverType((String) stringrefaddr.getContent());
      if (((stringrefaddr = (StringRefAddr) reference.get("serverName")) != null)
          || ((stringrefaddr = (StringRefAddr) reference.get("host")) != null))
        oracledatasource.setServerName((String) stringrefaddr.getContent());
      if ((stringrefaddr = (StringRefAddr) reference.get("databaseName")) != null
          || ((stringrefaddr = (StringRefAddr) reference.get("sid")) != null))
        oracledatasource.setDatabaseName((String) stringrefaddr.getContent());
      if ((stringrefaddr = (StringRefAddr) reference.get("serviceName")) != null)
        oracledatasource.setServiceName((String) stringrefaddr.getContent());
      if ((stringrefaddr = (StringRefAddr) reference.get("networkProtocol")) != null
          || ((stringrefaddr = (StringRefAddr) reference.get("protocol")) != null))
        oracledatasource.setNetworkProtocol((String) stringrefaddr.getContent());
      if (((stringrefaddr = (StringRefAddr) reference.get("portNumber")) != null)
          || ((stringrefaddr = (StringRefAddr) reference.get("port")) != null)) {
        String string_17_ = (String) stringrefaddr.getContent();
        oracledatasource.setPortNumber(Integer.parseInt(string_17_));
      }
      if ((stringrefaddr = (StringRefAddr) reference.get("tnsentryname")) != null
          || ((stringrefaddr = (StringRefAddr) reference.get("tns")) != null))
        oracledatasource.setTNSEntryName((String) stringrefaddr.getContent());
      else if (string.equals("oracle.jdbc.pool.OracleOCIConnectionPool")) {
        String string_18_ = null;
        if ((stringrefaddr = ((StringRefAddr) reference.get("connpool_is_poolcreated"))) != null)
          string_18_ = (String) stringrefaddr.getContent();
        if (string_18_.equals("true"))
          ((OracleOCIConnectionPool) oracledatasource).setPoolConfig(properties);
      }
    }
    return oracledatasource;
  }

  private Properties extractConnectionCacheProperties(String string) throws SQLException {
    Properties properties = new Properties();
    string = string.substring(1, string.length() - 1);
    int i = string.indexOf("AttributeWeights", 0);
    if (i >= 0) {
      if (string.charAt(i + 16) != '=' || i > 0 && string.charAt(i - 1) != ' ') {
        SQLException sqlexception = (DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 139));
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      Properties properties_19_ = new Properties();
      int i_20_ = string.indexOf("}", i);
      String string_21_ = string.substring(i, i_20_);
      String string_22_ = string_21_.substring(18);
      StringTokenizer stringtokenizer = new StringTokenizer(string_22_, ", ");
      StringTokenizer stringtokenizer_23_;
      stringtokenizer_23_ = stringtokenizer;
      synchronized (stringtokenizer_23_) {
        while (stringtokenizer.hasMoreTokens()) {
          String string_24_ = stringtokenizer.nextToken();
          int i_25_ = string_24_.length();
          int i_26_ = string_24_.indexOf("=");
          String string_27_ = string_24_.substring(0, i_26_);
          String string_28_ = string_24_.substring(i_26_ + 1, i_25_);
          properties_19_.setProperty(string_27_, string_28_);
        }
      }
      properties.put("AttributeWeights", properties_19_);
      if (i > 0 && i_20_ + 1 == string.length())
        string = string.substring(0, i - 2);
      else if (i > 0 && i_20_ + 1 < string.length()) {
        String string_29_ = string.substring(0, i - 2);
        String string_30_ = string.substring(i_20_ + 1, string.length());
        string = string_29_.concat(string_30_);
      } else
        string = string.substring(i_20_ + 2, string.length());
    }
    StringTokenizer stringtokenizer = new StringTokenizer(string, ", ");
    StringTokenizer stringtokenizer_31_;
    stringtokenizer_31_ = stringtokenizer;
    synchronized (stringtokenizer_31_) {
      while (stringtokenizer.hasMoreTokens()) {
        String string_32_ = stringtokenizer.nextToken();
        int i_33_ = string_32_.length();
        int i_34_ = string_32_.indexOf("=");
        String string_35_ = string_32_.substring(0, i_34_);
        String string_36_ = string_32_.substring(i_34_ + 1, i_33_);
        properties.setProperty(string_35_, string_36_);
      }
    }
    return properties;
  }

  protected OracleConnection getConnectionDuringExceptionHandling() {
    return null;
  }
}