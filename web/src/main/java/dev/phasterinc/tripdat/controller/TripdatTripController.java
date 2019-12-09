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
import dev.phasterinc.tripdat.model.dto.TripDto;
import dev.phasterinc.tripdat.service.TripItemWrapperService;
import dev.phasterinc.tripdat.service.TripdatTripItemService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

/**
 * Name: TripdatTripController
 * Purpose: Controller for requests regarding a {@code TripdatTrip}
 */
@SessionAttributes("trip")
@Slf4j
@Controller
public class TripdatTripController {

    // == fields ==
    private TripdatTripService tripService;
    private TripdatTripItemService tripdatTripItemService;
    private TripItemWrapperService tripItemWrapperService;
    private ModelMapper modelMapper;

    // == constructors ==

    /**
     * Name: TripdatTripController
     * Purpose: Constructor for injecting the dependencies of {@code TripdatTripController}
     * <p>
     *
     * @param tripdatTripService     {@code TripdatTripService} contains logic pertaining {@code TripdatTrip}
     * @param tripdatTripItemService {@code TripdatTripItemService} contains logic pertaining to {@code TripdatTripItem}
     * @param tripItemWrapperService {@code TripdatTripItemWrapperService} contains logic
     *                               pertaining to {@code TripdatTripItem & TripdatTripItemWrapper} classes
     */
    @Autowired
    public TripdatTripController(TripdatTripService tripdatTripService, TripdatTripItemService tripdatTripItemService,
                                 TripItemWrapperService tripItemWrapperService, ModelMapper modelMapper) {
        this.tripService = tripdatTripService;
        this.tripdatTripItemService = tripdatTripItemService;
        this.tripItemWrapperService = tripItemWrapperService;
        this.modelMapper = modelMapper;
    }
    // == handler methods ==

    /**
     * name: tripDetailsPage
     * purpose: Controller for the tripDetailsPage. The page contains the details of a specific Trip.
     * Synopsis: This Controller prepares the TripItemWrappers to be used in the View.
     *
     * @param model  - Model object to be passed to tbe view
     * @param tripId - The trip to be queried
     * @return The details page view
     * Algorithm:
     * 1. Find the trip to display.
     * 2. Add the trip to the model
     * 3. Add the Trip formatted date to the model
     * 4. Create a list of ItemWrappers filled with the items for the Trip
     * 5. order the ItemsWrappers by Date and Time
     * 6. Put the itemWrappers into a HashMap
     * 7. Convert Hashmap into TreeMap, which orders by key
     * 8. add itemsMap to the model
     * 9. return the
     * http://localhost:8080/user/trip/show/details?tripId=3
     */
    @GetMapping(value = Mappings.TRIP_DETAILS, params = {"tripId"})
    public String tripDetailsPage(Model model, @RequestParam("tripId") String tripId) {
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("TripDetails tripId passed:" + tripId);
        // find the trip to display with the tripId passed with the url
        TripdatTrip trip = tripService.findOne(Long.parseLong(tripId));
        if (trip == null) {
            return ViewNames.BAD_LINK;
        }
        // If trip does not belong to the user direct to access denied page
        if(!trip.getUser().getUserId().equals(user.getUserId())) {
            return ViewNames.ACCESS_DENIED;
        }
        model.addAttribute("trip", trip);
        model.addAttribute("tripId", tripId);
        model.addAttribute("tripFormattedDate", tripItemWrapperService.getFormattedDate(trip.getTripStartDate(), trip.getTripEndDate()));
        // Get a list of TripItemWrapper with the items from the trip
        List<TripItemWrapper> wrappers = tripItemWrapperService.getItemsInItemWrapper(trip);
        // order the itemWrappers by date and time
        tripItemWrapperService.orderItemWrappersByAscDateAndTime(wrappers);
        // put the itemWrappers in a map
        HashMap<LocalDate, List<TripItemWrapper>> itemsMap = tripItemWrapperService.getWrappersInMapByDate(wrappers, trip.getTripStartDate(), trip.getTripEndDate());
        // putting in a TreeMap to order the Map by key
        Map<LocalDate, List<TripItemWrapper>> orderedByKeyMap = new TreeMap<>(itemsMap);
        // add items map the model
        model.addAttribute("itemsMap", orderedByKeyMap);
        return ViewNames.TRIP_DETAILS;
    }


    /**
     * name: tripsPage
     * Purpose: Handler Method for the mapping /user/trip/show/trips
     * Displays the users past and upcoming trips.
     * Synopsis: Gives them options to edit or delete trips and to add a new trip
     *
     * @param model - The model for the view
     * @return - trips view name
     * http://localhost:8080/user/trip/show/trips
     * Algorithm:
     * 1. Get logged in user's information
     * 2. Query db for users Trips
     * 3. Declare local variables upcoming, and past, which store a users upcoming
     * and past trips accordingly
     * 4. Call method createUpcomingAndPastTripsCollections to populate collections upcoming and past
     * 5. Add collections upcoming and past to model
     * 6. Declare Lists upcomingTripsList and pastTripsList
     * 7. Declare and populate formattedDateString collections for upcoming and past trips
     * 8. Declare and populate durationOfTrips for upcoming and past trips
     * 9. Add all collections to model
     */
    @GetMapping(Mappings.USER_TRIPS)
    public String tripsPage(Model model) {
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // query DB for a user's Trips
        Set<TripdatTrip> trips = tripService.getTripsByUserId(user.getUserId());
        // Declare upcoming and past Sets that will store the upcoming and past trips

        TreeSet<TripdatTrip> upcoming = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(TripdatTrip::getTripStartDate)));

        TreeSet<TripdatTrip> past = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(TripdatTrip::getTripStartDate)));
        // populate the upcoming and past trip collections with the trips from trips Collection
        tripService.createUpcomingAndPastTripsCollections(trips, upcoming, past);
        model.addAttribute("upcomingTrips", upcoming);
        model.addAttribute("pastTrips", past);
        // Declare and initialize upcomingTripsList and pastTripsList in order to
        // use the methods getFormattedDateStrips and getDurationOfTrips
        List<TripdatTrip> upcomingTripsList = new ArrayList<>(upcoming);
        List<TripdatTrip> pastTripsList = new ArrayList<>(past);
        // call the getDurationOfTrips methods and getFormattedDateStrings for
        // upcoming and past trip collections.
        List<String> formattedUpcomingDateStrings = tripItemWrapperService.getFormattedDateStrings(upcomingTripsList);
        List<String> formattedPastDateStrings = tripItemWrapperService.getFormattedDateStrings(pastTripsList);
        List<String> durationOfTripsUpcoming = tripItemWrapperService.getDurationOfTrips(upcomingTripsList);
        List<String> durationOfTripsPast = tripItemWrapperService.getDurationOfTrips(pastTripsList);
        // Add the collections to the model to be used front end
        model.addAttribute("formattedDateStringsUpcoming", formattedUpcomingDateStrings);
        model.addAttribute("formattedDateStringsPast", formattedPastDateStrings);
        model.addAttribute("durationOfTripsUpcoming", durationOfTripsUpcoming);
        model.addAttribute("durationOfTripsPast", durationOfTripsPast);

        return ViewNames.USER_TRIPS;
    }

    /**
     * Name: showTripForm
     * Purpose: Handler for displaying the form to the user.
     *
     * @param model  - model to be referenced in the view
     * @param tripId - id of the trip to edit
     * @return trip form view
     * Algorithm:
     * 1. declare the local variable trip
     * 2. query db for the trip by id
     * 3. declare tripDto
     * 4. IF requestParam tripId EQ -1
     * 5.    Initialize tripDto with a tripId of 0, denoting new trip to
     * be created and initialize startDate and endDate to today's date
     * 6. ELSE
     * 7.    build tripDto with the queried trip's data
     * 8. Add tripDto to model
     * 9. return EDIT_TRIP view name
     */
    @GetMapping(value = {Mappings.EDIT_TRIP, Mappings.CREATE_TRIP})
    public String showTripForm(@RequestParam(required = false, defaultValue = "-1") Long tripId,
                               Model model) {
        // == local variables ==
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTrip trip = tripService.findOne(tripId);
        TripDto tripDto;
        // find the trip to display with the tripId passed with the url
        if (tripId != -1 && trip == null) {
            return ViewNames.BAD_LINK;
        }
        // If trip does not belong to the user direct to access denied page
        if(tripId != -1 &&  !trip.getUser().getUserId().equals(user.getUserId())) {
            return ViewNames.ACCESS_DENIED;
        }
        log.info("editing id = {}", tripId);
        if (tripId == -1) {
            // No trip found.
            // Declare and initialize TripdatTrip object
            // Set start & end dates to LocalDate
            tripDto = TripDto.builder().tripId(Long.valueOf(0))
                    .tripStartDate(LocalDate.now())
                    .tripEndDate(LocalDate.now()).build();
        } else {
            tripDto = TripDto.builder().tripId(trip.getTripId())
                    .tripStartDate(trip.getTripStartDate())
                    .tripEndDate(trip.getTripEndDate())
                    .tripDescription(trip.getTripDescription())
                    .tripName(trip.getTripName())
                    .destinationCity(trip.getDestinationCity())
                    .isUserTraveler(trip.getIsUserTraveler()).build();

        }
        // Add trip to model & return the trip form
        model.addAttribute("trip", tripDto);
        // No Trip deleted from show method but when a trip is deleted
        // a successful deletion message is displayed based off of isDeleted model
        // Attribute
        model.addAttribute("isDeleted", false);
        return tripId != -1 ? ViewNames.EDIT_TRIP : ViewNames.CREATE_TRIP;
    }

    /**
     * Name: processTrip
     * Purpose: To process a TripdatTrip form.
     *
     * @param tripId  - id of trip to query for
     * @param tripDto - Dto with trip fields
     * @param result  - results to display if there are errors
     * @return - redirect to show trips if the POST was successful
     * Algorithm:
     * 1. Check the dto for date conflicts with the new start and end date.
     * 2. If there are conflicts, stop processing and return to edit form with errors.
     * 3. If no Trip date range conflicts, and editing an existing trip. Check to make sure
     * no existing trip item falls out of the new date range.
     * 4. If any do, return to form with that error.
     * 5. If no conflicts, update Trip.
     * 6. When creating new Trip, only check for Trip date range conflicts
     */
    @RequestMapping(value = {Mappings.EDIT_TRIP, Mappings.CREATE_TRIP}, method = RequestMethod.POST, params = {"tripId"})
    public String processTrip(
            @RequestParam(required = false, defaultValue = "-1") Long tripId,
            @ModelAttribute("trip") @Valid TripDto tripDto, BindingResult result) {
        // Verify that start date is after end date
        // before anything else.
        if (tripDto.getTripStartDate().isAfter(tripDto.getTripEndDate())) {
            result.rejectValue("tripStartDate", null,
                    "Start date must be before end date.");
            result.rejectValue("tripEndDate", null,
                    "End date must be after start date.");
            return tripId != -1 ? ViewNames.EDIT_TRIP : ViewNames.CREATE_TRIP;
        }
        log.info("TripdatTrip from form = {}", tripDto);
        // Get logged in user's information
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTrip trip;
        // If Creating a new Trip initialize new trip
        if (tripId == -1) {
            trip = new TripdatTrip();
        } else {
            // editing a Trip, so find it by id.
            trip = tripService.getTripByTripId(tripDto.getTripId());
        }

        // Passes the userId as a constraint for the query.
        // checkForTripDateConflict queries the DB for Trips that overlap the new date Range
        // If the list has trips in it than there are conflicts.
        boolean isTripDateConflict = tripService.checkForTripDateConflict(tripDto, user.getUserId());
        // set error if there is a trip date conflict
        if (isTripDateConflict) {
            result.rejectValue("tripStartDate", null,
                    "This date range conflicts with other trips coming up.");
            result.rejectValue("tripEndDate", null,
                    "This date range conflicts with other trips coming up.");
        }
        // Cannot query the db to check for conflicts because items' segments must be
        // unpacked from their Trip Item and it would be some 20 odd joins to make sure
        // all item date ranges are included.
        //
        // true if a tripItem's start or end date is not within the new date range
        boolean isTripItemConflict;
        // if tripId from the Dto is greater than 0 it corresponds to a trip in db
        // get all items for this trip from the db
        if (tripDto.getTripId() > 0) {
            // get all Trip items and the items's segments into a list of TripItemWrapper
            List<TripItemWrapper> items = tripItemWrapperService.getItemsInItemWrapper(trip);
            // Call checkForTripItemConflictWithNewDate and pass the tripDto that has the new date range
            // and a collection of itemWrappers from this trip to check against.
            isTripItemConflict = tripService.isTripItemsOutOfNewTripDateRange(tripDto, items);
            // if item conflicts add the result bindings
            if (isTripItemConflict) {
                result.rejectValue("tripStartDate", null,
                        "There are trip items that do not fall within this date range.");
                result.rejectValue("tripEndDate", null,
                        "There are trip items that do not fall within this date range.");
            }
        }
        // if there are errors return to edit trip or create trip.
        if (result.hasErrors()) {
            return tripId != -1 ? ViewNames.EDIT_TRIP : ViewNames.CREATE_TRIP;
        }
        // Trip has no errors. Update the entity
        trip.setTripStartDate(tripDto.getTripStartDate());
        trip.setTripEndDate(tripDto.getTripEndDate());
        trip.setDestinationCity(tripDto.getDestinationCity());
        trip.setTripName(tripDto.getTripName());
        trip.setTripDescription(tripDto.getTripDescription());
        // If tripId is not -1 then update this Trip
        if (tripId != -1) {
            tripService.update(trip);

        } else {
            // Set the trip's user
            trip.setUser(user.getTripdatUser());
            // Creating a new Trip
            tripService.create(trip);
        }
        return "redirect:" + ViewNames.USER_TRIPS;
    }

    /**
     * Name: cancelEditTrip
     * Purpose: To cancel edits to a Trip and return to USER_TRIPS view
     *
     * @param request - the request parameters
     * @return - redirect to USER_TRIPS view
     */
    @PostMapping(value = Mappings.EDIT_TRIP, params = "cancel=cancel")
    public String cancelEditTrip(HttpServletRequest request) {

        return "redirect:" + ViewNames.USER_TRIPS;
    }


    /**
     * Name: deleteTrip
     * Purpose: To delete a user's Trip.
     * If the User has items, ask if they are ok with deleting all its items.
     * User has option of saving their items to the un-filed section.
     */
    @GetMapping(value = Mappings.DELETE_TRIP)
    public String deleteTrip(@RequestParam Long tripId, Model model) {
        // Check to see if the user is Ok with deleting all items associated with this trip.
        // If so delete the trip in its entirety or allow them to move the items to the
        // un-filed sections
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTrip trip = tripService.findOne(tripId);
        // find the trip to display with the tripId passed with the url
        if (trip == null) {
            return ViewNames.BAD_LINK;
        }
        // If trip does not belong to the user direct to access denied page
        if(!trip.getUser().getUserId().equals(user.getUserId())) {
            return ViewNames.ACCESS_DENIED;
        }
        tripService.deleteById(tripId);
        return "redirect:" + ViewNames.USER_TRIPS;
    }
}

