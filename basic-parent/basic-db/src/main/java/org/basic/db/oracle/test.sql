----------------------------------------------------------------------------------------------------------------------------
------------------------------------- Department Related Information--------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------
DROP TABLE DB_USER;
DROP SEQUENCE DB_USER_ID_SEQ;
DROP TYPE DB_TYPE_USER_TABLE;
DROP TYPE DB_TYPE_USER;
CREATE TABLE DB_USER (
    user_id NUMBER PRIMARY KEY,
    user_name VARCHAR2(200),
    creator VARCHAR2(200),
    deleted VARCHAR2(5)
);
CREATE SEQUENCE DB_USER_ID_SEQ;
CREATE BITMAP INDEX DB_IDX_TEST_USER_001 ON DB_USER(DELETED);
INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, 'Zhangsan', 'HR', 0);
INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, '王学还', '研发', 0);
INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, '秦武夫', 'HR', 1);

CREATE OR REPLACE TYPE DB_TYPE_USER
IS OBJECT(
        user_id NUMBER,
        user_name VARCHAR2(200),
        creator VARCHAR2(200)
);
/
CREATE OR REPLACE TYPE
 DB_TYPE_USER_TABLE IS TABLE OF DB_TYPE_USER;
/

create or replace
PACKAGE DB_USER_PKG
AS
    
    TYPE TYPE_REF_CURSOR IS REF CURSOR;
    
    FUNCTION GET_TODAY RETURN DATE;
    PROCEDURE ADD_USER(p_name VARCHAR2);
    PROCEDURE ADD_USER(p_user DB_TYPE_USER);
    PROCEDURE ADD_USERS(p_user_list DB_TYPE_USER_TABLE);
    PROCEDURE QUERY_DEPARTMENTS(
        p_user_list OUT DB_TYPE_USER_TABLE
    );
    
    FUNCTION GET_USER_NAME(p_user_id NUMBER) RETURN VARCHAR2;
    FUNCTION GET_USER(p_user_id NUMBER) RETURN DB_TYPE_USER;
    FUNCTION LIST_USERS RETURN TYPE_REF_CURSOR;    
END;
/
CREATE OR REPLACE
PACKAGE BODY DB_USER_PKG AS
    FUNCTION GET_TODAY RETURN DATE AS
    BEGIN
      RETURN SYSDATE;
    END GET_TODAY;
    
    PROCEDURE ADD_USER(p_name VARCHAR2) AS
    BEGIN
        INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, p_name, 'HR', 0);
    END ADD_USER;
    
    PROCEDURE ADD_USER(p_user DB_TYPE_USER) AS
    BEGIN
       INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, p_user.user_name, p_user.creator, 0);
    END ADD_USER;
    
    PROCEDURE ADD_USERS(p_user_list DB_TYPE_USER_TABLE) AS
    BEGIN
        FOR idx IN p_user_list.FIRST..p_user_list.LAST
        LOOP
            INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, p_user_list(idx).user_name, p_user_list(idx).creator, 0);
        END LOOP;    
    END ADD_USERS;
    
    PROCEDURE QUERY_DEPARTMENTS(
          p_user_list OUT DB_TYPE_USER_TABLE
      ) AS
        v_user DB_TYPE_USER := DB_TYPE_USER(0,'BLANK','N/A');
        CURSOR v_user_list IS SELECT * FROM DB_USER;
    BEGIN
        NULL;
    END QUERY_DEPARTMENTS;
    
    FUNCTION GET_USER_NAME(p_user_id NUMBER) RETURN VARCHAR2 AS
    BEGIN
      
      RETURN NULL;
    END GET_USER_NAME;
    
    FUNCTION GET_USER(p_user_id NUMBER) RETURN DB_TYPE_USER AS
        v_user DB_TYPE_USER := DB_TYPE_USER(0,'BLANK','N/A');
    BEGIN      
        SELECT user_id,user_name,creator INTO v_user.user_id, v_user.user_name,v_user.creator FROM DB_USER WHERE user_id = p_user_id;      
        RETURN v_user;  
    EXCEPTION
        WHEN OTHERS THEN      
          dbms_output.put_line('Cannot find the department by the dep_id='||p_user_id);
        RETURN v_user;
    END GET_USER;
    
    FUNCTION LIST_USERS RETURN TYPE_REF_CURSOR AS
        v_user_list TYPE_REF_CURSOR;
    BEGIN
        OPEN v_user_list FOR SELECT * FROM DB_USER;
        RETURN v_user_list;
    END LIST_USERS;
END DB_USER_PKG;
/
----------------------------------------------------------------------------------------------------------------------------
------------------------------------- Employee Related Information----------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------
drop table employee;
drop sequence seq_employee;

drop type table_of_names_t;
drop type varray_of_names_t;

drop procedure test_associative_array;
drop procedure test_nested_table;
drop procedure test_varray;

create sequence seq_employee;
CREATE TABLE EMPLOYEE (
	emp_id NUMBER(10,0), 
	emp_name VARCHAR2(100 BYTE), 
	age NUMBER(4,0), 
	dep_id NUMBER(3,0)
	,address_list address_list
)NESTED TABLE ADDRESS_LIST STORE AS NESTED_ADDRESS_LIST RETURN AS VALUE

drop type address_list;
create or replace type Address is Object(
    address_id number, address_name varchar2(100)
);
/
create or replace type address_list is table of address;
/
ALTER TABLE employee 
    ADD address_list address_list NESTED TABLE address_list store AS nested_address_list;

-- Unable to Render DDL with DBMS_METADATA using internal generator.

create or replace TYPE table_of_names_t IS TABLE OF VARCHAR2 (100);
/
create or replace TYPE varray_of_names_t IS TABLE OF VARCHAR2 (100);
/


create or replace
PROCEDURE test_associative_array
AS
    TYPE list_of_names_t IS TABLE OF VARCHAR2(50) INDEX BY pls_integer;
    v_happy_family list_of_names_t;
    l_row pls_integer;
BEGIN

    v_happy_family(12) := 'Eli';
    v_happy_family(-15070) := 'Steven';
    v_happy_family(-90900) := 'Chris';
    v_happy_family(88) := 'Veva';
    
    dbms_output.put_line('count='||v_happy_family.count);

    l_row := v_happy_family.FIRST;
    WHILE(l_row IS NOT NULL)
    LOOP
      DBMS_OUTPUT.PUT_LINE(l_row||'='||v_happy_family(l_row));
      l_row := v_happy_family.NEXT(l_row);
    END LOOP;

END test_associative_array;
/
create or replace
PROCEDURE test_nested_table
AS    
    v_happy_family table_of_names_t := table_of_names_t();
    v_children     table_of_names_t := table_of_names_t();
    v_parents      table_of_names_t := table_of_names_t ();
    l_row pls_integer;
BEGIN
    v_happy_family.extend(4);
    v_happy_family(1) := 'Terry';
    v_happy_family(2) := 'Steven';
    v_happy_family(3) := 'Emerson';
    v_happy_family(4) := 'Emma';
        
    v_parents.extend;
    v_parents(1) := v_happy_family(1);
    v_parents.extend;
    v_parents(1) := v_happy_family(2);
    
    --v_children := v_happy_family MULTISET EXCEPT v_parents;--very similar to the SQL MINUS
    
    dbms_output.put_line('total_count='||v_happy_family.count);
    dbms_output.put_line('parents_count='||v_parents.count);
    dbms_output.put_line('children_count='||v_children.count);
    
    l_row := v_happy_family.FIRST;
    WHILE(l_row IS NOT NULL)
    LOOP
      DBMS_OUTPUT.PUT_LINE(l_row||'='||v_happy_family(l_row));
      l_row := v_happy_family.NEXT(l_row);
    END LOOP;
END test_nested_table;
/
--create or replace type table_of_name_t 

create or replace
PROCEDURE test_varray
AS    
    v_happy_family varray_of_names_t := varray_of_names_t();
    l_row pls_integer;
BEGIN    
    v_happy_family.extend(41);
    v_happy_family(1) := 'Steven';
    v_happy_family(2) := 'Chris';
    v_happy_family(3) := 'Veva';
    v_happy_family(4) := 'Eli';
    
    dbms_output.put_line('count='||v_happy_family.count);

    l_row := v_happy_family.FIRST;
    WHILE(l_row IS NOT NULL)
    LOOP
      DBMS_OUTPUT.PUT_LINE(l_row||'='||v_happy_family(l_row));
      l_row := v_happy_family.NEXT(l_row);
    END LOOP;
END test_varray;
/



INSERT INTO employee(emp_id, emp_name, age, dep_id, address_list) 
	VALUES(seq_employee.nextval, 'zhangsan', 23, 1, address_list(address(100, 'Xi''An'),address(200, 'Shang''hai')));
INSERT INTO employee(emp_id, emp_name, age, dep_id, address_list)
	VALUES(seq_employee.nextval, 'wangwu', 33, 1, address_list(address(100, 'Xi''An'),address(200, 'Shang''hai')));
INSERT INTO employee(emp_id, emp_name, age, dep_id, address_list)
	VALUES(seq_employee.nextval, 'lisi', 43, 2, address_list(address(100, 'Xi''An'),address(200, 'Shang''hai')));

insert into table(select address_list from employee where emp_id =1) values(address(400, 'Lan''Zhou'));

update employee set address_list= address_list(address(100, 'Xi''An'),address(200, 'Shang''hai')) where 1=1;
update table(select address_list from employee where emp_id =1) set address_name='SHANG''HAI' WHERE address_id = 200

delete from table(select address_list from employee where emp_id=1) where address_id = 100;

/*
select * from 
  table(select address_list
          from employee e1 where e1.EMP_ID=1
       ) e2 where e2.address_id = 100;
	ADDRESS_ID  ADDRESS_NAME                                                                                                                              
----------  ------------                                                                                                                              
	100         Xi'An                                                                                                                       
       
       
*/
--------------------------------------------------------------------------------------------------------------------------------
--------------------------------------                                    ------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------
DROP TABLE PLAYER;
DROP TYPE SCORE_NESTED;
DROP TYPE SCORE_TYPE
/
CREATE TYPE SCORE_TYPE AS OBJECT(
    NO NUMBER,
    ITEM VARCHAR2(60),
    SCORE NUMBER
);
/
CREATE TYPE SCORE_NESTED AS TABLE OF SCORE_TYPE;
/
CREATE TABLE PLAYER(
    guid NUMBER,
    name VARCHAR2(20),
    score_list SCORE_NESTED
) nested TABLE score_list STORE AS SCORE_NESTED_TABLE;

-- 增加数据
INSERT INTO PLAYER (guid, NAME,score_list) 
       VALUES (100, 'zhangsan',
                 SCORE_NESTED(
                    SCORE_TYPE(1,'Climbing',100),
                    SCORE_TYPE(2,'Skating',100),
                    SCORE_TYPE(3,'Fishing',90),
                    SCORE_TYPE(4,'Surfing',80)
                )
           );
INSERT INTO PLAYER (guid, NAME,SCORE_list)
       VALUES (200, 'lisi',
                 SCORE_NESTED(
                   SCORE_TYPE(1,'Skating',100),
                   SCORE_TYPE(2,'Researching',150),
                   SCORE_TYPE(3,'Running',90)
                 )
           );
--追加数据           
INSERT INTO TABLE(SELECT SCORE_LIST FROM PLAYER WHERE NAME = 'zhangsan') VALUES (SCORE_TYPE(5, 'haha',10));    
-- 更新
--UPDATE PLAYER SET SCORE_LIST = SCORE_NESTED(SCORE_TYPE(5, 'haha',10)) WHERE NAME = 'zhangsan';
--删除
--UPDATE PLAYER SET SCORE_LIST = NULL WHERE NAME = 'zhangsan'
/*           
select * from player
    GUID                   NAME                 SCORE_LIST                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    ---------------------- -------------------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
    100                    zhangsan             YGONG1.SCORE_TYPE(YGONG1.SCORE_TYPE,YGONG1.SCORE_TYPE,YGONG1.SCORE_TYPE,YGONG1.SCORE_TYPE)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    200                    lisi                 YGONG1.SCORE_TYPE(YGONG1.SCORE_TYPE,YGONG1.SCORE_TYPE,YGONG1.SCORE_TYPE)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
select guid, name, S.* from player P, table(p.score_list) S WHERE p.name='zhangsan';
    GUID                   NAME                 NO                     ITEM                                                         SCORE                  
    ---------------------- -------------------- ---------------------- ------------------------------------------------------------ ---------------------- 
    100                    zhangsan             1                      shining                                                      100                    
    100                    zhangsan             2                      surfing                                                      100                    
    100                    zhangsan             3                      fishing                                                      90                     
    100                    zhangsan             4                      fluting                                                      80                     

SELECT * FROM TABLE(SELECT SCORE_list FROM PLAYER WHERE NAME = 'zhangsan') P;      
    NO                     ITEM                                                         SCORE                  
    ---------------------- ------------------------------------------------------------ ---------------------- 
    1                      shining                                                      100                    
    2                      surfing                                                      100                    
    3                      fishing                                                      90                     
    4                      fluting                                                      80                     
*/



