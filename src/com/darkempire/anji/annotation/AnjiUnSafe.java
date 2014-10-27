package com.darkempire.anji.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Виникає тоді, коли щось, що ним помічене працює досить непрозоро та складно.
 *
 * @author siredvin
 * @since Anji v0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface AnjiUnSafe {
}
