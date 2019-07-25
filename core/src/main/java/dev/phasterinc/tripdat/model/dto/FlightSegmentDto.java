package dev.phasterinc.tripdat.model.dto;


import dev.phasterinc.tripdat.model.FlightSegment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class FlightSegmentDto {

    private Long id;

    private String airlineName;

    private String flightNumber;

    private String ticketNumber;

    private String departureAirport;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private String departureTerminal;

    private String departureGate;

    private String arrivalAirport;

    private LocalDate arrivalDate;

    private LocalTime arrivalTime;

    private String arrivalTerminal;

    private String arrivalGate;

    private String fareClass;

    private String meal;

    private String baggageClaim;

    private String entertainment;

    private String onTimePercentage;

    private String aircraftType;

    private String operatingFlightNumber;

    private String operatedBy;

    private String stops;

    private String duration;

    private String distance;

    private String segmentNotes;

    private String seat;

    public static FlightSegmentDto buildDto(FlightSegment flightSegment) {

        return FlightSegmentDto.builder()
                .id(flightSegment.getFlightSegmentId())
                .airlineName(flightSegment.getAirlineName())
                .flightNumber(flightSegment.getFlightNumber())
                .ticketNumber(flightSegment.getFlightTicketNumber())
                .departureAirport(flightSegment.getFlightDepartureAirport())
                .departureDate(flightSegment.getFlightDepartureDate())
                .departureTime(flightSegment.getFlightDepartureTime())
                .departureTerminal(flightSegment.getFlightDepartureTerminal())
                .departureGate(flightSegment.getFlightDepartureGate())
                .arrivalAirport(flightSegment.getFlightArrivalAirport())
                .arrivalDate(flightSegment.getFlightArrivalDate())
                .arrivalTime(flightSegment.getFlightArrivalTime())
                .arrivalTerminal(flightSegment.getFlightArrivalTerminal())
                .arrivalGate(flightSegment.getFlightArrivalGate())
                .fareClass(flightSegment.getFlightFareClass())
                .meal(flightSegment.getFlightMeal())
                .baggageClaim(flightSegment.getFlightBaggageClaim())
                .entertainment(flightSegment.getFlightEntertainment())
                .onTimePercentage(flightSegment.getFlightOnTimePercentage())
                .aircraftType(flightSegment.getFlightAircraftType())
                .operatingFlightNumber(flightSegment.getOperatingFlightNumber())
                .operatedBy(flightSegment.getFlightOperatedBy())
                .stops(flightSegment.getFlightStops())
                .duration(flightSegment.getFlightDuration())
                .distance(flightSegment.getFlightDistance())
                .segmentNotes(flightSegment.getFlightSegmentNotes())
                .seat(flightSegment.getFlightSeats()).build();
    }

}
