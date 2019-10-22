package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.validation.FieldMatch;
import dev.phasterinc.tripdat.validation.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotEmpty;
/************************************************************
 * currentPassword:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Class: ChangePasswordDto
 * Purpose: Data Transfer Object used for changing a user's password
 */
@FieldMatch.List({
        @FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match"),
})
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChangePasswordDto {

    // == fields ==

    @NotEmpty
    private String currentPassword;

    @ValidPassword
    private String newPassword;

    @NotEmpty
    private String confirmNewPassword;

    /**
     * Name: isEmpty
     * Purpose: Verify if the object is empty
     * Synopsis: Determines if all the dto's attributes are empty.
     */
    public boolean isEmpty() {
        boolean allEmpty = true;
        if (this.currentPassword != null && !currentPassword.isEmpty()) allEmpty = false;
        if (this.newPassword != null && !newPassword.isEmpty()) allEmpty = false;
        if (this.confirmNewPassword != null && !confirmNewPassword.isEmpty()) allEmpty = false;
        return allEmpty;
    }
}
