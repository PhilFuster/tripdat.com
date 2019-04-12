package dev.phasterinc.tripdat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTrip - models the tripdat_trip table
 *
 */

@Data
@EqualsAndHashCode(of="tripId")
@Entity(name = "TripdatTrip")
@Table(name="tripdat_trip")
public class TripdatTrip implements Serializable {

    // == fields ===
    @Id
    @SequenceGenerator(name = "trip_generator", sequenceName = "tripdat_trip_trip_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "trip_generator")
    @Column(name = "trip_id", columnDefinition = "BIGINT")
    private Long tripId;

    @OneToMany(
            mappedBy = "tripdatTrip",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List< TripdatTripItem > tripItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
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

}
