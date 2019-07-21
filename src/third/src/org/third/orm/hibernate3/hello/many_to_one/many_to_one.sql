/**
hi_account --- many___to___one ---> hi_user
*/
CREATE SEQUENCE seq_user;
CREATE TABLE hi_User(
   user_id NUMBER PRIMARY KEY,
   user_name VARCHAR2(200) NOT NULL,
   account_id NUMBER
);
CREATE TABLE hi_account(
   account_id NUMBER PRIMARY KEY,
   account_name VARCHAR2(200)
);
