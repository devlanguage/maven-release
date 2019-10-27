ALTER ROLE pg_test_user_3 WITH LOGIN CREATEROLE; /*赋予登录权限*/  
ALTER ROLE pg_test_user_4 VALID UNTIL 'JUL 7 14:00:00 2012 +8'; /*设置角色的有效期*/  
ALTER ROLE  
名称  
ALTER ROLE -- 修改一个数据库角色  
语法  
ALTER ROLE name [ [ WITH ] option [ ... ] ]  


 grant role1 to jack;
 grant usage on schema public to public; --授权usage给所有用户(后一个public),否则看不到数据库中的表.
 
 三种方式还原到最初的jack角色
database1=> set role jack;
SET
database1=> set role none;
SET
database1=> reset role;
reset

revoke create on database database1 from r1;