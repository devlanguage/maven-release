CREATE OR REPLACE type EMP_TYPE AS  object
  (
    id   NUMBER ,
    name VARCHAR2 ,
    birthday DATE ,
    sex CHAR (1) 
  );
CREATE OR REPLACE PACKAGE EMP_UTIL IS
  FUNCTION get_emp_by_id  (p_id NUMBER)  RETURN EMP_TYPE;
  PROCEDURE create_emp  (p_emp EMP_TYPE);
END EMP_UTIL;

public class Sample
{
  Map getTypeMap() {
    Map map = new HashMap();
    map.put("EMP_TYPE", EmpType.class);
    return map;
  }

  public EmpType getEmpById(long id){
    Connection conn = null;
    CallableStatement cstmt = null;
    try  {
      conn = getConnection();
      conn.setTypeMap(getTypeMap());
      cstmt = conn.prepareCall("{? = call emp_util.get_emp_by_id(?)}");
      cstmt.registerOutParameter(1, Types.STRUCT, "EMP_TYPE");
      cstmt.setLong(2, id);
      cstmt.execute();
      return (EmpType) cstmt.getObject(1);
    }
    catch (Exception e)
    {
      return null;
    }
    finally
    {
      cstmt.close();
      conn.close();
    }
  }
}
