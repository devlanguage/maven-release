package org.third.orm.ibatis.account.client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.third.orm.ibatis.account.domain.persistence.Account;
import org.third.orm.ibatis.util.IbatisClient;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * This is not a best practices class. It's just an example to give you an idea of how iBATIS works.
 * For a more complete example, see JPetStore 5.0 at http://www.ibatis.com.
 */
public class SimpleExample {

    /**
     * SqlMapClient instances are thread safe, so you only need one. In this case, we'll use a
     * static singleton. So sue me. ;-)
     */
    private static SqlMapClient sqlMapperClient;

    /**
     * It's not a good idea to put code that can fail in a class initializer, but for sake of
     * argument, here's how you configure an SQL Map.
     */
    static {
        sqlMapperClient = IbatisClient.getInstance().getSqlMapClient();
    }

    public static List<Account> selectAllAccounts() throws SQLException {

        List<Account> queryForList = (List<Account>) sqlMapperClient
                .queryForList("selectAllAccounts");
        return queryForList;
    }

    public static Account selectAccountById(int id) throws SQLException {

        return (Account) sqlMapperClient.queryForObject("selectAccountById", id);
    }

    public static void insertAccount(Account account) throws SQLException {

        sqlMapperClient.insert("insertAccount", account);
    }

    public static void updateAccount(Account account) throws SQLException {

        sqlMapperClient.update("updateAccount", account);
    }

    public static void deleteAccount(int id) throws SQLException {

        try {
            sqlMapperClient.startTransaction(Connection.TRANSACTION_READ_COMMITTED);
            sqlMapperClient.delete("deleteAccount", id);
            sqlMapperClient.commitTransaction();
        } finally {
            sqlMapperClient.endTransaction();
        }
    }

    public static void main(String[] args) {

        try {
            for (Account account : selectAllAccounts()) {
                System.out.println(account);
            }
            System.out.println(selectAccountById(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
