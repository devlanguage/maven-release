package org.third.testdata.user.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.third.testdata.user.domain.UserDM;

public class UserDao {

    public UserDM createUser() {

        System.out.println("UserDao: Create User");
        return new UserDM("User_", "Password_");
    }

    // private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    // NamedParameterJdbcTemplate为JDBC操作增加了命名参数的特性支持，而不是传统的使用（'?'）作为参数的占位符。
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);

        /**
         * DriverManagerDataSource dataSource2 = new DriverManagerDataSource();
         * dataSource2.setDriverClassName("org.hsqldb.jdbcDriver"); dataSource2.setUrl("jdbc:hsqldb:hsql://localhost:");
         * dataSource2.setUsername("sa"); dataSource2.setPassword("");
         */
    }

    /**
     * 官方文档上还有例子: this.jdbcTemplate.update("insert into t_actor (first_name, last_name) values (?, ?)","Leonor",
     * "Watling"); this.jdbcTemplate.update("update t_actor set = ? where id = ?","Banjo", 5276L);
     * this.jdbcTemplate.update("delete from actor where id = ?",Long.valueOf(actorId)); 存储过程:
     * this.jdbcTemplate.update("call SUPPORT.REFRESH_ACTORS_SUMMARY(?)",Long.valueOf(unionId));
     */

    public void doCreateTable(String sql) {
        this.jdbcTemplate.execute(sql);
    }

    public void doDeleteObj(int id) {
        this.jdbcTemplate.update("delete from td_user where id = ?", id);// Long.valueOf(id)
    }

    public void doSaveObj(UserDM p) {
        // 插入方式其一 ,原始的,拼写sql语句后直接发送执行
        /**
         * this.jdbcTemplate.update("insert into td_user(name,birthDay,sex,weight,height) values(" +
         * "'"+p.getName()+"','"+new
         * java.sql.Timestamp(p.getBirthday().getTime())+"',"+p.getSex()+","+p.getWeight()+","+p.getHeight()+")");
         */
        // 插入方式二
        jdbcTemplate.update("insert into td_user(name,birthDay,sex,weight,height) values(?,?,?,?,?)",
                new Object[] { p.getName(), p.getBirthday(), p.getSex(), p.getWeight(), p.getHeight() }, new int[] {
                        java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP, java.sql.Types.BOOLEAN,
                        // java.sql.Types.DATE,则插入的只有日期,没有时间,2011-04-24 00:00:00;TIMESTAMP:2011-04-24 19:09:24
                        java.sql.Types.DOUBLE, java.sql.Types.FLOAT });

    }

    // //id name birthDay sex weight height

    public void doUpdateObj(UserDM p) {
        jdbcTemplate.update("update td_user set name = ? , birthDay = ? , sex = ? , weight = ? , height = ?  where id = ? ", new Object[] {
                p.getName(), p.getBirthday(), p.getSex(), p.getWeight(), p.getHeight(), p.getId() }, new int[] { java.sql.Types.VARCHAR,
                java.sql.Types.DATE, java.sql.Types.BOOLEAN, java.sql.Types.DOUBLE, java.sql.Types.FLOAT, java.sql.Types.INTEGER });

    }

    public int getCountEntites() {
        // int rowCount = this.jdbcTemplate.queryForInt("select count(*) from td_user");
        int rowCount = this.jdbcTemplate.queryForInt("select count(*) from td_user where id >= ?", new Object[] { 1 });
        // select count(*) from td_user where id >= ?
        // = this.jdbcTemplate.queryForInt("select count(*) from td_user where name = ?", "XXX");
        return rowCount;
    }

    public Serializable getObjByID(int id) {
        // Query for a String
        // this.jdbcTemplate.queryForObject("select name from td_user where id = ?",new Object[]{1212}, String.class);
        UserDM p = this.jdbcTemplate.queryForObject("select * from td_user where id = ?", new Object[] { id }, new RowMapper<UserDM>() {

            public UserDM mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserDM p = new UserDM();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBirthday(rs.getTimestamp("birthday"));
                p.setWeight(rs.getDouble("weight"));
                p.setHeight(rs.getFloat("height"));
                p.setSex(rs.getBoolean("sex"));
                return p;
            }
        });
        return p;
    }

    public List<UserDM> getObjsByID(int id) {
        List<UserDM> plist = this.jdbcTemplate.query("select * from td_user where id >= ?", new Object[] { id }, new RowMapper<UserDM>() {

            public UserDM mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserDM p = new UserDM();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBirthday(rs.getTimestamp("birthDay"));
                p.setWeight(rs.getDouble("weight"));
                p.setHeight(rs.getFloat("height"));
                p.setSex(rs.getBoolean("sex"));
                return p;
            }
        });
        return plist;
    }

    // 上面这个List也可以用下面来实现
    public List<UserDM> getObjsByID2(int id) {
        return this.jdbcTemplate.query("select * from td_user where id >= ?", new Object[] { id }, new UserMapper());
    }

    private static final class UserMapper implements RowMapper<UserDM> {
        public UserDM mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDM p = new UserDM();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setBirthday(rs.getTimestamp("birthDay"));
            p.setWeight(rs.getDouble("weight"));
            p.setHeight(rs.getFloat("height"));
            p.setSex(rs.getBoolean("sex"));
            return p;
        }
    }

    public String getNameAttri(int id) {
        String name = this.jdbcTemplate.queryForObject("select name from td_user where id = ?", new Object[] { id }, String.class);
        return name;
    }

    public Date getBirthday(int id) {
        return this.jdbcTemplate.queryForObject("select birthDay from td_user where id = ?", new Object[] { id }, Date.class);
    }

    public List<Map<String, Object>> getList() {
        return this.jdbcTemplate.queryForList("select * from td_user ");
    }

    public int[] batchUpdate(final List<UserDM> Users) {
        int[] updateCounts = jdbcTemplate.batchUpdate("update td_user set name = ? where id = ?", new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, Users.get(i).getName());
                ps.setInt(2, Users.get(i).getId());
            }

            public int getBatchSize() {
                return Users.size();
            }
        });
        return updateCounts;
    }

    /**
     * 返回分页后结果
     * 
     * @param pageNo
     * @param pageSize
     * @param id
     * @return
     * @throws SQLException
     */
    public CurrentPage<UserDM> getUserPage(final int pageNo, final int pageSize, int id) throws SQLException {
        PagingHelper<UserDM> ph = new PagingHelper<UserDM>();
        CurrentPage<UserDM> p = ph.fetchPage(jdbcTemplate, "select count(*) from td_user where id >= ?",// sqlCountRows
                "select * from td_user where id >= ?",// sqlFetchRows
                new Object[] { id },// args
                pageNo,// pageSize
                pageSize, new ParameterizedRowMapper<UserDM>() {
                    public UserDM mapRow(ResultSet rs, int i) throws SQLException {
                        return new UserDM(rs.getInt(1),// name,birthDay,sex,weight,height
                                rs.getString(2), rs.getTimestamp(3), rs.getDate(3), rs.getBoolean(4), rs.getDouble(5), rs.getFloat(6));
                    }
                });
        return p;
    }

    public int getNamedParameterJdbcCounts(int id) {
        String sql = "select count(*) from td_user where id >= :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        // 传一个对象
        // SqlParameterSource namedParametersx = new BeanPropertySqlParameterSource(new User());
        return namedParameterJdbcTemplate.queryForInt(sql, namedParameters);
    }

    public int getAutoIncrementKey() {
        final String INSERT_SQL = "insert into td_user (name) values(?)";
        final String name = "test getAutoIncrementKey";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] { name });
                ps.setString(1, name);
                return ps;

            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
