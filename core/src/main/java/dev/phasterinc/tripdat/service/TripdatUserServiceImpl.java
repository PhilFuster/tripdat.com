package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.TripdatUserDao;
import dev.phasterinc.tripdat.model.TripdatUser;
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
 * Name: TripdatUserServiceImpl - implements the TripdatUserService interface
 */
@Service
public class TripdatUserServiceImpl implements TripdatUserService {
    @Autowired
    TripdatUserDao dao;

    @Autowired
    public void setDao(TripdatUserDao daoToSet) {
        dao = daoToSet;
        dao.setClazz(TripdatUser.class);
    }

    @Override
    public TripdatUser findByEmail(String email) {

        return dao.findByEmail(email);
    }

    @Override
    public List<TripdatUser> findAll() {
        return dao.findAll();
    }

    @Override
    public TripdatUser findOne(long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(TripdatUser entity) {
        dao.create(entity);

    }

    @Override
    public TripdatUser update(TripdatUser entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(TripdatUser entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }

    @Override
    public TripdatUser findByLogin(String login) {
        return dao.findByLogin(login);
    }
}
