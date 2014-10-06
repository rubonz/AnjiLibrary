package com.darkempire.anji.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Методи, поля та класи позначені цією аннотацією можуть бути змінені.
 *
 * @author siredvin
 * @since Anji 0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface AnjiExperimental {
}
