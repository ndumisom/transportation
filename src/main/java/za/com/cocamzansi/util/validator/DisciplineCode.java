package za.com.cocamzansi.util.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.PARAMETER, ElementType.TYPE_USE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DisciplineCode.DisciplineCodeValidator.class)
public @interface DisciplineCode {
    String message() default "{custom.validation.constraints.DisciplineCode.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DisciplineCodeValidator implements ConstraintValidator<DisciplineCode, String> {
        @Override
        public void initialize(final DisciplineCode arg0) {
            // nothing to initialize.
        }

        @Override
        public boolean isValid(final String disciplineCode, final ConstraintValidatorContext ctx) {
            return disciplineCode == null || disciplineCode.matches("^\\d{3}$");
        }
    }
}
