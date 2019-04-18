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

@Repository
@Transactional
public class TripdatUserDaoImpl extends AbstractHibernateDao<TripdatUser> implements TripdatUserDao{

    @Override
    public TripdatUser findByEmail(String email) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("userEmail", email));

        return (TripdatUser) crit.uniqueResult();
    }

    @Override
    public TripdatUser findByLogin(String login) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<TripdatUser> query = builder.createQuery(TripdatUser.class);
        Root<TripdatUser> root = query.from(TripdatUser.class);
        query.select(root);
        query.where(builder.equal(root.get("userLogin"), login));
        Query<TripdatUser> q=getCurrentSession().createQuery(query);
        TripdatUser user = q.uniqueResult();
        return user;

    }
}
