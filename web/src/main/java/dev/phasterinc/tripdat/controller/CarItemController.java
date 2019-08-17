package dev.phasterinc.tripdat.controller;


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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * ClassName: CarItemController
 * Purpose: Controller for Car Item Mappings.
 *          Has functionality for Item CRUD pages such as EDIT, CREATE, DELETE items.
 */

@Slf4j
@Controller
@ControllerAdvice
public class CarItemController {
    // == fields ==
    private TripdatTripService tripService;
    private TripdatTripItemService tripItemService;
    private TripItemWrapperService tripItemWrapperService;

    // == constructors ==
    @Autowired
    public CarItemController(TripdatTripService tripdatTripService, TripdatTripItemService tripItemService,
                             TripItemWrapperService tripItemWrapperService) {
        this.tripService = tripdatTripService;
        this.tripItemService = tripItemService;
        this.tripItemWrapperService = tripItemWrapperService;
    }


    // == model attributes ==

    /**
     * Name: carItemDto
     * Purpose: Creates a carItemDto to be used when editing/creating a Car Rental
     * @param tripItemId - Id of the tripItem if editing an existing trip
     * @param tripId - Id of the Trip used to get back to the trip Details when done
     *               editing/creating an item
     * Algorithm:
     *               1. Declare variables being used
     *               2. Initialize Dto lists
     *               3. If creating a trip
     *               4.   Initialize the carItemDto
     *               5. else editing a trip
     *               6.   Find the Entity being edited
     *               7.   get the car
     *               8.   If flightSegments is empty
     *               9.     add two segmentDtos to list segmentDtos
     *               10.  else flightSegments not empty
     *               11.    for each segment in flightSegments
     *               12.      create a flightSegmentDto
     *               13.      insert dto into list segmentDtos
     *               14.  get list of Attendees from flight
     *               15.  if attendees is empty
     *               16.    add an AttendeeDto to list attendeeDtos
     *               17.  else attendees not empty
     *               18.    call AttendeeDto's static function buildDtoList
     *               19.  set flightItemDto by calling FlightItemDto's static function buildDto
     *               20. add tripId Attribute to model
     *               21. add flightItemDto to model
     */

    public CarRentalDto carRentalDto(Long tripItemId, Long tripId) {
        if(tripId == -1) {
            log.info("No TripId Passed to carItemController");
        }
        CarRentalDto carItemDto;
        TripdatTripItem tripItem;
        CarRental carRental;
        List<Attendee> attendees;
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        // Creating a trip
        boolean creatingTrip = tripItemId < 0;
        if(creatingTrip) {
            carItemDto = CarRentalDto.builder()
                    .itemId((long) -1)
                    .attendees(new ArrayList<AttendeeDto>())
                    .travelAgencyDto(TravelAgencyDto.builder().build())
                    .supplierDto(SupplierDto.builder().build())
                    .bookingDetailDto(BookingDetailDto.builder().build())
                    .build();
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.setTripId(tripId);
        } else {
            // editing a Car Item
            //
            // find tripItem
            tripItem = tripItemService.findByItemId(tripItemId);
            carRental = (CarRental) tripItem;
            attendees = carRental.getAttendees();
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
            carItemDto = CarRentalDto.buildDto(carRental, attendeeDtos);
        }

        return carItemDto;
    }
    /**
     * Name: showCarRentalForm
     * Purpose: Display a form for a Car Rental trip Item to the user to edit or create a new Car Rental
     */
    @GetMapping(value = {Mappings.EDIT_CAR_RENTAL, Mappings.CREATE_CAR_RENTAL})
    public String showCarRentalForm(Model model,
                                     @RequestParam(required = false, defaultValue = "-1") Long tripItemId,
                                     @RequestParam(defaultValue = "-1", required = false) Long tripId) {
        model.addAttribute("car", carRentalDto(tripItemId,tripId));
        // == local variables ==
        return tripItemId == -1 ? ViewNames.CREATE_FLIGHT:ViewNames.EDIT_FLIGHT;
    }

    /**
     * Name: processFlight
     * Purpose: To process a TripdatTrip form.
     * @param itemId - id of item to query for
     * @param result - results to display if there are errors
     * @return - redirect to show items if the POST was successful
     * Algorithm:
     *               1. Check the itemDto's Date ranges fall within the Trip's date range
     *               2. If there are conflicts, stop processing and return to edit form with errors.
     *               3. Check that itemDto's date ranges do not conflict with other items.
     *               4. If any do, return to form with that error.
     *               5. If no conflicts, update/create item.
     */
    @RequestMapping(value = {Mappings.EDIT_FLIGHT, Mappings.CREATE_FLIGHT}, method = RequestMethod.POST, params = {"itemId","tripId"})
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
        for(int i = 0; i < flightDto.getSegmentDtos().size();++i) {
            FlightSegmentDto newSegmentDto = flightDto.getSegmentDtos().get(i);
            boolean isDateRangeNull = (newSegmentDto.getDepartureDate() == null || newSegmentDto.getArrivalDate() == null);
            boolean isTimeRangeNull = newSegmentDto.getDepartureTime() == null || newSegmentDto.getArrivalTime() == null;
            if (!isDateRangeNull) {
                //
                // DepartureDate cannot be after arrival Date
                if( newSegmentDto.getDepartureDate().isAfter(newSegmentDto.getArrivalDate())) {
                    result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                            "Departure date must be before arrival date.");
                    result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                            "Arrival date must be after departure date.");
                }
                // TripItem must be within Trips date range
                if(newSegmentDto.getDepartureDate().isBefore(tripStartDate) || newSegmentDto.getArrivalDate().isAfter(tripEndDate)) {
                    result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                            "Date range does not fall within Trip's date range.");
                    result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                            "Date range does not fall within Trip's date range.");
                }
                //
                // Validate newItem duration does not overlap any items within trip
                for(int j = 0; j < wrappers.size();++j) {
                    TripItemWrapper currentItem = wrappers.get(j);
                    if(currentItem.getTripItemTypeCode() != "F") continue;
                    FlightSegmentDto segment = (FlightSegmentDto) currentItem.getTripItem();
                    //  if flightSegment.ID is equal to the newSegmentDto.segmentId being edited
                    // Do not cause false positive. ignore item.
                    log.info("dbSegment.segmentId: {}", segment.getSegmentId());
                    log.info("fromFormSegment.segmentId: {}", newSegmentDto.getSegmentId());
                    if( segment.getSegmentId().equals(newSegmentDto.getSegmentId())) {
                        continue;
                    }
                    // Continue if currentItem's date range is null
                    if(currentItem.getStartDate() == null || currentItem.getEndDate() == null) {
                        continue;
                    }
                    // If newItem and currentItem start on same day, and the currentItem starts and ends on same day, and
                    if(newSegmentDto.getDepartureDate().isEqual(currentItem.getStartDate())
                        && currentItem.getStartDate().isEqual(currentItem.getEndDate())) {
                        // if newItem starts and ends on same day
                        if(newSegmentDto.getDepartureDate().isEqual(newSegmentDto.getArrivalDate())) {
                            // newItem begins and ends on same day. It is possible for newItem
                            // and currentItem to not conflict as long as newItem starts after
                            // currentItem ends or ends before currentItem starts
                            /*if((newSegmentDto.getDepartureTime().isBefore(currentItem.getEndTime())
                                    && newSegmentDto.getArrivalTime().isAfter(currentItem.getStartTime()))
                                    || newSegmentDto.getArrivalTime().isAfter(currentItem.getStartTime())) {
                                result.rejectValue("segmentDtos[" + i + "].departureTime", null,
                                        "Conflict with other item in trip");
                                result.rejectValue("segmentDtos[" + i + "].arrivalTime", null,
                                        "Conflict with other item in trip");
                            }*/
                            if(!isTimeRangeNull && (currentItem.getStartTime() != null && currentItem.getEndTime() != null)) {
                                if(newSegmentDto.getArrivalTime().isAfter(currentItem.getStartTime())
                                        || newSegmentDto.getDepartureTime().isBefore(currentItem.getEndTime())) {
                                    result.rejectValue("segmentDtos[" + i + "].departureTime", null,
                                            "Time Conflict with other item in trip");
                                    result.rejectValue("segmentDtos[" + i + "].arrivalTime", null,
                                            "Time Conflict with other item in trip");
                                }
                            }
                        }
                               // newItem and currentItem start on same day
                    } else if(newSegmentDto.getDepartureDate().isEqual(currentItem.getStartDate())
                                // currentItem does not start and end on same day
                                && !currentItem.getStartDate().isEqual(currentItem.getEndDate())
                                // newItem starts and ends on same day
                                && newSegmentDto.getDepartureDate().isEqual(newSegmentDto.getArrivalDate())) {
                        // newItem must end before currentItem starts.
                        // if newItem endTime is not before currentItem's end time report error
                        if((newSegmentDto.getArrivalTime() != null && currentItem.getStartTime() != null) && !newSegmentDto.getArrivalTime().isBefore(currentItem.getStartTime())) {
                            result.rejectValue("segmentDtos[" + i + "].departureTime", null,
                                    "Time Conflict with other item in trip");
                            result.rejectValue("segmentDtos[" + i + "].arrivalTime", null,
                                    "Time Conflict with other item in trip");

                        }
                    }
                    // newItem does not start on same day as currentItem
                    // if newItem's arrivaleDate is not before the currentItem's startDate
                    // OR newItem's departureDate is not after currentItems endDate there is overlap
                    if( (currentItem.getStartDate() != null && currentItem.getEndDate() != null) && !(newSegmentDto.getArrivalDate().isBefore(currentItem.getStartDate())
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
                return  itemId == -1 ? ViewNames.CREATE_FLIGHT:ViewNames.EDIT_FLIGHT;
            }
        }

        // FlightItemDto has made it through the validation process
        // Create an entity from the flightItemDto
        TripdatTripItem flight;
        if(itemId == -1) {
            flight = FlightItemDto.buildEntity(trip,flightDto);
            flight.setUser(user.getTripdatUser());
            // create the flight
            tripItemService.create(flight);
        } else {
            // updating a flight
            flight = tripItemService.findByItemId(itemId);
            FlightItemDto.updateEntity((Flight)flight,flightDto);
            tripItemService.update(flight);
        }
        return "redirect:" + ViewNames.TRIP_DETAILS + "?tripId=" + tripId.toString();
    }

    @RequestMapping(value = {Mappings.DELETE_FLIGHT}, params = {"itemId","tripId"})
    public String deleteFlightItem(@RequestParam(required = false, defaultValue = "-1") Long itemId,
                                   @RequestParam Long tripId) {
        Flight flight = (Flight) tripItemService.findByItemId(itemId);
        tripItemService.delete(flight);
        return "redirect:" + ViewNames.TRIP_DETAILS + "?tripId=" + tripId.toString();
    }

}