package dev.phasterinc.tripdat.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import dev.phasterinc.tripdat.factory.TripdatTripItemFactory;
import dev.phasterinc.tripdat.model.Attendee;
import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.FlightSegment;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import dev.phasterinc.tripdat.model.dto.AttendeeDto;
import dev.phasterinc.tripdat.model.dto.FlightItemDto;
import dev.phasterinc.tripdat.model.dto.FlightSegmentDto;
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
     * @param model - model to send to the frontend
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
    @ModelAttribute
    public void flightItemDto(Model model, @RequestParam(defaultValue = "-1", required = false) Long tripItemId,
                              @RequestParam(defaultValue = "-1", required = false) Long tripId) {
        FlightItemDto flightItemDto;
        List<FlightSegment> flightSegments;
        TripdatTripItem tripItem;
        Flight flight;
        FlightSegmentDto segmentDto1;
        FlightSegmentDto segmentDto2;
        List<Attendee> attendees;
        List<FlightSegmentDto> segmentDtos = new ArrayList<>();
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        // Creating a trip
        if(tripItemId < 0) {
            flightItemDto = FlightItemDto.builder().attendees(new ArrayList<AttendeeDto>()).build();
            segmentDto1 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
            segmentDto2 = FlightSegmentDto.builder().build();
            segmentDtos.add(segmentDto1);
            segmentDtos.add(segmentDto2);
            flightItemDto.setSegmentDtos(segmentDtos);
            flightItemDto.getAttendees().add(AttendeeDto.builder().build());
        } else {
            // editing a trip
            //
            // find tripItem
            tripItem = tripItemService.findByItemId(tripItemId);
            flight = (Flight) tripItem;
            flightSegments = flight.getFlightSegments();
            // No Flight segments for this trip? Add two.
            if(flightSegments.isEmpty()) {
                segmentDto1 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
                segmentDto2 = FlightSegmentDto.builder().build();
                segmentDtos.add(segmentDto1);
                segmentDtos.add(segmentDto2);
            } else {
                for(FlightSegment segment: flightSegments) {
                    FlightSegmentDto flightSegmentDto = FlightSegmentDto.buildDto(segment);
                    segmentDtos.add(flightSegmentDto);
                }
            }
            attendees = flight.getAttendees();
            if (attendees.isEmpty()) {
                attendeeDtos.add(AttendeeDto.builder().build());
            } else {
                attendeeDtos = AttendeeDto.buildDtoList(attendees);
            }
            flightItemDto = FlightItemDto.buildDto(flight, segmentDtos, attendeeDtos);
        }
        log.info("FlightDto: {}", flightItemDto);
        model.addAttribute("tripId", tripId);
        model.addAttribute("flight", flightItemDto);
    }
    /**
     * Name: showFlightItemForm
     * Purpose: Display a form for a Flight trip Item to the user to edit or create a new Flight
     */
    @GetMapping(value = {Mappings.EDIT_FLIGHT, Mappings.CREATE_FLIGHT})
    public String showFlightItemForm(Model model, @RequestParam(required = false, defaultValue = "-1") Long tripItemId) {
        // == local variables ==
        // the Trip
        String title;
        String pageDescription;
        String header;
        // if not creating a new item
        // find it in DB and create a wrapper from the wrapper list
        if(tripItemId == -1) {
            title = "Create Flight";
            header = "Create a Flight";
            pageDescription = "Here you can enter your flight details";
        } else {
            title = "Edit Flight";
            pageDescription = "Here you can edit flight details";
            header = "Edit a Flight";
        }
        model.addAttribute("header", header);
        model.addAttribute("title", title);
        model.addAttribute("pageDescription", pageDescription);
        return ViewNames.EDIT_FLIGHT;
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
    public String processFlight(@ModelAttribute("flight") @Valid FlightItemDto flightDto,
                                @RequestParam(required = false, defaultValue = "-1") Long itemId,
                                @RequestParam Long tripId, BindingResult result) {
        log.info("TripId passed to processFlight: {}", tripId.toString());
        // Verify that each segment's start date is not after the item's end date
        for(FlightSegmentDto segmentDto: flightDto.getSegmentDtos()) {
            // DepartureDate cannot be after arrival Date
            if(segmentDto.getDepartureDate() != null && segmentDto.getDepartureDate().isAfter(segmentDto.getArrivalDate())) {
                result.rejectValue("departureDate", null,
                        "Start date must be before end date.");
                result.rejectValue("tripEndDate", null,
                        "End date must be after start date.");
                return itemId != -1 ? ViewNames.EDIT_TRIP : ViewNames.CREATE_TRIP;
            }
        }

        return "redirect:" + ViewNames.TRIP_DETAILS + "/" + tripId.toString();
    }


    /**
     * name: addAttendee
     * Purpose: Add an attendee to the FlightItemDto and
     * @param flightItemDto - flight being edited
     * @param bindingResult - results
     * @return returns User to the editFlight view
     */
    @RequestMapping(value = {Mappings.EDIT_FLIGHT}, params = {"addAttendee"})
    public String addAttendee(@ModelAttribute("flight") @Valid FlightItemDto flightDto,  final BindingResult bindingResult,@RequestParam(required = false, defaultValue = "-1") Long addAttendee) {
       flightDto.getAttendees().add(AttendeeDto.builder().build());
       return ViewNames.EDIT_FLIGHT;
    }

    /**
     * name: removeAttendee
     * purpose: Function will remove the attendee from the DTO list
     * @param flightItemDto - flightItemDto, used to remove the Attendee from the list of attendees it has
     * @param bindingResult
     * @param req
     * @return
     */
    @RequestMapping(value = {Mappings.EDIT_FLIGHT}, params = {"removeAttendee"})
    public String removeAttendee(@ModelAttribute("flight") @Valid FlightItemDto flightDto,  final BindingResult bindingResult, @RequestParam(required = false, defaultValue = "-1") Long itemId,
                                 @RequestParam(required = false, defaultValue = "-1") Long removeAttendee, final HttpServletRequest req) {
        final Long rowId = Long.valueOf(req.getParameter("removeAttendee"));
        List<AttendeeDto> dtos = flightDto.getAttendees();
        dtos.remove(rowId.intValue());
        return ViewNames.EDIT_FLIGHT;
    }

}
