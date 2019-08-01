package dev.phasterinc.tripdat.model.dto;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Supplier;
import lombok.Builder;
import lombok.Data;

/**
 * ClassName: SupplierDto
 * Purpose: Data Transfer Object for Supplier
 *
 *
 */
@Builder
@Data
public class SupplierDto {
    // == fields ==
    private Long id;
    private String supplierName;
    private String url;
    private String confirmationNumber;
    private String contactName;
    private String phoneNumber;
    private String email;

    public static SupplierDto buildDto(Supplier supplier) {
        SupplierDto dto = SupplierDto.builder()
                .id(supplier.getId())
                .supplierName(supplier.getSupplierName())
                .url(supplier.getSupplierUrl())
                .contactName(supplier.getSupplierContactName())
                .phoneNumber(supplier.getSupplierPhoneNumber())
                .email(supplier.getSupplierEmail())
                .build();
        return dto;
    }
}
