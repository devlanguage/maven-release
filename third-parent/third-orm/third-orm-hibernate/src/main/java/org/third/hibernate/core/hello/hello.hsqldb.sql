
/*    +--many-to-many <==================>  many-to-many-+ 			  ___________________
  ____|________        __________________				 |			 |					 |
 |             |      |                  |       ________|_____	     |dm_person_email    |
 | dm_event    |      |  dm_person_event |      |             |      | ______________set_|   -- map to set, element <==> blank
 |_____________|      |__________________|      |   dm_person | <--> |  *PERSON_ID       |
 |             |      |                  |      |_____________|      |   EMAIL_ADDR      |  
 | *EVENT_ID   | <--> | *EVENT_ID        |      |             |      |___________________|
 |  EVENT_DATE |      | *PERSON_ID       | <--> | *PERSON_ID  |   	  ___________________
 |  TITLE      |      |__________________|      |  AGE        |      |  				 |
 |             |                                |  profession |      |   dm_role         |   ---Map to--idbag, element <==> blank
 |_____________|                                |  FIRSTNAME  |      |___________________|        
                                          		|  LASTNAME   | <--> |					 |
                                                |  emblement  |      |  * person_id      |
                                    /---------> |_____________|<-\   |    collection_id  |
---Map to--join,					|				^            |   |    role_name      |
--   property list <======> blank   |			set,|one-to-many |   |___________________| 
									|	  <==> many-|to-one		 |	
					  ______________|_____	 _______|________    |
				     |  			  	 |   |     		     |	 \-------------\
         			 | dm_person_address |	 | dm_Magazine   |                 |
				     |___________________|   |_______________|       __________|________
				     |   * person_id	 |	 |* MAGAZINE_ID  |		| 					|
			 		 |	 * country		 |	 |  isbn         |		|dm_emblement		|  ----Map to bag, one-to-many <==> many-to-one
			         |   * city       	 | 	 |  price        |		|___________________|
					 |	 * town       	 |	 |  version      |		| * emblement_id    |
					 |___________________|	 |  title        | 		|   emblement_name	|
						  					 |* person_id    |		|   output			|
											 ----------------		|   unit 			|
																	| * person_id       |	
  																	--------------------
   _______________________________________________
  |                                               | 
  | dm_department                                 |
  |_______________________________________________|
  |                                               |
  | DEP_ID  DECIMAL(10,0),                         |
  | DEP_NAME  VARCHAR(100),                 |
  | LOGO  BLOB default empty_blob(),              |
  | DESCRIPTION  CLOB default empty_clob(),       |
  | BALANCE  DECIMAL(12,2),                        |
  | dep_structure  XMLTYPE,                       |
  | main_page HttpUriType,                        | 
  | xpath  DbUriType,                             |
  | Oracle_Xml_Db XdbUriType                      |
  |_______________________________________________|
  

MAIN_PAGE	HTTPURITYPE
XPATH	DBURITYPE
ORACLE_XML_DB	XDBURITYPE

*/  
  

CREATE table dm_person (
  id INTEGER  primary key,
  name VARCHAR(100),
	"AGE" DECIMAL(5,0),  
	"SEX" VARCHAR(10) DEFAULT 'FEMALE',
	"FIRST_NAME" VARCHAR(100), 
	"LAST_NAME" VARCHAR(100), 
	"PROFESSION" VARCHAR(20) DEFAULT 'WORKER' NOT NULL, 
	"POSITION" VARCHAR(32) DEFAULT 'Manager', 
	"EMBLEMENT" VARCHAR(32) DEFAULT 'WHEAT'
);
ALTER TABLE dm_PERSON ADD CONSTRAINT dm_PERSON_CHK_AGE CHECK (AGE BETWEEN 0 AND 100);
ALTER TABLE dm_PERSON ADD CONSTRAINT dm_PERSON_UK_NAME UNIQUE (FIRST_NAME, LAST_NAME);

CREATE TABLE dm_person_role (
	person_id INTEGER,
	collection_id VARCHAR(200)  primary key,
	role_name VARCHAR(100)
);
CREATE table dm_person_email (
	person_id INTEGER ,
	email_address VARCHAR(100)
);
CREATE table dm_person_address(
	person_id INTEGER,
	city  VARCHAR(100),
	country VARCHAR(100),
	town VARCHAR(100)
);
create table dm_emblement(
	id INTEGER  primary key,
	name VARCHAR(100),
	output DECIMAL(10,2),
	unit VARCHAR(50),
	person_id INTEGER
);
CREATE table dm_magazine(
	magazine_id INTEGER primary key,
	title VARCHAR(200),
	isbn   VARCHAR(100),
	price  DOUBLE,
	version INTEGER,
	person_id INTEGER
);

CREATE table dm_event (
	event_id INTEGER primary key,
	event_date TIMESTAMP,
	title VARCHAR(100)
);
CREATE table dm_person_event (
	event_id INTEGER,
	person_id INTEGER
);

        
-- INSERTING into dm_PERSON
Insert into dm_PERSON (PERSON_ID,AGE,FIRST_NAME,LAST_NAME,PROFESSION) values (100,23,'San','Zhang','PERSON');
Insert into dm_PERSON (PERSON_ID,AGE,FIRST_NAME,LAST_NAME,PROFESSION) values (200,24,'Si','Li','WORKER');
Insert into dm_PERSON (PERSON_ID,AGE,FIRST_NAME,LAST_NAME,PROFESSION) values (300,35,'Wang','Wu','PEASANT');
	
-- INSERTING into dm_PERSON_EMAIL
Insert into dm_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (100,'zhangsan-1@test.com');
Insert into dm_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (100,'zhangsan-2@test.com');
Insert into dm_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (100,'zhangsan-3@test.com');
Insert into dm_PERSON_EMAIL (PERSON_ID,EMAIL_ADDRESS) values (200,'Lisi-1@test.com');

-- INSERTING into dm_ADDRESS
Insert into dm_person_address (PERSON_ID,CITY,COUNTRY,TOWN) values (100,'Xi''An','China','QingQuan');
Insert into dm_person_address (PERSON_ID,CITY,COUNTRY,TOWN) values (200,'Los''Angeles','America','Washiton');
Insert into dm_person_address (PERSON_ID,CITY,COUNTRY,TOWN) values (300,'London','England','Suburban East');

-- INSERTING into dm_PERSON_ROLE
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (100,'4028f9081927858d0119278593320001','Oracle Manager');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (100,'4028f9081927858d0119278593320002','Oracle Developer');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (100,'4028f9081927858d0119278593320003','Data Warehouse Developer');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (200,'4028f9081927858d0119278593320004','HR');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320005','Finance Manager');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320006','Accountant');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320007','FEO');
Insert into dm_PERSON_ROLE (PERSON_ID,COLLECTION_ID,ROLE_NAME) values (300,'4028f9081927858d0119278593320008','Treasurer');

-- INSERTING into dm_EMBLEMENT
Insert into dm_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1001,100,'Wheat',12,'KG');
Insert into dm_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1002,100,'Corner',23,'KG');
Insert into dm_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1003,200,'broomcorn',44,'TON');
Insert into dm_EMBLEMENT (EMBLEMENT_ID,PERSON_ID,EMBLEMENT_NAME,OUTPUT,UNIT) values (1004,100,'soybean sprunt',56,'KG');

-- INSERTING into dm_MAGAZINE
Insert into dm_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE) 
	values (100,null,12.12,1.1,100,'Oracle Expert');
Insert into dm_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE)
	values (101,null,12.12,1.1,100,'Java 2 SE');
Insert into dm_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE)
	values (102,null,12.12,1.1,100,null);
Insert into dm_MAGAZINE (MAGAZINE_ID,ISBN,PRICE,VERSION,PERSON_ID,TITLE)
	values (103,null,13.12,1.2,200,'JVM Step by Step');

CREATE TABLE dm_DEPARTMENT(
    DEP_ID INTEGER,
    DEP_NAME VARCHAR(100), 
    LOGO BLOB, 
    DESCRIPTION CLOB, 
    BALANCE DECIMAL(12,2)
);
create sequence dm_seq_department;
