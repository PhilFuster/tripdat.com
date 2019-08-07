package dev.phasterinc.tripdat.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.rowset.internal.Row;
import dev.phasterinc.tripdat.factory.TripdatTripItemFactory;
import dev.phasterinc.tripdat.model.*;
import dev.phasterinc.tripdat.model.dto.*;
import dev.phasterinc.tripdat.service.TripItemWrapperService;
import dev.phasterinc.tripdat.service.TripdatTripItemService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
 * ClassName: TripdatTripItemController
 * Purpose: Controller for Trip Item Mappings.
 *          Has functionality for Item CRUD pages such as EDIT, CREATE, DELETE items.
 */

@Slf4j
@Controller
@ControllerAdvice
@SessionAttributes({"flight","tripId"})
public class FlightItemController {
    // == fields ==
    private TripdatTripService tripService;
    private TripdatTripItemService tripItemService;
    private TripItemWrapperService tripItemWrapperService;

    // == constructors ==
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
     * @param tripItemId - Id of the tripItem if editing an existing trip
     * @param tripId - Id of the Trip used to get back to the trip Details when done
     *               editing/creating an item
     * Algorithm:
     *               1. Declare variables being used
     *               2. Initialize Dto lists
     *               3. If creating a trip
     *               4.   Initialize the flightItemDto
     *               5. else editing a trip
     *               6.   Find the Entity being edited
     *               7.   get the flightSegments
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
    @ModelAttribute("flight")
    public FlightItemDto flightItemDto(@RequestParam(defaultValue = "-1", required = false) Long tripItemId,
                              @RequestParam(defaultValue = "-1", required = false) Long tripId) {
        if(tripId == -1) {
            log.info("No TripId Passed to flightItemController");
        }
        FlightItemDto flightItemDto;
        List<FlightSegment> flightSegments;
        TripdatTripItem tripItem;
        Flight flight;
        List<Attendee> attendees;
        List<FlightSegmentDto> segmentDtos = new ArrayList<>();
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        // Creating a trip
        boolean creatingTrip = tripItemId < 0;
        if(creatingTrip) {
            flightItemDto = FlightItemDto.builder()
                    .itemId((long) -1)
                    .attendees(new ArrayList<AttendeeDto>())
                    .travelAgencyDto(TravelAgencyDto.builder().build())
                    .supplierDto(SupplierDto.builder().build())
                    .bookingDetailDto(BookingDetailDto.builder().build())
                    .build();
            segmentDtos.add(FlightSegmentDto.builder().build());
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
            // No Flight segments for this trip? Add two.
            if(flightSegments.isEmpty()) {
                segmentDtos.add(FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build());
                segmentDtos.add(FlightSegmentDto.builder().build());
                segmentDtos.add(FlightSegmentDto.builder().build());
                segmentDtos.add(FlightSegmentDto.builder().build());
            } else {
                for(FlightSegment segment: flightSegments) {
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
        log.info("FlightDto: {}", flightItemDto);
        return flightItemDto;
    }
    /**
     * Name: showFlightItemForm
     * Purpose: Display a form for a Flight trip Item to the user to edit or create a new Flight
     */
    @GetMapping(value = {Mappings.EDIT_FLIGHT, Mappings.CREATE_FLIGHT})
    public String showFlightItemForm(Model model,
                                     @RequestParam(required = false, defaultValue = "-1") Long tripItemId,
                                     @ModelAttribute("flight") FlightItemDto flight) {
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
    @RequestMapping(value = {Mappings.EDIT_FLIGHT, Mappings.CREATE_FLIGHT}, method = RequestMethod.POST, params = {"itemId"})
    public String processFlight(@ModelAttribute("flight") FlightItemDto flightDto,
                                @RequestParam(required = false, defaultValue = "-1") Long itemId,
                                @RequestParam Long tripId, BindingResult result) {
        log.info("TripId passed to processFlight: {}", tripId.toString());
        log.info("flightDto: {} ", flightDto);
        TripdatTrip trip = tripService.findOne(tripId);
        LocalDate tripStartDate = trip.getTripStartDate();
        LocalDate tripEndDate = trip.getTripEndDate();
        // Verify that each segment's start date is not after the item's end date
        for(int i = 0; i < flightDto.getSegmentDtos().size();++i) {
            FlightSegmentDto segmentDto = flightDto.getSegmentDtos().get(i);

            boolean isDateRangeNull = (segmentDto.getDepartureDate() == null || segmentDto.getArrivalDate() == null);
            if (!isDateRangeNull) {
                //
                // DepartureDate cannot be after arrival Date
                if( segmentDto.getDepartureDate().isAfter(segmentDto.getArrivalDate())) {
                    result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                            "Start date must be before end date.");
                    result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                            "End date must be after start date.");
                }
                // Segment must be within Trips date range
                if(segmentDto.getDepartureDate().isAfter(tripStartDate) || segmentDto.getArrivalDate().isAfter(tripEndDate)) {
                    result.rejectValue("segmentDtos[" + i + "].departureDate", null,
                            "Date does not fall within Trip date range.");
                    result.rejectValue("segmentDtos[" + i + "].arrivalDate", null,
                            "Date does not fall within Trip date range.");
                }
                // Segment cannot have conflicting date with other trip Items

            }
            if (result.hasErrors()) {
                return  itemId == -1 ? ViewNames.CREATE_FLIGHT:ViewNames.EDIT_FLIGHT;
            }
        }
        return "redirect:" + ViewNames.TRIP_DETAILS + "/" + tripId.toString();
    }
}
