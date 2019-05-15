package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
 * Name: TripdatTripDaoImpl- Implementation of TripdatTripDao. extends AbstractHibernateDao
 *
 */
@Repository
@Transactional
public class TripdatTripDaoImpl extends AbstractHibernateDao<TripdatTrip> implements TripdatTripDao {

    @Override
    public Set<TripdatTrip> getTripItemsByTripId(final Long id) {

        List<TripdatTrip> list = getCurrentSession().createQuery("SELECT trip FROM TripdatTrip trip WHERE trip.tripId =:id ").setParameter("id", id).list();
        for(TripdatTrip tripItem : list) {
            System.out.println(tripItem.toString());
        }
        HashSet set = new HashSet(list);
        return set;
    }

    @Override
    public List<TripdatTrip> findThreeByDateAsc() {
        Query query = getCurrentSession().createQuery(
                "from TripdatTrip trip " +
                        "order by trip.tripStartDate asc"
        );

        query.setMaxResults(3);

        List<TripdatTrip> list = query.list();

        for (TripdatTrip trip : list) {
            System.out.println(
                    "Trip Name: " + trip.getTripName() +
                            "Trip Date: " + trip.getTripStartDate()

            );
        }
        return list;

    }
}
