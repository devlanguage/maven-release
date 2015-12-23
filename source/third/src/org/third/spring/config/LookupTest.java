/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 9:53:47 AM Apr 9, 2014
 *
 *****************************************************************************
 */
package org.third.spring.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.LookupOverride;
import org.springframework.beans.factory.support.MethodOverride;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created on Apr 9, 2014, 9:53:47 AM
 */
public class LookupTest {
    private static class CglibSubclassCreator {

        private static final Log logger = LogFactory.getLog(CglibSubclassCreator.class);

        private final RootBeanDefinition beanDefinition;

        private final BeanFactory owner;

        public CglibSubclassCreator(RootBeanDefinition beanDefinition, BeanFactory owner) {
            this.beanDefinition = beanDefinition;
            this.owner = owner;
        }

        /**
         * Create a new instance of a dynamically generated subclasses implementing the required lookups.
         * 
         * @param ctor
         *            constructor to use. If this is <code>null</code>, use the no-arg constructor (no parameterization,
         *            or Setter Injection)
         * @param args
         *            arguments to use for the constructor. Ignored if the ctor parameter is <code>null</code>.
         * @return new instance of the dynamically generated class
         */
        public Object instantiate(Constructor ctor, Object[] args) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(this.beanDefinition.getBeanClass());
            enhancer.setCallbackFilter(new CallbackFilterImpl());
            enhancer.setCallbacks(new Callback[] { NoOp.INSTANCE, new LookupOverrideMethodInterceptor(),
                    new ReplaceOverrideMethodInterceptor() });

            return (ctor == null) ? enhancer.create() : enhancer.create(ctor.getParameterTypes(), args);
        }

        /**
         * Class providing hashCode and equals methods required by CGLIB to ensure that CGLIB doesn't generate a
         * distinct class per bean. Identity is based on class and bean definition.
         */
        private class CglibIdentitySupport {

            /**
             * Exposed for equals method to allow access to enclosing class field
             */
            protected RootBeanDefinition getBeanDefinition() {
                return beanDefinition;
            }

            public boolean equals(Object other) {
                return (other.getClass().equals(getClass()) && ((CglibIdentitySupport) other).getBeanDefinition().equals(beanDefinition));
            }

            public int hashCode() {
                return beanDefinition.hashCode();
            }
        }

        /**
         * CGLIB MethodInterceptor to override methods, replacing them with an implementation that returns a bean looked
         * up in the container.
         */
        private class LookupOverrideMethodInterceptor extends CglibIdentitySupport implements MethodInterceptor {

            public Object intercept(Object obj, Method method, Object[] args, MethodProxy mp) throws Throwable {
                // Cast is safe, as CallbackFilter filters are used selectively.
                LookupOverride lo = (LookupOverride) beanDefinition.getMethodOverrides().getOverride(method);
                return owner.getBean(lo.getBeanName());
            }
        }

        /**
         * CGLIB MethodInterceptor to override methods, replacing them with a call to a generic MethodReplacer.
         */
        private class ReplaceOverrideMethodInterceptor extends CglibIdentitySupport implements MethodInterceptor {

            public Object intercept(Object obj, Method method, Object[] args, MethodProxy mp) throws Throwable {
                ReplaceOverride ro = (ReplaceOverride) beanDefinition.getMethodOverrides().getOverride(method);
                // TODO could cache if a singleton for minor performance optimization
                MethodReplacer mr = (MethodReplacer) owner.getBean(ro.getMethodReplacerBeanName());
                return mr.reimplement(obj, method, args);
            }
        }

        /**
         * CGLIB object to filter method interception behavior.
         */
        private class CallbackFilterImpl extends CglibIdentitySupport implements CallbackFilter {

            public int accept(Method method) {
                MethodOverride methodOverride = beanDefinition.getMethodOverrides().getOverride(method);
                if (logger.isTraceEnabled()) {
                    logger.trace("Override for '" + method.getName() + "' is [" + methodOverride + "]");
                }
                if (methodOverride == null) {
                    return PASSTHROUGH;
                } else if (methodOverride instanceof LookupOverride) {
                    return LOOKUP_OVERRIDE;
                } else if (methodOverride instanceof ReplaceOverride) {
                    return METHOD_REPLACER;
                }
                throw new UnsupportedOperationException("Unexpected MethodOverride subclass: " + methodOverride.getClass().getName());
            }
        }

        public void instantiate(String[] args) {
            Constructor<CommandManager> ctor = null;
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(CommandManager.class);
            enhancer.setCallbackFilter(new CallbackFilterImpl());
            enhancer.setCallbacks(new Callback[] { NoOp.INSTANCE, new LookupOverrideMethodInterceptor(),
                    new ReplaceOverrideMethodInterceptor() });

            Object cm = (ctor == null) ? enhancer.create() : enhancer.create(ctor.getParameterTypes(), args);
        }
    }

    /**
     * Class providing hashCode and equals methods required by CGLIB to ensure that CGLIB doesn't generate a distinct
     * class per bean. Identity is based on class and bean definition.
     */
    private class CglibIdentitySupport {

        /**
         * Exposed for equals method to allow access to enclosing class field
         */
        protected RootBeanDefinition getBeanDefinition() {
            return beanDefinition;
        }

        public boolean equals(Object other) {
            return (other.getClass().equals(getClass()) && ((CglibIdentitySupport) other).getBeanDefinition().equals(beanDefinition));
        }

        public int hashCode() {
            return beanDefinition.hashCode();
        }
    }

    /**
     * CGLIB MethodInterceptor to override methods, replacing them with an implementation that returns a bean looked up
     * in the container.
     */
    private class LookupOverrideMethodInterceptor extends CglibIdentitySupport implements MethodInterceptor {

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy mp) throws Throwable {
            // Cast is safe, as CallbackFilter filters are used selectively.
            LookupOverride lo = (LookupOverride) beanDefinition.getMethodOverrides().getOverride(method);
            return owner.getBean(lo.getBeanName());
        }
    }

    /**
     * CGLIB MethodInterceptor to override methods, replacing them with a call to a generic MethodReplacer.
     */
    private class ReplaceOverrideMethodInterceptor extends CglibIdentitySupport implements MethodInterceptor {

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy mp) throws Throwable {
            ReplaceOverride ro = (ReplaceOverride) beanDefinition.getMethodOverrides().getOverride(method);
            // TODO could cache if a singleton for minor performance optimization
            MethodReplacer mr = (MethodReplacer) owner.getBean(ro.getMethodReplacerBeanName());
            return mr.reimplement(obj, method, args);
        }
    }

    private RootBeanDefinition beanDefinition;
    private BeanFactory owner;

    /**
     * Index in the CGLIB callback array for passthrough behavior, in which case the subclass won't override the
     * original class.
     */
    private static final int PASSTHROUGH = 0;

    /**
     * Index in the CGLIB callback array for a method that should be overridden to provide method lookup.
     */
    private static final int LOOKUP_OVERRIDE = 1;

    /**
     * Index in the CGLIB callback array for a method that should be overridden using generic Methodreplacer
     * functionality.
     */
    private static final int METHOD_REPLACER = 2;
public static void main(String[] args) {
        new CglibSubclassCreator(null,null).instantiate(args);
}
}
