package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.model.*;
import lombok.*;

import java.util.ArrayList;
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
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FlightItemDto {


    private Long itemId;

    private Long tripId;

    private String itemNote;

    private String itemPhotoLink;

    private List<AttendeeDto> attendees;

    private String tripItemType;

    private String confirmationNumber;

    private List<FlightSegmentDto> segmentDtos;

    private TravelAgencyDto travelAgencyDto;

    private SupplierDto supplierDto;

    private BookingDetailDto bookingDetailDto;

    /**
     * name: buildDto
     * purpose: to build a FlightItemDto object.
     * @param flight - Flight object
     * @param segmentDtos - List of FlightSegmentDtos
     * @param attendeeDtos - List of AttendeeDtos
     * @return FlightItemDto - flightItemDto
     */
    public static FlightItemDto buildDto(Flight flight, List<FlightSegmentDto> segmentDtos, List<AttendeeDto> attendeeDtos ) {
        FlightItemDto flightItemDto;
        flightItemDto = FlightItemDto.builder()
                .itemNote(flight.getTripItemNote())
                .itemPhotoLink(flight.getTripItemPhotoLink())
                .tripItemType(flight.getTripItemType())
                .confirmationNumber(flight.getFlightConfirmationNumber())
                .itemId(flight.getTripItemId())
                .tripId(flight.getTripdatTrip().getTripId())
                .travelAgencyDto(TravelAgencyDto.buildDto(flight.getTravelAgency()))
                .supplierDto(SupplierDto.buildDto(flight.getSupplier()))
                .bookingDetailDto(BookingDetailDto.buildDto(flight.getBookingDetail()))
                .build();
        flightItemDto.setAttendees(new ArrayList<>());
        flightItemDto.getAttendees().addAll(attendeeDtos);
        flightItemDto.setSegmentDtos(new ArrayList<>());
        flightItemDto.getSegmentDtos().addAll(segmentDtos);
        return flightItemDto;
    }

    /**
     * name: updateEntity
     * purpose: update an Flight entity with updated information from a FlightItemDto
     * @param flight - Flight flight
     * @param flightDto - FlightItemDto flightDto
     */
    public static void updateEntity(Flight flight,FlightItemDto flightDto) {
        TravelAgency agency = flight.getTravelAgency();
        Supplier supplier = flight.getSupplier();
        BookingDetail bookingDetail = flight.getBookingDetail();
        // update other detail entities
        TravelAgencyDto.updateEntity(agency, flightDto.getTravelAgencyDto());
        SupplierDto.updateEntity(supplier, flightDto.getSupplierDto());
        BookingDetailDto.updateEntity(bookingDetail, flightDto.getBookingDetailDto());
        // update Segments
        for(int i = 0; i < flightDto.getSegmentDtos().size(); ++i) {
            FlightSegmentDto segmentDto = flightDto.getSegmentDtos().get(i);
            // if the dto is empty and the ID is null segment is new
            // and not updated with segment details so skip it
            if(segmentDto.isEmpty() && segmentDto.getSegmentId() == null) {

                continue;
            }

            FlightSegment segment;
            // if index is still within flightSegment's range
            if( i < flight.getFlightSegments().size()) {
                // get segment to be updated
                segment = flight.getFlightSegments().get(i);
                // if the DTO is marked to be deleted
                // remove from FlightSegments
                if(segmentDto.getIsToBeDeleted()) {
                    flight.removeSegment(segment);
                    flightDto.getSegmentDtos().remove(segmentDto);
                }
                // update segment
                FlightSegmentDto.updateEntity(segment,segmentDto);
                continue;
            } else {
                /*
                 segment is not empty, and it is not a segment already in the
                 the Flight's segment collection.
                 Add this segment to the segment's collection
                */
                if ((i == flight.getFlightSegments().size() || i == flightDto.getSegmentDtos().size() + 1) && segmentDto.isEmpty()) {
                    continue;
                }
                FlightSegment newSegment = FlightSegmentDto.buildEntity(segmentDto, flight);
                flight.addSegment(newSegment);
            }

        }
        // update attendees
        for(int i = 0; i < flightDto.getAttendees().size();++i) {
            AttendeeDto attendeeDto = flightDto.getAttendees().get(i);
            if(attendeeDto.isEmpty() && attendeeDto.getId() == null) {
                continue;
            }
            Attendee attendee;
            // if i is still within attendee's range
            if( i < flight.getAttendees().size()) {
                // get attendee to be updated
                attendee = flight.getAttendees().get(i);
                // if the DTO is marked to be deleted
                // remove from attendees collection
                if(attendeeDto.getIsAttendeeToBeDeleted()) {
                    flight.removeAttendee(attendee);
                    flightDto.getAttendees().remove(attendeeDto);
                    continue;
                }
                // update attendee
                AttendeeDto.updateEntity(attendee,attendeeDto);
            } else {
                // segment is not empty, and it is not a segment already in the
                // the Flight's segment collection.
                // Add this segment to the segment's collection
                if(attendeeDto.isEmpty()) {
                    continue;
                }
                Attendee newAttendee = AttendeeDto.buildEntity(attendeeDto, flight);
                flight.addAttendee(newAttendee);
            }
        }
    }

}
