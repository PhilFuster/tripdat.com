package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Name: Attendee
 * Purpose: To model the attendees for a trip item.
 */

//TODO @EqualsAndHashCode(of="tripItem.id") //Might have to just override manually
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity(name = "Attendee")
@Table(name = "attendee")
public class Attendee implements Serializable {

    // == fields ==
    @Id
    @SequenceGenerator(name = "attendee_generator", sequenceName = "attendee_attendee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendee_generator")
    @Column(name = "attendee_id", columnDefinition = "BIGINT")
    private Long attendeeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_item_id", referencedColumnName = "trip_item_id")
    private TripdatTripItem tripItem;

    @Column(name = "attendee_name", columnDefinition = "TEXT")
    private String attendeeName;

    @Column(name = "attendee_loyalty_program_number", columnDefinition = "TEXT")
    private String attendeeLoyaltyProgramNumber;

    @Column(name = "attendee_ticket_number", columnDefinition = "TEXT")
    private String attendeeTicketNumber;

    /**
     * Name: equals
     * Purpose: To override the equals method.
     * Synopsis: Override's the equals method.
     * <p>
     *
     * @param o Object to verify.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendee)) return false;

        return attendeeId != null && attendeeId.equals((((Attendee) o).getAttendeeId()));


    }

    /**
     * Name: hashCode
     * Purpose: hashCode Override
     * Synopsis: Object hashes must remain consistent across state changes, thus the
     * reason for returning a default value.
     * <p>
     */
    @Override
    public int hashCode() {
        return 31;
    }
}
