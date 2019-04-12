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

/**
 * Name: Supplier
 * Purpose: Model the table supplier in the db. Stores information of the supplier of a trip item
 */
@Data
// @EqualsAndHashCode(of = "supplierId")// unsure after removing supplierId ref TravelAgency.java
@Entity(name = "Supplier")
@Table(name = "supplier")
public class Supplier implements Serializable {
    // == fields ==
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_item_id")
    @MapsId
    private TripdatTripItem tripItem;

    @Column(name = "supplier_contact_name", columnDefinition = "TEXT")
    private String supplierContactName;

    @Column(name = "supplier_phone_number", columnDefinition = "TEXT")
    private String supplierPhoneNumber;

    @Column(name = "supplier_email", columnDefinition = "TEXT")
    private String supplierEmail;

    @Column(name = "supplier_url", columnDefinition = "TEXT")
    private String supplierUrl;

}
