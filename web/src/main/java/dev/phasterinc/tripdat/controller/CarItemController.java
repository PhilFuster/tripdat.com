package dev.phasterinc.tripdat.controller;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/


import dev.phasterinc.tripdat.model.*;
import dev.phasterinc.tripdat.model.dto.*;
import dev.phasterinc.tripdat.service.TripItemWrapperService;
import dev.phasterinc.tripdat.service.TripdatTripItemService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import dev.phasterinc.tripdat.util.Mappings;
import dev.phasterinc.tripdat.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: CarItemController
 * Purpose: Controller for Car Item Mappings.
 * Has end points for Item CRUD pages such as EDIT, CREATE, DELETE items.
 */

@Slf4j
@Controller
@ControllerAdvice
public class CarItemController {
    // == fields ==
    private TripdatTripService tripService;
    private TripdatTripItemService tripItemService;
    private TripItemWrapperService tripItemWrapperService;

    // == constructors ==

    /**
     * Name: CarItemController
     * Purpose: Constructor for the CarItemController class, which acts as the coordinator
     * between the model & the view
     * Synopsis: Has the Services needed injected so the class can use them.
     * <p>
     *
     * @param tripdatTripService     contains business logic for the {@Code TripdatTrip} class
     *                               like access to the dao (Data Access Object)
     * @param tripItemService        contains business logic for the {@code TripdatTripItem} class
     *                               like access to the dao
     * @param tripItemWrapperService contains business logic for the {@code TripItemWrapper}
     *                               class
     */
    @Autowired
    public CarItemController(TripdatTripService tripdatTripService, TripdatTripItemService tripItemService,
                             TripItemWrapperService tripItemWrapperService) {
        this.tripService = tripdatTripService;
        this.tripItemService = tripItemService;
        this.tripItemWrapperService = tripItemWrapperService;
    }


    // == model attributes ==

    /**
     * Name: carItemDto
     * Purpose: Creates a carItemDto to be used when editing/creating a Car Rental
     *
     * @param tripItemId - Id of the tripItem if editing an existing trip
     * @param tripId     - Id of the Trip used to get back to the trip Details when done
     *                   editing/creating an item
     *                   Algorithm:
     *                   1. Declare variables
     *                   2. Initialize Dto lists
     *                   3. If creating a trip
     *                   4.   Initialize the carItemDto
     *                   5. else editing a trip
     *                   6.   Find the Entity being edited
     *                   7.   get the car
     *                   8.   If flightSegments is empty
     *                   9.     add two segmentDtos to list segmentDtos
     *                   10.  else flightSegments not empty
     *                   11.    for each segment in flightSegments
     *                   12.      create a flightSegmentDto
     *                   13.      insert dto into list segmentDtos
     *                   14.  get list of Attendees from flight
     *                   15.  if attendees is empty
     *                   16.    add an AttendeeDto to list attendeeDtos
     *                   17.  else attendees not empty
     *                   18.    call AttendeeDto's static function buildDtoList
     *                   19.  set flightItemDto by calling FlightItemDto's static function buildDto
     *                   20. add tripId Attribute to model
     *                   21. add flightItemDto to model
     */
    public CarRentalDto carRentalDto(Long tripItemId, Long tripId) {
        if (tripId == -1) {
            log.info("No TripId Passed to carItemController");
        }
        CarRentalDto carItemDto;
        TripdatTripItem tripItem;
        CarRental carRental;
        List<Attendee> attendees;
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        TripdatTrip trip = tripService.findOne(tripId);
        // Creating a trip
        boolean creatingItem = (tripItemId < 0);
        if (creatingItem) {
            carItemDto = CarRentalDto.builder()
                    .itemId((long) -1)
                    .pickUpDate(trip.getTripStartDate())
                    .attendees(new ArrayList<AttendeeDto>())
                    .travelAgencyDto(TravelAgencyDto.builder().build())
                    .supplierDto(SupplierDto.builder().build())
                    .bookingDetailDto(BookingDetailDto.builder().build())
                    .build();
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.getAttendees().add(AttendeeDto.builder().build());
            carItemDto.setTripId(tripId);
        } else {
            // editing a Car Rental Item
            //
            // find Car Rental Item
            tripItem = tripItemService.findByItemId(tripItemId);
            carRental = (CarRental) tripItem;
            attendees = carRental.getAttendees();
            // populate 2 attendees for the User to enter if the so choose.
            if (attendees.isEmpty()) {
                attendeeDtos.add(AttendeeDto.builder().build());
                attendeeDtos.add(AttendeeDto.builder().build());
            } else {
                // Build AttendeeDtos list with attendees from Flight
                attendeeDtos = AttendeeDto.buildDtoList(attendees);
                // Add two more for user's use.
                attendeeDtos.add(AttendeeDto.builder().build());
                attendeeDtos.add(AttendeeDto.builder().build());
            }
            carItemDto = CarRentalDto.buildDto(carRental, attendeeDtos);
        }
        log.debug("carItemDto: {}", carItemDto);

        return carItemDto;
    }

    /**
     * Name: showCarRentalForm
     * Purpose: Display a form for a Car Rental trip Item to the user to edit or create a new Car Rental
     * Synopsis: Adds an existing or new CarRentalDto to the model.
     * <p>
     *
     * @param model      supplies attributes used for rendering the view
     * @param tripId     id of the {@code TripdatTrip} to reference. -1 if no id passed.
     * @param tripItemId id of the {@code TripdatTripItem} to reference. -1 if no id passed.
     *
     * @throws NoResultException when the item or trip id passed cannot be found.
     * @return String, name of view to render.
     */
    @GetMapping(value = {Mappings.EDIT_CAR_RENTAL, Mappings.CREATE_CAR_RENTAL})
    public String showCarRentalForm(Model model,
                                    @RequestParam(required = false, defaultValue = "-1", name = "itemId") Long tripItemId,
                                    @RequestParam(defaultValue = "-1", required = false, name = "tripId") Long tripId) {
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTripItem tripItem = null;
        TripdatTrip trip = null;
        // editing tripItem. Make sure it is valid & belongs to user.
        if(tripItemId != -1) {
            try {
                tripItem = tripItemService.findByItemId(tripItemId);
                if(!tripItem.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip Item");
                    return ViewNames.ACCESS_DENIED;
                }
                // Check that itemId is valid to the type of form being accessed.
                if(tripItem.getType() != "CR") {
                    log.info("Trip Item ID passed is not of type car rental.");
                    return ViewNames.BAD_LINK;
                }
            } catch (NoResultException e) {
                log.info("Trip Item could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        // Validate that TripId passed is valid, and belong to user.
        if(tripId != -1) {
            try {
                trip = tripService.findOne(tripId);
                if(!trip.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip");
                    return ViewNames.ACCESS_DENIED;
                }
            } catch (NoResultException e) {
                log.info("Trip could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        model.addAttribute("carRental", carRentalDto(tripItemId, tripId));
        // == local variables ==
        return tripItemId == -1 ? ViewNames.CREATE_CAR_RENTAL : ViewNames.EDIT_CAR_RENTAL;
    }

    /**
     * Name: processCarRental
     * Purpose: To process a TripdatTrip form.
     *
     * @param itemId - id of item to query for
     * @param result - results to display if there are errors
     * @return - redirect to show items if the POST was successful
     * Algorithm:
     * 1. Check the itemDto's Date ranges fall within the Trip's date range
     * 2. If there are conflicts, stop processing and return to edit form with errors.
     * 3. Check that itemDto's date ranges do not conflict with other items.
     * 4. If any do, return to form with that error.
     * 5. If no conflicts, update/create item.
     */
    @RequestMapping(value = {Mappings.EDIT_CAR_RENTAL, Mappings.CREATE_CAR_RENTAL}, method = RequestMethod.POST, params = {"itemId", "tripId"})
    public String processCarRental(@ModelAttribute("carRental") CarRentalDto rentalDto,
                                   @RequestParam(required = false, defaultValue = "-1") Long itemId,
                                   @RequestParam Long tripId, BindingResult result) {
        log.debug("TripId passed to processCarRental: {}", tripId.toString());
        log.debug("CarRentalDto itemId: {} ", rentalDto.getItemId());
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatTrip trip = tripService.findOne(tripId);
        LocalDate tripStartDate = trip.getTripStartDate();
        LocalDate tripEndDate = trip.getTripEndDate();
        //
        List<TripItemWrapper> wrappers = tripItemWrapperService.getItemsInItemWrapper(trip);
        tripItemWrapperService.orderItemWrappersByAscDateAndTime(wrappers);
        //
        boolean isDateRangeNull = (rentalDto.getPickUpDate() == null || rentalDto.getDropOffDate() == null);
        boolean isTimeRangeNull = rentalDto.getPickUpTime() == null || rentalDto.getDropOffTime() == null;
        //
        if (!isDateRangeNull) {
            // pickUp date cannot be after dropOff Date
            if (rentalDto.getPickUpDate().isAfter(rentalDto.getDropOffDate())) {
                result.rejectValue("pickUpDate", null,
                        "Pick up date must be before drop off date.");
                result.rejectValue("dropOffDate", null,
                        "Drop off date must be after pick up date.");
            }
            // TripItem must be within Trips date range
            if (rentalDto.getPickUpDate().isBefore(tripStartDate) || rentalDto.getDropOffDate().isAfter(tripEndDate)) {
                result.rejectValue("pickUpDate", null,
                        "Date range does not fall within Trip's date range.");
                result.rejectValue("dropOffDate", null,
                        "Date range does not fall within Trip's date range.");
            }
            //
            // Validate newItem duration does not overlap any items within trip
            for (int j = 0; j < wrappers.size(); ++j) {
                TripItemWrapper currentItem = wrappers.get(j);
                if (currentItem.getTripItemTypeCode() != "CR") continue;
                //  if flightSegment.ID is equal to the newSegmentDto.segmentId being edited
                // Do not cause false positive. ignore item.
                log.info("dbCarRental: {}", rentalDto);
                log.info("fromFormRentalID: {}", rentalDto.getItemId());
                if (rentalDto.getItemId().equals(currentItem.getId())) {
                    continue;
                }
                // Continue if currentItem's date range is null
                if (currentItem.getStartDate() == null || currentItem.getEndDate() == null) {
                    continue;
                }
                // If newItem and currentItem start on same day, and the currentItem starts and ends on same day, and
                if (rentalDto.getPickUpDate().isEqual(currentItem.getStartDate())
                        && currentItem.getStartDate().isEqual(currentItem.getEndDate())) {
                    // if newItem starts and ends on same day
                    if (rentalDto.getPickUpDate().isEqual(rentalDto.getDropOffDate())) {
                        // newItem begins and ends on same day. It is possible for newItem
                        // and currentItem to not conflict as long as newItem starts after
                        // currentItem ends or ends before currentItem starts
                        if (!isTimeRangeNull && (currentItem.getStartTime() != null && currentItem.getEndTime() != null)) {
                            if (rentalDto.getDropOffTime().isAfter(currentItem.getStartTime())
                                    || rentalDto.getPickUpTime().isBefore(currentItem.getEndTime())) {
                                result.rejectValue("pickUpTime", null,
                                        "Time Conflict with other item in trip");
                                result.rejectValue("dropOffTime", null,
                                        "Time Conflict with other item in trip");
                            }
                        }
                    }
                    // newItem and currentItem start on same day
                } else if (rentalDto.getPickUpDate().isEqual(currentItem.getStartDate())
                        // currentItem does not start and end on same day
                        && !currentItem.getStartDate().isEqual(currentItem.getEndDate())
                        // newItem starts and ends on same day
                        && rentalDto.getPickUpDate().isEqual(rentalDto.getDropOffDate())) {
                    // newItem must end before currentItem starts.
                    // if newItem endTime is not before currentItem's end time report error
                    if ((rentalDto.getDropOffTime() != null && currentItem.getStartTime() != null) && !rentalDto.getDropOffTime().isBefore(currentItem.getStartTime())) {
                        result.rejectValue("pickUpTime", null,
                                "Time Conflict with other item in trip");
                        result.rejectValue("dropOffTime", null,
                                "Time Conflict with other item in trip");

                    }
                }
                // newItem does not start on same day as currentItem
                // if newItem's pickUpDate is not before the currentItem's startDate
                // OR newItem's departureDate is not after currentItems endDate there is overlap
                if ((currentItem.getStartDate() != null && currentItem.getEndDate() != null) && !(rentalDto.getPickUpDate().isBefore(currentItem.getStartDate())
                        || rentalDto.getDropOffDate().isAfter(currentItem.getEndDate()))) {
                    result.rejectValue("pickUpDate", null,
                            "Date Conflict with other item in trip");
                    result.rejectValue("dropOffDate", null,
                            "Date Conflict with other item in trip");
                }
            }
        }
        //
        // If there are validation errors return to create or edit pages
        if (result.hasErrors()) {
            return itemId == -1 ? ViewNames.CREATE_CAR_RENTAL : ViewNames.EDIT_CAR_RENTAL;
        }

        // carRentalDto has made it through the validation process
        // Create an entity from the carItemDto
        TripdatTripItem carRental;
        if (itemId == -1) {
            carRental = CarRentalDto.buildEntity(rentalDto, trip);
            carRental.setUser(user.getTripdatUser());
            // create the car Rental
            tripItemService.create(carRental);
        } else {
            // updating a car Rental
            carRental = tripItemService.findByItemId(itemId);
            CarRentalDto.updateEntity((CarRental) carRental, rentalDto);
            tripItemService.update(carRental);
        }
        return "redirect:" + ViewNames.TRIP_DETAILS + "?tripId=" + tripId.toString();
    }

    /**
     * Name: deleteCarRentalItem
     * Purpose: End point for deleting a car item.
     * Synopsis: Finds the item by id & uses the {@code tripItemService} delete method.
     * <p>
     *
     * @param tripId Long, id of trip to reference for the item.
     * @param itemId Long, id of item to delete.
     *
     * @throws NoResultException when the itemId or tripId passed cannot be found.
     */
    @RequestMapping(value = {Mappings.DELETE_CAR_RENTAL}, params = {"itemId", "tripId"})
    public String deleteCarRentalItem(@RequestParam(required = false, defaultValue = "-1") Long itemId,
                                      @RequestParam Long tripId) {
        TripdatTripItem tripItem = null;
        TripdatTrip trip = null;
        // editing tripItem. Make sure it is valid & belongs to user.
        TripdatUserPrincipal user = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(itemId != -1) {
            try {
                tripItem = tripItemService.findByItemId(itemId);
                if(!tripItem.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip Item");
                    return ViewNames.ACCESS_DENIED;
                }
                // Check that itemId is valid to the type of form being accessed.
                if(tripItem.getType() != "CR") {
                    log.info("Trip Item ID passed is not of type car rental.");
                    return ViewNames.BAD_LINK;
                }
            } catch (NoResultException e) {
                log.info("Trip Item could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        // Validate that TripId passed is valid, and belong to user.
        if(tripId != -1) {
            try {
                trip = tripService.findOne(tripId);
                if(!trip.getUser().getUserId().equals(user.getUserId())) {
                    log.info("User does not have permission to access this Trip");
                    return ViewNames.ACCESS_DENIED;
                }
            } catch (NoResultException e) {
                log.info("Trip could not be found");
                return ViewNames.BAD_LINK;
            }
        }
        tripItemService.delete((CarRental) tripItem);
        return "redirect:" + ViewNames.TRIP_DETAILS + "?tripId=" + tripId.toString();
    }
}
