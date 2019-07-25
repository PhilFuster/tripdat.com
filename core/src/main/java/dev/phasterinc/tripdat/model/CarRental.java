package dev.phasterinc.tripdat.model;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Name: CarRental
 * Purpose: Derived class from TripdatTripItemDao. Models a CarRental tripItem
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

    @Column(name = "car_rental_pick_up_date", columnDefinition = "DATE")
    private LocalDate carRentalPickUpDate;

    @Column(name = "car_rental_pick_up_time", columnDefinition = "TIME")
    private LocalTime carRentalPickUpTime;

    @Column(name = "car_rental_drop_off_date", columnDefinition = "DATE")
    private LocalDate carRentalDropOffDate;

    @Column(name = "car_rental_drop_off_time", columnDefinition = "TIME")
    private LocalTime carRentalDropOffTime;

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

    public CarRental() {
        System.out.println("CarRental Item type set");
        super.setTripItemType("CR");
    }




}
