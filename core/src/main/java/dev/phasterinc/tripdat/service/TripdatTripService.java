package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.TripdatTrip;

import java.util.List;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripService: Interface for TripdatTripService
 * Purpose: The service for TripdatTrip table in db
 */
public interface TripdatTripService {
    List<TripdatTrip> findAll();
    TripdatTrip findOne(final Long id);
    void create(final TripdatTrip entity);
    TripdatTrip update(final TripdatTrip entity);
    void delete(final TripdatTrip entity);
    void deleteById(final Long entityId);
    TripdatTrip getTripByTripId(final Long id);
    List<TripdatTrip> get3TripsByUserIdOrderByDateAsc(final Long userId);


}
