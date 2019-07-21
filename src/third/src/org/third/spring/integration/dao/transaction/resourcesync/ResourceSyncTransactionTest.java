
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.third.spring.SpringTest;

/**
 * 9.4. ʹ����Դͬ��������
 * ����Ӧ�ñȽ�������ǣ���ͬ���������������δ����ģ��Լ�������α����ӵ���Ӧ����Ҫ��ͬ�����������Դ�ϣ����磬DataSourceTransactionManager
 * ��Ӧ��JDBC DataSource�� HibernateTransactionManager ��Ӧ��Hibernate�� SessionFactory
 * �ȣ������ǣ�ʣ�µ������ǣ�ֱ�ӻ��ӵ�ʹ��һ�ֳ־û�API��JDBC��Hibernate��JDO�ȣ���Ӧ�ô��룬���ȷ��ͨ����ص�
 * PlatformTransactionManager ��ǡ���ػ�ȡ��������Դ������������ͬ������Щ�������������������á����� �� ����������û�У���
 * 
 * 9.4.1. �߲�η���
 * ��ѡ�ķ�����ʹ��Spring�ĸ߲�־û�����API�����ַ�ʽ�����滻ԭʼ��API���������ڲ���װ����Դ���������á���������ͬ���Լ��쳣ӳ��ȹ��ܣ������û������ݷ��ʴ���Ͳ��ع�����Щ�������о������Լ��ĳ־û��߼���ͨ���������г־û�API����������
 * ģ�� ���������� JdbcTemplate��HibernateTemplate��JdoTemplate�ࣨ��Щ����ݲο��ĵ�������½�����ϸ��������
 * 
 * 9.4.2. �Ͳ�η���
 * �ڽϵͲ���ϣ���������Щ�ࣺDataSourceUtils�����JDBC����SessionFactoryUtils�����Hibernate����PersistenceManagerFactoryUtils�����JDO���ȵȡ�����Ӧ�ô�����˵��ֱ��ͬԭʼ�־û�API���е���Դ���ʹ򽻵��Ǹ��õ�ѡ��ʱ����Щ��ȷ��Ӧ�ô����ȡ����ȷ��Spring����������bean��������ȷͬ������������е��쳣��ӳ�䵽һ�µ�API��
 * 
 * ���磬��JDBC�����£��㲻��ʹ�ô�ͳ�ĵ��� DataSource �� getConnection() �����ķ�ʽ������ʹ��Spring��
 * org.springframework.jdbc.datasource.DataSourceUtils����������
 * 
 * Connection conn = DataSourceUtils.getConnection(dataSource);
 * �������һ��������֮������connection���ڣ���ʵ���������ء����򣬸÷������ý�������һ���µ�connection�Ĵ�����������connection����ѡ�أ���ͬ�����κ����е����񣬲�������ͬһ����Χ�ڱ������ĵ��ø��á����������ᵽ�ģ����������һ������ĺô��������κ�
 * SQLException������װΪSpring��ܵ�
 * CannotGetJdbcConnectionException��������Spring��ܵ�unchecked��DataAccessExceptions�����ϵ�е�һԱ���⽫����ȴ�
 * SQLException �м����ø������Ϣ�����ұ�֤�˿����ݿ⡪�����������־û�������������ֲ�ԡ�
 * 
 * Ӧ��ָ�����ǣ���Щ��ͬ��������û��Spring�������Ļ����й������ã�����ͬ�������ǿ�ѡ�ģ��������������Ƿ�ʹ��Spring����������㶼����ʹ����Щ�ࡣ
 * 
 * ��Ȼ��һ�����ù�Spring��JDBC֧�ֻ�Hibernate֧�֣���һ��Ͳ��ٻ�ѡ�� DataSourceUtils
 * ���Ǳ�ĸ������ˣ���Ϊ����������Spring����һ������������ֱ��ʹ����ص�API�����磬�����ʹ��Spring�� JdbcTemplate ��
 * jdbc.object ������ʹ��JDBC��Spring����Ļ��������ȷ�ػ�ȡ���ӣ����㲻��Ҫд�κ�������롣
 * 
 * 9.4.3. TransactionAwareDataSourceProxy ��������ײ����
 * TransactionAwareDataSourceProxy �ࡣ����һ����Ŀ�� DataSource �Ĵ�������װ��Ŀ��
 * DataSource���ṩ��Spring��������Ŀ�֪�ԡ�������ϣ���������һ��J2EE�������ṩ��������JNDI DataSource��
 * 
 * ����Ӧ����Զ����Ҫ��Ӧ�ô���ʹ�ã��������д��������Ҫֱ�Ӵ���һ����׼��JDBC�� DataSource
 * ���������ʱ����ͨ������Spring������������Щ������Ȼ���á���д�µĴ���ʱ����ѡ�ķ����ǲ��������ᵽ��Spring�߲����
 * 
 */
public class ResourceSyncTransactionTest extends SpringTest {

    public ResourceSyncTransactionTest(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new ResourceSyncTransactionTest("spring_transaction.xml").test();
    }

    public void test() {

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            ResultSet rs = conn.createStatement().executeQuery("select 89 from dual");
            rs.next();
            System.out.println(rs.getString(1));
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    final static void getConnectionByDataSourceUtil(ApplicationContext ctx) {

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        Connection conn = DataSourceUtils.getConnection(dataSource);
    }

    final static void getConnectionBySpringTemplate() {
        // JdbcTemplate��HibernateTemplate��JdoTemplate��

    }
}
