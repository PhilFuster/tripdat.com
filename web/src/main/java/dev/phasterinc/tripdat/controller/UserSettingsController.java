package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.model.dto.UserSettingsDto;
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
 * Name: UserSettingsController
 * Purpose: Maintenance for User Settings and details
 */
@Slf4j
@Controller
@RequestMapping(Mappings.SETTINGS)
public class UserSettingsController {

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
    @ModelAttribute("user")
    public UserSettingsDto userSettingsDto() {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser user = userPrincipal.getTripdatUser();
        UserSettingsDto settingsDto = UserSettingsDto.buildDto(user);
        return settingsDto;
    }

    /**
     * Name: showSettingsForm
     * Purpose: display the settings form to the user
     * Synopsis:
     *
     * @param model
     * @return
     */
    @GetMapping
    public String showSettingsForm(Model model) {

        return ViewNames.SETTINGS;
    }

    /**
     * Name: saveUserSettings
     * Purpose: To save the User's settings
     * Synopsis: Saves a user settings. If there are errors they will be displayed.
     * When a save is successful the user will be told.
     *
     *
     * @param userDto - data transfer object for user details
     * @param result - contains errors if present
     * @return either the registration page when invalid credentials are entered.
     *          registration success page if registration is successful
     */
    @PostMapping
    public String saveUserSettings(@ModelAttribute("user") @Valid UserSettingsDto userDto,
                                      BindingResult result) {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser userEntity = userPrincipal.getTripdatUser();

        TripdatUser existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email ");
        }

        if(result.hasErrors()) {
            return ViewNames.SETTINGS;
        }
        userService.updateUserInformation(userDto);
        return "redirect:" + ViewNames.SETTINGS_SUCCESS;
    }
}
