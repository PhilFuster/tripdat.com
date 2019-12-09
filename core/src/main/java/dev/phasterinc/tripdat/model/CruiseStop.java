package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.IDateAndTimeSpan;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;



/**
 * Name: CruiseStop
 * Purpose: Represents a stop during a Cruise.
 */

@Entity(name = "CruiseStop")
@Table(name = "cruise_stop")
@Data
public class CruiseStop implements Serializable, IDateAndTimeSpan {

    // == fields ==
    @Id
    @SequenceGenerator(name = "cruiseStop_generator", sequenceName = "cruise_stop_cruise_stop_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cruiseStop_generator")
    @Column(name = "cruise_stop_id", columnDefinition = "BIGINT")
    private Long cruiseStopId;


    @Column(name = "cruise_stop_port_name", columnDefinition = "TEXT")
    private String cruiseStopPortName;

    @Column(name = "cruise_stop_port_address", columnDefinition = "TEXT")
    private String cruiseStopPortAddress;

    @Column(name = "cruise_stop_arrival_date", columnDefinition = "DATE")
    private LocalDate cruiseStopArrivalDate;

    @Column(name = "cruise_stop_arrival_time", columnDefinition = "TIME WITHOUT TIME ZONE")
    private LocalTime cruiseStopArrivalTime;

    @Column(name = "cruise_stop_departure_date", columnDefinition = "DATE")
    private LocalDate cruiseStopDepartureDate;

    @Column(name = "cruise_stop_departure_time", columnDefinition = "TIME WITHOUT TIME ZONE")
    private LocalTime cruiseStopDepartureTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_item_id")
    private Cruise cruise;

    /**
     * Name: getStartDate
     * Purpose: To retrieve the start date of Cruise Stop
     * Synopsis: Implements the IDateAndTimeSpan interface method getStartDate
     * <p>
     *
     * @return LocalDate, the start date of cruise stop
     */
    @Override
    public LocalDate getStartDate() {
        return cruiseStopArrivalDate;
    }

    /**
     * Name: getEndDate
     * Purpose: Retrieve the departure date for a cruise stop
     * Synopsis: Implements the IDateAndTimeSpan interface method getEndDate
     * <p>
     *
     * @return LocalDate, the departure date of a cruise stop
     */
    @Override
    public LocalDate getEndDate() {
        return cruiseStopDepartureDate;
    }

    /**
     * Name: getStartTime
     * Purpose: Retrieve the arrival time for a cruise stop
     * Synopsis: Implements the IDateAndTimeSpan interface method getStartTime
     * <p>
     *
     * @return LocalTime, the arrival time for a cruise stop
     */
    @Override
    public LocalTime getStartTime() {
        return cruiseStopArrivalTime;
    }

    
    @Override
    public LocalTime getEndTime() {
        return null;
    }

    /**
     * Name: equals
     * Purpose: To override the default equals method functionality.
     * Synopsis: Overrides the default equals method functionality.
     * <p>
     *
     * @return Boolean, True if the CruiseStop equals the passed CruiseStop.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CruiseStop)) return false;

        return cruiseStopId != null && cruiseStopId.equals((((CruiseStop) o).getCruiseStopId()));
    }

    /**
     * Name: hashCode
     * Purpose: To override the default hashCode method.
     * Synopsis: Overrides the default hashCode method.
     * <p>
     *
     * @return int, The hashed key.
     */
    @Override
    public int hashCode() {
        return 31;
    }

}
