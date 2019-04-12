package dev.phasterinc.tripdat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * Name: TripdatTripItem - model the database table tripdat_trip_item
 */

@Data
@EqualsAndHashCode(of="tripItemId")
@Entity(name = "TripdatTripItem")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="tripdat_trip_item")
public class TripdatTripItem implements Serializable {

    // == fields ==
    @Id
    @SequenceGenerator(name = "tripItem_generator", sequenceName = "tripdat_trip_item_trip_item_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "tripItem_generator")
    @Column(name = "trip_item_id", columnDefinition = "BIGINT")
    private Long tripItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private TripdatTrip tripdatTrip;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private TripdatUser user;

    @Column(name = "trip_item_note", columnDefinition = "TEXT")
    private String tripItemNote;

    @Column(name = "trip_item_photo_link", columnDefinition="TEXT")
    private String tripItemPhotoLink;

    @OneToMany(
            mappedBy = "tripItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Attendee> attendees = new ArrayList<>();



    // TODO: must implement later :o
    // helpful link: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
        attendee.setTripItem(this);

    }

    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
        attendee.setTripItem(null);

    }

}
