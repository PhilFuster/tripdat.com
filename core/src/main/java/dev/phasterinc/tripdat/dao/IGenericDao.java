package dev.phasterinc.tripdat.dao;

import java.io.Serializable;
import java.util.List;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: IGenericDao - Interface for GenericDao class
 *
 * @param <T> - The model for this dao to represent
 */
public interface IGenericDao < T extends Serializable >{
    void setClazz(Class<T> clazzToSet);
    T findOne(final long id);
    List<T> findAll();
    void create(final T entity);
    T update(final T entity);
    void delete(final T entity);
    void deleteById(final long entityId);


}
