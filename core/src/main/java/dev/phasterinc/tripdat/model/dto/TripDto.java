package dev.phasterinc.tripdat.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Builder
@Data
public class TripDto {
    // == Constants ==
    public static final SimpleDateFormat  dateFormat
        = new SimpleDateFormat("yyyy-MM-dd");
    // == fields ==
    @NotEmpty
    private String tripName;

    private Long tripId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tripStartDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tripEndDate;

    private String tripDescription;

    @NotEmpty
    private String destinationCity;

    private Boolean isUserTraveler;

    private String tripImageLink;


}
