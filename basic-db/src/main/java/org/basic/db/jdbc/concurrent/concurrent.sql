drop sequence seq_user;
drop table db_user;
drop table db_account;

create sequence seq_user;
create table db_user(
	user_id number,
	user_name varchar2(200),
	state varchar2(2000)
);
create table account(
	account_id number,
	account_name varchar2(200),
	description varchar2(200)
);
create or replace
package pkg_db_basic
AS
    PROCEDURE create_user1(
            p_user_name VARCHAR2, p_state VARCHAR2
    );
    PROCEDURE create_user2(
          p_user_name VARCHAR2
    );
    PROCEDURE create_user3(
          p_user_name VARCHAR2
    );    
END pkg_db_basic;
/
create or replace PACKAGE BODY PKG_DB_BASIC AS

    PROCEDURE create_user1(
          p_user_name VARCHAR2, p_state VARCHAR2
    ) AS
          v_exist_count NUMBER;
          v_user_id NUMBER;
    BEGIN        
        SELECT count(*) INTO v_exist_count FROM db_user WHERE user_name = p_user_name;
        IF v_exist_count > 0 THEN
            UPDATE db_user SET state= p_user_name;
        ELSE
            SELECT seq_user.nextval INTO v_user_id FROM dual;
            INSERT INTO db_user(user_id, user_name) VALUES(v_user_id,p_user_name);
        END IF;
    END create_user1;    
    
    PROCEDURE create_user2(
          p_user_name VARCHAR2
    ) AS
          v_existed BOOLEAN DEFAULT FALSE;
          v_user_id NUMBER;
    BEGIN
       -- COMMIT;
        --SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
        LOCK TABLE db_user IN SHARE MODE;--EXCLUSIVE MODE;
        FOR l_user IN (SELECT * FROM db_user WHERE user_name = p_user_name)
        LOOP
            UPDATE db_user SET state= p_user_name;
            v_existed := TRUE;
        END LOOP;
                
        IF v_existed = FALSE THEN            
            SELECT seq_user.nextval INTO v_user_id FROM dual;
            INSERT INTO db_user(user_id, user_name) VALUES(v_user_id,p_user_name);
        END IF;
        COMMIT;
    END create_user2;
    PROCEDURE create_user3(
          p_user_name VARCHAR2
    ) AS
          v_existed BOOLEAN DEFAULT FALSE;
          v_user_id NUMBER;
    BEGIN
        lock table db_user in exclusive mode;
--      lock table  db_user in [row share][row exclusive][share][share row exclusive][exclusive] mode;
        rollback;
        
        SELECT 'UPDATE db_user SET state=3 WHERE user_name='||user_name||'''' INTO v_user_id FROM db_user WHERE user_name=p_user_name;
        dbms_output.put_line('id='||v_user_id);
        COMMIT;
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
        FOR l_user IN (SELECT * FROM db_user WHERE user_name = p_user_name)
        LOOP
            UPDATE db_user SET state= p_user_name;
            v_existed := TRUE;
        END LOOP;        
        IF v_existed = FALSE THEN            
            SELECT seq_user.nextval INTO v_user_id FROM dual;
            INSERT INTO db_user(user_id, user_name) VALUES(v_user_id,p_user_name);
        END IF;
        COMMIT;
    END create_user3;    
END PKG_DB_BASIC;
/