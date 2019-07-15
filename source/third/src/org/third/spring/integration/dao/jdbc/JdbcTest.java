
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Framework��JDBC���������ĸ������ɣ�core�� dataSource��object�Լ�support��
 * 
 * org.springframework.jdbc.core����JdbcTemplate���Լ���صĻص��ӿڣ�callback
 * interface��������ɡ� org.springframework.jdbc.core.simple �Ӱ��������
 * SimpleJdbcTemplate ���Լ���ص�SimpleJdbcInsert ���SimpleJdbcCall �ࡣ
 * org.springframework.jdbc.core.namedparam �Ӱ��������NamedParameterJdbcTemplate
 * ���Լ�����ص�֧���ࡣ
 * 
 * org.springframework.jdbc.datasource���ṩ��һЩ���������򻯶�DataSource�ķ��ʡ�ͬʱ�ṩ�˶��ּ򵥵�DataSourceʵ�֡���Щʵ�ֿ�������J2EE�������ж������Ժ����С�
 * ��Щ�������ṩ��һЩ��̬������������ͨ��JNDI����ȡ���ݿ����Ӻ͹ر����ӡ�ͬʱ֧�ְ󶨵���ǰ�̵߳����ݿ����ӡ�����ʹ��DataSourceTransactionManager��
 * 
 * ����org.springframework.jdbc.object��������һЩ�࣬���ڽ�RDBMS��ѯ�������Լ��洢���̱���ΪһЩ�����õġ��̰߳�ȫ�Ķ������ַ�ʽͨ��JDO����ģ�ͻ���������Щͨ����ѯ���صĶ����������ݿ�����Ķ���
 * ���ֶ���JDBC�ĸ߲�εķ�װ�ǻ���org.springframework.jdbc.core����JDBC�ĵͲ�η�װ֮�ϵġ�
 * 
 * ���org.springframework.jdbc.support��������SQLExceptionת�����Լ�һЩ�����Ĺ����ࡣ
 * 
 * ��JDBC���ù��������׳����쳣���ᱻת��Ϊ��org.springframework.dao���ж�����쳣��Ҳ����˵������ʹ��Spring��JDBC��װ��Ĵ�������ʵ���κ�JDBC����RDBMS��ص��쳣�������е���Щ��ת�����쳣����unchecked�쳣�����Ҳ������һ�ֶ����ѡ�������ץס��Щ�쳣���Ӷ�ת�����������͵��쳣����������ߴ�����
 
 * 
 * JdbcTemplate - ���Ǿ����Ҳ����õ�Spring����JDBC���ʵķ�������Ҳ����ͼ���ķ�װ,
 * �����Ĺ���ģʽ��ʵ���ڵײ�ʹ����JdbcTemplate��Ϊ��ײ��ʵ�ֻ�����JdbcTemplate��JDK 1.4���ϵĻ����Ϲ����úܺá�
 * 
 * NamedParameterJdbcTemplate -
 * ��JdbcTemplate���˷�װ���ṩ�˸��ӱ�ݵĻ�������������ʹ�÷�ʽ�����Ǵ�ͳ��JDBC��ʹ�õġ�?����Ϊ������ռλ�������ַ�ʽ������ҪΪĳ��SQLָ����������ʱ���Եø���ֱ�۶����á������Ա��빤����JDK
 * 1.4���ϡ�
 * 
 * SimpleJdbcTemplate -
 * ���������JdbcTemplate��NamedParameterJdbcTemplate����õĹ��ܣ�ͬʱ��Ҳ������һЩJava
 * 5�����������������ƣ����緺�͡�varargs��autoboxing�ȣ��Ӷ��ṩ�˸��Ӽ���API���ʷ�ʽ����Ҫ������Java 5���ϵĻ����С�
 * 
 * SimpleJdbcInsert �� SimpleJdbcCall -
 * ����������Գ���������ݿ�Ԫ���ݵ������������á�ͨ��ʹ������������б�̣�����Խ����ṩ���ݿ�������ߴ洢���̵������Լ�һ��Map��Ϊ����������Map��key��Ҫ�����ݿ���е��ֶα���һ�¡���������ͨ����SimpleJdbcTemplate���ʹ�á�����������Ҫ������JDK
 * 5���ϣ�ͬʱ���ݿ���Ҫ�ṩ�㹻��Ԫ������Ϣ��
 * 
 * RDBMS �������MappingSqlQuery, SqlUpdate and StoredProcedure -
 * ���ַ�ʽ�������ڳ�ʼ��������ݷ��ʲ�ʱ���������ò����̰߳�ȫ�Ķ��󡣸ö������㶨������Ĳ�ѯ��䣬������ѯ������������Ӧ��Query
 * 
 * 
 * 
 */
public class JdbcTest {

    public static void main(String[] args) {

        new JdbcTest().test();
    }

    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] { "/org/springtest/data_jdbc/spring_jdbc.xml" });

    }

}
