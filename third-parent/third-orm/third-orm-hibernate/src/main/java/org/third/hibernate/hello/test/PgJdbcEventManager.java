package org.third.hibernate.hello.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.basic.db.domain.ErrorEventDM;
import org.basic.db.domain.EventDM;
import org.basic.db.domain.PersonDM;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;

public class PgJdbcEventManager {

	static Session session = null;

	public static void main(String[] args) {
		org.hibernate.cfg.Configuration hibernateCfg = new Configuration();
		hibernateCfg.configure(PgJdbcEventManager.class.getClassLoader().getResource("hibernate.jdbc.xml"));
		org.hibernate.SessionFactory sessionFactory = hibernateCfg.buildSessionFactory();
		PgJdbcEventManager mgr = new PgJdbcEventManager();
		session = sessionFactory.openSession();
		session.beginTransaction();

		 mgr.testInsert();

		// mgr.testUpdate();
//        mgr.testQuery();
//		mgr.testNativeSQLQuery();
		//
		// mgr.testStatistics();

		session.getTransaction().commit();
		// session.close();
		session.close();
	}

	private void testQuery() {

		PersonDM person = null;
		Query query = null;
		// related Instance must existed, otherwise happened to HibernateException
		// person = (Person) session.load(Person.class, Long.valueOf("100"));
		// seems like load(), however if instance not exist, just return null;
		// person = (Person) session.get(Person.class, Long.valueOf("100"));
		// System.out.println(person);

		query = session.createQuery("from HiPerson p");
		for (Iterator iterator = query.iterate(); iterator.hasNext();) {
			PersonDM p = (PersonDM) iterator.next();
			System.err.println(p.getEmailAddresses());
			System.err.println(p.getMagazines());
			System.err.println(p.getEmblements());
			System.err.println(p.getRoles());
			System.err.println(p.getEvents());
			// if (p.getId() == 100) {
			// p.addRole("Oracle Manager");
			// p.addRole("Oracle Developer");
			// p.addRole("Data Warehouse Developer");
			// } else if (p.getId() == 200) {
			// p.addRole("HR");
			//
			// } else if (p.getId() == 300) {
			// p.addRole("Finance Manager");
			// p.addRole("Accountant");
			// p.addRole("FEO");
			// p.addRole("Treasurer");
			// }
			// for (String r : p.getRoles()) {
			// System.out.println(r);
			// }
		}

		query = session.createQuery("select magazine from Person p, Magazine magazine");
		for (Object p : query.list()) {
			System.out.println(p);
		}

		query = session.createQuery("select p.id, p.age, p.userName.firstName, p.userName.lastName from Person p");
		for (Object p : query.list()) {
			Object[] pFields = (Object[]) p;
			System.out.println("id=" + pFields[0] + ", firstName=" + pFields[1]);
		}
		// 对于query.list()总是通过�?��sql语句获取�?��记录,然后将其读出，填入pojo返回;
		// 但是query.iterate()，则是首先�?过一条Select SQL 获取�?��符合查询条件的记录的
		// id，再对这个id 集合进行循环操作，�?过单独的Select SQL 取出每个id �?��应的�? // 录，之后填入POJO中返回�?
		//
		// 也就是说，对于list 操作，需要一条SQL 完成。�?对于iterate 操作，需要n+1
		// 条SQL。，list方法将不会从Cache中读取数据�?iterator却会�?
		for (Iterator iterator = query.iterate(); iterator.hasNext();) {
			Object[] pFields = (Object[]) iterator.next();
			System.out.println("id=" + pFields[0] + ", firstName=" + pFields[1]);
		}
		// query = session
		// .createQuery("select age from Person person join person.magazines magazines
		// group by
		// person.age");

		// Event event = new Event();
		//
		// event.setTitle(title);
		// event.setDate(theDate);
		//
		// Person person = new Person();
		//
		// person.addToEvent(event);
		// session.save(person);

		Query getErrorEventsQuery = session.getNamedQuery("getAllErrorEvents");
		ScrollableResults rs = getErrorEventsQuery.scroll();
		final int PAGE_SIZE = 10;
		List<ErrorEventDM> errorEvents = new ArrayList<ErrorEventDM>();
		while (rs.next()) {
			System.out.println(rs.getRowNumber());
			// rs.scroll(rs.getRowNumber());//0--1--2--...
			errorEvents.add((ErrorEventDM) rs.get(0));
		}
		System.out.println(rs.getRowNumber());// -1
		System.out.println(errorEvents);
	}

	void testNativeSQLQuery() {

		// select {person1.*} from h3_person person1 where {person1.person_id}>10
		List persons = session.createSQLQuery("SELECT person1.* FROM H3_PERSON person1 WHERE ROWNUM<10").list();
		persons = session.getNamedQuery("getAllWorkersBySQL").list();

		List events = session.createSQLQuery("select event1.event_id, event1.title from h3_event event1").list();
	}

	public void testUpdate() {

		Query update = session.createQuery("Update Magazine set isbn = :isbn where person.id=100").setString("isbn",
				"059-9716-649");
		System.out.println("updateCount: " + update.executeUpdate());
	}

	private void testInsert() {

		// Query insert = session.createQuery("insert into Event ");
		EventDM event = new EventDM();

		event.setTitle("create_zhangsan");
		// Assign the identifier and persist it
		// If generator == assiged, using the current identifier and persist it
		session.save(event);
		// Persist the transient class
		session.persist(event);
		session.saveOrUpdate(event);

	}

	private void testStatistics() {

		Statistics stats = session.getSessionFactory().getStatistics();

		double queryCacheHitCount = stats.getQueryCacheHitCount();
		double queryCacheMissCount = stats.getQueryCacheMissCount();
		double queryCacheHitRatio = queryCacheHitCount / (queryCacheHitCount + queryCacheMissCount);

		System.out.println("Query Hit ratio:" + queryCacheHitRatio);

		EntityStatistics entityStats = stats.getEntityStatistics(PersonDM.class.getName());
		long changes = entityStats.getInsertCount() + entityStats.getUpdateCount() + entityStats.getDeleteCount();
		System.out.println(PersonDM.class.getName() + " changed " + changes + "times");
	}

}