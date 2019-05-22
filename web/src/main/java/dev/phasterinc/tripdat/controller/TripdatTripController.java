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
import java.util.*;

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
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // query DB for a user's Trips
        Set<TripdatTrip> trips = tripdatTripService.getTripsByUserId(user.getUserId());
        // Declare upcoming and past Sets that will store the upcoming and past trips



        TreeSet<TripdatTrip> upcoming = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(TripdatTrip::getTripStartDate)));

        TreeSet<TripdatTrip> past = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(TripdatTrip::getTripStartDate)));
        // populate the upcoming and past trip collections with the trips from trips Collection
        tripdatTripService.createUpcomingAndPastTripsCollections(trips, upcoming, past);
        model.addAttribute("upcomingTrips", upcoming);
        model.addAttribute("pastTrips", past);

        List<TripdatTrip> upcomingTripsList = new ArrayList<>(upcoming);
        List<TripdatTrip> pastTripsList = new ArrayList<>(past);
        List<String> formattedUpcomingDateStrings = tripItemWrapperService.getFormattedDateStrings(upcomingTripsList);
        List<String> formattedPastDateStrings = tripItemWrapperService.getFormattedDateStrings(pastTripsList);
        List<String> durationOfTripsUpcoming = tripItemWrapperService.getDurationOfTrips(upcomingTripsList);
        List<String> durationOfTripsPast = tripItemWrapperService.getDurationOfTrips(pastTripsList);
        model.addAttribute("formattedDateStringsUpcoming", formattedUpcomingDateStrings);
        model.addAttribute("formattedDateStringsPast", formattedPastDateStrings);
        model.addAttribute("durationOfTripsUpcoming", durationOfTripsUpcoming);
        model.addAttribute("durationOfTripsPast", durationOfTripsPast);

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
    @GetMapping(value = Mappings.TRIP_DETAILS, params = {"tripId"})
    public String tripDetailsPage(Model model, @RequestParam("tripId") String tripId) {
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

        model.addAttribute("itemsMap", orderedByKeyMap);
        return ViewNames.TRIP_DETAILS;
    }




}
