package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

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

/**
 * Name: TripdatTripServiceImpl
 * Purpose: Implementation to the TripdatTripService interface
 */
@Service
@Slf4j
@Transactional
public class TripdatTripServiceImpl implements TripdatTripService {


    TripdatTripDao dao;

    /**
     * Name: setDao
     * Purpose: To set the TripdatTripDao member variable dao.
     * Synopsis: Sets the database access object for the TripdatTripServiceImpl.
     * <p>
     *
     * @param daoToSet The TripdatTripDao to be used in the TripdatTripServiceImpl class
     */
    @Autowired
    public void setDao(TripdatTripDao daoToSet) {
        dao = daoToSet;
        dao.setClazz(TripdatTrip.class);
    }

    /**
     * Name: findAll
     * Purpose: Finds all the TripdatTrip records in the database.
     * Synopsis: Calls the IGenericDao findAll method.
     * <p>
     *
     * @return List of TripdatTrip records.
     */
    @Override
    public List<TripdatTrip> findAll() {
        return dao.findAll();
    }

    /**
     * Name: findOne
     * Purpose: To find the TripdatTrip with the id of the parameter id.
     * Synopsis: Calls the IGenericDao findOne method using the id passed in.
     * <p>
     *
     * @param id Long, id of the TripdatTrip to find in the database.
     * @return TripdatTrip that has the id passed.
     */
    @Override
    public TripdatTrip findOne(Long id) {
        return dao.findOne(id);
    }

    /**
     * Name: create
     * Purpose: To create a TripdatTrip record in the database with the information in the entity parameter.
     * Synopsis: Calls the IGenericDao create method to persist the TripdatTrip in the
     * database.
     *
     * <p>
     *
     * @param entity TripdatTrip to be persisted in the database.
     */
    @Override
    public void create(TripdatTrip entity) {
        dao.create(entity);
    }

    /**
     * Name: update
     * Purpose: To update an existing TripdatTrip entity.
     * Synopsis: Calls the IGenericDao update method on the entity passed.
     * <p>
     *
     * @param entity TripdatTrip to be updated.
     * @return The updated TripdatTrip
     */
    @Override
    public TripdatTrip update(TripdatTrip entity) {
        return dao.update(entity);
    }

    /**
     * Name: delete
     * Purpose: To delete the TripdatTrip record that corresponds to the entity passed.
     * Synopsis: Calls the IGenericDao delete method on the entity passed.
     * <p>
     *
     * @param entity TripdatTrip entity to be deleted from the database
     */
    @Override
    public void delete(TripdatTrip entity) {
        dao.delete(entity);

    }

    /**
     * Name: deleteById
     * Purpose: To delete a TripdatTrip record from the database with the passed in id.
     * Synopsis: Calls the deleteById method of the TripdatTripDao class.
     * <p>
     *
     * @param entityId Long, id of the entity to be deleted.
     */
    @Override
    public void deleteById(Long entityId) {
        dao.deleteById(entityId);

    }

    /**
     * Name: getTripByTripId
     * Purpose: To retrieve a trip from the database by its id.
     * Synopsis: Calls the TripdatTripDao method getTripByTripId.
     * <p>
     *
     * @param id Long, id of the TripdatTrip to retrieve from the database
     * @return TripdatTrip with the passed in id.
     */
    @Override
    public TripdatTrip getTripByTripId(final Long id) {

        return dao.getTripByTripId(id);
    }

    /**
     * Name: get3UpcomingTripsByUserIdOrderByDateAsc
     * Purpose: Calls member variable dao's method, get3upcomingTripsByUserIdOrderByDateAsc
     * which queries DB for a User's three most upcoming Trips ordered by tripStart
     * Date in ascending order.
     *
     * @param userId - a user's id.
     * @return - a List of a User's three most upcoming Trips ordered by tripStart Date in ascending order.
     */
    @Override
    public List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId) {
        return dao.get3UpcomingTripsByUserIdOrderByDateAsc(userId);
    }

    /**
     * Name: getTripsByUserId
     * Purpose: To retrieve a user's Trips.
     * Synopsis: Calls the getTripsByUserId method of TripdatTripDao class
     * <p>
     *
     * @param id Long, id of the user whose trips will be retrieved.
     * @return Set of TripdatTrip entities from the database.
     */
    @Override
    public Set<TripdatTrip> getTripsByUserId(Long id) {
        return dao.getTripsByUserId(id);
    }

    /**
     * Name: createUpcomingAndPastTripsCollections
     * Purpose: Filters a user's Trips into collections of Trips, upcoming and past which will
     * contain upcoming and past trips respectively.
     *
     * @param trips    - Set, a user's Trips
     * @param upcoming - Set, to be populated with a user's upcoming Trips
     * @param past     Set, to be populated with a user's upcoming Trips
     *                 Algorithm:
     *                 1. forEach trip in trips
     *                 2.   if current trip's end is before today's date
     *                 3.      add to past collection
     *                 4.   else add to upcoming collection
     */
    @Override
    public void createUpcomingAndPastTripsCollections(Set<TripdatTrip> trips, Set<TripdatTrip> upcoming, Set<TripdatTrip> past) {
        // for each trip if the endDate is before today add to past trips collections, else add to upcoming
        trips.forEach(trip -> {
            if (trip.getTripEndDate().isBefore(LocalDate.now())) {
                past.add(trip);
            } else {
                upcoming.add(trip);
            }

        });
    }

    /**
     * Name: CheckForDateConflict
     * Purpose: Takes a new start & end date, and a userId
     * Queries the DB for a user's Trips using userId
     * Checks for overlapping conflicts during the date range of newStart to newEnd passed
     * and all the trips the user has.
     *
     * @param tripDto - TripDto, trip data transfer object. Contains start and end date
     *                which are referenced to check for dateConflict.
     * @param userId  - Long, a user's Id used to query for user's trips.
     * @return - Boolean, true if there is a date conflict. False if there is none.
     * Algorithm:
     * 1. Initialize Set<TripdatTrip> trips, to a TreeSet ordered by start Date
     * 2. Query DB for a user's trips and add all to trips collection
     * 3. If the User is editing a Trip remove it from the trips collection
     * 4. for each trip in trips
     * 5.   if there is a date conflict
     * 6.      then return true
     * 7. return false
     */
    @Override
    public boolean checkForTripDateConflict(TripDto tripDto, Long userId) {


        Boolean isTripDateConflict = dao.isTripDateConflict(tripDto.getTripStartDate(), tripDto.getTripEndDate(), userId, tripDto.getTripId());
        if (isTripDateConflict) {
            log.info("TripDateConflict: " + tripDto.getTripStartDate().toString() + " - " + tripDto.getTripEndDate().toString());
        }

        return isTripDateConflict;
    }

    /**
     * Name: isTripItemsOutOfNewTripDateRange
     * Purpose: When editing a Trip's start and end date, must check to make sure there
     * are no conflicts between the new date range and existing Items the user has already
     * planned.
     *
     * @param tripDto - Contains the proposed Date range for the trip
     * @param items   - List<TripItemWrapper> - Contains unpacked tripItems and their segments
     * @return boolean - true if there are conflicts. False if all items fit in the proposed date range.
     * Algorithm:
     * 1. Initialize tripStarDate and tripEndDate
     * 2. for each item in items collection
     * 3.   if an item falls out of the tripDto's date range
     * 4.    return true
     * 5.   else false
     */
    @Override
    public boolean isTripItemsOutOfNewTripDateRange(TripDto tripDto, List<TripItemWrapper> items) {
        // == local variables ==
        // proposed new start and end dates for this trip
        LocalDate tripStartDate = tripDto.getTripStartDate();
        LocalDate tripEndDate = tripDto.getTripEndDate();
        log.debug("tripStartDate: {}", tripStartDate);
        log.debug("tripEndDate: {}", tripEndDate);
        // for each item check if its date range falls within the new date range.
        // If not( newEnd <= curEnd OR newStart >= curEnd)
        // there is a date conflict
        //
        // if there are no items for this trip, then there are no conflicts ...
        for (TripItemWrapper item : items) {
            LocalDate itemStartDate = item.getStartDate();
            LocalDate itemEndDate = item.getEndDate();
            log.debug("itemStartDate: {}", itemStartDate);
            log.debug("itemEndDate: {}", itemEndDate);
            // Check if an item's start date or end date falls outside of the passed
            // trip date range by calling helper function isTripOutOfDateRange
            if (isTripItemOutOfTripDateRange(item, tripDto)) {
                log.debug("new Trip Date Range: " + tripStartDate.toString() + " - " + tripEndDate.toString());
                log.debug("conflicting item date range: " + itemStartDate + " - " + itemEndDate);
                log.debug("=============== Trip Item CONFLICT with trip date range ================");
                return true;
            }
        }
        return false;
    }


    // TODO What are we doing with this function?

    /**
     * name: isNewItemDateRangeConflictingWithPreExistingItems
     * Purpose: Check new item's date range does not conflict with pre-existing items in Trip.
     *
     * @param items     - List of TripItemWrapper, items of a Trip
     * @param startDate - startDate of new Item
     * @param endDate   - endDate of new Item
     * @return boolean - true if conflict, false if not
     * Algorithm:
     * 1.
     */
    @Override
    public boolean isNewItemDateRangeConflictingWithPreExistingItems(List<TripItemWrapper> items, LocalDate startDate, LocalDate endDate, Long itemId) {
        // Remember you have functionality for creating a Map of ordered TripItemWrappers.
        // It is ordered down to the day and time. One possible way to check for overlap is to
        // get the list of items on the newStartDate.Iterate through and see if there is an item scheduled for that time.
        // if that is the case then there is overlap.
        for (TripItemWrapper item : items) {
            if (item.getId() == itemId) continue;
        }
        return false;
    }

    /**
     * Name: isTripItemOutOfTripDateRange
     * Purpose: To abstract the conditional that is a little hard to read
     *
     * @return boolean, returns true if a user's tripItem's  date range falls outside
     * of the passed tripDto's date range.
     * Algorithm:
     * 1. initialize local variables tripStartDate, tripEndDate,
     * itemStartDate, itemEndDate.
     * 2. return true if an item's startDate is before a trip's end date
     * OR an item's startDate is after a trip's endDate
     * OR an item's end date is after a trip's endDate
     * 3. else return false
     */
    private boolean isTripItemOutOfTripDateRange(TripItemWrapper item, TripDto tripDto) {
        LocalDate tripStartDate = tripDto.getTripStartDate();
        LocalDate tripEndDate = tripDto.getTripEndDate();
        LocalDate itemStartDate = item.getStartDate();
        LocalDate itemEndDate = item.getEndDate();

        return ((itemStartDate.compareTo(tripStartDate) < 0)
                || (itemStartDate.compareTo(tripEndDate) > 0)
                || (itemEndDate.compareTo(tripEndDate) > 0));
    }
}
