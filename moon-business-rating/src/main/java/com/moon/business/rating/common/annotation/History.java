package com.moon.business.rating.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface History {
    /**
     * name
     */
    String name() default "";

    String type() default "text";
    String readConverterExp() default "";
}
