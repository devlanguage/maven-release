package org.third.spring.integration.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.third.orm.ibatis.account.domain.persistence.Account;
import org.third.spring.SpringTest;
import org.third.testdata.db.bean.DbObject;

/**
 * <pre>
 * DROP TABLE IF EXISTS `springjdbc`.`t_people`;  
 * CREATE TABLE  `springjdbc`.`t_people` (  
 *   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
 *   `name` varchar(45) NOT NULL,  
 *   `birthDay` datetime DEFAULT NULL,  
 *   `sex` tinyint(1) DEFAULT NULL,  
 *   `weight` double DEFAULT NULL,  
 *   `height` float DEFAULT NULL,  
 *   PRIMARY KEY (`id`)  
 * ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
 * </pre>
 * 
 * Created on Oct 27, 2014, 2:53:06 PM
 */
public class SpringJdbcMySQLTester extends SpringTest {

    public SpringJdbcMySQLTester(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new SpringJdbcMySQLTester("spring_jdbc.xml").test();
    }

    public void test() {

        // SingleConnectionDataSource��SmartDataSource�ӿ�
        // ��һ��ʵ�֣����ڲ���װ��һ�������ӡ���������ʹ��֮�󽫲���رգ�����Ȼ�������ڶ��߳�
        // �Ļ�����ʹ�á�SingleConnectionDataSource scds = new
        // SingleConnectionDataSource();

        DriverManagerDataSource ds = getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(ds);
        // DriverManagerDataSourceÿ�η���һ��������
        // ����Ҫ��ʽ�ر�connection��JdbcTemplate�еķ������Լ�����connection

        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(ds);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("firstName", "js_tnbi%");
        Account account = (Account) npjt.queryForObject("select * from sysobjects where name like :firstName ", paramMap, new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                Account acc = new Account();
                System.out.println(rs.getString(1) + ", " + rs.getString(2));
                return acc;
            }
        });
        System.out.println(account);

        // jdbcTemplate.execute("create table t_user as select * from
        // user_tables");
        //
        // SqlQuery sq = new MappingSqlQuery();
        // SqlQuery sq = new MappingSqlQueryWithParameters();
        // // SqlFunction sc = new SqlFunction();
        // // StoredProcedure sp = new StoredProcedure();
        // SqlUpdate su = new SqlUpdate();

    }

    public final DriverManagerDataSource getDataSource() {

        DbObject dbObject = (DbObject) ctx.getBean("sybase");
        org.springframework.jdbc.datasource.DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbObject.getDriverClass());
        dataSource.setUrl(dbObject.getUrl());
        dataSource.setUsername(dbObject.getUser());
        dataSource.setPassword(dbObject.getPassword());
        return dataSource;
    }

}
