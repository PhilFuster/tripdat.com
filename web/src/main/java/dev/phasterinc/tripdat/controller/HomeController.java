package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.TripItemWrapper;
import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.service.TripItemWrapperService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    // == fields ==
    private final TripdatTripService tripdatTripService;
    private final TripItemWrapperService tripItemWrapperService;
    @Autowired
    public HomeController(TripdatTripService tripService, TripItemWrapperService tripItemWrapperService) {

        this.tripdatTripService = tripService;
        this.tripItemWrapperService = tripItemWrapperService;
    }





    @GetMapping(Mappings.HOME)
    public String root() {
        return ViewNames.INDEX;

    }



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

    /**
     * Name: userIndexPage
     * Purpose: creates model for the view. The model will contain the trips and items
     * needed to display a max of 4 user's next trips and the next 4 trip items for the upcoming trip.
     * @param model
     * @return - user index view name
     */
    @GetMapping(Mappings.USER_INDEX)
    public String userIndexPage(Model model) {
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TripdatTrip> tripsInAscendingOrder = tripdatTripService.get3UpcomingTripsByUserIdOrderByDateAsc(user.getUserId());
        List<String> formattedDateStrings = tripItemWrapperService.getFormattedDateStrings(tripsInAscendingOrder);
        List<String> durationOfTrips = tripItemWrapperService.getDurationOfTrips(tripsInAscendingOrder);

        model.addAttribute("trips", tripsInAscendingOrder);
        model.addAttribute("formattedDateStrings", formattedDateStrings);
        model.addAttribute("durationOfTrips", durationOfTrips);
        // TODO: What happens when Trip has no trip items yet? Must handle this null exception
        TripdatTrip nextTrip = tripsInAscendingOrder.get(0);

        List<TripItemWrapper> nextUpItems = tripItemWrapperService.getNextUpItemsInItemWrapper(nextTrip);
        tripItemWrapperService.orderItemWrappersByAscDateAndTime(nextUpItems);

        model.addAttribute("nextUpItems", nextUpItems);

        return ViewNames.USER_INDEX;

    }

}
