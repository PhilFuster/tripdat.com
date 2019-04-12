package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Name: BookingDetail
 * Purpose: Store the booking details for a trip Item
 */
@Data
//@EqualsAndHashCode(of = "bookingDetailId")// unsure after removing bookingDetail ref TravelAgency.java for final decision
@Entity(name = "BookingDetail")
@Table(name = "booking_detail")
public class BookingDetail implements Serializable {

    // == field ==
    @Id
    @Column(name = "trip_item_id")
    private Long tripItemId;

    @OneToOne(fetch = FetchType.LAZY)
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
}
