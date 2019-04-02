package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.TripdatUser;

import java.util.List;

public interface TripdatUserService {
    void save(TripdatUser tripdatUser);

    List<TripdatUser> list();

}
