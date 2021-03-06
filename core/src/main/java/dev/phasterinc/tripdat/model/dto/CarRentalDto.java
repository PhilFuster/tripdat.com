package dev.phasterinc.tripdat.model.dto;


import dev.phasterinc.tripdat.model.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



/**
 * Name: CarRentalDto
 * Purpose: Data Transfer Object for the CarRental table
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CarRentalDto {

    private Long itemId;

    private Long tripId;

    private String confirmationNumber;

    private String carRentalType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime pickUpTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dropOffDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime dropOffTime;

    private String pickUpLocationName;

    private String pickUpAddress;

    private String pickUpHoursOfOperation;

    private String pickUpPhoneNumber;

    private Boolean isDropOffLocationSameAsPickUp;

    private String dropOffLocationName;

    private String dropOffAddress;

    private String dropOffHoursOfOperation;

    private String dropOffPhoneNumber;

    private String mileageCharges;

    private String carDetails;

    private List<AttendeeDto> attendees;

    private String typeCode;

    private TravelAgencyDto travelAgencyDto;

    private SupplierDto supplierDto;

    private BookingDetailDto bookingDetailDto;

    /**
     * Name: isEmpty
     * Purpose: Verify if the object is empty
     * Synopsis: Determines if all the dto's attributes are empty.
     */
    public boolean isEmpty() {
        boolean allEmpty = true;
        if (this.confirmationNumber != null && !this.confirmationNumber.isEmpty()) allEmpty = false;
        if (this.carRentalType != null && !this.carRentalType.isEmpty()) allEmpty = false;
        if (this.pickUpDate != null) allEmpty = false;
        if (this.pickUpTime != null) allEmpty = false;
        if (this.dropOffDate != null) allEmpty = false;
        if (this.dropOffTime != null) allEmpty = false;
        if (this.pickUpLocationName != null && !this.pickUpLocationName.isEmpty()) allEmpty = false;
        if (this.pickUpAddress != null && !this.pickUpAddress.isEmpty()) allEmpty = false;
        if (this.pickUpHoursOfOperation != null && !this.pickUpHoursOfOperation.isEmpty()) allEmpty = false;
        if (this.pickUpPhoneNumber != null && !this.pickUpPhoneNumber.isEmpty()) allEmpty = false;
        if (this.dropOffLocationName != null && !this.dropOffLocationName.isEmpty()) allEmpty = false;
        if (this.dropOffAddress != null && !this.dropOffAddress.isEmpty()) allEmpty = false;
        if (this.dropOffHoursOfOperation != null && !this.dropOffHoursOfOperation.isEmpty()) allEmpty = false;
        if (this.dropOffPhoneNumber != null && !this.dropOffPhoneNumber.isEmpty()) allEmpty = false;
        if (this.mileageCharges != null && !this.mileageCharges.isEmpty()) allEmpty = false;
        if (this.carDetails != null && !this.carDetails.isEmpty()) allEmpty = false;
        return allEmpty;
    }

    /**
     * Name: buildDto
     * Purpose: To build a CarRentalDto instance from a CarRental entity
     * Synopsis: Build's a carRental Data Transfer Object from a CarRental entity and a
     * list of already created AttendeeDto objects.
     * <p>
     *
     * @param carRental    CarRental entity that the Data Transfer Object will be modeled after.
     * @param attendeeDtos List of AttendeeDto objects of attendees that will be
     *                     partaking in the CarRental.
     * @return CarRentalDto instance modeled after parameters passed in.
     */
    public static CarRentalDto buildDto(CarRental carRental, List<AttendeeDto> attendeeDtos) {
        CarRentalDto carRentalDto = CarRentalDto.builder()
                .itemId(carRental.getTripItemId())
                .tripId(carRental.getTripdatTrip().getTripId())
                .typeCode(carRental.getType())
                .carRentalType(carRental.getCarRentalType())
                .travelAgencyDto(TravelAgencyDto.buildDto(carRental.getTravelAgency()))
                .supplierDto(SupplierDto.buildDto(carRental.getSupplier()))
                .bookingDetailDto(BookingDetailDto.buildDto(carRental.getBookingDetail()))
                .confirmationNumber(carRental.getCarRentalConfirmationNumber())
                .pickUpDate(carRental.getCarRentalPickUpDate())
                .pickUpTime(carRental.getCarRentalPickUpTime())
                .dropOffDate(carRental.getCarRentalDropOffDate())
                .dropOffTime(carRental.getCarRentalDropOffTime())
                .pickUpLocationName(carRental.getCarRentalPickUpLocationName())
                .pickUpAddress(carRental.getCarRentalPickUpAddress())
                .pickUpHoursOfOperation(carRental.getCarRentalPickUpHoursOfOperation())
                .pickUpPhoneNumber(carRental.getCarRentalPhoneNumber())
                .dropOffLocationName(carRental.getCarRentalDropOffLocation())
                .dropOffAddress(carRental.getCarRentalDropOffAddress())
                .dropOffHoursOfOperation(carRental.getCarRentalDropOffHoursOfOperation())
                .dropOffPhoneNumber(carRental.getCarRentalDropOffPhoneNumber())
                .mileageCharges(carRental.getCarRentalMileageCharges())
                .carDetails(carRental.getCarRentalDetails())
                .build();
        carRentalDto.setAttendees(new ArrayList<>());
        carRentalDto.getAttendees().addAll(attendeeDtos);
        return carRentalDto;
    }

    /**
     * Name: buildEntity
     * Purpose: To build a CarRental entity
     * Synopsis: Build's CarRental entity from a carRentalDto & the Trip it belongs to
     * <p>
     *
     * @param carRentalDto CarRentalDto to model the entity after
     * @param trip         TripdatTrip the CarRental belongs to.
     * @return The build CarRental entity
     */
    public static CarRental buildEntity(CarRentalDto carRentalDto, TripdatTrip trip) {
        CarRental rental = CarRental.builder()
                .carRentalConfirmationNumber(carRentalDto.confirmationNumber)
                .carRentalType(carRentalDto.carRentalType)
                .carRentalDropOffAddress(carRentalDto.dropOffAddress)
                .carRentalDropOffDate(carRentalDto.dropOffDate)
                .carRentalDropOffHoursOfOperation(carRentalDto.dropOffHoursOfOperation)
                .carRentalDropOffLocation(carRentalDto.dropOffLocationName)
                .carRentalDropOffPhoneNumber(carRentalDto.dropOffPhoneNumber)
                .carRentalDropOffTime(carRentalDto.dropOffTime)
                .carRentalIsDropOffLocationSameAsPickUp(carRentalDto.isDropOffLocationSameAsPickUp)
                .carRentalPhoneNumber(carRentalDto.pickUpPhoneNumber)
                .carRentalPickUpAddress(carRentalDto.pickUpAddress)
                .carRentalPickUpDate(carRentalDto.pickUpDate)
                .carRentalPickUpHoursOfOperation(carRentalDto.pickUpHoursOfOperation)
                .carRentalPickUpLocationName(carRentalDto.pickUpLocationName)
                .carRentalPickUpTime(carRentalDto.pickUpTime)
                .carRentalDetails(carRentalDto.carDetails)
                .carRentalMileageCharges(carRentalDto.mileageCharges)
                .build();
        rental.setAttendees(new ArrayList<>());
        rental.setTripdatTrip(trip);
        // build other details
        rental.setTravelAgency(TravelAgencyDto.buildEntity(carRentalDto.getTravelAgencyDto(), rental));
        rental.setSupplier(SupplierDto.buildEntity(carRentalDto.getSupplierDto(), rental));
        rental.setBookingDetail(BookingDetailDto.buildEntity(carRentalDto.getBookingDetailDto(), rental));
        // Build Attendees list
        for (AttendeeDto attendeeDto : carRentalDto.getAttendees()) {
            if (attendeeDto.isEmpty()) {
                continue;
            }
            Attendee attendee = Attendee.builder()
                    .tripItem(rental)
                    .attendeeName(attendeeDto.getName())
                    .attendeeLoyaltyProgramNumber(attendeeDto.getLoyaltyProgramNumber())
                    .attendeeTicketNumber(attendeeDto.getTicketNumber())
                    .build();
            rental.getAttendees().add(attendee);
        }
        return rental;
    }

    /**
     * Name: updateEntity
     * Purpose: To update a CarRental entity
     * Synopsis: Updates CarRental entity with what is in a CarRental dto
     * <p>
     *
     * @param rental    CarRental entity to be updated
     * @param rentalDto CarRental dto that the entity will be updated with
     */
    public static void updateEntity(CarRental rental, CarRentalDto rentalDto) {
        TravelAgency agency = rental.getTravelAgency();
        Supplier supplier = rental.getSupplier();
        BookingDetail bookingDetail = rental.getBookingDetail();
        // update other detail entities
        TravelAgencyDto.updateEntity(agency, rentalDto.travelAgencyDto);
        SupplierDto.updateEntity(supplier, rentalDto.supplierDto);
        BookingDetailDto.updateEntity(bookingDetail, rentalDto.bookingDetailDto);
        // Update rental
        rental.setCarRentalConfirmationNumber(rentalDto.confirmationNumber);
        rental.setCarRentalType(rentalDto.carRentalType);
        rental.setCarRentalDropOffAddress(rentalDto.dropOffAddress);
        rental.setCarRentalDropOffDate(rentalDto.dropOffDate);
        rental.setCarRentalDropOffHoursOfOperation(rentalDto.dropOffHoursOfOperation);
        rental.setCarRentalDropOffLocation(rentalDto.getDropOffLocationName());
        rental.setCarRentalDropOffPhoneNumber(rentalDto.dropOffPhoneNumber);
        rental.setCarRentalDropOffTime(rentalDto.dropOffTime);
        rental.setCarRentalIsDropOffLocationSameAsPickUp(rentalDto.isDropOffLocationSameAsPickUp);
        rental.setCarRentalPhoneNumber(rentalDto.dropOffPhoneNumber);
        rental.setCarRentalPickUpAddress(rentalDto.pickUpAddress);
        rental.setCarRentalPickUpDate(rentalDto.pickUpDate);
        rental.setCarRentalPickUpHoursOfOperation(rentalDto.pickUpHoursOfOperation);
        rental.setCarRentalPickUpLocationName(rentalDto.pickUpLocationName);
        rental.setCarRentalPickUpTime(rentalDto.pickUpTime);
        rental.setCarRentalMileageCharges(rentalDto.mileageCharges);
        rental.setCarRentalDetails(rentalDto.carDetails);
        // update attendees
        for(int i = 0; i < rentalDto.getAttendees().size(); ++i) {
            AttendeeDto attendeeDto = rentalDto.getAttendees().get(i);
            if (attendeeDto.isEmpty() && attendeeDto.getId() == null) {
                continue;
            }
            Attendee attendee;
            // if i is still within attendee's range
            if (i < rental.getAttendees().size()) {
                // get attendee to be updated
                attendee = rental.getAttendees().get(i);
                // if the DTO is marked to be deleted
                if (attendeeDto.getIsAttendeeToBeDeleted()) {
                    rental.removeAttendee(attendee);
                    rentalDto.getAttendees().remove(attendeeDto);
                    continue;
                }
                // remove from attendees collection
                // update attendee
                AttendeeDto.updateEntity(attendee, attendeeDto);
            } else {
                // segment is not empty, and it is not a segment already in the
                // the Flight's segment collection.
                // Add this segment to the segment's collection
                if (attendeeDto.isEmpty()) {
                    continue;
                }
                Attendee newAttendee = AttendeeDto.buildEntity(attendeeDto, rental);
                rental.addAttendee(newAttendee);
            }
        }
    }
}
