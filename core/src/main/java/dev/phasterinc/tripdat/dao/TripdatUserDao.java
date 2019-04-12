package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatUser;


public interface TripdatUserDao extends IGenericDao<TripdatUser> {

    TripdatUser findByEmail(String email);
    TripdatUser findByLogin(String loginId);

}
