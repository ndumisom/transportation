package za.com.cocamzansi.util.validator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneNotNull.AteleastOneNotNullValidator.class)
@Repeatable(AtLeastOneNotNulls.class)
@Documented
public @interface AtLeastOneNotNull {
    String message() default "{custom.validation.constraints.AtLeastOneNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fieldNames();

    class AteleastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {
        private String[] fieldNames;

        @Override
        public void initialize(final AtLeastOneNotNull annotation) {
            fieldNames = annotation.fieldNames();
        }

        @Override
        public boolean isValid(final Object obj, final ConstraintValidatorContext ctx) {
            if (obj == null) {
                return true;
            }

            Object value;
            for (String field : fieldNames) {
                try {
                    value = BeanUtils.getProperty(obj, field);

                    if (value != null) {
                        return true;
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    return false;
                }
            }

            return false;
        }
        
    }
}
