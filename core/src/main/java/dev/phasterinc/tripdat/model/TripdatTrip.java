package dev.phasterinc.tripdat.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tripdat_trip")
public class TripdatTrip {

    @Id
    @Column(name = "trip_id", columnDefinition = "BIGSERIAL")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long tripId;

    @Column(name = "user_id", columnDefinition = "BIGINT")
    private Long userId;


}
