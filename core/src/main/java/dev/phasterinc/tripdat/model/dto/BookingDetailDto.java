package dev.phasterinc.tripdat.model.dto;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.BookingDetail;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * ClassName: BookingDetailDto
 * Purpose: Data Transfer Object for BookingDetail
 *
 *
 */
@Builder
@Data
public class BookingDetailDto {
    // == fields ==
    private Long id;
    private String siteName;
    private String url;
    private LocalDate bookingDate;
    private String referenceNumber;
    private String totalCost;
    private Boolean isPurchased;
    private String phoneNumber;
    private String email;

    public static BookingDetailDto buildDto(BookingDetail bookingDetail) {
        BookingDetailDto dto = BookingDetailDto.builder()
                .id(bookingDetail.getId())
                .siteName(bookingDetail.getBookingSiteName())
                .url(bookingDetail.getBookingSiteUrl())
                .bookingDate(bookingDetail.getBookingDate())
                .phoneNumber(bookingDetail.getBookingSitePhoneNumber())
                .email(bookingDetail.getBookingSiteEmail())
                .referenceNumber(bookingDetail.getBookingReferenceNumber())
                .totalCost(bookingDetail.getBookingTotalCost())
                .isPurchased(bookingDetail.getBookingIsPurchased())
                .build();
        return dto;
    }
}
