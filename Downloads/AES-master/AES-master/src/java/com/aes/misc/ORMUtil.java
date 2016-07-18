
package com.aes.misc;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 13, 2015 2:42:46 PM
 * 
 */

public class ORMUtil {
   public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
                return null;
        }

        if (entity instanceof HibernateProxy) {
                Hibernate.initialize(entity);
                entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }
}
