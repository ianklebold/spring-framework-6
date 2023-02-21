package jedi.springframework.spring6restmvc.controller;

import jedi.springframework.spring6restmvc.model.Can;
import jedi.springframework.spring6restmvc.services.CanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class CanController {
    private final CanService canService;

    public Can getCanById(UUID id){
        Can can = canService.getCanById(id);

        if(can != null){
            log.info("Dog by Id : " + can.getId());
            return can;
        }else {
            log.warn("We couldn't find the dog with id : " + id);
            return null;
        }
    }



}
