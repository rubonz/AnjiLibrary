package com.darkempire.anji.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Методи, поля та класи позначені цією аннотацією взагалі не зрозуміло навіщо комусь потрібні.
 * Швидше за все, вони покинуті і вже не будуть підтримуватися
 *
 * @author siredvin
 * @since Anji v0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface AnjiUnknow {
}
