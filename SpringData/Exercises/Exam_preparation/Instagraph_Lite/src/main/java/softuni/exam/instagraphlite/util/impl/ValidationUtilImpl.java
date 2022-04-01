package softuni.exam.instagraphlite.util.impl;

import softuni.exam.instagraphlite.util.ValidationUtil;

import javax.validation.Validation;

public class ValidationUtilImpl implements ValidationUtil {
    @Override
    public <D> boolean isValid(D dto) {
        return Validation
                .buildDefaultValidatorFactory()
                .getValidator()
                .validate( dto )
                .isEmpty();
    }
}
