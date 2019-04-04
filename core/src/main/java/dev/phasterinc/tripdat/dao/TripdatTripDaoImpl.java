package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;
import dev.phasterinc.tripdat.model.TripdatUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TripdatTripDaoImpl implements TripdatTripDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(TripdatTrip trip) {
        sessionFactory.getCurrentSession().save(trip);
    }

    @Override
    public List<TripdatTrip> list() {
        @SuppressWarnings("unchecked")
        TypedQuery<TripdatTrip> query = sessionFactory.getCurrentSession().createQuery("FROM TripdatTrip");
        return query.getResultList();
    }
}
