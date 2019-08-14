package dev.phasterinc.tripdat.model.dto;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Supplier;
import dev.phasterinc.tripdat.model.TravelAgency;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;

/**
 * ClassName: SupplierDto
 * Purpose: Data Transfer Object for Supplier
 *
 *
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SupplierDto {
    // == fields ==
    private Long id;
    private String supplierName;
    private String url;
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

    public static Supplier buildEntity(SupplierDto supplierDto, TripdatTripItem item) {
        Supplier supplier = Supplier.builder()
                .tripItem(item)
                .supplierContactName(supplierDto.getContactName())
                .supplierEmail(supplierDto.getEmail())
                .supplierName(supplierDto.getSupplierName())
                .supplierUrl(supplierDto.getUrl())
                .supplierPhoneNumber(supplierDto.getPhoneNumber())
                .build();
        return supplier;
    }

    public static void updateEntity(Supplier supplier, SupplierDto supplierDto) {
        supplier.setSupplierName(supplierDto.supplierName);
        supplier.setSupplierUrl(supplierDto.url);
        supplier.setSupplierContactName(supplierDto.contactName);
        supplier.setSupplierPhoneNumber(supplierDto.phoneNumber);
        supplier.setSupplierEmail(supplierDto.email);
    }
}
