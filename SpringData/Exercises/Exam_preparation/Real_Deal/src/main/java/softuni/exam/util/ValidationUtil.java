package softuni.exam.util;

import javax.validation.ConstraintViolation;

public interface ValidationUtil {

    <D> boolean isValid(D dto);
}
