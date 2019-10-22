package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.model.dto.ChangePasswordDto;
import dev.phasterinc.tripdat.service.CustomUserDetailsService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: ChangePassword Controller
 * Purpose: Handles changing a user's password
 */
@Slf4j
@Controller
@RequestMapping(Mappings.CHANGE_PW)
public class ChangePasswordController {

    @Autowired
    private CustomUserDetailsService userService;


    /**
     * name: userSettingsDto
     * purpose: To create a userSettingsDto from the currently logged in user.
     *
     * Synopsis: Creates a user Principal object and retrieves the TripdatUser object.
     *           Then it builds a UserSettingsDto and returns it.
     *
     *
     * @return UserSettingsDto instance
     */
    @ModelAttribute("passwordDto")
    public ChangePasswordDto changePasswordDto() {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser user = userPrincipal.getTripdatUser();
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder().build();
        return changePasswordDto;
    }

    /**
     * Name: showChangePasswordForm
     * Purpose: display the change password form
     *
     * Synopsis: This form enables the user to change their password by confirming their current one,
     * and providing a new password, twice.
     * @param model Model, contains the ChangePasswordDto object.
     * @return String, the name of the Change Password html file
     */
    @GetMapping
    public String showSettingsForm(Model model) {

        return ViewNames.CHANGE_PW;
    }

    /**
     * Name: saveUserSettings
     * @param passwordDto - data transfer object for user details
     * @param result - contains errors if present
     * @return either the registration page when invalid credentials are entered.
     *          registration success page if registration is successful
     */
    @PostMapping
    public String changeUserPassword(@ModelAttribute("passwordDto") @Valid ChangePasswordDto passwordDto,
                                      BindingResult result) {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser userEntity = userPrincipal.getTripdatUser();
        if(!userService.validatePassword(passwordDto.getCurrentPassword()) ) {
            // User wants to change password but old password is incorrect
            result.rejectValue("currentPassword", null, "old password invalid");
        }

        if(result.hasErrors()) {
            return ViewNames.CHANGE_PW;
        }
        userService.changeUserPassword(passwordDto);
        return "redirect:" + ViewNames.CHANGE_PW_SUCCESS;
    }
}
