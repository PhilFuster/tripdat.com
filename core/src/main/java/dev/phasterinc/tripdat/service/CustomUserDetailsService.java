package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.dto.ChangeEmailDto;
import dev.phasterinc.tripdat.model.dto.ChangePasswordDto;
import dev.phasterinc.tripdat.model.dto.UserRegistrationDto;
import dev.phasterinc.tripdat.model.dto.UserSettingsDto;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Name: CustomUserDetailsService
 * Purpose: Contains functionality for TripdatUser maintenance.
 */

public interface CustomUserDetailsService extends UserDetailsService {
    TripdatUser findByEmail(String email);

    TripdatUser createUser(UserRegistrationDto registrationDto);

    TripdatUser updateUserInformation(UserSettingsDto settingsDto);

    TripdatUser findByLogin(String login);

    Boolean validatePassword(String password);

    void changeUserPassword(ChangePasswordDto passwordDto);

    void changeUserEmail(ChangeEmailDto emailDto);

}
