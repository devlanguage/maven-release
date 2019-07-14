JDBC3.0 Compliant with SQL:99
Compliant with SQL:2003
Supported  by JDK6
1. Automatically Load the registerd JDBC Driver  
      for(Enumeration<Driver> registerdDrivers = DriverManager.getDrivers(); registerdDrivers.hasMoreElements();){
            java.sql.Driver driver = registerdDrivers.nextElement();          
      }
   vendorDb.jar
      |__META-INF
           |_services
              |__java.sql.Driver -->content is plain text such as com.mysql.jdbc.Driver
2. Add new Type:
    public interface SQLXML