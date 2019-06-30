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
import java.util.List;
import java.util.Set;
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

    /**
     * Name: get3UpcomingTripsByUserIdOrderByDateAsc
     * Purpose: Calls member variable dao's method, get3upcomingTripsByUserIdOrderByDateAsc
     *          which queries DB for a User's three most upcoming Trips ordered by tripStart
     *          Date in ascending order.
     *
     * @param userId - a user's id.
     * @return - a List of a User's three most upcoming Trips ordered by tripStart Date in ascending order.
     */
    @Override
    public List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId) {
        return dao.get3UpcomingTripsByUserIdOrderByDateAsc(userId);
    }

    @Override
    public Set<TripdatTrip> getTripsByUserId(Long id) {
        return dao.getTripsByUserId(id);
    }

    /**
     * Name: createUpcomingAndPastTripsCollections
     * Purpose: Filters a user's Trips into collections of Trips, upcoming and past which will
     *          contain upcoming and past trips respectively.
     * @param trips - Set, a user's Trips
     * @param upcoming - Set, to be populated with a user's upcoming Trips
     * @param past Set, to be populated with a user's upcoming Trips
     * Algorithm:
     *            1. forEach trip in trips
     *            2.   if current trip's end is before today's date
     *            3.      add to past collection
     *            4.   else add to upcoming collection
     */
    @Override
    public void createUpcomingAndPastTripsCollections(Set<TripdatTrip> trips, Set<TripdatTrip> upcoming, Set<TripdatTrip> past) {
        // for each trip if the endDate is before today add to past trips collections, else add to upcoming
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
     * @param tripDto - TripDto, trip data transfer object. Contains start and end date
     *                  which are referenced to check for dateConflict.
     * @param userId - Long, a user's Id used to query for user's trips.
     * @return - Boolean, true if there is a date conflict. False if there is none.
     * Algorithm:
     *              1. Initialize Set<TripdatTrip> trips, to a TreeSet ordered by start Date
     *              2. Query DB for a user's trips and add all to trips collection
     *              3. If the User is editing a Trip remove it from the trips collection
     *              4. for each trip in trips
     *              5.   if there is a date conflict
     *              6.      then return true
     *              7. return false
     */
    @Override
    public boolean checkForTripDateConflict(TripDto tripDto, Long userId) {


        Boolean isTripDateConflict = dao.isTripDateConflict(tripDto.getTripStartDate(), tripDto.getTripEndDate(), userId, tripDto.getTripId());
        if (isTripDateConflict) {
            log.info("TripDateConflict: " + tripDto.getTripStartDate().toString() + " - " + tripDto.getTripEndDate().toString());
        }
        // no conflicts, return false
        return isTripDateConflict;
    }
    /**
     * Name: isTripItemsOutOfNewTripDateRange
     * Purpose: When editing a Trip's start and end date, must check to make sure there
     *          are no conflicts between the new date range and existing Items the user has already
     *          planned.
     * @param tripDto - Contains the proposed Date range for the trip
     * @param items - List<TripItemWrapper> - Contains unpacked tripItems and their segments
     * @return boolean - true if there are conflicts. False if all items fit in the proposed date range.
     * Algorithm:
     *              1. Initialize tripStarDate and tripEndDate
     *              2. for each item in items collection
     *              3.   if an item falls out of the tripDto's date range
     *              4.    return true
     *              5.   else false
     */
    @Override
    public boolean isTripItemsOutOfNewTripDateRange(TripDto tripDto, List<TripItemWrapper> items) {
        // == local variables ==
        // proposed new start and end dates for this trip
        LocalDate tripStartDate = tripDto.getTripStartDate();
        LocalDate tripEndDate = tripDto.getTripEndDate();
        log.info("tripStartDate: {}",tripStartDate);
        log.info("tripEndDate: {}",tripEndDate);
        // for each item check if its date range falls within the new date range.
        // If not( newEnd <= curEnd OR newStart >= curEnd)
        // there is a date conflict
        //
        // if there are no items for this trip, then there are no conflicts ...
        for(TripItemWrapper item: items) {
            LocalDate itemStartDate = item.getStartDate();
            LocalDate itemEndDate = item.getEndDate();
            log.info("itemStartDate: {}", itemStartDate);
            log.info("itemEndDate: {}", itemEndDate);
            // Check if an item's start date or end date falls outside of the passed
            // trip date range by calling helper function isTripOutOfDateRange
            if(isTripItemOutOfTripDateRange(item, tripDto) ) {
                log.info("=============== Trip Item CONFLICT with trip date range ================");
                log.info("new Trip Date Range: " + tripStartDate.toString() + " - " + tripEndDate.toString());
                log.info("conflicting item date range: " + itemStartDate + " - " + itemEndDate);
                return true;
            }
        }
        return false;
    }
    /**
     *  Name: isTripItemOutOfTripDateRange
     *  Purpose: To abstract the conditional that is a little hard to read
     *  @return boolean, returns true if a user's tripItem's  date range falls outside
     *          of the passed tripDto's date range.
     *  Algorithm:
     *              1. initialize local variables tripStartDate, tripEndDate,
     *                 itemStartDate, itemEndDate.
     *              2. return true if an item's startDate is before a trip's end date
     *                 OR an item's startDate is after a trip's endDate
     *                 OR an item's end date is after a trip's endDate
     *              3. else return false
     */
    private boolean isTripItemOutOfTripDateRange(TripItemWrapper item, TripDto tripDto) {
        LocalDate tripStartDate = tripDto.getTripStartDate();
        LocalDate tripEndDate = tripDto.getTripEndDate();
        LocalDate itemStartDate = item.getStartDate();
        LocalDate itemEndDate = item.getEndDate();

        return ((itemStartDate.compareTo(tripStartDate) < 0)
                ||(itemStartDate.compareTo(tripEndDate) > 0 )
                ||(itemEndDate.compareTo(tripEndDate) > 0 ));
    }
}
