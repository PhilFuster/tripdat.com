package dev.phasterinc.tripdat.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripDto
 * Purpose: A TripdatTrip Data Transfer Object
 */

@Builder
@Data
public class TripDto {
    // == Constants ==
    public static final SimpleDateFormat dateFormat
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
