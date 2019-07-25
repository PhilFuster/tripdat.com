package dev.phasterinc.tripdat.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: Flight - model flight_information table in db
 *
 *
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Flight")
@Table(name = "flight")
public class Flight extends TripdatTripItem implements Serializable {

    // == fields ==
    @Column(name = "flight_confirmation_number", columnDefinition = "TEXT")
    private String flightConfirmationNumber;

    @OneToMany(
     mappedBy = "flight",
     cascade = CascadeType.ALL,
     orphanRemoval = true
    )
    // helps solve error where hibernate does not let there be more than 1 collections in a entity
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FlightSegment> flightSegments = new ArrayList<>();


    @PostConstruct
    public void initTripItemType() {
        super.setTripItemType("F");
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightConfirmationNumber='" + flightConfirmationNumber + '\'' +
                ", flightSegments=" + flightSegments +
                '}';
    }

    /*public Flight(){
        super.setTripItemType("F");
    }*/

}
