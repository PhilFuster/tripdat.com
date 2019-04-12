package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping(Mappings.HOME)
    public String root() {
        return ViewNames.INDEX;

    }

    @GetMapping(Mappings.USER_INDEX)
    public String userIndex() {
        return ViewNames.USER_INDEX;
    }

    @GetMapping(Mappings.LOGIN)
    public String login() {
        return ViewNames.LOGIN;
    }

    @GetMapping(Mappings.ACCESS_DENIED)
    public String accessDenied() {
        return ViewNames.ACCESS_DENIED;
    }

}
