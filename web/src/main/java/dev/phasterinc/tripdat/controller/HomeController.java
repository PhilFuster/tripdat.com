package dev.phasterinc.tripdat.controller;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Name: HomeController
 * Purpose: Controller for some basic pages.
 */
@Slf4j
@Controller
public class HomeController {

    // == fields ==
    private final TripdatTripService tripdatTripService;
    private final TripItemWrapperService tripItemWrapperService;

    /**
     * Name: HomeController
     * Purpose: Constructor for injecting the dependencies of {@code HomeController}
     * <p>
     *
     * @param tripService            {@code TripdatTrip} service containing logic pertinent to Trips.
     * @param tripItemWrapperService {@code TripItemWrapper} service containing logic pertaining to trip item wrappers.
     */
    @Autowired
    public HomeController(TripdatTripService tripService, TripItemWrapperService tripItemWrapperService) {

        this.tripdatTripService = tripService;
        this.tripItemWrapperService = tripItemWrapperService;
    }


    /**
     * Name: root
     * Purpose: Serves the landing page of Tripdat Trip application
     * <p>
     *
     * @return String, View Name of the Index.
     */
    @GetMapping(Mappings.HOME)
    public String root() {
        return ViewNames.INDEX;

    }


    /**
     * Name: login
     * Purpose: To serve the login page to the user.
     * Synopsis: If no user signed in, the user will prompted to log in, otherwise serves
     * the user index page.
     * <p>
     *
     * @return String, if a user is logged in serve the user index, else serve the log in page.
     */
    @GetMapping(Mappings.LOGIN)
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            /*User is logged in return to index*/
            return ViewNames.USER_INDEX;
        }
        return ViewNames.LOGIN;
    }

    /**
     * Name: accessDenied
     * Purpose: Display an Access Denied page when a user tries to access a page they do
     * not have permission to access
     * Synopsis: When a user does not have permission to view a certain page, serve this
     * access denied page.
     * <p>
     */
    @GetMapping(Mappings.ACCESS_DENIED)
    public String accessDenied() {
        return ViewNames.ACCESS_DENIED;
    }

    /**
     * Name: userIndexPage
     * Purpose: creates model for the view. The model will contain the trips and items
     * needed to display a max of 4 user's next trips and the next 4 trip items for the upcoming trip.
     *
     * @param model Model, add attributes to be rendered in the view.
     * @return String, user index view name
     */
    @GetMapping(Mappings.USER_INDEX)
    public String userIndexPage(Model model) {
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TripdatTrip> tripsInAscendingOrder = tripdatTripService.get3UpcomingTripsByUserIdOrderByDateAsc(user.getUserId());
        List<String> formattedDateStrings = tripItemWrapperService.getFormattedDateStrings(tripsInAscendingOrder);
        List<String> durationOfTrips = tripItemWrapperService.getDurationOfTrips(tripsInAscendingOrder);
        TripdatTrip nextTrip;
        List<TripItemWrapper> nextUpItems = new ArrayList<>();
        List<TripItemWrapper> result = new ArrayList<>();
        if (!tripsInAscendingOrder.isEmpty()) {
            nextTrip = tripsInAscendingOrder.get(0);
            nextUpItems = tripItemWrapperService.getNextUpItemsInItemWrapper(nextTrip);

            // Next 4 items are retrieved. Filter items that have already occurred.
            result = nextUpItems.stream()
                    .filter(item -> displayNextUpItem(item))
                    .collect(Collectors.toList());
            tripItemWrapperService.orderItemWrappersByAscDateAndTime(result);
        }
        model.addAttribute("trips", tripsInAscendingOrder);
        model.addAttribute("formattedDateStrings", formattedDateStrings);
        model.addAttribute("durationOfTrips", durationOfTrips);
        if (result.size() > 4) {
            model.addAttribute("nextUpItems", result.subList(0, 4));
        } else {
            model.addAttribute("nextUpItems", result);
        }
        return ViewNames.USER_INDEX;
    }

    /**
     * Name: displayNextUpItem
     * Purpose: Helper function to determine if a nextUpItem should be displayed
     * Synopsis: A next up Item should not be displayed if the item has already happened.
     * If a next up item has already started, but it has not ended and this item is the arrival(end date version of it)
     * it should be displayed to the user as a next up item.
     *
     * @return boolean, true if item should be displayed, false if not
     */
    boolean displayNextUpItem(TripItemWrapper item) {
        // Item starts after today
        if(!item.getStartDate().isBefore(LocalDate.now())) {
            return true;
            // item does not end before today and it is an arrival item. Thus
            // should be displayed to user so they know when this item ends
        } else if(!item.getEndDate().isBefore(LocalDate.now()) && item.getIsArrival()) {
            return true;
        } else return false;
    }

}
