package com.darkempire.anji.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Методи, поля та класи позначені цією аннотацією мають застарілу та непрозору логіку і було б непогано їх
 * переписати та змустити працювати правильно.
 *
 * @author siredvin
 * @since Anji 0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface AnjiRewrite {
}
