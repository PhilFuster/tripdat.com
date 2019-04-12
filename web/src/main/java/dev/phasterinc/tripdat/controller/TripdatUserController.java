package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.service.TripdatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TripdatUserController {

    // == fields ==
    private final TripdatUserService tripdatUserService;


    // == constructors ==

    @Autowired
    public TripdatUserController(TripdatUserService tripdatUserService) {
        this.tripdatUserService = tripdatUserService;


    }

    /*@GetMapping("/")
    public String userForm(Model model) {
        model.addAttribute("users", tripdatUserService.findAll());
        return "users";

    }*/

    /*@GetMapping(Mappings.SIGN_IN)
    public String signInForm(Model model) {

        return ViewNames.SIGN_IN;

    }*/



}
