package dev.phasterinc.tripdat.validation;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Name: FieldMatchValidator
 * Purpose: To determine two fields String values are the same.
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    /**
     * Name: initialize
     * Purpose: To initialize the validator in preparation for isValid calls.
     * Synopsis: Sets the member variables firstFieldName & secondFieldName by retrieving
     * them from the Annotation.
     * <p>
     *
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    /**
     * Name: isValid
     * Purpose: To determine if the two fields contain the same String values.
     * Synopsis: Gets the values of the two fields, then compares them to each other.
     * <p>
     *
     * @param value, object to validate.
     * @param context, context in which the constraint is evaluated.
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}

