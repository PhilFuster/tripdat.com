package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTripItem;
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
            System.out.println("result of findItemsByTrip id is null");
        }
        System.out.println("number of items found by id: " + set.size());
        set.forEach((tripItem -> System.out.println(tripItem)));

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
}
