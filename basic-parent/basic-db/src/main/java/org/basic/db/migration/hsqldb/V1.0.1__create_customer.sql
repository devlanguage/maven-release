CREATE TABLE dm_customer (
  ID INTEGER NOT NULL identity,
  NAME VARCHAR(20),
  FIRSTNAME VARCHAR(20),
  LASTNAME VARCHAR(20),
  STREET VARCHAR(20),
  CITY VARCHAR(20),
  VERSION INTEGER DEFAULT 0,
  MODIFIED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT SYS_PK_10096 PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX SYS_IDX_SYS_PK_10096_10097 ON dm_customer (ID);

INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (0,'Susanne','Schneider','214 - 20th Ave.','Oslo');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (1,'Julia','Heiniger','18 Upland Pl.','Paris');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (2,'Robert','Ringer','141 College Av.','Lyon');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (3,'Bob','Karsen','504 College Av.','Seattle');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (4,'Laura','Ringer','524 Seventh Av.','Chicago');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (5,'Robert','White','63 - 20th Ave.','Oslo');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (6,'Anne','Schneider','552 - 20th Ave.','Olten');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (7,'Robert','Miller','69 Seventh Av.','Palo Alto');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (8,'Julia','Sommer','39 Upland Pl.','Oslo');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (9,'Susanne','Steel','278 Seventh Av.','New York');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (10,'James','Karsen','366 Upland Pl.','Palo Alto');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (11,'George','Ott','402 - 20th Ave.','Berne');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (12,'John','Fuller','297 Upland Pl.','Berne');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (13,'Bob','Miller','158 Seventh Av.','Paris');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (14,'Mary','King','18 Seventh Av.','Berne');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (15,'John','Peterson','405 Upland Pl.','Paris');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (16,'Susanne','King','24 - 20th Ave.','San Francisco');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (17,'Janet','King','371 - 20th Ave.','Berne');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (18,'Bill','Karsen','293 College Av.','Seattle');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (19,'Susanne','Heiniger','3 Seventh Av.','San Francisco');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (20,'Julia','Ott','1 College Av.','Chicago');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (21,'Susanne','May','309 Upland Pl.','Olten');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (22,'James','Heiniger','453 Seventh Av.','Paris');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (23,'Sylvia','May','543 College Av.','San Francisco');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (24,'George','Karsen','523 Upland Pl.','Lyon');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (25,'Anne','Miller','533 - 20th Ave.','Boston');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (26,'Bill','Sommer','87 Seventh Av.','Dallas');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (27,'Susanne','Ringer','465 Seventh Av.','Oslo');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (28,'Julia','Sommer','490 - 20th Ave.','Oslo');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (29,'Janet','Fuller','469 Seventh Av.','Lyon');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (30,'John','Schneider','545 Seventh Av.','Oslo');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (31,'Andrew','Steel','119 Upland Pl.','New York');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (32,'Robert','Smith','72 College Av.','Seattle');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (33,'Bob','Sommer','62 - 20th Ave.','Seattle');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (34,'John','Schneider','388 - 20th Ave.','Paris');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (35,'Janet','Miller','27 Upland Pl.','Olten');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (36,'Janet','King','266 - 20th Ave.','Paris');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (37,'Bob','Fuller','437 College Av.','Seattle');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (38,'Sylvia','Sommer','31 Seventh Av.','Dallas');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (39,'Robert','Schneider','333 Upland Pl.','New York');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (40,'Michael','Miller','183 - 20th Ave.','Seattle');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (41,'Bill','White','522 Upland Pl.','San Francisco');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (42,'James','Fuller','108 Seventh Av.','Lyon');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (43,'James','Sommer','311 Seventh Av.','San Francisco');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (44,'Laura','Steel','356 Upland Pl.','New York');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (45,'Andrew','Miller','184 College Av.','Boston');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (46,'Sylvia','Karsen','263 Upland Pl.','San Francisco');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (47,'Mary','White','258 Seventh Av.','New York');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (48,'Anne','Ringer','135 - 20th Ave.','Chicago');
INSERT INTO dm_customer (ID,FIRSTNAME,LASTNAME,STREET,CITY) VALUES (49,'Susanne','Smith','121 - 20th Ave.','Chicago');