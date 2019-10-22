package dev.phasterinc.tripdat.model.dto;

import dev.phasterinc.tripdat.model.BookingDetail;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

import java.time.LocalDate;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/


/**
 * ClassName: BookingDetailDto
 * Purpose: Data Transfer Object for BookingDetail
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

    /**
     * Name: buildDto
     * Purpose: Build a BookingDetail Data Transfer Object
     * Synopsis: Builds a BookingDetaiDto object from a BookingDetail entity object
     *
     * @param bookingDetail, BookingDetail object referenced to create the dto
     * @return BookingDetailDto instance with the data from the entity in the database
     */
    public static BookingDetailDto buildDto(BookingDetail bookingDetail) {
        return BookingDetailDto.builder()
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
    }

    /**
     * Name: buildEntity
     * Purpose: To build a BookingDetail Entity
     * Synopsis: Builds a bookingDetail entity from a BookingDetail object and TripItem it belong to.
     *
     * @param bookingDetailDto BookingDetailDto used to build the Entity from.
     * @param item             TripdatTripItem the BookingDetail belongs to.
     * @return BookngDetail object built from the DTO.
     */
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

    /**
     * Name: updateEntity
     * Purpose: To update a BookingDetail entity
     * Synopsis: Updates a BookingDetail entity with from a BookingDetailDto
     *
     * @param bookingDetail    BookingDetail entity to update
     * @param bookingDetailDto BookingDetailDto to update the BookingDetail entity with.
     */
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
