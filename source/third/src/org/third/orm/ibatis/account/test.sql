declare
  cursor cur_table_list is select * from user_tables where table_name like 'HI\_%' escape '\';
  v_user_table user_tables%rowtype;
  
  cursor cur_seq_name_list is select sequence_name from user_sequences where sequence_name like 'HI\_%' escape '\';
  v_seq_name varchar2(200);
begin
  open cur_table_list;
  fetch cur_table_list into v_user_table;
  while cur_table_list%found loop
    execute immediate 'drop table ' || v_user_table.table_name;
    fetch cur_table_list into v_user_table;						
  end loop;
  close cur_table_list;
  
  open cur_seq_name_list;
  fetch cur_seq_name_list into v_seq_name;
  while cur_seq_name_list%found loop
  	execute immediate 'drop sequence ' || v_seq_name;
  	fetch cur_seq_name_list into v_seq_name;
  end loop;
  close cur_seq_name_list;
END;
/
create sequence hi_seq_account;
create table hi_account(
  id number,
  first_name varchar2(200),
  last_name varchar2(200),
  email_address varchar2(200)  
);
create or replace
procedure retrieve_account_name(
    p_account_id in number, p_account_name out varchar2
)
AS	
BEGIN
    FOR l_accounts IN (select * from hi_account where id = p_account_id)
    LOOP
        p_account_name := 'id='||l_accounts.id||',first_name='||l_accounts.first_name||',last_name='||l_accounts.last_name;
    END LOOP;
    IF p_account_name is null THEN
        p_account_name :=' Cannot Found the account by the id='||p_account_id;
    END IF;
END retrieve_account_name;
/

insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-1', 'chen', 'lizhao.chen@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-2', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-3', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-4', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-5', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-6', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-7', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-8', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-9', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-10', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-11', 'gong', 'yongjie.gong@tellabs.com');
insert into hi_account(id, first_name, last_name, email_address) values(hi_seq_account.nextval, 'yongjie-12', 'gong', 'yongjie.gong@tellabs.com');
