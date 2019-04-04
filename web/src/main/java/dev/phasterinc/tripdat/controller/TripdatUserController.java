package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.service.TripdatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TripdatUserController {

    @Autowired
    private TripdatUserService tripdatUserService;


    // == constructors ==
    @Autowired
    public TripdatUserController(TripdatUserService tripdatUserService) {
        this.tripdatUserService = tripdatUserService;

    }

    @GetMapping("/")
    public String userForm(Model model) {
        model.addAttribute("users", tripdatUserService.list());
        return "users";

    }



}
