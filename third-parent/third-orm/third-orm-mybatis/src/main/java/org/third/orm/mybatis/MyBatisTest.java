package org.third.orm.mybatis;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.third.common.user.domain.AddressDM;
import org.third.common.user.domain.UserDM;

/**
 * <pre>
 *
 * </pre>
 */
public class MyBatisTest {
    private static org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MyBatisTest.class);
    static {
        try {
            // URL configFile = MyBatisTest.class.getResource("MyBatis_Configuration.xml");
            // reader = org.apache.ibatis.io.Resources.getResourceAsReader(configFile.toString());
            // sqlSessionFactory = new org.apache.ibatis.session.SqlSessionFactoryBuilder().build(reader);
            sqlSessionFactory = new org.apache.ibatis.session.SqlSessionFactoryBuilder().build(MyBatisTest.class
                    .getResourceAsStream("MyBatis_Configuration.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static org.apache.ibatis.session.SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }

    public void testDynamicSQL() {
        org.apache.ibatis.session.SqlSession session = sqlSessionFactory.openSession();
        String userNamespace = "org.third.testdata.user.domain.UserMapper";
        try {
            
            UserDM user = (UserDM) session.selectOne(userNamespace + ".selectUserByID", 1);
            logger.info(user.toString());

            Consumer<Object> displayResult = (x) -> {
                logger.info(String.valueOf(x));
            };

            UserDM userConditino = new UserDM();
            userConditino.setId(10);
            List<AddressDM> addresss = session.selectList(userNamespace + ".listUserByAddressID", userConditino);
            addresss.forEach(displayResult);

            // SELECT * FROM td_address WHERE id IN ( ? , ? )
            List<AddressDM> addresss1 = session.selectList(userNamespace + ".listAddressByIdArray", new int[] { 1, 10 });
            addresss1.forEach(displayResult);

            // SELECT * FROM td_address WHERE id IN ( ? , ? )
            List<Integer> addressIdList = new ArrayList<Integer>();
            addressIdList.add(1);
            addressIdList.add(2);
            List<AddressDM> addresss2 = session.selectList(userNamespace + ".listAddressByIdList", addressIdList);
            addresss2.forEach(displayResult);

            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", "value");
            session.update(userNamespace + ".updateAddress", map);
            session.selectList(userNamespace + ".listAddress").forEach(displayResult);

        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {

    }
}
