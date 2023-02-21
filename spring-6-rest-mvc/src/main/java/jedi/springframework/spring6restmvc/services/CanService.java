package jedi.springframework.spring6restmvc.services;

import jedi.springframework.spring6restmvc.model.Can;

import java.util.UUID;

public interface CanService {
    Can getCanById(UUID id);
}
