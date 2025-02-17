package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Date;

public class MinimumDateValidator implements ConstraintValidator<MinimumDate, Date> {
    private Date minimumDate;

    @Override
    public void initialize(MinimumDate constraintAnnotation) {
        minimumDate = Date.valueOf(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && !value.before(minimumDate);
    }
}
