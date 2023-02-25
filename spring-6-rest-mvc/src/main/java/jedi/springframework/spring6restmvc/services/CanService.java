package jedi.springframework.spring6restmvc.services;

import jedi.springframework.spring6restmvc.model.Can;

import java.util.List;
import java.util.UUID;

public interface CanService {

    List<Can> getCans();
    Can getCanById(UUID id);

    Can saveCan(Can can);

    void updateCan(Can can, UUID dogId);

    void deleteById(UUID dogId);

    void patchCan(Can can, UUID dogId);
}
