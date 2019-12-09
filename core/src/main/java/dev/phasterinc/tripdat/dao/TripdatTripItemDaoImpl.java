package dev.phasterinc.tripdat.dao;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;


/**
 * Name: TripdatTripItemDaoImpl - Implementation of TripdatTripItemDao. Extends AbstractHibernateDao
 *
 * @param <T> - The model for this dao to represent
 */
@Repository
@Slf4j
@Transactional
public class TripdatTripItemDaoImpl extends AbstractHibernateDao<TripdatTripItem> implements TripdatTripItemDao {

    /**
     * Name: findByTripId
     * Purpose: Find a trip by a tripId and return its items
     * Synopsis: Queries database for a trip by its id
     *
     * @param id Long, the id of the trip.
     * @return HashSet, a HashSet of the trip's items.
     */
    @Override
    public HashSet<TripdatTripItem> findByTripId(long id) {

        Query query = getCurrentSession().createQuery(
                "from TripdatTripItem as item " +
                        "where item.tripdatTrip.tripId = :id "

        ).setParameter("id", id);
        HashSet<TripdatTripItem> set = new HashSet<TripdatTripItem>(query.getResultList());
        if (set.isEmpty()) {
            log.debug("result of findItemsByTrip id is null");
        }
        log.debug("number of items found by id: " + set.size());
        set.forEach((tripItem -> log.debug(tripItem.toString())));

        return set;
    }

    /**
     * Name: findMax4ById
     * Purpose: Finds the four upcoming trip items for a trip.
     * Synopsis: Queries the database for the four upcoming trip items by trip id.
     *
     * @param id Long, the id of the trip
     * @return HashSet, populated with the four upcoming trip items for a specific trip.
     */
    @Override
    public HashSet<TripdatTripItem> findMax4ByTripId(long id) {

        Query query = getCurrentSession().createQuery(
                "from TripdatTripItem as item " +
                        " where item.tripdatTrip.tripId = :id "

        ).setParameter("id", id)
                .setMaxResults(4);
        HashSet<TripdatTripItem> set = new HashSet<TripdatTripItem>(query.getResultList());
        return set;
    }

    /**
     * Name: findByItemId
     * Purpose: To find a trip's item by the item id.
     * Synopsis: Queries the database for a trip's item by the item's id
     *
     * @param id Long, id of the trip item.
     */
    @Override
    public TripdatTripItem findByItemId(long id) {

        Query query = getCurrentSession().createQuery(
                "from TripdatTripItem as item " +
                        "where item.tripItemId = :id "
        ).setParameter("id", id);
        TripdatTripItem item = (TripdatTripItem) query.getSingleResult();
        log.info("TripItem from findByItemId: {}", item);
        return item;
    }
}
