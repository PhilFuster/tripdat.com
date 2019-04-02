package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatUser;

import java.util.List;

public interface TripdatUserDao {
    void save(TripdatUser user );

    List<TripdatUser> list();
}
