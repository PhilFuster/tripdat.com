package dev.phasterinc.tripdat.model;

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
 * Name: FlightInformation - model flight_information table in db
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "FlightInformation")
@Table(name = "flight_information")
public class FlightInformation extends TripdatTripItem implements Serializable {

    // == fields ==
    @Column(name = "flight_confirmation_number", columnDefinition = "TEXT")
    private String flightConfirmationNumber;

    @OneToMany(
     mappedBy = "flightInformation",
     cascade = CascadeType.ALL,
     orphanRemoval = true
    )
    // helps solve error where hibernate does not let there be more than 1 collections in a entity
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FlightSegment> flightSegments = new ArrayList<>();


    @PostConstruct
    public void initTripItemType() {
        System.out.println("Initialized tripItemType for flightInformation object");
        super.setTripItemType("F");


    }

    @Override
    public String toString() {
        return "FlightInformation{" +
                "flightConfirmationNumber='" + flightConfirmationNumber + '\'' +
                ", flightSegments=" + flightSegments +
                '}';
    }

    public FlightInformation(){
        System.out.println("Flight Information Item type set");
        super.setTripItemType("F");
    }

}
