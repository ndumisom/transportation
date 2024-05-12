package za.com.cocamzansi.util.validator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullWhenPrimaryHasValue.NotNullWhenPrimaryHasValueValidator.class)
@Repeatable(NotNullWhenPrimaryHasValues.class)
@Documented
/*
 * This annotation validates the primary fields value against expected value,
 * if both are equal then it will validates the secondary field should not null*/
public @interface NotNullWhenPrimaryHasValue {

	String message() default "{custom.validation.constraints.NotNullWhenPrimaryHasValue.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String primaryField();

	String secondaryField();

	String expectedValue();

	class NotNullWhenPrimaryHasValueValidator implements ConstraintValidator<NotNullWhenPrimaryHasValue, Object> {

		private String primaryField;
		private String secondaryField;
		private String expectedValue;

		@Override
		public void initialize(NotNullWhenPrimaryHasValue constraintAnnotation) {

			this.primaryField = constraintAnnotation.primaryField();
			this.secondaryField = constraintAnnotation.secondaryField();
			this.expectedValue = constraintAnnotation.expectedValue();

		}

		@Override
		public boolean isValid(Object obj, ConstraintValidatorContext context) {

			try {
				if (obj == null || BeanUtils.getProperty(obj, primaryField) == null) {
					return true;
				}

				final String primaryFieldValue = BeanUtils.getProperty(obj, primaryField);
				final String secondaryFieldValue = BeanUtils.getProperty(obj, secondaryField);

				if (expectedValue.equals(primaryFieldValue) && secondaryFieldValue == null) {
					return false;
				}

			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				return false;
			}
			return true;
		}

	}

}
