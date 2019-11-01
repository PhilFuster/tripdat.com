package dev.phasterinc.tripdat.model.dto;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Attendee;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AttendeeDto
 * Purpose: Data Transfer Object for an Attendee
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


    @Builder.Default
    private Boolean isAttendeeToBeDeleted = false;

    /**
     * Name: isEmpty
     * Purpose: Verify if the object is empty
     * Synopsis: Determines if all the dto's attributes are empty.
     */
    public boolean isEmpty() {
        boolean allEmpty = true;
        if (this.name != null && !name.isEmpty()) allEmpty = false;
        if (this.loyaltyProgramNumber != null && !loyaltyProgramNumber.isEmpty()) allEmpty = false;
        if (this.ticketNumber != null && !ticketNumber.isEmpty()) allEmpty = false;
        if (this.id != null && id != null) allEmpty = false;
        return allEmpty;
    }

    /**
     * Name: buildDtoList
     * Purpose: To build a list of AttendeDTOs from a list of Attendee entities.
     * Synopsis: For each Attendee in the passed parameter attendees, an AttendeeDto will be created.
     *
     * @param attendees List, of Attendee objects
     * @return List, of AttendeeDto objects
     */
    public static List<AttendeeDto> buildDtoList(List<Attendee> attendees) {
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

    /**
     * Name: buildEntity
     * Purpose: To build an Attendee Entity
     * Synopsis: Builds an Attendee Entity from the AttendeeDto passed
     *
     * @param attendeeDto The attendDto to build the Attendee off of
     * @param item        The item the atteneeDto is for
     * @return the created Attendee entity
     */
    public static Attendee buildEntity(AttendeeDto attendeeDto, TripdatTripItem item) {
        return Attendee.builder()
                .tripItem(item)
                .attendeeName(attendeeDto.name)
                .attendeeTicketNumber(attendeeDto.ticketNumber)
                .attendeeLoyaltyProgramNumber(attendeeDto.loyaltyProgramNumber)
                .build();
    }

    /**
     * Name: updateEntity
     * Purpose: To update an Attendee entity
     * Synopsis: Updates an entity entity with what is in the AttendeeDto
     */
    public static void updateEntity(Attendee attendee, AttendeeDto attendeeDto) {
        attendee.setAttendeeLoyaltyProgramNumber(attendeeDto.loyaltyProgramNumber);
        attendee.setAttendeeName(attendeeDto.name);
        attendee.setAttendeeTicketNumber(attendeeDto.ticketNumber);
    }
}
