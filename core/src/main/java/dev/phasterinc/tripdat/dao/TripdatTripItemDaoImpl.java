package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTripItem;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripItemDaoImpl - Implementation of TripdatTripItemDao. Extends AbstractHibernateDao
 * @param <T> - The model for this dao to represent
 */
@Repository
@Slf4j
@Transactional
public class TripdatTripItemDaoImpl extends AbstractHibernateDao<TripdatTripItem> implements TripdatTripItemDao {
    @Override
    public HashSet<TripdatTripItem> findByTripId(long id) {

        Query query = getCurrentSession().createQuery(
                "from TripdatTripItem as item " +
                            "where item.tripdatTrip.tripId = :id "

        ).setParameter("id", id);
        HashSet<TripdatTripItem> set = new HashSet<TripdatTripItem>( query.getResultList());
        if(set.isEmpty()) {
            log.debug("result of findItemsByTrip id is null");
        }
        log.debug("number of items found by id: " + set.size());
        set.forEach((tripItem -> log.debug(tripItem.toString())));

        return set;
    }

    @Override
    public HashSet<TripdatTripItem> findMax4ByTripId(long id) {

        Query query = getCurrentSession().createQuery(
                "from TripdatTripItem as item " +
                        "where item.tripdatTrip.tripId = :id "

        ).setParameter("id", id).setMaxResults(4);
        HashSet<TripdatTripItem> set = new HashSet<TripdatTripItem>( query.getResultList());
        return set;
    }

    @Override
    public TripdatTripItem findByItemId(long id) {

        Query query = getCurrentSession().createQuery(
                "from TripdatTripItem as item " +
                                "where item.tripItemId = :id "
        ).setParameter("id", id);
        TripdatTripItem item = (TripdatTripItem)  query.getSingleResult();
        log.info("TripItem from findByItemId: {}",item);
        return item;
    }
}
