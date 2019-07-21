package org.third.hibernate2.hello.many_to_one;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

public class MtmTester {

	public static void main(String[] args) throws Exception {

		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Account account1 = new Account();
		account1.accountName = "NTU-M8-419";
		Account account2 = new Account();
		account2.accountName = "NTU-G3-302";

		User u1 = new User();
		u1.userName = ("shenbin");
		u1.account = account1;

		User u2 = new User();
		u2.userName = ("chenyan");
		u2.account = account1;

		User u3 = new User();
		u3.userName = ("zhangsna");
		u3.account = account1;

		Transaction tx = session.beginTransaction();
		session.save(u1);
		session.save(u2);
		session.save(u3);
		tx.commit();

		User user = (User) session.load(User.class, new Integer(1));
		System.out.println(user.userName);
		System.out.println(user.account.accountName);
		session.close();
		sessionFactory.close();

	}

}
