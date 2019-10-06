package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.validation.FieldMatch;
import dev.phasterinc.tripdat.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
@Data
public class UserRegistrationDto {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;


    @NotEmpty
    private String login;

    @AssertTrue
    private Boolean terms;
}
