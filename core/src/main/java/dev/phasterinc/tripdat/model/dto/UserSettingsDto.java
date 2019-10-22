package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.validation.FieldMatch;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: UserSettingsDto
 * Purpose: Dto for User settings
 */

@FieldMatch.List({
        @FieldMatch(first = "newPassword", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserSettingsDto {

    @NotEmpty
    private String login;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String displayName;

    private String homeAirport;

    private String city;

    /**
     * Name: buildDto
     * Purpose: To build a UserSettings Data Transfer Object
     * Synopsis: Builds a UserSettings DTO from the TripdatUser entity.
     * <p>
     *
     * @param user TripdatUser the DTO represents
     */
    public static UserSettingsDto buildDto(TripdatUser user) {
        UserSettingsDto dto = UserSettingsDto.builder()
                .login(user.getUserLogin())
                .email(user.getUserEmail())
                .displayName(user.getUserDisplayName())
                .firstName(user.getUserFirstName())
                .lastName(user.getUserLastName())
                .city(user.getUserCity())
                .homeAirport(user.getUserHomeAirport())
                .build();
        return dto;
    }

    /**
     * Name: buildEntity
     * Purpose: To build a TripdatUser entity
     * Synopsis: Builds a TripdatUser entity from a UserSettings Data Transfer Object.
     * <p>
     *
     * @param settingsDto The Settings DTO the User will be populated with.
     */
    public static TripdatUser buildEntity(UserSettingsDto settingsDto) {
        return TripdatUser.builder()
                .userLogin(settingsDto.getLogin())
                .userEmail(settingsDto.getEmail())
                .userDisplayName(settingsDto.getDisplayName())
                .userFirstName(settingsDto.getFirstName())
                .userLastName(settingsDto.getLastName())
                .userCity(settingsDto.getCity())
                .userHomeAirport(settingsDto.getHomeAirport())
                .build();
    }

    /**
     * Name: updateEntity
     * Purpose: To update a TripdatUser entity
     * Synopsis: Updates a TripdatUser's information with the information the Data Transfer Object
     * <p>
     *
     * @param tripdatUser     TripdatUser entity that will be updated
     * @param userSettingsDto The SettingsDto the User will be updated with.
     */
    public static void updateEntity(TripdatUser tripdatUser, UserSettingsDto userSettingsDto) {
        tripdatUser.setUserLogin(userSettingsDto.login);
        tripdatUser.setUserEmail(userSettingsDto.email);
        tripdatUser.setUserDisplayName(userSettingsDto.displayName);
        tripdatUser.setUserFirstName(userSettingsDto.firstName);
        tripdatUser.setUserLastName(userSettingsDto.lastName);
        tripdatUser.setUserCity(userSettingsDto.city);
        tripdatUser.setUserHomeAirport(userSettingsDto.homeAirport);
    }
}
