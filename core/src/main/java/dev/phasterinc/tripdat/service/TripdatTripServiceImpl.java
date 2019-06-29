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
        tripDto.setTripStartDate(LocalDate.of(2020,7,23));
        tripDto.setTripEndDate(LocalDate.of(2020,8,14));
        TripDto testDto = TripDto.builder().tripStartDate(LocalDate.of(2020,7,24))
                                           .tripEndDate(LocalDate.of(2020,7,24))
                                           .tripId(tripDto.getTripId())
                                           .build();
        testIsTripDateConflict(testDto, userId);
        Boolean isTripDateConflict = dao.isTripDateConflict(tripDto.getTripStartDate(), tripDto.getTripEndDate(), userId, tripDto.getTripId());
        if (isTripDateConflict) {
            log.info("TripDateConflict: " + tripDto.getTripStartDate().toString() + " - " + tripDto.getTripEndDate().toString());
        }
        /*// tripDto id is greater than 0, a trip is being edited. Remove the trip from the collection
        // so a false conflict does not occur
        if(tripDto.getTripId() > 0) {
            // remove a tripId if found
            trips.removeIf((TripdatTrip trip)-> trip.getTripId().equals(tripDto.getTripId()));
        }
        // if the trips do not not overlap return true. There is a date conflict
        for (TripdatTrip trip : trips) {
            // If not( newEnd <= curEnd OR newStart >= curEnd) ->
            // There is a date conflict
            boolean isTripDateConflict =  !(tripDto.getTripEndDate().compareTo(trip.getTripStartDate()) <= 0) ||
                    (tripDto.getTripStartDate().compareTo(trip.getTripEndDate()) >= 0);
            //
            boolean isTripDateConflict =  !(tripDto.getTripEndDate().compareTo(trip.getTripStartDate()) <= 0) ||
                    (tripDto.getTripStartDate().compareTo(trip.getTripEndDate()) >= 0);

            log.info("newDateRange: " + tripDto.getTripStartDate().toString() + " - " + tripDto.getTripEndDate().toString());
            log.info("conflicting date range: " + trip.getTripStartDate().toString() + " - " + trip.getTripEndDate().toString()); // code for testing this function
            if (isTripDateConflict) {
                log.info("===============CONFLICT================");
                log.info("newDateRange: " + tripDto.getTripStartDate().toString() + " - " + tripDto.getTripEndDate().toString());
                log.info("conflicting date range: " + trip.getTripStartDate().toString() + " - " + trip.getTripEndDate().toString()); // code for testing this function
                return true;
            }

        }*/
        // no conflicts, return false
        return isTripDateConflict;
    }

    /**
     * Name: testIsTripDateConflict
     * Purpose: Tests different possible Date conflicts to test durability of the function.
     * Notes: Will be manipulating trips date as to avoid having to alter the db for the test.
     * Will be referencing the DB for a User's trips to test against. Trips will be viewed
     * on the database console to check for validity.
     * @param tripDto - TripDto, contains tripStartDate and tripEndDate to test against
     *                  the user's id
     * @param userId  - the user to query.
     * Tests:
     *               1. tripDate is on the same day as an item
     */
    private void testIsTripDateConflict(TripDto tripDto, Long userId) {
        LocalDate tripStartDate = tripDto.getTripStartDate();
        LocalDate tripEndDate = tripDto.getTripEndDate();
        //
        boolean isTripDateConflict = dao.isTripDateConflict(tripStartDate, tripEndDate,userId, tripDto.getTripId());
        if (isTripDateConflict) {
            log.info("TripDateConflict: " + tripStartDate.toString() + " - " + tripEndDate.toString());
        }
        log.info("Test is TripDate conflict");
        log.info("tripStartDate              tripEndDate              expected result              result");
        // Test when trip begins and ends on same day as existing trip.
        // expected result: false
        tripDto.setTripStartDate(LocalDate.of(2019, 5, 10));
        tripDto.setTripEndDate(LocalDate.of(2019, 5, 20));
        testDateConflict(tripDto, userId, "true");
        //
        // Test when trip start date matches other trips start date
        // expected result: true
        tripDto.setTripStartDate(LocalDate.of(2020, 9, 3));
        tripDto.setTripEndDate(LocalDate.of(2020, 10, 26));
        testDateConflict(tripDto, userId, "true");
        //
        // Test when trip end date is on same day existing trips start date
        // expected result: true
        tripDto.setTripStartDate(LocalDate.of(2019, 5, 1));
        tripDto.setTripEndDate(LocalDate.of(2019, 5, 11));
        testDateConflict(tripDto, userId, "true");
        //
        // Test when there is no conflict
        // expected result: true
        tripDto.setTripStartDate(LocalDate.of(2021, 1, 5));
        tripDto.setTripEndDate(LocalDate.of(2021, 2, 11));
        testDateConflict(tripDto, userId, "false");

    }

    /**
     * Name: testDateConflict
     * Purpose: helper function to perform a isTripDateConflict test and log it
     * @param tripDto - TripDto, holds trip's start and end date
     * @param userId - Long, userId whose trips to query from db
     * @param expectedResult - String, the expected result when running a test case.
     */
    private void testDateConflict(TripDto tripDto, Long userId, String expectedResult) {
        LocalDate tripStartDate = tripDto.getTripStartDate();
        LocalDate tripEndDate = tripDto.getTripEndDate();
        Long tripId = tripDto.getTripId();
        boolean isTripDateConflict = dao.isTripDateConflict(tripStartDate, tripEndDate,userId, tripId);
        log.info(tripStartDate.toString() + "    " + tripEndDate.toString() + "             " + expectedResult + "        " + isTripDateConflict);
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
