/**
 * The file org.basic.annotation.Actor.java was created by yongjie.gong on 2008-12-9
 */
package org.basic.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author feiye
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
@Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE })
public @interface Actor {

    public String id();

    public String name();

}
