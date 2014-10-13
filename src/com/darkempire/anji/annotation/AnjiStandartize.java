package com.darkempire.anji.annotation;

import java.lang.annotation.*;

/**
 * Методи, поля та класи позначені цією аннотацією мають бути стандартизовані та якось вплетені у схему
 *
 * @author siredvin
 * @since Anji 0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Inherited
public @interface AnjiStandartize {
}
