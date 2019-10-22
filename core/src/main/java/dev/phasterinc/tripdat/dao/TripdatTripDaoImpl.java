package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripDaoImpl
 * Purpose: Implementation of TripdatTripDao. extends AbstractHibernateDao
 */
@Repository
@Slf4j
@Transactional
public class TripdatTripDaoImpl extends AbstractHibernateDao<TripdatTrip> implements TripdatTripDao {

    /**
     * name: getTripByTripId
     * Purpose: To retrieve a Trip by its tripid
     *
     * @param id - id of trip
     * @return - A singular Trip if a trip has that id
     */
    @Override
    public TripdatTrip getTripByTripId(final Long id) {

        TripdatTrip trip = (TripdatTrip) getCurrentSession().createQuery(
                "SELECT trip FROM TripdatTrip trip WHERE trip.tripId =:id ")
                .setParameter("id", id).getSingleResult();


        return trip;
    }

    /**
     * name: get3UpcomingTripsByUserIdOrderByDateAsc
     * Purpose: Query the DB for the 3 most recent Trips that have not ended and order by ascending date
     *
     * @return - List of TripdatTrip objects
     */
    @Override
    public List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId) {
        Query query = getCurrentSession().createQuery(
                "from TripdatTrip trip " +
                        "where trip.user.userId = :id and trip.tripEndDate > current_date " +
                        "order by trip.tripStartDate asc"

        );
        query.setParameter("id", userId);
        query.setMaxResults(3);

        List<TripdatTrip> list = query.list();


        return list;
    }

    /**
     * Name: getTripsByUserid
     * Purpose: query DB for TripdatTrip objects by userId
     *
     * @return
     */
    @Override
    public Set<TripdatTrip> getTripsByUserId(final Long userId) {
        Query query = getCurrentSession().createQuery(
                "from TripdatTrip trip " +
                        "where trip.user.userId = :id "

        );
        query.setParameter("id", userId);
        Set<TripdatTrip> set = new HashSet<TripdatTrip>(query.list());
        return set;
    }

    /**
     * Name: isTripDateConflict
     * Purpose: Takes a start & end date, and a userId
     * Queries the DB for a user's Trips using userId
     * Checks for overlapping conflicts during the date range of newStart to newEnd passed
     * and all the trips the user has.
     *
     * @param newStartDate - LocalDate, newStartDate used to compare with user's trips
     * @param newEndDate   - LocalDate, newEndDate used to compare with user's trips
     * @param userId       - Long, a user's Id used to query for user's trips.
     * @param tripId       - Long, a Trip's id. Used to query against DB. If the tripId exists
     *                     in the DB I do not want it to be in the result set so that it does not cause
     *                     a false positive date conflict.
     * @return - Boolean, true if there is a date conflict. False if there is none.
     */
    @Override
    public Boolean isTripDateConflict(LocalDate newStartDate, LocalDate newEndDate, Long userId, Long tripId) {
        Query query = getCurrentSession().createQuery(
                "from TripdatTrip trip " +
                        "where user.userId = :userId and " +
                        "trip.tripId != :tripId and " +
                        "(:newStartDate < tripEndDate and tripStartDate < :newEndDate)"

        );
        //
        query.setParameter("newStartDate", newStartDate)
                .setParameter("newEndDate", newEndDate)
                .setParameter("userId", userId)
                .setParameter("tripId", tripId);
        log.info("newStartDate: " + newStartDate + "  newEndDate: " + newEndDate);
        TripdatTrip conflictingTrip;
        boolean isExistingConflict;
        try {
            conflictingTrip = (TripdatTrip) query.getSingleResult();
            isExistingConflict = true;
        } catch (Exception e) {
            isExistingConflict = false;
        }
        return isExistingConflict;
    }


}
