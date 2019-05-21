package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripDaoImpl- Implementation of TripdatTripDao. extends AbstractHibernateDao
 *
 */
@Repository
@Transactional
public class TripdatTripDaoImpl extends AbstractHibernateDao<TripdatTrip> implements TripdatTripDao {

    /**
     * name: getTripByTripId
     * Purpose: To retrieve a Trip by its tripid
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
     * name: get3TripsByUserIdOrderByDateAsc
     * Purpose: Query the DB for the 3 most recent Trips and order by ascending date
     * @return - List of TripdatTrip objects
     */
    @Override
    public List<TripdatTrip> get3TripsByUserIdOrderByDateAsc(final Long userId) {
        Query query = getCurrentSession().createQuery(
                "from TripdatTrip trip " +
                        "where trip.user.userId = :id " +
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
     * @return
     */
    @Override
    public Set<TripdatTrip> getTripsByUserId() {
        return null;
    }
}
