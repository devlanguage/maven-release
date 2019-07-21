/*    +--many-to-many <==================>  many-to-many-+ 			  ___________________
  ____|________        __________________				 |			 |					 |
 |             |      |                  |       ________|_____	     |h3_person_email    |
 | h3_event    |      |  h3_person_event |      |             |      | ______________set_|   -- map to set, element <==> blank
 |_____________|      |__________________|      |   h3_person | <--> |  *PERSON_ID       |
 |             |      |                  |      |_____________|      |   EMAIL_ADDR      |  
 | *EVENT_ID   | <--> | *EVENT_ID        |      |             |      |___________________|
 |  EVENT_DATE |      | *PERSON_ID       | <--> | *PERSON_ID  |   	  ___________________
 |  TITLE      |      |__________________|      |  AGE        |      |  				 |
 |             |                                |  profession |      |   h3_role         |   ---Map to--idbag, element <==> blank
 |_____________|                                |  FIRSTNAME  |      |___________________|        
                                          		|  LASTNAME   | <--> |					 |
                                                |  emblement  |      |  * person_id      |
                                    /---------> |_____________|<-\   |    collection_id  |
---Map to--join,					|				^            |   |    role_name      |
--   property list <======> blank   |			set,|one-to-many |   |___________________| 
									|	  <==> many-|to-one		 |	
					  ______________|_____	 _______|________    |
				     |  			  	 |   |     		     |	 \-------------\
         			 | h3_person_address |	 | h3_Magazine   |                 |
				     |___________________|   |_______________|       __________|________
				     |   * person_id	 |	 |* MAGAZINE_ID  |		| 					|
			 		 |	 * country		 |	 |  isbn         |		|h3_emblement		|  ----Map to bag, one-to-many <==> many-to-one
			         |   * city       	 | 	 |  price        |		|___________________|
					 |	 * town       	 |	 |  version      |		| * emblement_id    |
					 |___________________|	 |  title        | 		|   emblement_name	|
						  					 |* person_id    |		|   output			|
											 ----------------		|   unit 			|
																	| * person_id       |	
  																	--------------------
   _______________________________________________
  |                                               | 
  | h3_department                                 |
  |_______________________________________________|
  |                                               |
  | DEP_ID  NUMBER(10,0),                         |
  | DEP_NAME  VARCHAR2(100 BYTE),                 |
  | LOGO  BLOB default empty_blob(),              |
  | DESCRIPTION  CLOB default empty_clob(),       |
  | BALANCE  NUMBER(12,2),                        |
  | dep_structure  XMLTYPE,                       |
  | main_page HttpUriType,                        | 
  | xpath  DbUriType,                             |
  | Oracle_Xml_Db XdbUriType                      |
  |_______________________________________________|
  

MAIN_PAGE	HTTPURITYPE
XPATH	DBURITYPE
ORACLE_XML_DB	XDBURITYPE

*/  
  
begin
	for l_tables IN (SELECT * FROM user_tables WHERE table_name like ('H3_%') )
	loop
          dbms_output.PUT_LINE(l_tables.table_name);
	   execute immediate 'drop table '|| l_tables.table_name ||' cascade constraints';
	END loop;
END;
/                            

CREATE table h3_person (
	"PERSON_ID" NUMBER primary key, 
	"AGE" NUMBER(5,0),  
	"FIRST_NAME" VARCHAR2(100 BYTE), 
	"LAST_NAME" VARCHAR2(100 BYTE), 
	"PROFESSION" VARCHAR2(20 BYTE) DEFAULT 'WORKER' NOT NULL ENABLE, 
	"POSITION" VARCHAR2(4000 BYTE) DEFAULT 'Manager', 
	"EMBLEMENT" VARCHAR2(4000 BYTE) DEFAULT 'WHEAT'
);
COMMENT ON COLUMN "H3_PERSON"."PROFESSION" IS 'It identify what person is working on, "WORKER", "PEASANT","PERSON"';
COMMENT ON COLUMN "H3_PERSON"."EMBLEMENT" IS 'wheat, rice, broomcorn, oat,barley, soybean sprout';
CREATE TABLE h3_person_role (
	person_id NUMBER,
	collection_id VARCHAR2(200)  primary key,
	role_name varchar2(100)
);
CREATE table h3_person_email (
	person_id NUMBER ,
	email_address VARCHAR2(100)
);
CREATE table h3_person_address(
	person_id NUMBER,
	city  VARCHAR2(100),
	country VARCHAR2(100),
	town VARCHAR2(100),
	constraint p_addr_pk(person_id, city, country, town)
);
create table h3_emblement(
	emblement_id number  primary key,
	emblement_name VARCHAR2(100),
	output NUMBER(10,2),
	unit VARCHAR2(50),
	person_id number
);
CREATE table h3_magazine(
	magazine_id NUMBER primary key,
	title VARCHAR2(200),
	isbn   VARCHAR2(100),
	price  DOUBLE PRECISION,
	version NUMBER,
	person_id NUMBER
);

CREATE table h3_event (
	event_id NUMBER primary key,
	event_date TIMESTAMP,
	title VARCHAR2(100)
);
CREATE table h3_person_event (
	event_id NUMBER,
	person_id NUMBER
);

        
-- INSERTING into H3_PERSON
Insert into H3_PERSON (PERSON_ID,AGE,FIRST_NAME,LAST_NAME,PROFESSION) values (100,23,'San','Zhang','PERSON');
Insert into H3_PERSON (PERSON_ID,AGE,FIRST_NAME,LAST_NAME,PROFESSION) values (200,24,'Si','Li','WORKER');
Insert into H3_PERSON (PERSON_ID,AGE,FIRST_NAME,LAST_NAME,PROFESSION) values (300,35,'Wang','Wu','PEASANT');
	
-- INSERTING into H3_PERSON_EMAIL
Insert into H3_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (100,'zhangsan-1@test.com');
Insert into H3_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (100,'zhangsan-2@test.com');
Insert into H3_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (100,'zhangsan-3@test.com');
Insert into H3_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (200,'Lisi-1@test.com');

-- INSERTING into H3_ADDRESS
Insert into h3_person_address (PERSON_ID,CITY,COUNTRY,TOWN) values (100,'Xi''An','China','QingQuan');
Insert into h3_person_address (PERSON_ID,CITY,COUNTRY,TOWN) values (200,'Los''Angeles','America','Washiton');
Insert into h3_person_address (PERSON_ID,CITY,COUNTRY,TOWN) values (300,'London','England','Suburban East');

-- INSERTING into H3_PERSON_ROLE
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (100,'4028f9081927858d0119278593320001','Oracle Manager');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (100,'4028f9081927858d0119278593320002','Oracle Developer');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (100,'4028f9081927858d0119278593320003','Data Warehouse Developer');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (200,'4028f9081927858d0119278593320004','HR');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320005','Finance Manager');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320006','Accountant');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320007','FEO');
Insert into H3_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320008','Treasurer');

-- INSERTING into H3_EMBLEMENT
Insert into H3_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1001,100,'Wheat',12,'KG');
Insert into H3_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1002,100,'Corner',23,'KG');
Insert into H3_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1003,200,'broomcorn',44,'TON');
Insert into H3_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1004,100,'soybean sprunt',56,'KG');

-- INSERTING into H3_MAGAZINE
Insert into H3_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE) 
	values (100,null,12.12,1.1,100,'Oracle Expert');
Insert into H3_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE)
	values (101,null,12.12,1.1,100,'Java 2 SE');
Insert into H3_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE)
	values (102,null,12.12,1.1,100,null);
Insert into H3_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE)
	values (103,null,13.12,1.2,200,'JVM Step by Step');
	
create or replace
PROCEDURE H3_LIST_DEPARTMENT
AS        
    TYPE TYPE_REF_CURSOR IS REF CURSOR;
    
    v_dep DEPARTMENT%ROWTYPE;
    v_ref_cursor TYPE_REF_CURSOR;
    v_dep_id NUMBER := 1;    
    v_balance NUMBER(10,2);    
BEGIN
  dbms_output.put_line('Orignal Data: ');
  FOR l_dep IN (
      select dep_id,dep_name,logo,description,balance from DEPARTMENT
  )
  LOOP
      dbms_output.put_line('Department:dep_id='||l_dep.dep_id || ',dep_name='||l_dep.dep_name || ',balance='||l_dep.balance);
  END lOOP;
  
  dbms_output.put_line('Orignal Result: ');  
  OPEN v_ref_cursor FOR 'SELECT * FROM DEPARTMENT WHERE dep_id = :dep_id ' using v_dep_id;  
  LOOP
  	 FETCH v_ref_cursor INTO v_dep;
  	 EXIT WHEN v_ref_cursor%NOTFOUND;  	 
     dbms_output.put_line('Department:dep_id='||v_dep.dep_id || ',dep_name='||v_dep.dep_name || ',balance='||v_dep.balance);     
  END LOOP;
  CLOSE v_ref_cursor;
  
  dbms_output.put_line('Single Line Query: ');
  EXECUTE IMMEDIATE 'SELECT * FROM department WHERE dep_id = :dep_id' INTO v_dep USING v_dep_id;
  dbms_output.put_line('Department:dep_id='||v_dep.dep_id || ',dep_name='||v_dep.dep_name || ',balance='||v_dep.balance);
  
  dbms_output.put_line('Update: ');  
  EXECUTE IMMEDIATE 'UPDATE department SET balance = 1.5 * balance WHERE dep_id = :dep_id RETURNING balance INTO :balance' USING v_dep_id RETURNING INTO v_balance;
  
  dbms_output.put_line('Updated Result: ');  
  OPEN v_ref_cursor FOR 'SELECT * FROM DEPARTMENT WHERE dep_id = :dep_id ' using v_dep_id;
  FETCH v_ref_cursor INTO v_dep;
  WHILE v_ref_cursor%FOUND LOOP
      dbms_output.put_line('Department:dep_id='||v_dep.dep_id || ',dep_name='||v_dep.dep_name || ',balance='||v_dep.balance);
      FETCH v_ref_cursor INTO v_dep;    
  END LOOP;
  CLOSE v_ref_cursor;
END LIST_DEPARTMENT;

create or replace
PROCEDURE H3_ADD_DEPARTMENT(p_department_name VARCHAR2) 
AS
BEGIN
  INSERT INTO DEPARTMENT(dep_id, dep_name) VALUES(seq_department.nextval, p_department_name);
END ADD_DEPARTMENT;
/

CREATE OR REPLACE
PROCEDURE H3_EXCEPTION_TEST AS
    
    unvalid_dep_id EXCEPTION;
    error_format EXCEPTION;
    PRAGMA EXCEPTION_INIT(error_format, -20010);
    bool1 BOOLEAN := 1=1;
BEGIN
    <<Exception_Block_1>>
    BEGIN
        dbms_output.put_line('sqlCode: '||SQLCODE);
        dbms_output.put_line('Default SqlErrM: '||sqlerrm);
        dbms_output.put_line(sqlerrm(-10000));
        <<Raise_Exception_Block>>
        BEGIN
            IF bool1 THEN
              raise_application_error(20010, '');
            ELSE
              raise DBMS_LDAP.INVALID_SESSION;
            END IF;
        END Raise_Exception_Block;
        
        EXCEPTION

          WHEN unvalid_dep_id THEN
             <<Catch_Exception_Block>>
             DECLARE
                error_id number;
             BEGIN
                null;
             END Catch_Exception_Block;
          WHEN OTHERS THEN
            dbms_output.put_line('Uncaughted Exception: SqlCode='||SQLCODE ||', SqlErrM=' || SQLERRM(SQLCODE));
            dbms_output.put_line('Uncaughted Exception: ' || DBMS_UTILITY.FORMAT_CALL_STACK());
            dbms_output.put_line('Uncaughted Exception: ' || DBMS_UTILITY.FORMAT_ERROR_STACK());
            --RAISE_APPLICATION_ERROR(SQLCODE, DBMS_UTILITY.FORMAT_CALL_STACK(), TRUE);
      END Exception_Block_1;
      
      <<Exception_Block_2>>
      DECLARE 
        error_format2 EXCEPTION;
        PRAGMA exception_init(error_format2, -20010);
      BEGIN
        IF 1=1 THEN
            RAISE error_format;
        ELSE
            RAISE error_format2;
        END IF;
        EXCEPTION
            WHEN error_format OR error_format2 THEN
                dbms_output.put_line('Exception_Block_2: ');
                dbms_output.put_line(DBMS_UTILITY.FORMAT_CALL_STACK());
                RAISE_APPLICATION_ERROR(SQLCODE, DBMS_UTILITY.FORMAT_CALL_STACK(), TRUE);            
      END Exception_Block_2;      
END EXCEPTION_TEST;
/



drop table department;
CREATE TABLE DEPARTMENT(
    DEP_ID NUMBER(10,0),
    DEP_NAME VARCHAR2(100 BYTE), 
    LOGO BLOB default empty_blob(), 
    DESCRIPTION CLOB default empty_clob(), 
    BALANCE NUMBER(12,2),
    dep_structure XMLTYPE,
    MAIN_PAGE HttpUriType,        
    xpath  DBUriType,
    oracle_xml_db XDBUriType
);

declare 
    v_main_page HttpUriType := HttpUriType.createUri('http://www.baidu.com');
    v_baidu_log HttpUriType := HttpUriType.createUri('http://www.baidu.com/img/logo.gif');
    v_description CLOB;
    v_logo BLOB;
    v_dep_structure XMLType := XMLType.CreateXML(
       '<?xml version="1.0"?>
        <fall>
           <name>Munising Falls</name>
           <county>Alger</county>
           <state>MI</state>
           <url>http://www.baidu.com/munising_falls.html</url>
        </fall>');
begin
    v_description := v_main_page.getClob();
    v_logo := v_main_page.getBlob();
     INSERT INTO department(
        dep_id, 
        logo,
        description,
        dep_structure             
    ) VALUES (
      100,   
      v_logo,
      v_description,v_dep_structure);
  --Display the message
    DBMS_OUTPUT.PUT_LINE(SUBSTR(v_description,1,60));
    IF v_dep_structure.existsNode('/fall/url') > 0 THEN
        dbms_output.put_line('url:' || v_dep_structure.extract('/fall/url/text()').getStringVal());
        dbms_output.put_line(
             XMLType.getStringVal(
                XMLType.extract(v_dep_structure,'/fall/name/text()')
             ));
    END IF;
end;
/

DECLARE
   my_book_new_info books%ROWTYPE;
   my_book_return_info books%ROWTYPE;
   
   names name_varray;
   new_salaries number_varray;   
BEGIN
   my_book.isbn := '1-56592-335-9';
   my_book.title := 'ORACLE PL/SQL PROGRAMMING';
   my_book.summary := 'General user guide and reference';
   my_book.author := 'FEUERSTEIN, STEVEN AND BILL PRIBYL';
   my_book.page_count := 980; -- new page count for 3rd edition

   UPDATE books
      SET ROW = my_book_new_info
     WHERE isbn = my_book.isbn
     RETURNING isbn, title, summary, author, page_count
          INTO my_book_return_info;
           populate_names_array (names);

--   FORALL index_row IN
--       [ lower_bound ... upper_bound |
--         INDICES OF indexing_collection |
--         VALUES OF indexing_collection
--       ]
--       [ SAVE EXCEPTIONS ]
--       sql_statement;

   FORALL indx IN names.FIRST .. names.LAST
      UPDATE compensation
         SET salary = new_compensation (names(indx))
       WHERE name = names (indx)
       RETURNING salary BULK COLLECT INTO new_salaries;
END;



