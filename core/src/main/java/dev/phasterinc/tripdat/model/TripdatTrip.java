package dev.phasterinc.tripdat.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTrip - models the tripdat_trip table
 *
 */

@Data
@Entity
@Table(name="tripdat_trip")
public class TripdatTrip {

    @Id
    @SequenceGenerator(name = "trip_generator", sequenceName = "tripdat_trip_item_trip_item_id_seq")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "trip_generator")
    @Column(name = "user_id", columnDefinition = "BIGSERIAL")
    private Long tripId;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private TripdatUser user;


    @NotEmpty
    @Column(name = "trip_name", columnDefinition = "TEXT")
    private String tripName;

    @Temporal(TemporalType.DATE)
    @Column(name = "trip_start_date", columnDefinition = "DATE")
    private Date tripStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "trip_end_date", columnDefinition = "DATE")
    private Date tripEndDate;

    @Column(name = "trip_description", columnDefinition = "TEXT")
    private String tripDescription;

    @Column(name = "trip_image_link", columnDefinition = "TEXT")
    private String tripImageLink;

    @Column(name = "trip_destination_city", columnDefinition = "TEXT")
    private String destinationCity;

}
