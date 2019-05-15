package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.service.TripdatUserService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    // == fields ==
    private final TripdatUserService  tripdatUserService;

    public HomeController(TripdatUserService tripdatUserService) {
        this.tripdatUserService = tripdatUserService;
    }





    @GetMapping(Mappings.HOME)
    public String root() {
        return ViewNames.INDEX;

    }

    /*@GetMapping(Mappings.USER_INDEX)
    public String userIndex(Model model, Principal principal) {
        System.out.println(principal.getName());
        tripdatUserService.findByLogin(principal.getName());


        // == fields ==
        return ViewNames.USER_INDEX;
    }*/

    @GetMapping(Mappings.LOGIN)
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)) {
            /*User is logged in return to index*/
            return ViewNames.USER_INDEX;
        }
        return ViewNames.LOGIN;
    }

    @GetMapping(Mappings.ACCESS_DENIED)
    public String accessDenied() {
        return ViewNames.ACCESS_DENIED;
    }

}
