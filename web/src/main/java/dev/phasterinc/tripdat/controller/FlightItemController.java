package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.factory.TripdatTripItemFactory;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
    public void flightItemDto(Model model, @RequestParam(defaultValue = "-1", required = false) Long tripItemId) {
        FlightItemDto flightItemDto;
        List<FlightSegment> flightSegments;
        TripdatTripItem tripItem;
        Flight flight;
        List<FlightSegmentDto> segmentDtos = new ArrayList<>();
        FlightSegmentDto segmentDto1;
        FlightSegmentDto segmentDto2;

        // Creating a trip
        if(tripItemId < 0) {
            flightItemDto = FlightItemDto.builder().build();
            segmentDto1 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
            segmentDto2 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
            segmentDtos.add(segmentDto1);
            segmentDtos.add(segmentDto2);
            flightItemDto.setSegmentDtos(segmentDtos);
        } else {
            // find tripItem
            tripItem = tripItemService.findByItemId(tripItemId);
            flight = (Flight) tripItem;
            flightSegments = flight.getFlightSegments();
            if(flightSegments.isEmpty()) {
                segmentDto1 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
                segmentDto2 = FlightSegmentDto.builder().departureDate(LocalDate.now()).arrivalDate(LocalDate.now()).build();
                segmentDtos.add(segmentDto1);
                segmentDtos.add(segmentDto2);
            } else {
                for(FlightSegment segment: flightSegments) {
                    FlightSegmentDto flightSegmentDto = FlightSegmentDto.buildDto(segment);
                    segmentDtos.add(flightSegmentDto);
                }
            }
            flightItemDto = FlightItemDto.buildDto(flight, segmentDtos);
        }
        log.info("FlightDto: {}", flightItemDto);
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
}
