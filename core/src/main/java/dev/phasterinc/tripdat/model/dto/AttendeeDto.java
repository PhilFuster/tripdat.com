package dev.phasterinc.tripdat.model.dto;
import dev.phasterinc.tripdat.model.Attendee;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
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
@Builder
@Data
public class AttendeeDto {

    // == fields ==
    private Long id;

    private String name;

    private String loyaltyProgramNumber;

    private String ticketNumber;

    private Boolean isAttendeeToBeDeleted;

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
}
