package softuni.exam.util.impl;

import softuni.exam.util.ValidationUtil;

import javax.validation.Validation;

public class ValidationUtilImpl implements ValidationUtil {
    @Override
    public <E> boolean isValid(E entity) {
        return Validation.buildDefaultValidatorFactory().getValidator().validate(E );
    }
}
