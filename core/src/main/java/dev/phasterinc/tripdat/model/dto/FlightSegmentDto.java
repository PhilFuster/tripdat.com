package dev.phasterinc.tripdat.model.dto;


import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.FlightSegment;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: FlightSemgentDto
 * Purpose: FlightSegment's Data Transfer Object.
 */

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FlightSegmentDto {

    private Long itemId;

    private Long segmentId;

    private String airlineName;

    private String flightNumber;

    private String ticketNumber;

    private String departureAirport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    private String departureTerminal;

    private String departureGate;

    private String arrivalAirport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    @DateTimeFormat(pattern = "HH:mm")
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

    private String typeCode;

    private Boolean isToBeDeleted;

    /**
     * Name: isEmpty
     * Purpose: Verify if the object is empty
     * Synopsis: Determines if all the dto's attributes are empty.
     */
    public boolean isEmpty() {
        boolean allEmpty = true;
        if (this.segmentId != null) allEmpty = false;
        if (this.airlineName != null && !this.airlineName.isEmpty()) allEmpty = false;
        if (this.flightNumber != null && !this.flightNumber.isEmpty()) allEmpty = false;
        if (this.ticketNumber != null && !this.ticketNumber.isEmpty()) allEmpty = false;
        if (this.departureAirport != null && !this.departureAirport.isEmpty()) allEmpty = false;
        if (this.departureDate != null) allEmpty = false;
        if (this.departureTime != null) allEmpty = false;
        if (this.departureTerminal != null && !this.departureTerminal.isEmpty()) allEmpty = false;
        if (this.departureGate != null && !this.departureGate.isEmpty()) allEmpty = false;
        if (this.arrivalAirport != null && !this.arrivalAirport.isEmpty()) allEmpty = false;
        if (this.arrivalDate != null) allEmpty = false;
        if (this.arrivalTime != null) allEmpty = false;
        if (this.arrivalTerminal != null && !this.arrivalTerminal.isEmpty()) allEmpty = false;
        if (this.arrivalGate != null && !this.arrivalGate.isEmpty()) allEmpty = false;
        if (this.fareClass != null && !this.fareClass.isEmpty()) allEmpty = false;
        if (this.meal != null && !this.meal.isEmpty()) allEmpty = false;
        if (this.baggageClaim != null && !this.baggageClaim.isEmpty()) allEmpty = false;
        if (this.entertainment != null && !this.entertainment.isEmpty()) allEmpty = false;
        if (this.onTimePercentage != null && !this.onTimePercentage.isEmpty()) allEmpty = false;
        if (this.aircraftType != null && !this.aircraftType.isEmpty()) allEmpty = false;
        if (this.operatingFlightNumber != null && !this.operatingFlightNumber.isEmpty()) allEmpty = false;
        if (this.operatedBy != null && !this.operatedBy.isEmpty()) allEmpty = false;
        if (this.stops != null && !this.stops.isEmpty()) allEmpty = false;
        if (this.duration != null && !this.duration.isEmpty()) allEmpty = false;
        if (this.distance != null && !this.distance.isEmpty()) allEmpty = false;
        if (this.segmentNotes != null && !this.segmentNotes.isEmpty()) allEmpty = false;
        if (this.seat != null && !this.seat.isEmpty()) allEmpty = false;
        return allEmpty;
    }

    /**
     * Name: buildDto
     * Purpose: To build a FlightSegment Data Transfer Object from a FlightSegment entity
     * Synopsis: Build's a FlightSegment DTO from a FlightSegment entity
     * <p>
     *
     * @param flightSegment FlightSegment Entity the DTO will be populated with
     * @return The FlightSegmentDto instance built.
     */
    public static FlightSegmentDto buildDto(FlightSegment flightSegment) {

        return FlightSegmentDto.builder()
                .segmentId(flightSegment.getFlightSegmentId())
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
                .isToBeDeleted(false)
                .typeCode("F")
                .seat(flightSegment.getFlightSeats()).build();
    }

    /**
     * Name: buildEntity
     * Purpose:  To build a FlightSegment entity
     * Synopsis: Build's a FlightSegment entity from a FlightSegment Data Transfer object
     * & the Flight Item it belongs to
     * <p>
     *
     * @param flight     Flight Entity that will be built.
     * @param segmentDto FlightSegmentDto the Flight entity is based off of.
     * @return FlightSegment entity that is built.
     */
    public static FlightSegment buildEntity(FlightSegmentDto segmentDto, Flight flight) {

        return FlightSegment.builder()
                .flight(flight)
                .airlineName(segmentDto.getAirlineName())
                .flightNumber(segmentDto.getFlightNumber())
                .flightTicketNumber(segmentDto.getTicketNumber())
                .flightDepartureAirport(segmentDto.getDepartureAirport())
                .flightDepartureDate(segmentDto.getDepartureDate())
                .flightDepartureTime(segmentDto.getDepartureTime())
                .flightDepartureTerminal(segmentDto.getDepartureTerminal())
                .flightDepartureGate(segmentDto.getDepartureGate())
                .flightArrivalAirport(segmentDto.getArrivalAirport())
                .flightArrivalDate(segmentDto.getArrivalDate())
                .flightArrivalTime(segmentDto.getArrivalTime())
                .flightArrivalTerminal(segmentDto.getArrivalTerminal())
                .flightArrivalGate(segmentDto.getArrivalGate())
                .flightFareClass(segmentDto.getFareClass())
                .flightMeal(segmentDto.getMeal())
                .flightBaggageClaim(segmentDto.getBaggageClaim())
                .flightEntertainment(segmentDto.getEntertainment())
                .flightOnTimePercentage(segmentDto.getOnTimePercentage())
                .flightAircraftType(segmentDto.getAircraftType())
                .operatingFlightNumber(segmentDto.getOperatingFlightNumber())
                .flightOperatedBy(segmentDto.getOperatedBy())
                .flightStops(segmentDto.getStops())
                .flightDuration(segmentDto.getDuration())
                .flightDistance(segmentDto.getDistance())
                .flightSegmentNotes(segmentDto.getSegmentNotes())
                .flightSeats(segmentDto.getSeat())
                .build();

    }

    /**
     * Name: updateEntity
     * Purpose: To update a Flight entity
     * Synopsis: Update's a Flight entity with the information from a FlightSegment Dto.
     * <p>
     *
     * @param segment    FlightSegment Entity that will be updated.
     * @param segmentDto FlightSegmentDto has the information the entity will be updated with.
     */
    public static void updateEntity(FlightSegment segment, FlightSegmentDto segmentDto) {
        segment.setAirlineName(segmentDto.airlineName);
        segment.setFlightNumber(segmentDto.flightNumber);
        segment.setFlightTicketNumber(segmentDto.ticketNumber);
        segment.setFlightDepartureAirport(segmentDto.departureAirport);
        segment.setFlightDepartureDate(segmentDto.departureDate);
        segment.setFlightDepartureTime(segmentDto.departureTime);
        segment.setFlightDepartureTerminal(segmentDto.departureTerminal);
        segment.setFlightDepartureGate(segmentDto.departureGate);
        segment.setFlightArrivalAirport(segmentDto.arrivalAirport);
        segment.setFlightArrivalDate(segmentDto.arrivalDate);
        segment.setFlightArrivalTime(segmentDto.arrivalTime);
        segment.setFlightArrivalTerminal(segmentDto.arrivalTerminal);
        segment.setFlightArrivalGate(segmentDto.arrivalGate);
        segment.setFlightFareClass(segmentDto.fareClass);
        segment.setFlightMeal(segmentDto.meal);
        segment.setFlightBaggageClaim(segmentDto.baggageClaim);
        segment.setFlightEntertainment(segmentDto.entertainment);
        segment.setFlightOnTimePercentage(segmentDto.onTimePercentage);
        segment.setFlightAircraftType(segmentDto.aircraftType);
        segment.setOperatingFlightNumber(segmentDto.operatingFlightNumber);
        segment.setFlightOperatedBy(segmentDto.operatedBy);
        segment.setFlightStops(segmentDto.stops);
        segment.setFlightDuration(segmentDto.duration);
        segment.setFlightDistance(segmentDto.distance);
        segment.setFlightSegmentNotes(segmentDto.segmentNotes);
        segment.setFlightSeats(segmentDto.seat);
    }
}
