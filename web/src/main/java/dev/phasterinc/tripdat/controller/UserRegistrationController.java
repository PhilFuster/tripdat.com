package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.dto.UserRegistrationDto;
import dev.phasterinc.tripdat.service.CustomUserDetailsService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: UserRegistrationController
 * Purpose: Process registration form, enforce constraints, register user if valid entry
 *
 */

@SessionAttributes("trip")
@Controller
@RequestMapping(Mappings.REGISTRATION)
public class UserRegistrationController {

    @Autowired
    private CustomUserDetailsService userService;

    /**
     * Name: userRegistrationDto
     * Purpose: To create a new instance of a UserRegistrationDto object
     * Synopsis: Returns a new UserRegistrationDto instance.
     */
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    /**
     * Name: showRegistrationForm
     * Purpose: To display the registration form
     * Synopsis: Returns the view Name of the registration.html file.
     */
    @GetMapping
    public String showRegistrationForm(Model model) {
        return ViewNames.REGISTRATION;
    }

    /**
     * Name: registerUserAccount
     * @param userDto - data transfer object for user details
     * @param result - contains errors if present
     * @return either the registration page when invalid credentials are entered.
     *          registration success page if registration is successful
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result) {
        TripdatUser existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email ");
        }
        TripdatUser existing1 = userService.findByLogin(userDto.getLogin());
        if (existing1 != null) {
            result.rejectValue("login", null, "There is already an account registered with that login ");
        }
        if(result.hasErrors()) {
            return ViewNames.REGISTRATION;
        }

        userService.createUser(userDto);
        return "redirect:" + ViewNames.REGISTRATION_SUCCESS;

    }
}
