package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TripdatUserDaoImpl implements TripdatUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(TripdatUser user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<TripdatUser> list() {
        @SuppressWarnings("unchecked")
        TypedQuery<TripdatUser> query = sessionFactory.getCurrentSession().createQuery("FROM TripdatUser");
        return query.getResultList();
    }
}
