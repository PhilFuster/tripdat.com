package dev.phasterinc.tripdat.model.dto;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TravelAgency;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

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

    public static void updateEntity(TravelAgency agency, TravelAgencyDto agencyDto) {
        agency.setTravelAgencyName(agencyDto.agencyName);
        agency.setTravelAgencyUrl(agencyDto.url);
        agency.setTravelAgencyConfirmationNumber(agencyDto.confirmationNumber);
        agency.setTravelAgencyContactName(agencyDto.contactName);
        agency.setTravelAgencyPhoneNumber(agencyDto.phoneNumber);
        agency.setTravelAgencyEmail(agencyDto.email);

    }
}
