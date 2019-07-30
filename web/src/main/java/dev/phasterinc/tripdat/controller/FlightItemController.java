package dev.phasterinc.tripdat.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import dev.phasterinc.tripdat.factory.TripdatTripItemFactory;
import dev.phasterinc.tripdat.model.Attendee;
import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.FlightSegment;
import dev.phasterinc.tripdat.model.TripdatTripItem;
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
    @ModelAttribute
    public void flightItemDto(Model model, @RequestParam(defaultValue = "-1", required = false) Long tripItemId,
                              @RequestParam(defaultValue = "-1", required = false) Long tripId) {
        FlightItemDto flightItemDto;
        List<FlightSegment> flightSegments;
        TripdatTripItem tripItem;
        Flight flight;
        List<FlightSegmentDto> segmentDtos = new ArrayList<>();
        FlightSegmentDto segmentDto1;
        FlightSegmentDto segmentDto2;
        // Creating a trip
        if(tripItemId < 0) {
            flightItemDto = FlightItemDto.builder().attendees(new ArrayList<Attendee>()).build();
            segmentDto1 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
            segmentDto2 = FlightSegmentDto.builder().build();
            segmentDtos.add(segmentDto1);
            segmentDtos.add(segmentDto2);
            flightItemDto.setSegmentDtos(segmentDtos);
            flightItemDto.getAttendees().add(new Attendee());
            // TODO: edit when deicision is made about attendeeDto
        } else {
            // find tripItem
            tripItem = tripItemService.findByItemId(tripItemId);
            flight = (Flight) tripItem;
            flightSegments = flight.getFlightSegments();
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
            // TODO: add an attendee if none. Will revisit when decision about attendeeDto is made..
            flightItemDto = FlightItemDto.buildDto(flight, segmentDtos);
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
        return "redirect:" + ViewNames.TRIP_DETAILS;
    }


    /**
     * addAttendee
     * @param flightItemDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = {Mappings.EDIT_FLIGHT}, params = {"addAttendee"})
    public String addAttendee(final FlightItemDto flightItemDto,  final BindingResult bindingResult) {
       flightItemDto.getAttendees().add(new Attendee()) ;
       return ViewNames.EDIT_FLIGHT;
    }


    @RequestMapping(value = {Mappings.EDIT_FLIGHT}, params = {"removeAttendee"})
    public String removeAttendee(final FlightItemDto flightItemDto,  final BindingResult bindingResult, final HttpServletRequest req) {
        final Long attendeeId = Long.valueOf(req.getParameter("removeAttendee"));

        return ViewNames.EDIT_FLIGHT;
    }

}
