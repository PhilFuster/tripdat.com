package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.Supplier;
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
 * Name: SupplierServiceImpl - implements the SupplierService interface
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    // == fields ==
    IGenericDao< Supplier > dao;

    @Autowired
    public void setDao(IGenericDao<Supplier> daoToSet) {
        dao = daoToSet;
        dao.setClazz(Supplier.class);
    }

    @Override
    public List<Supplier> findAll() {
        return dao.findAll();
    }

    @Override
    public Supplier findOne(long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(Supplier entity) {
        dao.create(entity);

    }

    @Override
    public Supplier update(Supplier entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(Supplier entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);

    }
}
