package jedi.springframework.spring6restmvc.services;

import jedi.springframework.spring6restmvc.model.Can;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CanServiceImpl implements CanService {

    Map<UUID,Can> canMap;

    public CanServiceImpl(){
        this.canMap = new HashMap<>();

        Can can1 = Can.builder()
                .name("Merci")
                .id(UUID.randomUUID())
                .birthDate(LocalDateTime.of(2022,6,12,0,0,0))
                .ownerName("Abi")
                .pedigree("Pinscher")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Can can2 = Can.builder()
                .name("Marti")
                .id(UUID.randomUUID())
                .birthDate(LocalDateTime.of(2022,6,12,0,0,0))
                .ownerName("Ian")
                .pedigree("None")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Can can3 = Can.builder()
                .name("Berni")
                .id(UUID.randomUUID())
                .birthDate(LocalDateTime.of(2022,6,12,0,0,0))
                .ownerName("Agustina")
                .pedigree("None")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        canMap.put(can1.getId(),can1);
        canMap.put(can2.getId(),can2);
        canMap.put(can3.getId(),can3);
    }

    @Override
    public List<Can> getCans() {
        return new ArrayList<>(this.canMap.values());
    }

    @Override
    public Can getCanById(UUID id) {
        log.info("Get Can By id method was called from service");

        return this.canMap.get(id);
    }

    @Override
    public Can saveCan(Can can) {
        Can canSaved =
                Can.builder()
                .id(UUID.randomUUID())
                .name(can.getName())
                .pedigree(can.getPedigree())
                .ownerName(can.getOwnerName())
                .birthDate(can.getBirthDate())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        canMap.put(canSaved.getId(),canSaved);

        return canSaved;
    }

    @Override
    public void updateCan(Can canUpdated, UUID dogId) {
        Can can = getCanById(dogId);

        if (can != null){
            can.setName(canUpdated.getName());
            can.setPedigree(canUpdated.getPedigree());
            can.setOwnerName(canUpdated.getOwnerName());
            can.setBirthDate(canUpdated.getBirthDate());

            can.setUpdateDate(LocalDateTime.now());

            canMap.put(dogId,can);
        }
    }

    @Override
    public void deleteById(UUID dogId) {
        Can can = getCanById(dogId);

        if (can != null){
            canMap.remove(dogId);
        }
    }

    @Override
    public void patchCan(Can canPatched, UUID dogId) {
        Can can = getCanById(dogId);

        if (can != null){

            if(StringUtils.hasText(canPatched.getName())){
                can.setName(canPatched.getName());
            }

            if(StringUtils.hasText(canPatched.getPedigree())){
                can.setPedigree(canPatched.getPedigree());
            }

            if(StringUtils.hasText(canPatched.getOwnerName())){
                can.setOwnerName(canPatched.getOwnerName());

            }

            if(canPatched.getBirthDate() != null){
                can.setBirthDate(canPatched.getBirthDate());
            }

            can.setUpdateDate(LocalDateTime.now());

            canMap.put(dogId,can);
        }
    }
}
