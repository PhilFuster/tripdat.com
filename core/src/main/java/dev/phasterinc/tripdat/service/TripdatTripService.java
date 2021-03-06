package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripItemWrapper;
import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.dto.TripDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


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

    List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId);

    Set<TripdatTrip> getTripsByUserId(final Long id);

    void createUpcomingAndPastTripsCollections(Set<TripdatTrip> trips, Set<TripdatTrip> upcoming, Set<TripdatTrip> past);

    boolean checkForTripDateConflict(TripDto tripDto, Long userId);

    boolean isTripItemsOutOfNewTripDateRange(TripDto tripDto, List<TripItemWrapper> items);

    boolean isNewItemDateRangeConflictingWithPreExistingItems(List<TripItemWrapper> items, LocalDate startDate, LocalDate endDate, Long itemId);
}
