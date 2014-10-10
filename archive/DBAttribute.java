package com.darkempire.anji.database;

import com.darkempire.anji.annotation.AnjiUnknow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create in 12:43
 * Created by siredvin on 08.04.14.
 */
@AnjiUnknow
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBAttribute {
    public String name();

    public String type();

    public String id();

    public boolean isNullable() default false;

    public boolean isPrimaryKey() default false;

    public boolean isIndex() default false;
}
