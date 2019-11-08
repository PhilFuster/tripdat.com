package dev.phasterinc.tripdat.model.dto;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.validation.FieldMatch;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Class: ChangeEmailDto
 * Purpose: Data Transfer Object used for changing a user's email
 */
@FieldMatch.List({
        @FieldMatch(first = "newEmail", second = "confirmNewEmail", message = "The email fields must match")
})
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChangeEmailDto {

    // == fields ==

    @NotEmpty
    private String currentEmail;

    @Email
    private String newEmail;

    @Email
    private String confirmNewEmail;


    /**
     * Name: isEmpty
     * Purpose: Verify if the object is empty
     * Synopsis: Determines if all the dto's attributes are empty.
     */
    public boolean isEmpty() {
        boolean allEmpty = true;
        if (this.currentEmail != null && !currentEmail.isEmpty()) allEmpty = false;
        if (this.newEmail != null && !newEmail.isEmpty()) allEmpty = false;
        if (this.confirmNewEmail != null && !confirmNewEmail.isEmpty()) allEmpty = false;
        return allEmpty;
    }
}
