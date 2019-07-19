package dev.phasterinc.tripdat.service;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripItemWrapper;
import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.TripdatTripItem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * TripItemWrapperService - TripItemWrapperService will perform all the logic for preparing
 *                         the tripItems to be added to the model to pass to the userIndex view.
 */
public interface TripItemWrapperService {

    String getFormattedDate(LocalDate startDate, LocalDate endDate);

    String formatMonthString(String month);

    List<String> getDurationOfTrips(List<TripdatTrip> trips);

    List<String> getFormattedDateStrings(List<TripdatTrip> trips);

    List<TripItemWrapper> getNextUpItemsInItemWrapper(TripdatTrip nextTrip);

    List<TripItemWrapper> unwrapTripItemsIntoWrappers(Set<TripdatTripItem> items);

    void orderItemWrappersByAscDateAndTime(List<TripItemWrapper>  wrappers);

    List<TripItemWrapper> getItemsInItemWrapper(TripdatTrip trip);
    HashMap<LocalDate, List<TripItemWrapper>> getWrappersInMapByDate(List<TripItemWrapper> wrappers, LocalDate startDate, LocalDate endDate);
    TripItemWrapper getOneItemById(Long tripItemId);









}
