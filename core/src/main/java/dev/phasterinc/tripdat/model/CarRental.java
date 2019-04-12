package dev.phasterinc.tripdat.model;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Name: CarRental
 * Purpose: Derived class from TripdatTripItem. Models a CarRental tripItem
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "CarRental")
@Table(name = "car_rental")
/*@PrimaryKeyJoinColumn(name = "trip_item_id", referencedColumnName = "trip_item_id")*/
public class CarRental extends TripdatTripItem implements Serializable {
    // == fields ==
    /*
    @Id
    @SequenceGenerator(name = "carRental_generator", sequenceName = "car_rental_car_rental_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "carRental_generator")
    @Column(name = "car_rental_id", columnDefinition = "BIGINT")
    private long carRentalId;*/


    @Column(name = "car_rental_confirmation_number", columnDefinition = "TEXT")
    private String carRentalConfirmationNumber;

    @Column(name = "car_rental_pick_up_location_name", columnDefinition = "TEXT")
    private String carRentalPickUpLocationName;

    @Column(name = "car_rental_pick_up_address", columnDefinition = "TEXT")
    private String carRentalPickUpAddress;

    @Column(name = "car_rental_pick_up_hours_of_operation", columnDefinition = "TEXT")
    private String carRentalPickUpHoursOfOperation;

    @Column(name = "car_rental_phone_number", columnDefinition = "TEXT")
    private String carRentalPhoneNumber;

    @Column(name = "car_rental_is_drop_off_location_same_as_pick_up", columnDefinition = "BOOLEAN")
    private Boolean carRentalIsDropOffLocationSameAsPickUp;

    @Column(name = "car_rental_drop_off_location_name", columnDefinition = "TEXT")
    private String carRentalDropOffLocation;

    @Column(name = "car_rental_drop_off_address", columnDefinition = "TEXT")
    private String carRentalDropOffAddress;

    @Column(name = "car_rental_drop_off_hours_of_operation", columnDefinition = "TEXT")
    private String carRentalDropOffHoursOfOperation;

    @Column(name = "car_rental_drop_off_phone_number", columnDefinition = "TEXT")
    private String carRentalDropOffPhoneNumber;


}
