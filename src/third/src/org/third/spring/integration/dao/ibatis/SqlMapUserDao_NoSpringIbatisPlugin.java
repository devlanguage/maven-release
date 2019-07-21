
import java.sql.SQLException;
import java.util.List;

import org.third.testdata.user.domain.UserDM;

import com.ibatis.sqlmap.client.SqlMapClient;

public class SqlMapUserDao_NoSpringIbatisPlugin implements UserDao_Ibatis {

    private com.ibatis.sqlmap.client.SqlMapClient ibatisSqlMapClient;

    public SqlMapClient getIbatisSqlMapClient() {
        return ibatisSqlMapClient;
    }

    public void setIbatisSqlMapClient(SqlMapClient sqlMapClient) {
        this.ibatisSqlMapClient = sqlMapClient;
    }

    public UserDM getUserById(int id) {
        UserDM parameter = new UserDM();
        parameter.setId(id);
        UserDM u = null;
        try {
            u = (UserDM) this.ibatisSqlMapClient.queryForObject("getUserById", parameter);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return u;

    }

    @SuppressWarnings("unchecked")
    public List<UserDM> getAllUsers() {
        List<UserDM> allUsers = null;
        try {
            allUsers = ibatisSqlMapClient.queryForList("listUsers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

}
