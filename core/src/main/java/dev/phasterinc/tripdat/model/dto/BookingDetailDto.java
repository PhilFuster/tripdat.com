package dev.phasterinc.tripdat.model.dto;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.BookingDetail;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

import java.time.LocalDate;

/**
 * ClassName: BookingDetailDto
 * Purpose: Data Transfer Object for BookingDetail
 *
 *
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

    public static BookingDetail buildEntity(BookingDetailDto bookingDetailDto, TripdatTripItem item) {
        return BookingDetail.builder()
                .tripItem(item)
                .bookingSiteName(bookingDetailDto.siteName)
                .bookingSiteUrl(bookingDetailDto.url)
                .bookingDate(bookingDetailDto.bookingDate)
                .bookingReferenceNumber(bookingDetailDto.referenceNumber)
                .bookingTotalCost(bookingDetailDto.totalCost)
                .bookingIsPurchased(bookingDetailDto.isPurchased)
                .bookingSitePhoneNumber(bookingDetailDto.phoneNumber)
                .bookingSiteEmail(bookingDetailDto.email)
                .build();
    }

    public static void updateEntity(BookingDetail bookingDetail, BookingDetailDto bookingDetailDto) {
        bookingDetail.setBookingSiteName(bookingDetailDto.siteName);
        bookingDetail.setBookingSiteUrl(bookingDetailDto.url);
        bookingDetail.setBookingDate(bookingDetailDto.bookingDate);
        bookingDetail.setBookingReferenceNumber(bookingDetailDto.referenceNumber);
        bookingDetail.setBookingTotalCost(bookingDetailDto.totalCost);
        bookingDetail.setBookingIsPurchased(bookingDetailDto.isPurchased);
        bookingDetail.setBookingSitePhoneNumber(bookingDetailDto.phoneNumber);
        bookingDetail.setBookingSiteEmail(bookingDetailDto.email);
    }
}
