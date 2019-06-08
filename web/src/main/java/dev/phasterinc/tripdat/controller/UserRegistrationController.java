package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.dto.UserRegistrationDto;
import dev.phasterinc.tripdat.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Name: UserRegistrationController
 * Purpose: Process registration form, enforce constraints, register user if valid entry
 *
 */

@SessionAttributes("trip")
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private CustomUserDetailsService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
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
            result.rejectValue("email", null, "There is already an account registered with that email ");;
        }
        TripdatUser existing1 = userService.findByLogin(userDto.getLogin());
        if (existing1 != null) {
            result.rejectValue("login", null, "There is already an account registered with that login ");
        }
        if(result.hasErrors()) {
            return "registration";
        }
        userService.save(userDto);
        return "redirect:/registration?success";

    }
}
