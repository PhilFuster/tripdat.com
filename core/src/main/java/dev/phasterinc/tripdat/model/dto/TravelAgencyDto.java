package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.model.TravelAgency;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * ClassName: TravelAgencyDto
 * Purpose: Data Transfer Object for TravelAgency
 *
 *
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TravelAgencyDto {
    // == fields ==
    private Long id;
    private String agencyName;
    private String url;
    private String confirmationNumber;
    private String contactName;
    private String phoneNumber;
    private String email;

    /**
     * Name: buildDto
     * Purpose: To build a TravelAgency Data transfer object.
     * Synopsis: Build's a TravelAgencyDto instance from a TravelAgency entity
     * <p>
     *
     * @param agency The TravelAgency the DTO will be modeling
     * @return The built TravelAgency Data Transfer Object
     */
    public static TravelAgencyDto buildDto(TravelAgency agency) {
        TravelAgencyDto dto = TravelAgencyDto.builder()
                .id(agency.getId())
                .agencyName(agency.getTravelAgencyName())
                .url(agency.getTravelAgencyUrl())
                .confirmationNumber(agency.getTravelAgencyConfirmationNumber())
                .contactName(agency.getTravelAgencyContactName())
                .phoneNumber(agency.getTravelAgencyPhoneNumber())
                .email(agency.getTravelAgencyEmail())
                .build();
        return dto;
    }

    /**
     * Name: buildEntity
     * Purpose: To build a TravelAgency Entity from a TravelAgency Data Transfer Object
     * Synopsis: Build's a TravelAgency Entity from a TravelAgencyDTO and the Item it belongs to.
     * <p>
     *
     * @param agencyDto TravelAgency Data Transfer Object the Entity will be modeled after.
     * @param item The TripdatTripItem the TravelAgency belongs to.
     * @return the built TravelAgency dto.
     */
    public static TravelAgency buildEntity(TravelAgencyDto agencyDto, TripdatTripItem item) {
        TravelAgency agency = TravelAgency.builder()
                .tripItem(item)
                .travelAgencyConfirmationNumber(agencyDto.getConfirmationNumber())
                .travelAgencyContactName(agencyDto.getContactName())
                .travelAgencyEmail(agencyDto.getEmail())
                .travelAgencyName(agencyDto.getAgencyName())
                .travelAgencyUrl(agencyDto.getUrl())
                .travelAgencyPhoneNumber(agencyDto.getPhoneNumber())
                .build();
        return agency;
    }

    /**
     * Name: updateEntity
     * Purpose: To update a TravelAgency entity
     * Synopsis: Updates a TravelAgency entity with the information from the DTO.
     * <p>
     *
     * @param agency The TravelAgency entity that will be updated.
     * @param agencyDto The TravelAgencyDTO where the information is coming from.
     */
    public static void updateEntity(TravelAgency agency, TravelAgencyDto agencyDto) {
        agency.setTravelAgencyName(agencyDto.agencyName);
        agency.setTravelAgencyUrl(agencyDto.url);
        agency.setTravelAgencyConfirmationNumber(agencyDto.confirmationNumber);
        agency.setTravelAgencyContactName(agencyDto.contactName);
        agency.setTravelAgencyPhoneNumber(agencyDto.phoneNumber);
        agency.setTravelAgencyEmail(agencyDto.email);

    }
}
