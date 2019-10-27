CREATE USER gongyo1 IDENTIFIED BY gongyo1;
GRANT unlimited tablespaces TO gongyo1
alter database test01 owner to test01;
alter schema public  owner to test01;

select * from pg_settings