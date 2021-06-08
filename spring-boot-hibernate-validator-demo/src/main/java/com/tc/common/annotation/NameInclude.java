package com.tc.common.annotation;

import com.tc.validator.NameIncludeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {NameIncludeValidator.class})
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameInclude {

    String message();

    Class<?>[] groups() default {};

    String value();

    Class<? extends Payload>[] payload() default {};
}
