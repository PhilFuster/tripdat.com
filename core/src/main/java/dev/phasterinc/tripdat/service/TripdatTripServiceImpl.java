package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.TripdatTripDao;
import dev.phasterinc.tripdat.model.TripItemWrapper;
import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.dto.TripDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripServiceImpl
 * Purpose: Implementation to the TripdatTripService interface
 */
@Service
@Slf4j
@Transactional
public class TripdatTripServiceImpl implements TripdatTripService{


    TripdatTripDao dao;

    @Autowired
    public void setDao(TripdatTripDao daoToSet) {
        dao = daoToSet;
        dao.setClazz(TripdatTrip.class);
    }

    @Override
    public List<TripdatTrip> findAll() {
        System.out.println("Looking for all trips...");
        return dao.findAll();
    }

    @Override
    public TripdatTrip findOne(Long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(TripdatTrip entity) {
        dao.create(entity);
    }

    @Override
    public TripdatTrip update(TripdatTrip entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(TripdatTrip entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(Long entityId) {
        dao.deleteById(entityId);

    }

    @Override
    public TripdatTrip getTripByTripId(final Long id) {

        return dao.getTripByTripId(id);
    }

    @Override
    public List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId) {
        return dao.get3UpcomingTripsByUserIdOrderByDateAsc(userId);
    }

    @Override
    public Set<TripdatTrip> getTripsByUserId(Long id) {
        return dao.getTripsByUserId(id);
    }

    @Override
    public void createUpcomingAndPastTripsCollections(Set<TripdatTrip> trips, Set<TripdatTrip> upcoming, Set<TripdatTrip> past) {
        // for each trip if the endDat is before today add to past trips collections, else add to upcoming
        trips.forEach((trip)->{
            if(trip.getTripEndDate().isBefore(LocalDate.now())) {
                past.add(trip);
            } else {
                upcoming.add(trip);
            }

        });
    }

    /**
     * Name: CheckForDateConflict
     * Purpose: Takes a new start & end date, and a userId
     *          Queries the DB for a user's Trips using userId
     *          Checks for overlapping conflicts during the date range of newStart to newEnd passed
     *          and all the trips the user has.
     * @param newStartDate
     * @param newEndDate
     * @param userId
     * @return
     */
    @Override
    public boolean checkForTripDateConflict(TripDto tripDto, Long userId) {
        Set<TripdatTrip> trips = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(TripdatTrip::getTripStartDate)));
        trips.addAll(getTripsByUserId(userId));
        // tripDto id is greater than 0, a trip is being edited. Remove the trip from the collection
        // so a false conflict does not occur
        if(tripDto.getTripId() > 0) {
            // remove a tripId if found
            trips.removeIf((TripdatTrip trip)-> trip.getTripId().equals(tripDto.getTripId()));
        }

        // if the trips do not not overlap return true. There is a date conflict
        for (TripdatTrip trip : trips) {
            // If not( newEnd <= curEnd OR newStart >= curEnd) ->
            // There is a date conflict
            boolean isTripDateConflict =  ((tripDto.getTripEndDate().compareTo(trip.getTripStartDate()) == 0 ||
                    tripDto.getTripEndDate().compareTo(trip.getTripStartDate()) < 0)
                    ||
                    (tripDto.getTripStartDate().compareTo(trip.getTripEndDate()) == 0 ||
                            tripDto.getTripStartDate().compareTo(trip.getTripEndDate()) > 0));
            if (isTripDateConflict) {
                /*System.out.println("===============CONFLICT================");
                System.out.println("newDateRange: " + tripDto.getTripStartDate().toString() + " - " + tripDto.getTripEndDate().toString());
                System.out.println("conflicting date range: " + trip.getTripStartDate().toString() + " - " + trip.getTripEndDate().toString());*/ // code for testing this function
                return true;
            }
        }
        // no conflicts, return false
        return false;

    }

    /**
     * Name: checkForTripItemConflictWithNewDateRange
     * Purpose: When editing a Trip's start and end date, program must check to make sure there
     *          are no conflicts between the new date range and existing Items the user has already
     *          created. This function handles that
     * @param tripDto - Contains the proposed Date range for the trip
     * @param items - List of TripItemWrapper that contains the unpacked Items and their segments
     * @return boolean, true if there are conflicts. False if all items fit in the proposed date range.
     * Algorithm:
     *              1.
     */
    @Override
    public boolean checkForTripItemConflictWithNewDateRange(TripDto tripDto, List<TripItemWrapper> items) {
        // == local variables ==
        // proposed new start and end dates for this trip
        LocalDate newStartDate = tripDto.getTripStartDate();
        LocalDate newEndDate = tripDto.getTripEndDate();
        log.info("newStartDate: {}",newStartDate);
        log.info("newEndDate: {}",newEndDate);

        // for each item check if its date range falls within the new date range.
        // If not( newEnd <= curEnd OR newStart >= curEnd)
        // there is a date conflict

        for(TripItemWrapper item: items) {
            log.info("itemStartDate: {}", item.getStartDate().toString());
            log.info("itemEndDate: {}", item.getEndDate().toString());
            boolean isTripItemConflictWithNewDate = (( newEndDate.compareTo(item.getStartDate()) == 0 ||
                    newEndDate.compareTo(item.getStartDate()) < 0)
                    ||
                    (newStartDate.compareTo(item.getEndDate()) == 0 ||
                            newStartDate.compareTo(item.getEndDate()) > 0));
            log.info("isTripItemConflictWithNewDate: {}", isTripItemConflictWithNewDate);
            if(isTripItemConflictWithNewDate ) {
                log.info("=============== Trip Item CONFLICT with trip date range ================");
                log.info("newDateRange: " + newStartDate.toString() + " - " + newEndDate.toString());
                log.info("conflicting date range: " + item.getStartDate().toString() + " - " + item.getEndDate().toString());
                return true;
            }
        }
        return false;
    }
}
