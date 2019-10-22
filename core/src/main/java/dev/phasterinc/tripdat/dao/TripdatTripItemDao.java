package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTripItem;

import java.util.HashSet;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripItemDao
 * Purpose: interface for TripItemDao and extends the IgenericDao interface
 */
public interface TripdatTripItemDao extends IGenericDao<TripdatTripItem> {
    HashSet<TripdatTripItem> findByTripId(final long Id);

    HashSet<TripdatTripItem> findMax4ByTripId(final long id);

    TripdatTripItem findByItemId(final long id);


}
