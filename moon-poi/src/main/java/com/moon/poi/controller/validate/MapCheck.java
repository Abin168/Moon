package com.moon.poi.controller.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MapValidator.class)
public @interface MapCheck {
    String message() default "模板填充数据不能为空";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}