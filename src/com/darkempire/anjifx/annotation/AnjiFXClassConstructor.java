package com.darkempire.anjifx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 01.11.13
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface AnjiFXClassConstructor {
    public String[] constructorParams() default {};

    public boolean isEmptyExist() default true;
}
