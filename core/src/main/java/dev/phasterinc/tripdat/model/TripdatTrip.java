package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * Name: TripdatTrip - models the tripdat_trip table
 */

@Data
@Entity(name = "TripdatTrip")
@Table(name = "tripdat_trip")
public class TripdatTrip implements Serializable {

    // == fields ===
    @Id
    @SequenceGenerator(name = "trip_generator", sequenceName = "tripdat_trip_trip_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_generator")
    @Column(name = "trip_id", columnDefinition = "BIGINT")
    private Long tripId;

    @OneToMany(
            mappedBy = "tripdatTrip",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TripdatTripItem> tripItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private TripdatUser user;


    @NotEmpty
    @Column(name = "trip_name", columnDefinition = "TEXT")
    private String tripName;


    @Column(name = "trip_start_date", columnDefinition = "DATE")
    private LocalDate tripStartDate;


    @Column(name = "trip_end_date", columnDefinition = "DATE")
    private LocalDate tripEndDate;

    @Column(name = "trip_description", columnDefinition = "TEXT")
    private String tripDescription;

    @Column(name = "trip_image_link", columnDefinition = "TEXT")
    private String tripImageLink;

    @Column(name = "trip_destination_city", columnDefinition = "TEXT")
    private String destinationCity;

    @Column(name = "trip_is_user_traveler", columnDefinition = "BOOLEAN")
    private Boolean isUserTraveler;

    public void addTripItem(TripdatTripItem tripItem) {
        tripItems.add(tripItem);
        tripItem.setTripdatTrip(this);
    }

    public void removeTripItem(TripdatTripItem tripItem) {
        tripItems.remove(tripItem);
        tripItem.setTripdatTrip(null);
    }

    /**
     * Name: equals
     * Purpose: Overriding the default implementation of equals method to ensure validity of comparison
     * Synopsis: When using database-generated unique identifiers, a comparison between the two objects' id
     * is what is required.
     * <p>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripdatTrip)) return false;
        TripdatTrip trip = (TripdatTrip) o;
        return tripId != null && tripId.equals(((TripdatTrip) o).getTripId());
    }

    /**
     * Name: hashCode
     * Purpose: Overrides default implementation of hashCode
     * Synopsis: When using database-generated unique identifiers, the hashcode needs to
     * be consistent across state transitions, thus the reason for returing 31.
     * <p>
     */
    @Override
    public int hashCode() {
        return 31;
    }

    /**
     * Name: toString
     * Purpose: Overrides default implementation of toString method.
     * <p>
     *
     * @return String representation of a TripdatTrip object.
     */
    @Override
    public String toString() {
        return "TripdatTrip{" +
                "tripId=" + tripId +
                ", tripItems=" + tripItems +
                ", user=" + user +
                ", tripName='" + tripName + '\'' +
                ", tripStartDate=" + tripStartDate +
                ", tripEndDate=" + tripEndDate +
                ", tripDescription='" + tripDescription + '\'' +
                ", tripImageLink='" + tripImageLink + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", isUserTraveler=" + isUserTraveler +
                '}';
    }
}
