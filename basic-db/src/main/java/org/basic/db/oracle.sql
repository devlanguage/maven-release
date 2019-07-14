CREATE VIEW TS_USAGE AS 
SELECT d.status "Status",   d.tablespace_name "Name",
       d.contents "Type",   d.extent_management "ExtentMngr",
       TO_CHAR(NVL(a.bytes / 1024 / 1024, 0), '99,999,990.900') "Size (M)",
       TO_CHAR(NVL(a.bytes - NVL(f.bytes, 0), 0) / 1024 / 1024, '99999999.999')  "Used (M)",
       TO_CHAR(NVL((a.bytes - NVL(f.bytes, 0)) / a.bytes * 100, 0), '990.00')||'%' "Used %"
FROM sys.dba_tablespaces d,
       (select tablespace_name, sum(bytes) bytes
          from dba_data_files   group by tablespace_name) a,
       (select tablespace_name, sum(bytes) bytes
          from dba_free_space   group by tablespace_name) f
WHERE d.tablespace_name = a.tablespace_name(+)  AND d.tablespace_name = f.tablespace_name(+)
   AND NOT (d.extent_management like 'LOCAL' AND d.contents like 'TEMPORARY')
UNION ALL
SELECT d.status "Status",  d.tablespace_name "Name",
       d.contents "Type",  d.extent_management "ExtentMngr",
       TO_CHAR(NVL(a.bytes / 1024 / 1024, 0), '99,999,990.900') "Size (M)",
       TO_CHAR(NVL(t.bytes, 0) / 1024 / 1024, '99999999.999')  "Used (M)",
       TO_CHAR(NVL(t.bytes / a.bytes * 100, 0), '990.00')||'%'  "Used %"
FROM sys.dba_tablespaces d,
       (select tablespace_name, sum(bytes) bytes
          from dba_temp_files      group by tablespace_name) a,
       (select tablespace_name, sum(bytes_cached) bytes
          from v$temp_extent_pool  group by tablespace_name) t
WHERE d.tablespace_name = a.tablespace_name(+)  AND d.tablespace_name = t.tablespace_name(+)
   AND d.extent_management like 'LOCAL'     AND d.contents like 'TEMPORARY';
grant select ts_usage to public;
create public synonym ts_usage for ts_usage;


conn /as sysdba;
startup nomount;  
CREATE DATABASE NGX5500C
  USER SYS identified by oracle
  USER system identified by oracle
  LOGFILE GROUP 1 ('C:\oracle\product\10.2.0\oradata\NGX5500C\redo01.log') size 100M,  
     GROUP 2 ('C:\oracle\product\10.2.0\oradata\NGX5500C\redo02.log') size 100M,  
     GROUP 3 ('C:\oracle\product\10.2.0\oradata\NGX5500C\redo03.log') size 100M 
  MAXINSTANCES 1
  MAXLOGHISTORY 1
  MAXLOGFILES 16
  MAXLOGMEMBERS 3
  MAXDATAFILES 100
  CHARACTER SET AL32UTF8
  NATIONAL CHARACTER SET AL16UTF16
  DATAFILE 'C:\oracle\product\10.2.0\oradata\NGX5500C\SYSTEM01.dbf'
    SIZE 300M REUSE AUTOEXTEND ON NEXT 10240K MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL
  SYSAUX DATAFILE 'c:\oracle\product\10.2.0\oradata\NGX5500C\SYSAUX01.dbf' 
    SIZE 300M REUSE AUTOEXTEND ON NEXT 10240K MAXSIZE UNLIMITED
  UNDO TABLESPACE UNDOTBS1 DATAFILE 'C:\oracle\product\10.2.0\oradata\NGX5500C\undotbs01.dbf'
    SIZE 200M REUSE AUTOEXTEND ON NEXT 5120K MAXSIZE UNLIMITED   
  DEFAULT TEMPORARY TABLESPACE temp01 TEMPFILE 'C:\oracle\product\10.2.0\oradata\NGX5500C\temp01.dbf'
    SIZE 200M REUSE AUTOEXTEND ON NEXT 5120K MAXSIZE UNLIMITED
  DEFAULT TABLESPACE ts1 DATAFILE 
  'C:\oracle\product\10.2.0\oradata\NGX5500C\ts1_01.dbf' SIZE 200M REUSE AUTOEXTEND ON NEXT 5120K MAXSIZE UNLIMITED
  ,'C:\oracle\product\10.2.0\oradata\NGX5500C\ts1_02.dbf' SIZE 200M REUSE AUTOEXTEND ON NEXT 5120K MAXSIZE UNLIMITED;
  

SQL>CREATE tablespace users  
    datafile 'C:\oracle\product\10.2.0\oradata\NGX5500C\users01.dbf' size 100M  
    reuse autoextend on next 1280K maxsize unlimited  
    EXTENT management local;  
SQL>CREATE tablespace index1 
    datafile 'C:\oracle\product\10.2.0\oradata\NGX5500C\index01.dbf' size 100M  
    reuse autoextend on next 1280K maxsize unlimited  
    EXTENT management local; 
@C:\oracle\product\10.2.0\db_2\rdbms\admin\catalog.sql    
