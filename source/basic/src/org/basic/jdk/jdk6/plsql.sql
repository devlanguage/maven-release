create table dep(id number(5) primary key, name varchar2(50) not null);
insert into dep(id, name) values(1, 'HR');
insert into dep(id, name) values(2, 'Marketing');
insert into dep(id, name) values(3, 'RD');

create table emp(id number(5) primary key, name varchar2(50) not null, age number(3), depId number(5) not null);
insert into emp(id, name, age, depID) values(1, 'zhangsan-1' , 22, 1);
insert into emp(id, name, age, depID) values(2, 'zhangsan-2' , 23, 2);
insert into emp(id, name, age, depID) values(3, 'zhangsan-3' , 24, 1);
insert into emp(id, name, age, depID) values(4, 'zhangsan-4' , 25, 2);
insert into emp(id, name, age, depID) values(5, 'zhangsan-5' , 26, 1);
insert into emp(id, name, age, depID) values(6, 'zhangsan-6' , 27, 1);
insert into emp(id, name, age, depID) values(7, 'zhangsan-7' , 28, 3);

/*把所有雇员的age更新成和雇员名为‘zhangsan-2’的年龄一样*/
update emp set age=(select age from emp where name='zhangsan-2');

/*更新zhangsan-3属于同一部门的所有人的年龄*/
update emp set age = (select age from emp where name='zhangsan-3') where depId = (select depId from emp where name='zhangsan-3');
/*删除所有和zhangsan-3所有同一个部们的所有人*/
delete from emp where depid = (select depid from emp where name='zhangsan-3');


/

declare
  type t1_record_type is record(t1%type, t1%name);
  t1_row t1_record_type;
  tid t1.id%type;
  tname t1.name%type;
begin
  for i in (select * from t1) loop
     select id, name into tid, tname from t1 where id = i.id;
     case
      when i.id = 1 or i.id = 2 then tid := tid +1;
     end case;
     
     if (i.id = 1) then
        dbms_output.put_line('This is the first line ' || 't1:[id=' || i.id || ', name=' || i.name || ']');
     elsif (i.id = 2) then
        dbms_output.put_line('This is the second line ' || 't1:[id=' || i.id || ', name=' || i.name || ']');
     elsif (i.id = 3) then
        dbms_output.put_line('This is the third line ' || 't1:[id=' || i.id || ', name=' || i.name || ']');
     else 
        dbms_output.put_line('This is Common Line ' || 't1:[id=' || i.id || ', name=' || i.name || ']');
     end if;
     
     dbms_output.put_line('tid=' || tid);
  end loop;  
end;
/
  
  
create or replace PROCEDURE Jobs_monitor_Proc AS
  CURSOR job_cursor IS SELECT job,last_date,last_sec FROM user_jobs WHERE what LIKE 'monitor%';
  var_last_date   user_jobs.LAST_DATE%TYPE;
  var_last_sec    user_jobs.LAST_SEC%TYPE;
  var_sys_date   user_jobs.LAST_DATE%TYPE;

  var_job_number   user_jobs.JOB%TYPE;
  var_new_job      user_jobs.JOB%TYPE;
BEGIN
  OPEN job_cursor;
  LOOP
  	  FETCH job_cursor INTO var_job_number,var_last_date,var_last_sec ; 
        EXIT WHEN  job_cursor%NOTFOUND;
	  SELECT  SYSDATE INTO var_sys_date FROM dual;
      
	  ---judge whether or not the Gap>5 minute
	  IF var_sys_date-var_last_date >10/24/60 THEN
        dbms_job.remove(var_job_number);
        dbms_job.submit(var_new_job,'monitor_pro();',SYSDATE,'SYSDATE+1/24/60');
        COMMIT;
	  END IF ;
  END LOOP;  
  CLOSE job_cursor;
END  Jobs_monitor_Proc ;

create or replace procedure submit_job( 
    v_time IN Number, v_proc_name In Varchar2,
    v_jobid out number)
AS
    l_jobid binary_integer;
    l_time Number;
BEGIN
    --调用submit_job(v_time, v_proc_name)；如提交一个job设置在每天23:45运行存储过程"copy_data"，
    --则调用submit_job(23.75, 'copy_data;'),其中0.75为45/60.
    l_time := ROUND(v_time/24,3);
    dbms_job.submit(job => l_jobid, what => v_proc_name, 
        next_date => TRUNC(sysdate) + l_time,
        Interval => 'trunc(sysdate) +' || to_char(l_time), --防止执行时间越来越后        --Interval => 'sysdate +1', --必须大于当时的时间。
        no_parse => NULL
    );
COMMIT;
    --dbms_output.put_line('JobID = '||l_jobid);
    v_jobid := l_jobid;
END submit_job;

