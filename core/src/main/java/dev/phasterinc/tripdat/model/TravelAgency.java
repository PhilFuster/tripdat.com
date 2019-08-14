package dev.phasterinc.tripdat.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TravelAgency - models TravelAgency table in db
 * Purpose: To model the travel_agency table in db
 */

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@EqualsAndHashCode(of = "travelAgencyId") // I believe it will now be of="tripItemId") but not sure
@Entity(name = "TravelAgency")
@Table(name = "travel_agency")
public class TravelAgency implements Serializable {
    // == fields ==
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_item_id")
    @MapsId
    private TripdatTripItem tripItem;

    @Column(name = "travel_agency_name", columnDefinition = "TEXT")
    private String travelAgencyName;

    @Column(name = "travel_agency_url", columnDefinition = "TEXT")
    private String travelAgencyUrl;

    @Column(name = "travel_agency_confirmation_number", columnDefinition = "TEXT")
    private String travelAgencyConfirmationNumber;

    @Column(name = "travel_agency_contact_name", columnDefinition = "TEXT")
    private String travelAgencyContactName;

    @Column(name = "travel_agency_phone_number", columnDefinition = "TEXT")
    private String travelAgencyPhoneNumber;

    @Column(name = "travel_agency_email", columnDefinition = "TEXT")
    private String travelAgencyEmail;

    @Override
    public String toString() {
        return "TravelAgency{" +
                "id=" + id +
                ", travelAgencyName='" + travelAgencyName + '\'' +
                ", travelAgencyUrl='" + travelAgencyUrl + '\'' +
                ", travelAgencyConfirmationNumber='" + travelAgencyConfirmationNumber + '\'' +
                ", travelAgencyContactName='" + travelAgencyContactName + '\'' +
                ", travelAgencyPhoneNumber='" + travelAgencyPhoneNumber + '\'' +
                ", travelAgencyEmail='" + travelAgencyEmail + '\'' +
                '}';
    }
}
