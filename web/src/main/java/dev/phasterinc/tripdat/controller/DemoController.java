package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.service.DemoService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import dev.phasterinc.tripdat.service.TripdatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class DemoController {

    private final DemoService demoService;
    private TripdatUserService userService;
    private TripdatTripService tripService;
    // == constructors ==
    @Autowired
    public DemoController(DemoService demoService, TripdatUserService userService, TripdatTripService tripService) {
        this.demoService = demoService;
        this.userService = userService;
        this.tripService = tripService;

    }

    @GetMapping("demo")
    public String demo (Model model) {
        model.addAttribute("message", demoService.getMessage());
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("pw",user.getPassword());
        TripdatUser foundUser = userService.findOne(Long.parseLong("8"));
        log.info("No user found,{}",foundUser);
        TripdatTrip trip = tripService.findOne(Long.parseLong("2"));
        log.info("No trip found, {}",trip);
        return "demo/demo";
    }

}
