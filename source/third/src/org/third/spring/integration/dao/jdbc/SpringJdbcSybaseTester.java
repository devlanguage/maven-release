package org.third.spring.integration.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.third.orm.ibatis.account.domain.persistence.Account;
import org.third.spring.SpringTest;
import org.third.testdata.db.bean.DbObject;
import org.third.testdata.user.dao.CurrentPage;
import org.third.testdata.user.dao.UserDao;
import org.third.testdata.user.domain.UserDM;

/**
 * <pre>
 * IF EXISTS (SELECT 1 FROM sysobjects WHERE type='U' AND name='js_tnbi_people' )
 *   DROP TABLE js_tnbi_people
 * go
 * CREATE TABLE js_tnbi_people (  
 *    id int IDENTITY NOT NULL ,
 *    name varchar(45) NOT NULL,  
 *    birth_day datetime DEFAULT getdate(),  
 *    working_day timestamp DEFAULT getdate(),  
 *    sex tinyint DEFAULT NULL,  
 *    weight float DEFAULT 55.4,  
 *    height float DEFAULT 169.10,  
 *    PRIMARY KEY (id)  
 *  )
 * go
 * 
 * </pre>
 * 
 * Created on Oct 27, 2014, 2:53:06 PM
 */
public class SpringJdbcSybaseTester extends SpringTest {

    public UserDao getPeopleDao() {
        return (UserDao) ctx.getBean("userDao");
    }

    public SpringJdbcSybaseTester(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new SpringJdbcSybaseTester("spring_jdbc.xml").test();
    }

    public void test() {
        UserDao ud = (UserDao) ctx.getBean("userDao");
        System.out.println(ud.getDataSource());
        selectOne();

        // // SingleConnectionDataSource��SmartDataSource�ӿ�
        // // ��һ��ʵ�֣����ڲ���װ��һ�������ӡ���������ʹ��֮�󽫲���رգ�����Ȼ�������ڶ��߳�
        // // �Ļ�����ʹ�á�SingleConnectionDataSource scds = new
        // // SingleConnectionDataSource();
        //
        // DriverManagerDataSource ds = getDataSource();
        // JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // jdbcTemplate.setDataSource(ds);
        // // DriverManagerDataSourceÿ�η���һ��������
        // // ����Ҫ��ʽ�ر�connection��JdbcTemplate�еķ������Լ�����connection
        //
        // NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(ds);
        // Map<String, String> paramMap = new HashMap<String, String>();
        // paramMap.put("firstName", "js_tnbi%");
        // Account account = (Account) npjt.queryForObject("select * from sysobjects where name like :firstName ",
        // paramMap, new RowMapper() {
        //
        // public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        //
        // Account acc = new Account();
        // System.out.println(rs.getString(1) + ", " + rs.getString(2));
        // return acc;
        // }
        // });
        // System.out.println(account);
        //
        // // jdbcTemplate.execute("create table t_user as select * from
        // // user_tables");
        // //
        // // SqlQuery sq = new MappingSqlQuery();
        // // SqlQuery sq = new MappingSqlQueryWithParameters();
        // // // SqlFunction sc = new SqlFunction();
        // // // StoredProcedure sp = new StoredProcedure();
        // // SqlUpdate su = new SqlUpdate();

    }

    /**
     * DML语句
     */
    // @Test//测试通过,创建表T_person
    public void CreateTable() {
        // java.lang.NoClassDefFoundError: org/apache/commons/collections/CursorableLinkedList报了个错
        // commons-collections.jar 加上此jar
        String createSql = "CREATE TABLE T_people(id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," + "name VARCHAR(45) NOT NULL,"
                + "birthDay DATETIME NOT NULL," + "sex BOOLEAN NOT NULL," + "weight DOUBLE NOT NULL," + "height FLOAT NOT NULL,"
                + "PRIMARY KEY (id)" + ")ENGINE = InnoDB; ";
        getPeopleDao().doCreateTable(createSql);
    }

    /**
     * 增删改查
     */
    // id name birthDay sex weight height
    // @Test//测试通过,添加二条数据
    public void insert() {
        UserDM people = new org.third.testdata.user.domain.UserDM();
        people.setName("ZCtime");
        people.setBirthday(new Timestamp(new Date().getTime()));
        people.setSex(true);
        people.setHeight(178F);
        people.setWeight(130D);
        getPeopleDao().doSaveObj(people);
    }

    // @Test//测试通过
    public void update() {
        UserDM people = new org.third.testdata.user.domain.UserDM();
        people.setId(1);
        people.setName("TestUpdate");
        people.setBirthday(new Timestamp(new Date().getTime()));
        people.setSex(true);
        people.setHeight(178F);
        people.setWeight(130D);
        getPeopleDao().doUpdateObj(people);
    }

    // @Test//测试通过,查询单个对象
    public void selectOne() {
        UserDM p = (UserDM) getPeopleDao().getObjByID(1);
        System.out.println(p.getName() + "_" + p.getBirthday());
    }

    // @Test//测试通过,拿到多个对象
    public void selectList() {
        System.out.println(getPeopleDao().getObjsByID(2).size());
    }

    // @Test//测试通过得到属性为Date的
    public void SelectOneDateAtrri() {
        System.out.println(getPeopleDao().getBirthday(1));
    }

    // @Test//测试通过得到属性为String的
    public void selectOneStringAttri() {
        String name = getPeopleDao().getNameAttri(1);
        System.out.println(name);
    }

    // @Test//测试通过
    public void selectCounts() {
        int counts = getPeopleDao().getCountEntites();
        System.out.println(counts);
    }

    // @Test//测试通过,这搞出来的怎么类json数据?
    public void selectForList() {
        System.out.println(getPeopleDao().getList());
    }

    // @Test//测试通过
    public void deleteObj() {
        getPeopleDao().doDeleteObj(2);
    }

    // @Test//分页测试
    public void testPage() throws SQLException {
        CurrentPage<UserDM> currentPageUser = getPeopleDao().getUserPage(1, 2, 0);
        List<UserDM> pList = currentPageUser.getPageItems();
        for (int i = 0; i < pList.size(); i++) {
            System.out.println(pList.get(i));
        }

    }

    // @Test//测试通过,拿到插入后自动生成的主键
    public void getGeneratedKey() {
        System.out.println(getPeopleDao().getAutoIncrementKey());
    }

    // 测试批处理
    @Test
    public void testBatch() {
        UserDM people = new org.third.testdata.user.domain.UserDM();
        people.setId(1);
        people.setName("123");
        UserDM people2 = new org.third.testdata.user.domain.UserDM();
        people2.setId(3);
        people2.setName("123");
        List<UserDM> peList = new ArrayList<UserDM>();
        peList.add(people);
        peList.add(people2);
        System.out.println(getPeopleDao().batchUpdate(peList));
        ;
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
