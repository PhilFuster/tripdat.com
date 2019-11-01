package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.*;

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
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Entity(name = "CarRental")
@Table(name = "car_rental")
public class CarRental extends TripdatTripItem implements Serializable {
    // == fields ==


    @Column(name = "car_rental_confirmation_number", columnDefinition = "TEXT")
    private String carRentalConfirmationNumber;

    @Column(name = "car_rental_type", columnDefinition = "TEXT")
    private String carRentalType;

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

    @Column(name = "car_rental_mileage_charges", columnDefinition = "TEXT")
    private String carRentalMileageCharges;

    @Column(name = "car_rental_details", columnDefinition = "TEXT")
    private String carRentalDetails;

    /**
     * Name: getType
     * Purpose: Used to identify the CarRental Type of TridatTripItem
     * Synopsis: Returns the type of TripdatTripItem.
     * <p>
     *
     * @return String represents the CarRental TripdatTripItem type.
     */
    @Override
    public String getType() {
        return "CR";
    }


}
