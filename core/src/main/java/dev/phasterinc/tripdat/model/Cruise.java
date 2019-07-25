package dev.phasterinc.tripdat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: Cruise - model cruise_information table in db. Extends TripdatTripItem and implements Serializable
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Cruise")
@Table(name = "cruise")
public class Cruise extends TripdatTripItem implements Serializable {
    // == fields ==
    @Column(name = "cruise_line_name", columnDefinition = "TEXT")
    private String cruiseLineName;

    @Column(name = "cruise_ship_name", columnDefinition = "TEXT")
    private String cruiseShipName;

    @Column(name = "cruise_confirmation_number", columnDefinition = "TEXT")
    private String cruiseConfirmationNumber;

    @Column(name = "cruise_start_port_name", columnDefinition = "TEXT")
    private String cruiseStartPortName;

    @Column(name = "cruise_start_port_address", columnDefinition = "TEXT")
    private String cruiseStartPortAddress;

    @Column(name = "cruise_start_date", columnDefinition = "DATE")
    private LocalDate cruiseStartDate;

    @Column(name = "cruise_start_time", columnDefinition = "TIME WITHOUT TIME ZONE")
    private LocalTime cruiseStartTime;

    @Column(name = "cruise_start_time_zone", columnDefinition = "TEXT")
    private String cruiseStartTimeZone;

    @Column(name = "cruise_is_start_location_same_as_end", columnDefinition = "BOOLEAN")
    private String cruiseIsStartLocationSameAsEnd;

    @Column(name = "cruise_end_port_name", columnDefinition = "TEXT")
    private String cruiseEndPortName;

    @Column(name = "cruise_end_port_address", columnDefinition = "TEXT")
    private String cruiseEndPortAddress;

    @Column(name = "cruise_end_date", columnDefinition = "DATE")
    private String cruiseEndDate;

    @Column(name = "cruise_end_time", columnDefinition = "TIME WITHOUT TIME ZONE")
    private LocalTime cruiseEndTime;

    @Column(name = "cruise_end_time_zone", columnDefinition = "TEXT")
    private String cruiseEndTimeZone;

    @Column(name = "cruise_cabin_number", columnDefinition = "TEXT")
    private String cruiseCabinNumber;

    @Column(name = "cruise_dining_information", columnDefinition = "TEXT")
    private String cruiseDiningInformation;

    @OneToMany(
            mappedBy = "cruise",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CruiseStop> cruiseStops = new ArrayList<>();


    // == public methods ==
    public void addStop(CruiseStop stop) {
        cruiseStops.add(stop);
        stop.setCruise(this);
    }

    public void removeStop(CruiseStop stop) {
        cruiseStops.remove(stop);
        stop.setCruise(null);
    }
}
