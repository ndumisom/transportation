package za.com.cocamzansi.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target(value = {ElementType.PARAMETER, ElementType.TYPE_USE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ISO8601YearPart.ISO8601YearPartValidator.class)
public @interface ISO8601YearPart {
    String message() default "{custom.validation.constraints.ISO8601YearPart.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ISO8601YearPartValidator implements ConstraintValidator<ISO8601YearPart, Integer> {
        @Override
        public void initialize(final ISO8601YearPart arg0) {
            // nothing to initialize.
        }

        @Override
        public boolean isValid(final Integer year, final ConstraintValidatorContext ctx) {
            return year == null || year.toString().matches("^\\d{4}$");
        }
    }
}
