package dev.phasterinc.tripdat.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: AbstractHibernateDAO
 * Purpose: An abstract parameterized DAO which supports the common generic operations
 *          and is meant to be extended for each entity.
 * @param <T> : the dao to perform operations on.
 */
public abstract class AbstractHibernateDao< T extends Serializable> implements IGenericDao<T>{

    // == fields ==
    private Class< T > clazz;

    @Autowired
    private SessionFactory sessionFactory;

    // == public methods ==

    /**
     * Name: setClazz
     * Purpose: set the clazz object to perform operations on
     * @param clazzToSet - the dao to perform operations on
     */
    public void setClazz ( Class< T > clazzToSet ) {
        this.clazz = clazzToSet;
    }

    /**
     * Name: findOne
     * Purpose: find the row with id passed
     * @param id - id to search the db for
     * @return - the row found from the query. If there is one.
     */
    public T findOne( final long id ) {
        //return (T) getCurrentSession().get(clazz, id );
        return (T) getCurrentSession().get(clazz, id);
    }

    /**
     * Name: findAll
     * Purpose: findAll instances of clazz
     * @return - a List of all the instances of clazz in db
     */
    public List< T > findAll(){
        return getCurrentSession()
                .createQuery( "from " + clazz.getName() ).list();
    }

    /**
     * Name: create
     * Purpose: Persist the passed entity  in the db
     * @param entity - the entity to persist.
     */
    public void create( final T entity ){
        getCurrentSession().persist( entity );
    }

    /**
     * Name: update
     * Purpose: update the entity passed
     * @param entity - the entity to update
     * @return - returns the updated entity
     */
    public T update( final T entity ){
        return (T) getCurrentSession().merge( entity );
    }

    public void delete( final T entity ){
        getCurrentSession().delete( entity );
    }
    public void deleteById( final long id ){
        final T entity = findOne( id);
        delete( entity );
    }

    protected final Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    protected Criteria createEntityCriteria(){
        return getCurrentSession().createCriteria(clazz);
    }
}
