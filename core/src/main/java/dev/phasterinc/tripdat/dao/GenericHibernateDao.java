package dev.phasterinc.tripdat.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: GenericHibernateDao - model a generic dao implementation object to inject into my services
 *
 * @param <T> - The model for this dao implementation to represent
 */

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Primary
@Transactional
public class GenericHibernateDao<T extends Serializable>
        extends AbstractHibernateDao<T> implements IGenericDao<T> {

    @Override
    public void setClazz(Class<T> clazzToSet) {
        super.setClazz(clazzToSet);
    }

    @Override
    public T findOne(long id) {
        return super.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return super.findAll();
    }

    @Override
    public void create(T entity) {
        super.create(entity);
    }

    @Override
    public T update(T entity) {
        return super.update(entity);
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
    }

    @Override
    public void deleteById(long id) {
        super.deleteById(id);
    }
}
