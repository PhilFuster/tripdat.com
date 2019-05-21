package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;

import java.util.List;
import java.util.Set;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripDao - Dao object for tripdat_trip table, extends IGenericDao
 *
 *
 */
public interface TripdatTripDao extends IGenericDao<TripdatTrip> {

    TripdatTrip getTripByTripId(final Long id);
    List<TripdatTrip> get3TripsByUserIdOrderByDateAsc(final Long userId);
    Set<TripdatTrip> getTripsByUserId();

}
