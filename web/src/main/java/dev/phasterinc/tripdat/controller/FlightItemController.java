package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.model.FlightInformation;
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
        FlightInformation flight;
        List<FlightSegmentDto> segmentsDto = new ArrayList<>();

        // Creating a trip
        if(tripItemId < 0) {
            flightItemDto = FlightItemDto.builder().build();


        } else {
            // find tripItem
            tripItem = tripItemService.findByItemId(tripItemId);
            flight = (FlightInformation) tripItem;

            flightSegments = flight.getFlightSegments();

            for(FlightSegment segment: flightSegments) {
                FlightSegmentDto flightSegmentDto = FlightSegmentDto.builder()
                                                                    .id(segment.getFlightSegmentId())
                                                                    .airlineName(segment.getAirlineName())
                                                                    .flightNumber(segment.getFlightNumber())
                                                                    .ticketNumber(segment.getFlightTicketNumber())
                                                                    .departureAirport(segment.getFlightDepartureAirport())
                                                                    .departureDate(segment.getFlightDepartureDate())
                                                                    .departureTime(segment.getFlightDepartureTime())
                                                                    .departureTerminal(segment.getFlightDepartureTerminal())
                                                                    .departureGate(segment.getFlightDepartureGate())
                                                                    .arrivalAirport(segment.getFlightArrivalAirport())
                                                                    .arrivalDate(segment.getFlightArrivalDate())
                                                                    .arrivalTime(segment.getFlightArrivalTime())
                                                                    .arrivalTerminal(segment.getFlightArrivalTerminal())
                                                                    .arrivalGate(segment.getFlightArrivalGate())
                                                                    .fareClass(segment.getFlightFareClass())
                                                                    .meal(segment.getFlightMeal())
                                                                    .baggageClaim(segment.getFlightBaggageClaim())
                                                                    .entertainment(segment.getFlightEntertainment())
                                                                    .onTimePercentage(segment.getFlightOnTimePercentage())
                                                                    .aircraftType(segment.getFlightAircraftType())
                                                                    .operatingFlightNumber(segment.getOperatingFlightNumber())
                                                                    .operatedBy(segment.getFlightOperatedBy())
                                                                    .stops(segment.getFlightStops())
                                                                    .duration(segment.getFlightDuration())
                                                                    .distance(segment.getFlightDistance())
                                                                    .segmentNotes(segment.getFlightSegmentNotes())
                                                                    .seat(segment.getFlightSeats()).build();
                segmentsDto.add(flightSegmentDto);
            }
            flightItemDto = FlightItemDto.builder().itemNote(flight.getTripItemNote())
                    .itemPhotoLink(flight.getTripItemPhotoLink())
                    .attendees(flight.getAttendees())
                    .travelAgency(flight.getTravelAgency())
                    .supplier(flight.getSupplier())
                    .bookingDetail(flight.getBookingDetail())
                    .tripItemType(flight.getTripItemType())
                    .confirmationNumber(flight.getFlightConfirmationNumber())
                    .segmentDtos(segmentsDto)
                    .itemId(flight.getTripItemId())
                    .build();


        }

        log.info("FlightDto: {}", flightItemDto);
        model.addAttribute("flight", flightItemDto);


    }

    /*@ModelAttribute("segments")
    public List<FlightSegmentDto> getFlightSegments(Model model,@RequestParam(defaultValue = "-1", required = false) Long tripItemId) {


    }*/

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
        /*FlightItemDto dto;
        TripdatTripItem tripItem;
        FlightInformation flight;
        log.info("TripItemId for Dto: {}", tripItemId);
        tripItem = tripItemService.findByItemId(tripItemId);*/
        //flight = (FlightInformation) tripItem;
        // the Trip and its segments
/*        List<TripItemWrapper> wrappers;
        // the wrapper
        TripItemWrapper wrapper;*/
        // if not creating a new item
        // find it in DB and create a wrapper from the wrapper list
        if(tripItemId == -1) {
            title = "Create Flight";
            header = "Create a Flight";
            pageDescription = "Here you can enter your flight details";
            // Initialize a new Flight TripItem


        } else {
            title = "Edit Flight";
            pageDescription = "Here you can edit flight details";
            header = "Edit a Flight";
            //flight =(FlightInformation) tripItemService.findOne(tripItemId);
            /*dto = FlightItemDto.builder().itemNote(flight.getTripItemNote())
                    .itemPhotoLink(flight.getTripItemPhotoLink())
                    .attendees(flight.getAttendees())
                    .travelAgency(flight.getTravelAgency())
                    .supplier(flight.getSupplier())
                    .bookingDetail(flight.getBookingDetail())
                    .tripItemType(flight.getTripItemType())
                    .confirmationNumber(flight.getFlightConfirmationNumber())
                    .flightSegments(flight.getFlightSegments())
                    .itemId(flight.getTripItemId())
                    .build();*/
        }
        model.addAttribute("header", header);
        model.addAttribute("title", title);
        model.addAttribute("pageDescription", pageDescription);
        //model.addAttribute("flight", tripItem);
        return ViewNames.EDIT_FLIGHT;
    }
}
