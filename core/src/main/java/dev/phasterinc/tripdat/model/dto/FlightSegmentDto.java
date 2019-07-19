package dev.phasterinc.tripdat.model.dto;


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


}
