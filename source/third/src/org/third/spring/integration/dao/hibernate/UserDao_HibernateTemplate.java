
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.third.testdata.user.domain.UserDM;

public class UserDao_HibernateTemplate implements UserDao_Hibernate {

    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setSessionFactory(org.hibernate.SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List<UserDM> getAllUsers() {
        List<UserDM> userList = (List<UserDM>) this.hibernateTemplate.findByNamedQuery(HQL_getAllUsers);
        return userList;
    }

    public void testTransaction(UserDM user) {

    }

    @SuppressWarnings("unchecked")
    public List<UserDM> getUsesByName(final String name) {
        return (List<UserDM>) this.hibernateTemplate.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) {

                Criteria criteria = session.createCriteria(UserDM.class);
                criteria.setLockMode(LockMode.READ);
                criteria.add(Restrictions.like("name", name + "%"));
                criteria.addOrder(Order.asc("id"));

                criteria.setMaxResults(6);
                return criteria.list();
            }
        });

        // return this.hibernateTemplate.find("from test.Product product where
        // product.category=?", name);
    }

    public UserDM getUserById(int id) {
        List<UserDM> userList = (List<UserDM>) this.hibernateTemplate.findByNamedQueryAndNamedParam(HQL_getUserById, "id", id);
        return userList.get(0);
    }

}
