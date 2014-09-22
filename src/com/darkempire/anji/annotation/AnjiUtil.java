package com.darkempire.anji.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анотація, яка позначає допоміжні Util класи.
 * Це класи, які містять лише public static методи
 *
 * @author siredvin
 * @since Anji 0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
public @interface AnjiUtil {
}
