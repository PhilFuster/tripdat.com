package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.TravelAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TravelAgencyServiceImpl - implements the TravelAgencyService interface
 */
@Service
public class TravelAgencyServiceImpl implements TravelAgencyService {

    // == fields ==
    IGenericDao<TravelAgency> dao;

    @Autowired
    public void setDao(IGenericDao< TravelAgency > daoToSet) {
        dao = daoToSet;
        dao.setClazz(TravelAgency.class);
    }

    @Override
    public List<TravelAgency> findAll() {
        return dao.findAll();
    }

    @Override
    public TravelAgency findOne(long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(TravelAgency entity) {
        dao.create(entity);

    }

    @Override
    public TravelAgency update(TravelAgency entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(TravelAgency entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);

    }
}
