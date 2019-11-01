package dev.phasterinc.tripdat.model.dto;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Supplier;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.*;


/**
 * ClassName: SupplierDto
 * Purpose: Data Transfer Object for Supplier
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

    /**
     * Name: buildDto
     * Purpose: To build a supplier data transfer object from a Supplier entity.
     * Synopsis: Builds a SupplierDto instance from a Supplier entity.
     * <p>
     *
     * @param supplier Supplier entity the dto will be based off of.
     * @return SupplierDto object built.
     */
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

    /**
     * Name: buildEntity
     * Purpose: To build a Supplier entity
     * Synopsis: Builds a Supplier entity from a SupplierDto.
     * <p>
     *
     * @param supplierDto SupplierDto this entity will be modeled after.
     * @param item        TripdatTripItem this Supplier belongs to.
     * @return the Supplier entity built.
     */
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

    /**
     * Name: updateEntity
     * Purpose: To update a Supplier entity with the information from the SupplierDto
     * Synopsis: Update's a Supplier entity with information from a SupperlierDto
     * <p>
     *
     * @param supplierDto Supplier data transfer object the entity will be updated with.
     * @param supplier    The Supplier entity that will be updated
     */
    public static void updateEntity(Supplier supplier, SupplierDto supplierDto) {
        supplier.setSupplierName(supplierDto.supplierName);
        supplier.setSupplierUrl(supplierDto.url);
        supplier.setSupplierContactName(supplierDto.contactName);
        supplier.setSupplierPhoneNumber(supplierDto.phoneNumber);
        supplier.setSupplierEmail(supplierDto.email);
    }
}
