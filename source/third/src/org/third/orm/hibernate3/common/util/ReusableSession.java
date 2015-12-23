/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.common.util.ReusableSession.java is created on 2008-4-7
 */
package org.third.orm.hibernate3.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 
 */
public class ReusableSession implements Filter {

    protected static ThreadLocal hibernateHolder = new ThreadLocal();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        hibernateHolder.set(HibernateUtil.currentSession());
        try {
            // ……
            chain.doFilter(request, response);
            // ……
        } finally {
            Session sess = (Session) hibernateHolder.get();
            if (sess != null) {
                hibernateHolder.set(null);
                try {
                    sess.close();
                } catch (HibernateException ex) {
                    throw new ServletException(ex);
                }
            }
        }
    }

    public void destroy() {

    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}
