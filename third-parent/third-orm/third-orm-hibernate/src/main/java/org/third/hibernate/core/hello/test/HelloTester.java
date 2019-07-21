package org.third.hibernate.core.hello.test;

import org.third.hibernate.core.connection.OpenJpaUtil;

public class HelloTester {

	public static void main(String[] args) {

		OpenJpaUtil.getEntityManager();
	}

}
