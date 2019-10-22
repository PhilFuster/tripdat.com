package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatUser;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatUserDaoImpl
 * Purpose: Implements TripdatUserDao and extends the AbstractHibernateDao class
 */
@Repository
@Transactional
public class TripdatUserDaoImpl extends AbstractHibernateDao<TripdatUser> implements TripdatUserDao {

    /**
     * Name: findByEmail
     * Purpose: To find a user by email
     * Synopsis: Queries the db for a user searching with the email entered in the form
     *
     * @param email String, the email to query the database with.
     */
    @Override
    public TripdatUser findByEmail(String email) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("userEmail", email));

        return (TripdatUser) crit.uniqueResult();
    }

    /**
     * Name: findByLogin
     * Purpose: To find a user by the login entered
     * Synopsis: Build's a query and queries the database for a unique result
     *
     * @param login String, login to query the database with
     */
    @Override
    public TripdatUser findByLogin(String login) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<TripdatUser> query = builder.createQuery(TripdatUser.class);
        Root<TripdatUser> root = query.from(TripdatUser.class);
        query.select(root);
        query.where(builder.equal(root.get("userLogin"), login));
        Query<TripdatUser> q = getCurrentSession().createQuery(query);
        TripdatUser user = q.uniqueResult();
        return user;

    }
}
