IF EXISTS (SELECT * FROM sysobjects WHERE name = 'td_ddl_manager' AND type = 'P')
  DROP PROCEDURE td_ddl_manager
go
CREATE PROCEDURE td_ddl_manager
AS
DECLARE
   @v_obj_name VARCHAR(200),
   @v_obj_type VARCHAR(100),
   @v_ddl_sql VARCHAR(500)
DECLARE  
  cur_func_list CURSOR FOR SELECT name, CASE type WHEN 'SF' THEN 'FUNCTION' WHEN 'P' THEN 'PROCEDURE' WHEN 'TR' THEN 'TRIGGER' WHEN 'V' THEN 'VIEW' WHEN 'U' THEN 'TABLE' END FROM sysobjects WHERE (name LIKE 'td_%' AND name !='td_ddl_manager') AND type IN('SF','P','TR','V','U')
      ORDER BY CASE name WHEN 'td_md' THEN 0  WHEN 'td_os' THEN 0 WHEN 'td_mlsn' THEN 2 WHEN 'td_me' THEN 3 
                         WHEN 'td_tp' THEN 4  WHEN 'td_link' THEN 4 WHEN 'td_snc' THEN 4 WHEN 'td_crossconnect' THEN 4
                         WHEN 'td_route' THEN 5
                         WHEN 'td_mef_customer' THEN 21 WHEN 'td_mef_evc' THEN 22 WHEN 'td_mef_ptp' THEN 23 WHEN 'td_mef_uni' THEN 24
                         ELSE 100 END DESC
BEGIN
  OPEN cur_func_list
  FETCH cur_func_list INTO @v_obj_name,@v_obj_type
WHILE @@fetch_status = 0
BEGIN
  IF @v_obj_type IS NOT NULL
  BEGIN
    SET @v_ddl_sql = 'DROP ' + @v_obj_type + ' ' + @v_obj_name
    PRINT @v_ddl_sql
    EXECUTE (@v_ddl_sql) 
  END
  FETCH cur_func_list INTO @v_obj_name,@v_obj_type
  END
  CLOSE cur_func_list
  DEALLOCATE CURSOR cur_func_list  
END
go
EXECUTE td_ddl_manager
go