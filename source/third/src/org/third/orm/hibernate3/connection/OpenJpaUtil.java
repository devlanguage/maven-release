/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.core.connection.OpenJpaUtil.java is created on 2008-3-3
 */
package org.third.orm.hibernate3.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContextType;

import org.third.orm.hibernate3.common.bean.OpenJpaConstants;

/**
 * 
 */
public class OpenJpaUtil {

    private static EntityManagerFactory OPENJPA_ENTITY_MANAGER_FACTORY = null;
    static {
        // get an EntityManagerFactory using the Persistence class; typically
        // the factory is cached for easy repeated use
        // EntityManagerFactory factory = Persistence.createEntityManagerFactory(null);

        // Create a new EntityManagerFactory using the System properties.
        // The "hellojpa" name will be used to configure based on the
        // corresponding name in the META-INF/persistence.xml file
        // EntityManagerFactory factory = Persistence
        // .createEntityManagerFactory(OpenJpaConstants.OPENJPA_ENTITY_MANAGER_FACTORY_NAME);
        try{
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                OpenJpaConstants.OPENJPA_ENTITY_MANAGER_FACTORY_NAME, System.getProperties());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public final static EntityManager getEntityManager() {

        // get an EntityManager from the factory
        return OPENJPA_ENTITY_MANAGER_FACTORY.createEntityManager();
    }

}
