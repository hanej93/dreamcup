package com.dreamcup.common.validator;


import com.dreamcup.common.annotation.Enum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (enumValue.toString().equals(value)
                        || (this.annotation.ignoreCase() && enumValue.toString().equalsIgnoreCase(value))) {
                    return true;
                }
            }
        }
        return false;
    }
}