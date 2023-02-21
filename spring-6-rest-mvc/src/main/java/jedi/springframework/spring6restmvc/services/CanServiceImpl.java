package jedi.springframework.spring6restmvc.services;

import jedi.springframework.spring6restmvc.model.Can;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class CanServiceImpl implements CanService {
    @Override
    public Can getCanById(UUID id) {
        log.info("Get Can By id method was called from service");

        return Can.builder()
                .name("Merci")
                .id(UUID.randomUUID())
                .birthDate(LocalDateTime.of(2022,6,12,0,0,0))
                .ownerName("Abi")
                .pedigree("Pinscher")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
