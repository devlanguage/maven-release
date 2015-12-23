/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.access.AccountDao.java is created on 2008-6-18
 */
package org.third.orm.ibatis.account.dao.ibatis.ibatis_style;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.third.orm.ibatis.account.dao.AccountDao;
import org.third.orm.ibatis.account.dao.ibatis.BaseSqlMapStyle;
import org.third.orm.ibatis.account.domain.persistence.Account;
import org.third.orm.ibatis.account.domain.persistence.Account_Map;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * 
 */
public class AccountDaoImpl extends BaseSqlMapStyle implements AccountDao {

    public AccountDaoImpl(DaoManager daoManager) {

        super(daoManager);
    }

    private final static String SQL_LIST_ACCCOUNTS = "selectAllAccounts";
    private final static String SQL_GET_ACCOUNTS_BY_ACCOUNT = "selectAccountsByAccount";
    private final static String SQL_GET_XML_ACCOUNT_BY_ID = "selectAccountById";
    private final static String SQL_UPDATE_ACCOUNT_EMAIL = "updateAccountEmail";

    public List<Account> listAccounts() {

        return this.queryForList(SQL_LIST_ACCCOUNTS);
    }

    public List<Account> listAccounts(Account condition) {

        return this.queryForList(SQL_LIST_ACCCOUNTS, condition);
    }

    public PaginatedList listAccounts(int pageSize) {

        return this.queryForPaginatedList(SQL_LIST_ACCCOUNTS, pageSize);
    }

    public List<Account> listAccount(int start, int end) {

        return this.queryForList(SQL_LIST_ACCCOUNTS, start + 1, end + end);

    }

    /**
     * <pre>
     * 批处理: 如果要执行很多非查询（insert/update/delete）的语句，您可能喜欢将它们作为一个批处理来执行，以减少网络通讯流量，并让JDBC
     * Driver进行优化（例如压缩）。SQL Map API使用批处理很简单，可以使用两个简单的方法划分批处理的边界
     * sqlMap.startBatch(); 
     * //…execute statements in between 
     * sqlMap.executeBatch(); 
     * 
     * 当调用endBatch（）方法时，提交�的批处理语句将通过JDBC Driver来执executeBatch来执行 </pre>
     * 
     * @return
     */
    public int batchUpdte() {

        return 0;
    }

    /**
     * <pre>
     *      全局（分布式）事�?     *      SQL Map框架支持全局事务。全�?��务也叫分布式事务，它可以允许您在同一事务中更新多个数据库（或其他符合JTA规范的资源），即同时成功或失败�?
     *      􀁺 External/Programmatic Global 事务
     *      您可以�?择外部管理或手工编程管理全局事务，或实现�?��象EJB�?��的架构�?使用EJB，您可以通过使用EJB的描述文件定义事务范围�?更多的细节超出了本文的范围�?要支持外部管理或手工编程管理全局事务，必须在SQL Map配置文件�?参见前面章节的内�?设定&lt;transactionManager&gt;的type属�?为EXTERNAL。使用外部管理的全局事务，SQL Map事务控制方法变得有的多余，因为事务的�?��、提交和回�?都由外部的事务管理器来控制�?但是，使用SqlMapClient的startTransaction（），commitTransaction（）或rollbackTransaction（）来划分事务范围（相对于自动的事务处理），还是对提高�?能有帮助。继续使用这些方法，可以保持编程规范的一致�?。另�?��好处是，在某些情况下，您可能�?��改变关闭资源的顺序（不幸的是，不同的应用服务器和事务管理器具有不同的规则）�?除了这些考虑，要使用全局事务，不�?��改变您的SQL Map代码�?     *      􀁺 受管理的（Managed）全�?���?     *      SQL Map框架也可以为您管理全�?��务�?要支持受管理的全�?��务，必须在SQL Map配置文件中设�?lt;transactionManager&gt;的type属�?为JTA，并设定“UserTransaction”属性为JNDI的全名，以使SqlMapClient实例能找到UserTransaction对象。更详细的配置信息，参见前面关于&lt;transactionManager&gt;的说明�?
     *      使用全局事务编程，代码没有多大的不同，但有几个小小的地方要注意�?例如�?     *      try {
     *      orderSqlMap.startTransaction();
     *      storeSqlMap.startTransaction();
     *      orderSqlMap.insertOrder(�?;
     *      orderSqlMap.updateQuantity(�?;
     *      storeSqlMap.commitTransaction();
     *      orderSqlMap.commitTransaction();
     *      } finally {
     *      try {
     *      storeSqlMap.endTransaction()
     *      } finally {
     *      orderSqlMap.endTransaction()
     *      }
     *      }
     *      上面的例子中，假设我们�?过两个SqlMapClient来使用两个不同的数据库�?第一个开始事务的SqlMapClient（orderSqlMap）同时也�?��了一个全�?��务�?在这之后，所有其他的操作将被看作是这个全�?��务的�?��分，直到同一个SqlMapClient（orderSqlMap）调用commitTransaction（）或rollbackTransaction（），届时全�?��务被提交并完成其他所有的操作�?     *      警告！虽然这些看起来很简单，但记住不要滥用全�?��分布式）事务，这点很重要。这样做既有性能方面的�?虑，同时也是因为全局事务会让应用服务器和数据库驱动程序的设置变得更复杂�?虽然看起来简单，您可能还是会遇到�?��困难。记住，EJB拥有更多厂商和工具支持�?对于�?��分布式事务的应用，最好还是使用Session EJB。在www.ibatis.com的JPetstore，就是一个使用SQL Map全局事务的例�?     * </pre>
     * 
     * @return
     */
    public int globalUpdate() {

        return 0;
    }

    public int updateAccount(Account account) {

        int updateRowCount = 0;
        /*******************************************************************************************
         * <pre>
         * 注意！事务不能嵌套�?在调用commit（）或rollback（）之前，从同一线程多次调用.startTransaction，将引起抛出例外�?         *      换句话说，对于每个SqlMap实例，每个线程最多只能打�?��个事务�?
         * 注意！qlMapClient事务处理使用Java的ThreadLocal保存事务对象�?         *      这意味着在处理事务时，每个调用startTransaction（）的线程，将得到一个唯�?��Connection对象�?         *      将一个Connection对象返回数据源（或关闭连接）唯一的方法是调用commitTransaction（）或rollbackTransaction（）方法�?         *      否则，会用光连接池中的连接并导致死锁�?         * </pre>
         ******************************************************************************************/
        try {
            this.getSqlMapTransactionManager().startTransaction(
                    Connection.TRANSACTION_READ_COMMITTED);
            Account result = (Account) this.queryForList(SQL_GET_ACCOUNTS_BY_ACCOUNT, account).get(
                    0);
            if (result.getId() > 0) {
                this.update(SQL_UPDATE_ACCOUNT_EMAIL, account);
            }
            this.getSqlMapTransactionManager().commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.getSqlMapTransactionManager().endTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return updateRowCount;

    }

    public void listAccountsWithRowHandler(RowHandler rowHandler) {

        Account account = new Account();
        account.setId(1);
        account.setFirstName("yong%");
        account.setLastName("chen%");
        account.setEmailAddress("yongjie_g8100@126.com");

        this.update("updateAccountEmail", account);

        String payload = (String) this.queryForObject(SQL_GET_XML_ACCOUNT_BY_ID, account);
        System.out.println(payload);

        // Map<String, Account> map = this.queryForMap(SQL_LIST_ACCCOUNTS, null, "firstName");
        // System.out.println(map);
        //
        //        

        // map = this.queryForMap(SQL_GET_ACCOUNTS_BY_ACCOUNT, account, "firstName");
        // System.err.println(map);

        // Account_Map accountMap = new Account_Map();
        // accountMap.setAccountID(1);
        // accountMap.setAccountName("yong%");

        /**
         * <pre>
         * class java.util.HashMap: {emailAddress=yongjie.gong@tellabs.com, firstName=yongjie-2, id=2, lastName=gong}
         * class java.util.HashMap: {emailAddress=yongjie.gong@tellabs.com, firstName=yongjie-3, id=3, lastName=gong}
         * class java.util.HashMap: {emailAddress=yongjie.gong@tellabs.com, firstName=yongjie-4, id=4, lastName=gong}
         * class java.util.HashMap: {emailAddress=yongjie.gong@tellabs.com, firstName=yongjie-5, id=5, lastName=gong}
         * class java.util.HashMap: {emailAddress=yongjie.gong@tellabs.com, firstName=yongjie-6, id=6, lastName=gong}
         * </pre>
         */
        Map accountMap = new HashMap();
        accountMap.put("accountID", "1");
        accountMap.put("accountName", "yong%");
        accountMap.put("accountID", "1");
        List<Map> accounts = (List<Map>) this
                .queryForList("getAccountByMapParam", accountMap, 0, 5);
        for (Map o : accounts) {
            System.err.println(o.getClass() + ": " + o);
        }

        Account_Map account_Map = new Account_Map();
        account_Map.setAccountID(1);
        Object procedureClassResult = this.queryForObject("retrieve_account_name_by_class",
                account_Map);
        System.out.println(account_Map.getAccountName());

        Object procedureResult = this.queryForObject("retrieve_account_name_by_map", accountMap);
        System.out.println(accountMap.get("accountName"));

        // this.queryWithRowHandler(SQL_LIST_ACCCOUNTS, rowHandler);
    }

}
