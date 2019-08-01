package dev.phasterinc.tripdat.model.dto;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TravelAgency;
import lombok.Builder;
import lombok.Data;

/**
 * ClassName: TravelAgencyDto
 * Purpose: Data Transfer Object for TravelAgency
 *
 *
 */
@Builder
@Data
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
}
