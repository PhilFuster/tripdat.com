package dev.phasterinc.tripdat.dao;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatTrip;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


/**
 * Name: TripdatTripDao
 * Purpose: Dao object for tripdat_trip table, extends IGenericDao
 */
public interface TripdatTripDao extends IGenericDao<TripdatTrip> {

    TripdatTrip getTripByTripId(final Long id);

    List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId);

    Set<TripdatTrip> getTripsByUserId(final Long id);

    Boolean isTripDateConflict(LocalDate startDate, LocalDate endDate, final Long userId, final Long tripId);

}
