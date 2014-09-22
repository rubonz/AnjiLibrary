package com.darkempire.anjifx.annotation;

import java.lang.annotation.*;

/**
 * Create in 14:35
 * Created by siredvin on 23.12.13.
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface UseSunLibrary {
}
