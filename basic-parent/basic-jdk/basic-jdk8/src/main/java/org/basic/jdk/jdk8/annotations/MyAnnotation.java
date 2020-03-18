package org.basic.jdk.jdk8.annotations;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@interface MyAnnotation {

}