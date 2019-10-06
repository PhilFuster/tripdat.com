package dev.phasterinc.tripdat.model;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/


import dev.phasterinc.tripdat.model.dto.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: TripItemWrapper - A wrapper class to model trip information for TripItems with segments.
 *                         This wrapper will have a field, tripItem of type Object. This Object will
 *                         be casted by checking the field tripItemTypeCode:String which will identify the type
 *                         of the tripItem.
 *Purpose: To be able to store all the types of tripItems in one collection and order them by date and time.
 *         This will make it possible to render in the view.
 *
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Builder(toBuilder = false)
public class TripItemWrapper {
    // == fields ==

    // will determine the type of tripItem being dealt with and what view fragment to render
    private String tripItemTypeCode;

    // tripItem will store an object that could be any type of tripItem
    // eg: FlightSegment, RailStop, generalTransportation segment, restaurant, meeting. . .
    private Object tripItem;

    // used to order the collection of all tripItems
    private LocalDate startDate;
    private LocalTime startTime;

    // Segments do not carry the confirmation number therefore it must be stored
    // somewhere for access
    private String confirmationNumber;

    // Segments will need the Supplier, TravelAgency, and BookingDetails
    private SupplierDto supplier;
    private TravelAgencyDto travelAgency;
    private BookingDetailDto bookingDetail;

    // the tripItem id itself.
    // If the item is a segment, the object item will have the segmentId
    private Long id;

    // depending on item endDate and endTime could not be needed.
    // Could also be more like arrivalTime and arrivalDate.
    // generic information needed for most items and segments.
    // will be null and not used by view for certain items
    private LocalDate endDate;
    private LocalTime endTime;

    private List<AttendeeDto> attendees = new ArrayList<>();

    private String notes;

    // A flag telling the front end to use the arrival component when displaying an item
    private boolean isArrival;


    // == public methods ==



    public TripItemWrapper(String tripItemTypeCode, LocalDate startDate, LocalTime startTime) {
        this.tripItemTypeCode = tripItemTypeCode;
        this.startDate = startDate;
        this.startTime = startTime;


    }

    public String getSmallIconDate() {
        StringBuilder builder = new StringBuilder();
        if(null != this.startDate) {
            builder.append(startDate.getDayOfWeek().toString().substring(0,3)).append(", ").append(startDate.getMonth().toString().substring(0,3)).append(" ").append(startDate.getDayOfMonth());
        }
        return builder.toString();
    }

    public String getLegendValue() {
        return "DEPART";
    }

    public String getDepartTimeFormatted() {
        if(startTime==null) {
            return "";
        }
        return startTime.format(DateTimeFormatter.ofPattern("h:mma"));
    }

    public String getArriveTimeFormatted() {
        if(endTime==null) {
            return "";
        }
        return endTime.format(DateTimeFormatter.ofPattern("h:mma"));
    }

    public String getMainTitle() {
        StringBuilder builder = new StringBuilder();

        switch (tripItemTypeCode) {
            case "F":
                FlightSegmentDto segment = (FlightSegmentDto) tripItem;
                if(segment.getDepartureAirport() != null && segment.getArrivalAirport() !=  null) {
                    builder.append(segment.getDepartureAirport()).append(" to ").append("\n").append(segment.getArrivalAirport());
                }
                break;
            case "CR":
                CarRentalDto rental = (CarRentalDto) tripItem;
                if(rental.getSupplierDto().getSupplierName() != null) {
                    builder.append(rental.getSupplierDto().getSupplierName());
                }
                break;
        }
        return builder.toString();
    }

    public String getSubTitle() {
        StringBuilder builder = new StringBuilder();
        switch (tripItemTypeCode) {
            case "F":
                FlightSegmentDto segment = (FlightSegmentDto) tripItem;
                if(segment.getAirlineName()!= null) {
                    builder.append(segment.getAirlineName()).append(" ");
                }
                if(segment.getFlightNumber() != null) {
                    builder.append(segment.getFlightNumber());
                }
                break;
            case "CR":
                CarRentalDto rental = (CarRentalDto) tripItem;
                if(rental.getPickUpLocationName() != null) {
                    builder.append(rental.getPickUpLocationName());
                }
                break;

        }
        return builder.toString();

    }

    public String getIdAsString() {
        return Long.toString(id);

    }

    public String getDurationOfSegment() {
        String duration = new String();
        StringBuilder builder = new StringBuilder();
        if(startDate != null && startTime != null && endDate != null && endTime != null) {
            /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDT = LocalDateTime.parse(startDate.toString() + startTime.toString(), formatter);
            LocalDateTime endDT = LocalDateTime.parse(endDate.toString() + endTime.toString(), formatter);*/
            // calculating the total number of hours and minutes between two dateTime objects.

            // get days between startDate and endDate in hours
            Long daysBetween = startDate.until(endDate, ChronoUnit.DAYS);
            Long hours = daysBetween * 24;

            // get hours in day between starTime and endTime
            Long hoursInDayBetween = startTime.until(endTime, ChronoUnit.HOURS);
            hours = hours + hoursInDayBetween;

            // get minutes between starTime and endTime
            LocalTime startMins = LocalTime.of(0,startTime.getMinute());
            LocalTime endMins = LocalTime.of(0, endTime.getMinute());
            String minutes = Long.toString(Math.abs(Duration.between(startMins, endMins).toMinutes()));

            // LocalTime localTime = LocalTime.of(Integer.parseInt(Long.toString(hours)), Integer.parseInt(Long.toString(minutes)));
            if(!hours.equals("0")) {
                builder.append(hours.toString()).append("h");
            }
            if(!minutes.equals("0")) {
                if(!hours.equals("0")) {
                    builder.append(", ");
                }
                builder.append(minutes).append("m");
            }

            duration = builder.toString();
            return duration;
        }

        return builder.append("-").toString();
    }

    /**
     * Name: getTerminalAndGate
     * Purpose: Only flightSegments in the view will call for this function. It will cast the Object type tripItem passed to flightSegment
     * and return terminal and gate if possible.
     *
     * @return
     */
    public String getDepartureTerminalAndGate() {
        FlightSegmentDto segment = (FlightSegmentDto) tripItem;
        StringBuilder build = new StringBuilder();
        if(segment.getDepartureTerminal() != null) {
            build.append(segment.getDepartureTerminal());
        }
        build.append("/");
        if(segment.getDepartureGate()!=null) {
            build.append(segment.getDepartureGate());
        }

        return build.toString();

    }

    public String getSeat() {
        if(tripItemTypeCode == "F") {
            FlightSegmentDto segment = (FlightSegmentDto) tripItem;
            if(segment.getSeat() != null && !segment.getSeat().isEmpty()) {
                return segment.getSeat();
            }
        }
        return "-";
    }












}
