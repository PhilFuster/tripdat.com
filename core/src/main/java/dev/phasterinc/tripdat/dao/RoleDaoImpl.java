package dev.phasterinc.tripdat.dao;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Role;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 * Name: RoleDaoImpl - Implementation of RoleDao interface
 */
@Repository
@Transactional
public class RoleDaoImpl extends AbstractHibernateDao<Role> implements RoleDao {
    @Override
    public Role findByName(String name) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Role> query = builder.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        query.select(root);
        query.where(builder.equal(root.get("name"), name));
        Query<Role> q = getCurrentSession().createQuery(query);
        Role role = q.uniqueResult();

        return role;
    }


}
