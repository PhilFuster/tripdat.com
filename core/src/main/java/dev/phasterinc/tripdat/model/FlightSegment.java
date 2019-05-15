package dev.phasterinc.tripdat.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "FlightSegment")
@Table(name = "flight_segment")
@Data
public class FlightSegment implements Serializable {

    // == fields ==
    @Id
    @SequenceGenerator(name = "flightSegment_generator", sequenceName = "flight_segment_flight_segment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "flightSegment_generator")
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

    @Column(name ="flight_entertainment", columnDefinition = "TEXT")
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

    @Column(name = "flight_seat", columnDefinition = "TEXT")
    private String flightSeat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_item_id")
    private FlightInformation flightInformation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightSegment )) return false;

        return flightSegmentId != null && flightSegmentId.equals((((FlightSegment) o).getFlightSegmentId()));


    }

    @Override
    public int hashCode() {
        return 31;
    }


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
                ", flightMeal='" + flightMeal + '\'' +
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
                ", flightSeat='" + flightSeat + '\'' +
                '}';
    }
}
