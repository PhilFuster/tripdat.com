package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.service.TripdatTripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TripdatTripController {


    @Autowired
    private TripdatTripService tripdatTripService;

    // == constructors ==
    @Autowired
    public TripdatTripController(TripdatTripService tripdatTripService ) {
        this.tripdatTripService = tripdatTripService;
    }

    @GetMapping("/trip/")
    public String userForm(Model model) {
        model.addAttribute("users", tripdatTripService.list());
        return "users";

    }



}
