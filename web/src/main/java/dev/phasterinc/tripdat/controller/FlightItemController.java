package dev.phasterinc.tripdat.controller;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.*;
import dev.phasterinc.tripdat.model.dto.*;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * ClassName: FlightItemController
 * Purpose: Controller for Flight Item Mappings.
 * Has functionality for Item CRUD pages such as EDIT, CREATE, DELETE items.
 */

@Slf4j
@Controller
@ControllerAdvice
public class FlightItemController {
    // == fields ==
    private TripdatTripService tripService;
    private TripdatTripItemService tripItemService;
    private TripItemWrapperService tripItemWrapperService;

    // == constructors ==

    /**
     * Name: FlightItemController
     * Purpose: Constructor for injecting dependencies of the class.
     * Synopsis: Injects the classes that are needed for this class to perform properly.
     * <p>
     *
     * @param tripdatTripService     {@code TripdatTrip} service for performing logic pertaining to trips
     * @param tripItemService        {@code TripdatTripItem} service for performing logic pertaining to trip items.
     * @param tripItemWrapperService {@code TripItemWrapper} service for performing logic
     *                               pertaining to trip Item wrappers.
     */
    @Autowired
    public FlightItemController(TripdatTripService tripdatTripService, TripdatTripItemService tripItemService,
                                TripItemWrapperService tripItemWrapperService) {
        this.tripService = tripdatTripService;
        this.tripItemService = tripItemService;
        this.tripItemWrapperService = tripItemWrapperService;
    }


    // == model attributes ==

    /**
     * Name: flightItemDto
     * Purpose: Creates a flightItemDto to be used when editing/creating a Flight
     *
     * @param tripItemId - Id of the tripItem if editing an existing trip
     * @param tripId     - Id of the Trip used to get back to the trip Details when done
     *                   editing/creating an item.
     *                   Algorithm:
     *                   1. Declare variables being used
     *                   2. Initialize Dto lists for {@code AttendeeDto, FlightItemDto}
     *                   3. If creating a trip
     *                   4.   Initialize the flightItemDto
     *                   5. else editing a trip
     *                   6.   Find the Entity being edited
     *                   7.   get the flightSegments
     *                   8.   If flightSegments is empty
     *                   9.     add two segmentDtos to list segmentDtos for creation.
     *                   10.  else flightSegments not empty
     *                   11.    for each segment in flightSegments
     *                   12.      create a flightSegmentDto
     *                   13.      insert dto into list segmentDtos
     *                   14.  get list of Attendees from flight
     *                   15.  if attendees is empty
     *                   16.    add an AttendeeDto to list attendeeDtos
     *                   17.  else attendees not empty
     *                   18.    call AttendeeDto's static function buildDtoList
     *                   19.  set flightItemDto by calling FlightItemDto's static function buildDto
     *                   20. add tripId Attribute to model
     *                   21. add flightItemDto to model
     */

    public FlightItemDto flightItemDto(Long tripItemId, Long tripId) {
        if (tripId == -1) {
            log.info("No TripId Passed to flightItemController");
        }
        TripdatTrip trip = tripService.findOne(tripId);
        Flight flight;
        TripdatTripItem tripItem;
        List<FlightSegment> flightSegments;
        List<Attendee> attendees;
        FlightItemDto flightItemDto;
        List<FlightSegmentDto> segmentDtos = new ArrayList<>();
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        // Creating a trip
        boolean creatingTrip = tripItemId < 0;
        if (creatingTrip) {
            flightItemDto = FlightItemDto.builder()
                    .itemId((long) -1)
                    .attendees(new ArrayList<AttendeeDto>())
                    .travelAgencyDto(TravelAgencyDto.builder().build())
                    .supplierDto(SupplierDto.builder().build())
                    .bookingDetailDto(BookingDetailDto.builder().build())
                    .build();
            segmentDtos.add(FlightSegmentDto.builder().departureDate(trip.getTripStartDate()).build());
            segmentDtos.add(FlightSegmentDto.builder().build());
            segmentDtos.add(FlightSegmentDto.builder().build());
            segmentDtos.add(FlightSegmentDto.builder().build());
            flightItemDto.setSegmentDtos(segmentDtos);
            flightItemDto.getAttendees().add(AttendeeDto.builder().build());
            flightItemDto.getAttendees().add(AttendeeDto.builder().build());
            flightItemDto.getAttendees().add(AttendeeDto.builder().build());
            flightItemDto.getAttendees().add(AttendeeDto.builder().build());
            flightItemDto.setTripId(tripId);
        } else {
            // editing a trip
            //
            // find tripItem
            tripItem = tripItemService.findByItemId(tripItemId);
            flight = (Flight) tripItem;
            flightSegments = flight.getFlightSegments();
            // No Flight segments for this trip? Add four.
            if (flightSegments.isEmpty()) {
                segmentDtos.add(FlightSegmentDto.builder()
                        .departureDate(trip.getTripStartDate())
                        .arrivalDate(trip.getTripStartDate().plusDays(1))
                        .build());
                segmentDtos.add(FlightSegmentDto.builder().build());
                segmentDtos.add(FlightSegmentDto.builder().build());
                segmentDtos.add(FlightSegmentDto.builder().build());
            } else {
                // sort segments by date and time.
                flightSegments.sort(Comparator.nullsLast(Comparator.comparing(FlightSegment::getFlightDepartureDate, Comparator.nullsLast(LocalDate::compareTo)).thenComparing(FlightSegment::getFlightDepartureTime, Comparator.nullsLast(LocalTime::compareTo))));
                // create Dtos for each segment.
                for (FlightSegment segment : flightSegments) {
                    FlightSegmentDto flightSegmentDto = FlightSegmentDto.buildDto(segment);
                    segmentDtos.add(flightSegmentDto);
                }
                // adding two more FlightSegmentDtos so user can add if they want
                segmentDtos.add(FlightSegmentDto.builder().build());
                segmentDtos.add(FlightSegmentDto.builder().build());
            }
            attendees = flight.getAttendees();
            // populate attendees if empty
            if (attendees.isEmpty()) {
                attendeeDtos.add(AttendeeDto.builder().build());
                attendeeDtos.add(AttendeeDto.builder().build());
            } else {
                // Build AttendeeDtos list with attendees from Flight
                attendeeDtos = AttendeeDto.buildDtoList(attendees);
                // Add two more for user's use.
                attendeeDtos.add(AttendeeDto.builder().build());
                attendeeDtos.add(AttendeeDto.builder().build());
            }
            flightItemDto = FlightItemDto.buildDto(flight, segmentDtos, attendeeDtos);
        }

        return flightItemDto;
    }

    /**
     * Name: showFlightItemForm
     * Purpose: Display a form for a Flight trip Item to the user to edit or create a new
     * Flight depending on the Mapping requested.
     * <p>
     *
     * @param model      Model to add attributes to for rendering in the view.
     * @param tripId     Long,id of trip to retrieve the FlightItem from.
     * @param tripItemId Long, id of the Flight to retrieve.
     *
     * @throws NoResultException when the itemId or tripId passed is invalid.
     */
    @GetMapping(value = {Mappings.EDIT_FLIGHT, Mappings.CREATE_FLIGHT})
    public String showFlightItemForm(Model model,
                                     @RequestParam(required = false, defaultValue = "-1") Long tripItemId,
                                     @RequestParam(defaultValue = "-1", required = false) Long tripId) {
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTripItem tripItem = null;
        TripdatTrip trip = null;
        // editing tripItem. Make sure it is valid & belongs to user.
        if(tripItemId != -1) {
            try {
                tripItem = tripItemService.findByItemId(tripItemId);
                if(!tripItem.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip Item");
                    return ViewNames.ACCESS_DENIED;
                }
                // Check that itemId is valid to the type of form being accessed.
                if(tripItem.getType() != "F") {
                    log.info("Trip Item ID passed is not of type Flight.");
                    return ViewNames.BAD_LINK;
                }
            } catch (NoResultException e) {
                log.info("Trip Item could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        // Validate that TripId passed is valid, and belong to user.
        if(tripId != -1) {
            try {
                trip = tripService.findOne(tripId);
                if(!trip.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip");
                    return ViewNames.ACCESS_DENIED;
                }
            } catch (NoResultException e) {
                log.info("Trip could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        FlightItemDto flight = flightItemDto(tripItemId,tripId);
        model.addAttribute("flight", flight);
        // == local variables ==
        return tripItemId == -1 ? ViewNames.CREATE_FLIGHT : ViewNames.EDIT_FLIGHT;
    }

    /**
     * Name: processFlight
     * Purpose: To process a TripdatTrip form.
     *
     * @param itemId - id of item to query for
     * @param result - results to display if there are errors
     * @return - redirect to show items if the POST was successful
     * Algorithm:
     * 1. Check the itemDto's Date range falls within the Trip's date range
     * 2. If there are conflicts, stop processing and return to edit form with errors.
     * 3. Check that itemDto's date ranges do not conflict with other items.
     * 4. If any conflict, return to form with that error.
     * 5. If no conflicts, update/create item.
     */
    @RequestMapping(value = {Mappings.EDIT_FLIGHT, Mappings.CREATE_FLIGHT}, method = RequestMethod.POST, params = {"itemId", "tripId"})
    public String processFlight(@ModelAttribute("flight") FlightItemDto flightDto,
                                @RequestParam(required = false, defaultValue = "-1") Long itemId,
                                @RequestParam Long tripId, BindingResult result) {
        log.info("TripId passed to processFlight: {}", tripId.toString());
        log.info("flightDto segmentId: {} ", flightDto.getSegmentDtos().get(0).getSegmentId());
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTrip trip = tripService.findOne(tripId);
        LocalDate tripStartDate = trip.getTripStartDate();
        LocalDate tripEndDate = trip.getTripEndDate();
        List<TripItemWrapper> wrappers = tripItemWrapperService.getItemsInItemWrapper(trip);
        tripItemWrapperService.orderItemWrappersByAscDateAndTime(wrappers);
        // Verify that each segment's start date is not after the item's end date
        for (int i = 0; i < flightDto.getSegmentDtos().size(); ++i) {
            FlightSegmentDto newSegmentDto = flightDto.getSegmentDtos().get(i);
            boolean isDateRangeNull = (newSegmentDto.getDepartureDate() == null || newSegmentDto.getArrivalDate() == null);
            boolean isTimeRangeNull = newSegmentDto.getDepartureTime() == null || newSegmentDto.getArrivalTime() == null;
            if (!isDateRangeNull) {
                //
                // DepartureDate cannot be after arrival Date
                if (newSegmentDto.getDepartureDate().isAfter(newSegmentDto.getArrivalDate())) {
                    result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                            "Departure date must be before arrival date.");
                    result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                            "Arrival date must be after departure date.");
                }
                // TripItem must be within Trips date range
                if (newSegmentDto.getDepartureDate().isBefore(tripStartDate) || newSegmentDto.getArrivalDate().isAfter(tripEndDate)) {
                    result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                            "Date range does not fall within Trip's date range.");
                    result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                            "Date range does not fall within Trip's date range.");
                }
                //
                // Validate newItem duration does not overlap any items within trip
                for (int j = 0; j < wrappers.size(); ++j) {
                    TripItemWrapper currentItem = wrappers.get(j);
                    if (currentItem.getTripItemTypeCode() != "F") continue;
                    FlightSegmentDto segment = (FlightSegmentDto) currentItem.getTripItem();
                    //  if flightSegment.ID is equal to the newSegmentDto.segmentId being edited
                    // Do not cause false positive. ignore item.
                    log.info("dbSegment.segmentId: {}", segment.getSegmentId());
                    log.info("fromFormSegment.segmentId: {}", newSegmentDto.getSegmentId());
                    if (segment.getSegmentId().equals(newSegmentDto.getSegmentId())) {
                        continue;
                    }
                    // Continue if currentItem's date range is null
                    if (currentItem.getStartDate() == null || currentItem.getEndDate() == null) {
                        continue;
                    }
                    // If newItem and currentItem start on same day, and the currentItem starts and ends on same day, and
                    if (newSegmentDto.getDepartureDate().isEqual(currentItem.getStartDate())
                            && currentItem.getStartDate().isEqual(currentItem.getEndDate())) {
                        // if newItem starts and ends on same day
                        if (newSegmentDto.getDepartureDate().isEqual(newSegmentDto.getArrivalDate())) {
                            // newItem begins and ends on same day. It is possible for newItem
                            // and currentItem to not conflict as long as newItem starts after
                            // currentItem ends or ends before currentItem starts
                            if (!isTimeRangeNull && (currentItem.getStartTime() != null && currentItem.getEndTime() != null)) {
                                if (newSegmentDto.getArrivalTime().isAfter(currentItem.getStartTime())
                                        || newSegmentDto.getDepartureTime().isBefore(currentItem.getEndTime())) {
                                    result.rejectValue("segmentDtos[" + i + "].departureTime", null,
                                            "Time Conflict with other item in trip");
                                    result.rejectValue("segmentDtos[" + i + "].arrivalTime", null,
                                            "Time Conflict with other item in trip");
                                }
                            }
                        }
                        // newItem and currentItem start on same day
                    } else if (newSegmentDto.getDepartureDate().isEqual(currentItem.getStartDate())
                            // currentItem does not start and end on same day
                            && !currentItem.getStartDate().isEqual(currentItem.getEndDate())
                            // newItem starts and ends on same day
                            && newSegmentDto.getDepartureDate().isEqual(newSegmentDto.getArrivalDate())) {
                        // newItem must end before currentItem starts.
                        // if newItem endTime is not before currentItem's end time report error
                        if ((newSegmentDto.getArrivalTime() != null && currentItem.getStartTime() != null) && !newSegmentDto.getArrivalTime().isBefore(currentItem.getStartTime())) {
                            result.rejectValue("segmentDtos[" + i + "].departureTime", null,
                                    "Time Conflict with other item in trip");
                            result.rejectValue("segmentDtos[" + i + "].arrivalTime", null,
                                    "Time Conflict with other item in trip");

                        }
                    }
                    // newItem does not start on same day as currentItem
                    // if newItem's arrivaleDate is not before the currentItem's startDate
                    // OR newItem's departureDate is not after currentItems endDate there is overlap
                    if ((currentItem.getStartDate() != null && currentItem.getEndDate() != null) && !(newSegmentDto.getArrivalDate().isBefore(currentItem.getStartDate())
                            || newSegmentDto.getDepartureDate().isAfter(currentItem.getEndDate()))) {
                        result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                                "Date Conflict with other item in trip");
                        result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                                "Date Conflict with other item in trip");

                    }
                }
            }
            //
            // If there are validation errors return to create or edit pages
            if (result.hasErrors()) {
                return itemId == -1 ? ViewNames.CREATE_FLIGHT : ViewNames.EDIT_FLIGHT;
            }
        }

        // FlightItemDto has made it through the validation process
        // Create an entity from the flightItemDto
        TripdatTripItem flight;
        if (itemId == -1) {
            flight = FlightItemDto.buildEntity(trip, flightDto);
            flight.setUser(user.getTripdatUser());
            // create the flight
            tripItemService.create(flight);
        } else {
            // updating a flight
            flight = tripItemService.findByItemId(itemId);
            FlightItemDto.updateEntity((Flight) flight, flightDto);
            tripItemService.update(flight);
        }
        return "redirect:" + ViewNames.TRIP_DETAILS + "?tripId=" + tripId.toString();
    }

    /**
     * Name: deleteFlightItem
     * Purpose: To delete a FlightItem from a Trip.
     * Synopsis: Searches the database for a flightItem with id passed & deletes it.
     * <p>
     *
     * @param tripId Long, id of the Trip that contains the item to be deleted.
     * @param itemId Long, id of the item to be deleted.
     *
     * @throws NoResultException when tripItemId or tripId passed cannot be found.
     */
    @RequestMapping(value = {Mappings.DELETE_FLIGHT}, params = {"itemId", "tripId"}, method = RequestMethod.POST)
    public String deleteFlightItem(@RequestParam(required = false, defaultValue = "-1") Long itemId,
                                   @RequestParam Long tripId) {
        TripdatTrip trip = null;
        TripdatTripItem tripItem = null;
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // attempting to delete a tripItem. Make sure it is valid & belongs to user.
        if(itemId != -1) {
            try {
                tripItem = tripItemService.findByItemId(itemId);
                if(!tripItem.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip Item");
                    return ViewNames.ACCESS_DENIED;
                }
                // Check that itemId is valid to the type of form being accessed.
                if(tripItem.getType() != "F") {
                    log.info("Attempting to delete an item not of type flight.");
                    return ViewNames.BAD_LINK;
                }
            } catch (NoResultException e) {
                log.info("Trip Item could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        // Validate that tripId passed is valid, and belongs to user.
        if(tripId != -1) {
            try {
                trip = tripService.findOne(tripId);
                if(!trip.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip");
                    return ViewNames.ACCESS_DENIED;
                }
            } catch (NoResultException e) {
                log.info("Trip could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        tripItemService.delete((Flight) tripItem);
        return "redirect:" + ViewNames.TRIP_DETAILS + "?tripId=" + tripId.toString();
    }

}
