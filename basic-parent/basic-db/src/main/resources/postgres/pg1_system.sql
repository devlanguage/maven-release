--Create Trigger function
CREATE OR REPLACE FUNCTION auditlogfunc()
  RETURNS TRIGGER as
$$  
BEGIN  
  INSERT INTO AUDIT_HIS(EMP_ID,EMP_NAME,ENTRY_DATE) VALUES (OLD.ID,OLD.NAME,current_timestamp); 
  INSERT INTO AUDIT(EMP_ID,EMP_NAME,ENTRY_DATE) VALUES (NEW.ID,NEW.NAME,current_timestamp);   
  
  RETURN NULL;   
END;  
$$ LANGUAGE plpgsql;

a.触发器函数是触发器触发时调用，函数返回的类型必须是TRIGGER ，且不能有任何参数
b.postgresql触发器函数中自带一些特殊变量：
  NEW：数据类型是record，在insert、update操作触发时存储新的数据行
  OLD：数据类型是record，在update、delete操作触发时存储旧的数据行
  TG_OP：内容为“INSERT”，“UPDATE”，“DELETE”，“TRUNCATE”，用于指定DML语句类型
  TG_TABLE_NAME：触发器所在表的表名称 TG_SCHEMA_NAME：触发器所在表的模式 

--create table trigger
CREATE TRIGGER example_trigger AFTER INSERT OR UPDATE ON COMPANY  
    FOR EACH ROW EXECUTE PROCEDURE auditlogfunc()
--create table trigger
CREATE TRIGGER company_view_trigger AFTER UPDATE ON company_view 
    EXECUTE PROCEDURE auditlogfunc();

--删除触发器
DROP TRIGGER example_trigger on COMPANY;
drop function function_name (parameters_list);
