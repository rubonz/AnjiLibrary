package com.darkempire.anji.database;

import com.darkempire.anji.annotation.AnjiUnknow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create in 12:44
 * Created by siredvin on 08.04.14.
 */
@AnjiUnknow
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBForeignKey {
    public String value();
}
