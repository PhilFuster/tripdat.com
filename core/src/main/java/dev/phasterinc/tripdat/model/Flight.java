package dev.phasterinc.tripdat.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
 */
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Flight")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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


    @Override
    public String getType() {
        return "F";
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightConfirmationNumber='" + flightConfirmationNumber + '\'' +
                ", flightSegments=" + flightSegments +
                '}';
    }

    public void addSegment(FlightSegment segment) {
        flightSegments.add(segment);
        segment.setFlight(this);
    }

    public void removeSegment(FlightSegment segment) {
        flightSegments.remove(segment);
        segment.setFlight(null);
    }

}
