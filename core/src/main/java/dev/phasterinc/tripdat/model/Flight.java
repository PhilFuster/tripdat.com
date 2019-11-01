package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Name: getType
     * Purpose: To get the type of TripItem
     * Synopsis: Return's a String representation of the item type
     * <p>
     *
     * @return String, String representation of the item type.
     */
    @Override
    public String getType() {
        return "F";
    }

    /**
     * Name: toString
     * Purpose: Get String representation of Flight object.
     * Synopsis: Returns String representatiion of Flight object.
     * <p>
     */
    @Override
    public String toString() {
        return "Flight{" +
                "flightConfirmationNumber='" + flightConfirmationNumber + '\'' +
                ", flightSegments=" + flightSegments +
                '}';
    }

    /**
     * Name: addSegment
     * Purpose: To add a FlightSegment to the member variable flightSegments.
     * Synopsis: Adds a FlightSegment object to the flightSegments member variable.
     * <p>
     *
     * @param segment, FlightSegment object to add to the collection.
     */
    public void addSegment(FlightSegment segment) {
        flightSegments.add(segment);
        segment.setFlight(this);
    }

    /**
     * Name: removeSegment
     * Purpose: To remove the FlightSegment object passed from the member variable flightSegments.
     * Synopsis: Removes the FlightSegment object passed from the member variable flightSegments.
     * <p>
     *
     * @param segment FlightSegment to remove from the collection.
     */
    public void removeSegment(FlightSegment segment) {
        flightSegments.remove(segment);
        segment.setFlight(null);
    }

}
