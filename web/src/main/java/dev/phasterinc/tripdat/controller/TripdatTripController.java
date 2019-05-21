package dev.phasterinc.tripdat.controller;


import dev.phasterinc.tripdat.model.TripItemWrapper;
import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.service.TripItemWrapperService;
import dev.phasterinc.tripdat.service.TripdatTripItemService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Controller
public class TripdatTripController {



    private TripdatTripService tripdatTripService;

    private TripdatTripItemService tripdatTripItemService;

    private TripItemWrapperService tripItemWrapperService;

    // == constructors ==
    @Autowired
    public TripdatTripController(TripdatTripService tripdatTripService, TripdatTripItemService tripdatTripItemService, TripItemWrapperService tripItemWrapperService ){
        this.tripdatTripService = tripdatTripService;
        this.tripdatTripItemService = tripdatTripItemService;
        this.tripItemWrapperService = tripItemWrapperService;
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
        List<TripdatTrip> tripsInAscendingOrder = tripdatTripService.get3TripsByUserIdOrderByDateAsc(user.getUserId());
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

    /**
     * name: tripsPage
     * Purpose: Controller for the mapping /user/trip/show/trips
     *          Displays the users past trips and upcoming trips.
     *          Gives them options to edit or delete trips and to add a new trip
     * @param model
     * @return - trips view name
     */
    @GetMapping(Mappings.USER_TRIPS)
    public String tripsPage(Model model) {

        return ViewNames.USER_TRIPS;
    }

    /**
     * name: tripDetailsPage
     * purpose: Controller for the tripDetailsPage. The page contains the details of the whole Trip.
     *          This Controller prepares the TripItemWrappers to be used in the View.
     * @param model - Model object to be passed to tbe view
     * @param segmentId - The id of the segment that was clicked on to view the trip
     * @param tripId - The trip to be queried
     * @return The details page view
     */
    @GetMapping(value = Mappings.TRIP_DETAILS, params = {"segmentId", "tripId"})
    public String tripDetailsPage(Model model, @RequestParam("segmentId") String segmentId, @RequestParam("tripId") String tripId) {
        log.info("TripDetails tripId passed:" + tripId);
        // fin the trip to display with the tripId passed with the url
       TripdatTrip trip = tripdatTripService.findOne(Long.parseLong(tripId));
        model.addAttribute("trip", trip);
        model.addAttribute("tripFormattedDate", tripItemWrapperService.getFormattedDate(trip.getTripStartDate(), trip.getTripEndDate()));
        // Get a list of TripItemWrapper with the items from the trip
        List<TripItemWrapper> wrappers = tripItemWrapperService.getItemsInItemWrapper(trip);
        // order the itemWrappers by date and time
        tripItemWrapperService.orderItemWrappersByAscDateAndTime(wrappers);
        // put the itemWrappers in a map
        HashMap<LocalDate, List<TripItemWrapper>> itemsMap = tripItemWrapperService.getWrappersInMapByDate(wrappers, trip.getTripStartDate(), trip.getTripEndDate());
        // putting in a TreeMap to order the Map by key
        Map<LocalDate, List<TripItemWrapper>> orderedByKeyMap = new TreeMap<LocalDate, List<TripItemWrapper>>(itemsMap);
        /*orderedByKeyMap.forEach((k,v)->{
            log.info("Day: " + k.toString());
            v.forEach((item)->log.info("   Time: " + item.getStartTime().toString()));
        } );*/
        model.addAttribute("itemsMap", orderedByKeyMap);
        return ViewNames.TRIP_DETAILS;
    }




}
