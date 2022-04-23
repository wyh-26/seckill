package com.example.seckilldemo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;




@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)

@Documented
@Constraint(
        validatedBy = {}
)
public @interface IsMobile {

    boolean required() default true;
    String message() default "{javax.validation.constraints.NotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}