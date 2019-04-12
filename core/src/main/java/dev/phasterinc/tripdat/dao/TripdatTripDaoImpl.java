package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripdatTripDaoImpl extends AbstractHibernateDao<TripdatTrip> implements TripdatTripDao {

    @Override
    public List<TripdatTrip> getTripItemsByTripId() {

        List<TripdatTrip> list = getCurrentSession().createQuery("SELECT trip FROM TripdatTrip trip JOIN trip.tripItems tripItems ").list();
        for(TripdatTrip tripItem : list) {
            System.out.println(tripItem.toString());
        }
        return list;
    }
}
