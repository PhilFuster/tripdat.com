package dev.phasterinc.tripdat.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: BookingDetail
 * Purpose: Store the booking details for a trip Item
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@EqualsAndHashCode(of = "bookingDetailId")// unsure after removing bookingDetail ref TravelAgency.java for final decision
@Entity(name = "BookingDetail")
@Table(name = "booking_detail")
public class BookingDetail implements Serializable {

    // == field ==
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_item_id")
    @MapsId
    private TripdatTripItem tripItem;

    @Column(name = "booking_site_name", columnDefinition = "TEXT")
    private String bookingSiteName;

    @Column(name = "booking_site_url", columnDefinition = "TEXT")
    private String bookingSiteUrl;

    @Column(name = "booking_date", columnDefinition = "DATE")
    private LocalDate bookingDate;

    @Column(name = "booking_reference_number", columnDefinition = "TEXT")
    private String bookingReferenceNumber;

    @Column(name = "booking_site_phone_number", columnDefinition = "TEXT")
    private String bookingSitePhoneNumber;

    @Column(name = "booking_site_email", columnDefinition = "TEXT")
    private String bookingSiteEmail;

    @Column(name = "booking_total_cost", columnDefinition = "TEXT")
    private String bookingTotalCost;

    @Column(name = "booking_is_purchased", columnDefinition = "BOOLEAN")
    private Boolean bookingIsPurchased;

    /**
     * Name: toString
     * Purpose: To override default toString method
     * Synopsis: Overrides default toString method
     * <p>
     *
     * @return String that represents the BookingDetail entity.
     */
    @Override
    public String toString() {
        return "BookingDetail{" +
                "id=" + id +
                ", bookingSiteName='" + bookingSiteName + '\'' +
                ", bookingSiteUrl='" + bookingSiteUrl + '\'' +
                ", bookingDate=" + bookingDate +
                ", bookingReferenceNumber='" + bookingReferenceNumber + '\'' +
                ", bookingSitePhoneNumber='" + bookingSitePhoneNumber + '\'' +
                ", bookingSiteEmail='" + bookingSiteEmail + '\'' +
                ", bookingTotalCost='" + bookingTotalCost + '\'' +
                ", bookingIsPurchased=" + bookingIsPurchased +
                '}';
    }
}
