package dev.phasterinc.tripdat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * Name: TripdatTripItemDao - model the database table tripdat_trip_item
 */

@Data
@EqualsAndHashCode(of="tripItemId")
@Entity(name = "TripdatTripItem")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="tripdat_trip_item")
public abstract class TripdatTripItem implements Serializable {

    // == fields ==
    @Id
    @SequenceGenerator(name = "tripItem_generator", sequenceName = "tripdat_trip_item_trip_item_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "tripItem_generator")
    @Column(name = "trip_item_id", columnDefinition = "BIGINT")
    private Long tripItemId;

    @ManyToOne(fetch = FetchType.EAGER)
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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Attendee> attendees = new ArrayList<>();

    @OneToOne(
            mappedBy = "tripItem", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false
    )
    private TravelAgency travelAgency;

    @OneToOne(
            mappedBy = "tripItem", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false
    )
    private Supplier supplier;

    @OneToOne(
            mappedBy = "tripItem", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false
    )
    private BookingDetail bookingDetail;


    @Transient
    private String tripItemType;


    // TODO: must implement later :o (Is implemented as far as I am concerned. Leaving as a note for now
    // helpful link: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
        attendee.setTripItem(this);

    }

    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
        attendee.setTripItem(null);

    }

    public abstract String getType();

    @Override
    public String toString() {
        return "TripdatTripItem{" +
                "tripItemId=" + tripItemId +
                ", tripId=" + tripdatTrip.getTripId() +
                ", user=" + user +
                ", tripItemNote='" + tripItemNote + '\'' +
                ", tripItemPhotoLink='" + tripItemPhotoLink + '\'' +
                ", attendees=" + attendees +
                ", travelAgency=" + travelAgency +
                ", supplier=" + supplier +
                ", bookingDetail=" + bookingDetail +
                ", tripItemType='" + tripItemType + '\'' +
                '}';
    }
}
