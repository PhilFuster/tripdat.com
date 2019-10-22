package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.model.dto.ChangeEmailDto;
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
 * Name: ChangeEmail Controller
 * Purpose: Handles changing a user's email
 */
@Slf4j
@Controller
@RequestMapping(Mappings.CHANGE_EMAIL)
public class ChangeEmailController {

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
    @ModelAttribute("emailDto")
    public ChangeEmailDto changeEmailDto() {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser user = userPrincipal.getTripdatUser();
        ChangeEmailDto changeEmailDto = ChangeEmailDto.builder()
                .currentEmail(user.getUserEmail())
                .build();
        return changeEmailDto;
    }

    /**
     * Name: showChangeEmailForm
     * Purpose: display the change email form
     *
     * Synopsis: This form enables the user to change their email by confirming their current one,
     * and providing a new Email, confirming it.
     * @param model Model, contains the ChangeEmailDto object.
     * @return String, the name of the Change Email html file
     */
    @GetMapping
    public String showSettingsForm(Model model) {
        return ViewNames.CHANGE_EMAIL;
    }

    /**
     * Name: saveUserSettings
     * @param EmailDto - data transfer object for user details
     * @param result - contains errors if present
     * @return either the registration page when invalid credentials are entered.
     *          registration success page if registration is successful
     *
     * Algorithm:
     *              1. Retrieve User Principal.
     *              2. Check if the new Email entered is in use by another User.
     *              3. If there are errors, display errors to user.
     *              4. else, update user's email, and display success page.
     *
     */
    @PostMapping
    public String changeUserEmail(@ModelAttribute("emailDto") @Valid ChangeEmailDto EmailDto,
                                      BindingResult result) {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser existing = userService.findByEmail(EmailDto.getNewEmail());
        if (existing != null) {
            log.info("Existing found!");
            result.rejectValue("newEmail", null, "There is already an account registered with that email.");
        }
        if(result.hasErrors()) {
            log.info("Errors found in email.html: {}", result);
            return ViewNames.CHANGE_EMAIL;
        }
        userService.changeUserEmail(EmailDto);
        return "redirect:" + ViewNames.CHANGE_EMAIL_SUCCESS;
    }
}
