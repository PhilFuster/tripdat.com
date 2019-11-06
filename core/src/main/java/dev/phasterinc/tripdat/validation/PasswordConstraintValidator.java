package dev.phasterinc.tripdat.validation;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Name: PasswordConstraintValidator
 * Purpose: Validates a password passes security constraints.
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Name: initialize
     * Purpose: To initialize the validator in preparation for isValid calls.
     * Synopsis: No preparation needed, but had to complete the implementation contract.
     * <p>
     */
    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * Name: isValid
     * Purpose: To determine if password passed follows certain security guidelines.
     * Synopsis: Creates a PasswordValidator object with the constrains that need to be
     * enforced.
     * <p>
     *
     * @param password, password String to validate.
     * @param context,  context in which the constraint is evaluated.
     * @return {@code false} if {@code password} does not pass the constraint
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(
                String.join("\n",getInvalidPasswordStrings()))
                .addConstraintViolation();
        return false;
    }

    /**
     * Name: getInvalidPasswordStrings
     * Purpose: To generate the list of Password Requirements to display to the user.
     * Synopsis: Builds a list of Strings & adds the current required constraints for a password.
     * <p>
     *
     * @return {@code invalidPasswordString} List of String representations of Password Requirements.
     */
    public List<String> getInvalidPasswordStrings() {
        List<String> invalidPasswordStrings = new ArrayList<>();
        invalidPasswordStrings.add("Length: 8 - 30 Characters");
        invalidPasswordStrings.add("Must contain at least one Upper Case character");
        invalidPasswordStrings.add("Must contain at least one Lower Case character");
        invalidPasswordStrings.add("Must contain at least one Number");
        invalidPasswordStrings.add("Must contain at least one Special Character:"
                + " !" + '\"' + "#$%&" + "\'" + "()*+,-./:;<=>?@[\\]^_`{|}~");
        invalidPasswordStrings.add("No whitespaces allowed");
        return invalidPasswordStrings;
    }
}
