package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.model.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * ClassName: FlightItemDto
 * Purpose: Data Transfer Object for a Flight Trip Item
 *
 */
@Builder
@Data
public class FlightItemDto {


    private Long itemId;

    private String itemNote;

    private String itemPhotoLink;

    private List<AttendeeDto> attendees;

    private TravelAgency travelAgency;

    private Supplier supplier;

    private BookingDetail bookingDetail;

    private String tripItemType;

    private String confirmationNumber;

    private List<FlightSegmentDto> segmentDtos;

    public static FlightItemDto buildDto(Flight flight, List<FlightSegmentDto> segmentDtos, List<AttendeeDto> attendeeDtos ) {
        FlightItemDto flightItemDto;
        flightItemDto = FlightItemDto.builder()
                .itemNote(flight.getTripItemNote())
                .itemPhotoLink(flight.getTripItemPhotoLink())
                .attendees(attendeeDtos)
                .travelAgency(flight.getTravelAgency())
                .supplier(flight.getSupplier())
                .bookingDetail(flight.getBookingDetail())
                .tripItemType(flight.getTripItemType())
                .confirmationNumber(flight.getFlightConfirmationNumber())
                .segmentDtos(segmentDtos)
                .itemId(flight.getTripItemId())
                .build();
        return flightItemDto;
    }



}
