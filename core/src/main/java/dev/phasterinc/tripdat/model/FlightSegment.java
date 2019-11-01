package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Name: FlightSegment
 * Purpose: Represents a FlightSegment instance.
 */

@Entity(name = "FlightSegment")
@Table(name = "flight_segment")
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
public class FlightSegment implements Serializable {

    // == fields ==
    @Id
    @SequenceGenerator(name = "flightSegment_generator", sequenceName = "flight_segment_flight_segment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flightSegment_generator")
    @Column(name = "flight_segment_id", columnDefinition = "BIGINT")
    private Long flightSegmentId;

    @Column(name = "airline_name", columnDefinition = "TEXT")
    private String airlineName;

    @Column(name = "flight_number", columnDefinition = "TEXT")
    private String flightNumber;

    @Column(name = "flight_ticket_number", columnDefinition = "TEXT")
    private String flightTicketNumber;

    @Column(name = "flight_departure_airport", columnDefinition = "TEXT")
    private String flightDepartureAirport;

    @Column(name = "flight_departure_date", columnDefinition = "DATE")
    private LocalDate flightDepartureDate;

    @Column(name = "flight_departure_time", columnDefinition = "TIME")
    private LocalTime flightDepartureTime;

    @Column(name = "flight_departure_terminal", columnDefinition = "TEXT")
    private String flightDepartureTerminal;

    @Column(name = "flight_departure_gate", columnDefinition = "TEXT")
    private String flightDepartureGate;

    @Column(name = "flight_arrival_airport", columnDefinition = "TEXT")
    private String flightArrivalAirport;

    @Column(name = "flight_arrival_date", columnDefinition = "DATE")
    private LocalDate flightArrivalDate;

    @Column(name = "flight_arrival_time", columnDefinition = "TIME")
    private LocalTime flightArrivalTime;

    @Column(name = "flight_arrival_time_zone", columnDefinition = "TEXT")
    private String flightArrivalTimeZone;

    @Column(name = "flight_arrival_terminal", columnDefinition = "TEXT")
    private String flightArrivalTerminal;

    @Column(name = "flight_arrival_gate", columnDefinition = "TEXT")
    private String flightArrivalGate;

    @Column(name = "flight_fare_class", columnDefinition = "TEXT")
    private String flightFareClass;

    @Column(name = "flight_meal", columnDefinition = "TEXT")
    private String flightMeal;

    @Column(name = "flight_baggage_claim", columnDefinition = "TEXT")
    private String flightBaggageClaim;

    @Column(name = "flight_entertainment", columnDefinition = "TEXT")
    private String flightEntertainment;

    @Column(name = "flight_on_time_percentage", columnDefinition = "TEXT")
    private String flightOnTimePercentage;

    @Column(name = "flight_aircraft_type", columnDefinition = "TEXT")
    private String flightAircraftType;

    @Column(name = "operating_flight_number", columnDefinition = "TEXT")
    private String operatingFlightNumber;

    @Column(name = "flight_operated_by", columnDefinition = "TEXT")
    private String flightOperatedBy;

    @Column(name = "flight_stops", columnDefinition = "TEXT")
    private String flightStops;

    @Column(name = "flight_duration", columnDefinition = "TEXT")
    private String flightDuration;

    @Column(name = "flight_distance", columnDefinition = "TEXT")
    private String flightDistance;

    @Column(name = "flight_segment_notes", columnDefinition = "TEXT")
    private String flightSegmentNotes;

    @Column(name = "flight_seats", columnDefinition = "TEXT")
    private String flightSeats;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_item_id")
    private Flight flight;

    /**
     * Name: equals
     * Purpose: Overriding the default equals method.
     * Synopsis: Does a comparison between the caller's ID and the passed in objects id.
     * <p>
     *
     * @param o Object that the comparison will be done on.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightSegment)) return false;

        return flightSegmentId != null && flightSegmentId.equals((((FlightSegment) o).getFlightSegmentId()));


    }

    /**
     * Name: hashCode
     * Purpose: Override's default hashCode implementation.
     * Synopsis: By giving all instances of FlightSegment the same Hashcode, comparison is possible.
     * <p>
     */
    @Override
    public int hashCode() {
        return 31;
    }


    /**
     * Name: toString
     * Purpose: Overrides the default implementation of toString method
     * <p>
     */
    @Override
    public String toString() {
        return "FlightSegment{" +
                "flightSegmentId=" + flightSegmentId +
                ", airlineName='" + airlineName + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightTicketNumber='" + flightTicketNumber + '\'' +
                ", flightDepartureAirport='" + flightDepartureAirport + '\'' +
                ", flightDepartureDate=" + flightDepartureDate +
                ", flightDepartureTime=" + flightDepartureTime +
                ", flightDepartureTerminal='" + flightDepartureTerminal + '\'' +
                ", flightDepartureGate='" + flightDepartureGate + '\'' +
                ", flightArrivalAirport='" + flightArrivalAirport + '\'' +
                ", flightArrivalDate=" + flightArrivalDate +
                ", flightArrivalTime=" + flightArrivalTime +
                ", flightArrivalTimeZone='" + flightArrivalTimeZone + '\'' +
                ", flightArrivalTerminal='" + flightArrivalTerminal + '\'' +
                ", flightArrivalGate='" + flightArrivalGate + '\'' +
                ", flightFareClass='" + flightFareClass + '\'' +
                ", meal='" + flightMeal + '\'' +
                ", flightBaggageClaim='" + flightBaggageClaim + '\'' +
                ", flightEntertainment='" + flightEntertainment + '\'' +
                ", flightOnTimePercentage='" + flightOnTimePercentage + '\'' +
                ", flightAircraftType='" + flightAircraftType + '\'' +
                ", operatingFlightNumber='" + operatingFlightNumber + '\'' +
                ", flightOperatedBy='" + flightOperatedBy + '\'' +
                ", flightStops='" + flightStops + '\'' +
                ", flightDuration='" + flightDuration + '\'' +
                ", flightDistance='" + flightDistance + '\'' +
                ", flightSegmentNotes='" + flightSegmentNotes + '\'' +
                ", flightSeats='" + flightSeats + '\'' +
                '}';
    }
}
