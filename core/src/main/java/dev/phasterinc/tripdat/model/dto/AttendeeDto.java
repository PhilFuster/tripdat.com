package dev.phasterinc.tripdat.model.dto;
import dev.phasterinc.tripdat.model.Attendee;
import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * ClassName: AttendeeDto
 * Purpose: Data Transfer Object for an Attendee
 *
 *
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendeeDto {

    // == fields ==
    private Long id;

    private String name;

    private String loyaltyProgramNumber;

    private String ticketNumber;

    private Boolean isAttendeeToBeDeleted;

    public boolean isEmpty() {
        boolean allEmpty = true;
        if(this.name != null && !name.isEmpty()) allEmpty = false;
        if(this.loyaltyProgramNumber != null && !loyaltyProgramNumber.isEmpty()) allEmpty = false;
        if(this.ticketNumber != null && !ticketNumber.isEmpty()) allEmpty = false;
        if(this.id != null && id !=  null) allEmpty = false;
        return allEmpty;
    }

    public static List<AttendeeDto> buildDtoList(List<Attendee>attendees) {
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        // build list of AttendeeDto with attendees from passed list of attendees
        attendees.forEach((attendee -> {
            AttendeeDto dto = AttendeeDto.builder()
                    .id(attendee.getAttendeeId())
                    .name(attendee.getAttendeeName())
                    .loyaltyProgramNumber(attendee.getAttendeeLoyaltyProgramNumber())
                    .ticketNumber(attendee.getAttendeeTicketNumber())
                    .isAttendeeToBeDeleted(false)
                    .build();
            attendeeDtos.add(dto);
        }));
        return attendeeDtos;
    }

    public static Attendee buildEntity(AttendeeDto attendeeDto, TripdatTripItem item) {
        return Attendee.builder()
                .tripItem(item)
                .attendeeName(attendeeDto.name)
                .attendeeTicketNumber(attendeeDto.ticketNumber)
                .attendeeLoyaltyProgramNumber(attendeeDto.loyaltyProgramNumber)
                .build();
    }

    public static void updateEntity(Attendee attendee, AttendeeDto attendeeDto) {
        attendee.setAttendeeLoyaltyProgramNumber(attendeeDto.loyaltyProgramNumber);
        attendee.setAttendeeName(attendeeDto.name);
        attendee.setAttendeeTicketNumber(attendeeDto.ticketNumber);
    }
}
