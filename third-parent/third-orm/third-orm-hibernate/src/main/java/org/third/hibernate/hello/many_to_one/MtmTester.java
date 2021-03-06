package org.third.hibernate.hello.many_to_one;

import org.basic.db.domain.AccountDM;
import org.basic.db.domain.UserDM;
import org.hsqldb.rights.User;

public class MtmTester {
	public static void main(String[] args) {

		org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration()
				.configure(Thread.class.getResource("/hibernate.jdbc.xml"));
		config.addAnnotatedClass(AccountDM.class);
		config.addAnnotatedClass(UserDM.class);

		org.hibernate.SessionFactory sessionFactory = config.buildSessionFactory();
		org.hibernate.Session session = sessionFactory.openSession();
		AccountDM account1 = new AccountDM();
		account1.setId(3);
		account1.setName("NTU-M8-419");

		UserDM u1 = new UserDM();
		u1.setId(1);
		u1.setName("shenbin");
		u1.setAccount(account1);

		UserDM u2 = new UserDM();
		u2.setId(3);
		u2.setAccount(account1);
		u2.setName("chenyan");

		org.hibernate.Transaction tx = session.beginTransaction();
		session.saveOrUpdate(account1);
		session.saveOrUpdate(u1);
		session.saveOrUpdate(u2);

		UserDM user = (UserDM) session.load(UserDM.class, new Integer(1));
		System.out.println(user.getName());
		System.out.println(user.getAccount().getName());
		user.setHeight(120);
		tx.commit();

		session.close();
		sessionFactory.close();

	}

}
