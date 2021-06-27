package com.tc.validator;

import com.tc.common.annotation.NameInclude;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameIncludeValidator implements ConstraintValidator<NameInclude, String> {

    private String val;

    @Override
    public void initialize(NameInclude constraintAnnotation) {
        val = constraintAnnotation.value();
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (!StringUtils.hasLength(value)) {
            return true;
        }
        return !StringUtils.hasLength(val) || !val.equals(value);
    }
}
