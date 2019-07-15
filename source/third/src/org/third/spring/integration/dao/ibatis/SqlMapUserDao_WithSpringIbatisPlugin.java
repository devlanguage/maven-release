
import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.third.testdata.user.domain.UserDM;

public class SqlMapUserDao_WithSpringIbatisPlugin extends SqlMapClientDaoSupport implements UserDao_Ibatis {

    public UserDM getUserById(int id) {
        UserDM parameter = new UserDM();
        parameter.setId(id);

        UserDM u = null;
        try {
            u = (UserDM) this.getSqlMapClient().queryForObject("getUserById", parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @SuppressWarnings( { "unused", "unchecked" })
    public List<UserDM> getAllUsers() {

        com.ibatis.sqlmap.client.SqlMapClient ibatisClient = this.getSqlMapClient();

        org.springframework.orm.ibatis.SqlMapClientTemplate ibatisClientTemplate = this.getSqlMapClientTemplate();

        List<UserDM> allUsers = null;
        try {
            allUsers = this.getSqlMapClient().queryForList("listUsers");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    // private SqlMapClient sqlMapClient;
    //
    // public SqlMapClient getSqlMapClient() {
    // return sqlMapClient;
    // }
    //
    // public void setSqlMapClient(SqlMapClient sqlMapClient) {
    // this.sqlMapClient = sqlMapClient;
    // }

}
