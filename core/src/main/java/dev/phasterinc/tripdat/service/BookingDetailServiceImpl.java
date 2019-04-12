package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.BookingDetail;
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
 * Name: BookingDetailServiceImpl - implements the BookingDetailService interface
 */
@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    // == fields ==
    IGenericDao< BookingDetail > dao;

    @Autowired
    public void setDao(IGenericDao< BookingDetail > daoToSet) {
        dao = daoToSet;
        dao.setClazz(BookingDetail.class);
    }

    @Override
    public List<BookingDetail> findAll() {
        return dao.findAll();
    }

    @Override
    public BookingDetail findOne(long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(BookingDetail entity) {
        dao.create(entity);

    }

    @Override
    public BookingDetail update(BookingDetail entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(BookingDetail entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);

    }
}
