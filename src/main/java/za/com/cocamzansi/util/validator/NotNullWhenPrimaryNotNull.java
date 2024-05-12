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
@Constraint(validatedBy = NotNullWhenPrimaryNotNull.NotNullWhenPrimaryNotNullValidator.class)
@Repeatable(NotNullWhenPrimaryNotNulls.class)
@Documented
public @interface NotNullWhenPrimaryNotNull {
    String message() default "{custom.validation.constraints.NotNullWhenPrimaryNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String primaryField();

    String[] secondaryFields();

    class NotNullWhenPrimaryNotNullValidator implements ConstraintValidator<NotNullWhenPrimaryNotNull, Object> {
        private String primaryField;
        private String[] secondaryFields;

        @Override
        public void initialize(final NotNullWhenPrimaryNotNull annotation) {
            primaryField = annotation.primaryField();
            secondaryFields = annotation.secondaryFields();
        }

        @Override
        public boolean isValid(final Object obj, final ConstraintValidatorContext ctx) {
            try {
                if (obj == null || BeanUtils.getProperty(obj, primaryField) == null) {
                    return true;
                }

                Object value;
                for (String field : secondaryFields) {
                    value = BeanUtils.getProperty(obj, field);

                    if (value == null) {
                        return false;
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                return false;
            }

            return true;
        }
    }
}
