package jedi.followmypath.webapp.services.cars;

import com.auditsystem.auditsystemcommons.entities.Audit;
import com.auditsystem.auditsystemcommons.entities.enums.AuditType;
import com.auditsystem.auditsystemcommons.entities.enums.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jedi.followmypath.webapp.client.audit.AuditClient;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.mappers.CarMapper;
import jedi.followmypath.webapp.model.dto.CarDTO;
import jedi.followmypath.webapp.repositories.CarRepository;
import jedi.followmypath.webapp.services.pagesrequest.PageRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CarServiceJPA implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    private final PageRequestService pageRequestService;

    private final AuditClient auditClient;


    public static final String CAR_PATH = "/api/v1/cars";
    private static final String PROPERTY_SORT = "model";
    @Override
    public CarDTO createCar(CarDTO carDTO) throws JsonProcessingException {

        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        auditClient.createAudit(
                Audit.builder()
                        .description("Car created")
                        .level(Level.INFO)
                        .auditType(AuditType.WEBSERVICE)
                        .webservice(CAR_PATH)
                        .log(jsonMapper.writeValueAsString(carDTO))
                .build()
        );

        return carMapper.carToCarDto(
                carRepository.save(carMapper.carDtoToCar(carDTO))
        );
    }

    @Override
    public Optional<CarDTO> updateCar(UUID uuid, CarDTO carDTO) throws JsonProcessingException {
        Optional<CarDTO> car = getCarById(uuid);
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        if(car.isPresent()){

            Car carUpdated = carMapper.carDtoToCar(car.get());

            carUpdated.setModel(carDTO.getModel());
            carUpdated.setMake(carDTO.getMake());
            carUpdated.setPatentCar(carDTO.getPatentCar());
            carUpdated.setSize(carDTO.getSize());
            carUpdated.setYearCar(carDTO.getYearCar());
            carUpdated.setFuelType(carDTO.getFuelType());
            carUpdated.setUpdateCarDate(LocalDateTime.now());

            carRepository.save(carUpdated);

            auditClient.createAudit(
                    Audit.builder()
                            .description("Car updated")
                            .level(Level.INFO)
                            .auditType(AuditType.WEBSERVICE)
                            .webservice(CAR_PATH)
                            .log(jsonMapper.writeValueAsString(carDTO))
                            .build()
            );

            return Optional.of(carMapper.carToCarDto(carUpdated));
        }

        return Optional.empty();
    }

    @Override
    public Optional<CarDTO> getCarById(UUID id) {
        return Optional.ofNullable(carMapper.carToCarDto(carRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public Page<CarDTO> getCars(String model, String make, Integer yearCar,
                                Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = pageRequestService.buildPageRequest(pageNumber,pageSize,PROPERTY_SORT);

        Page<Car> pageCars;
        if(StringUtils.hasText(model) && !StringUtils.hasText(make)){
            pageCars = listCarByModel(model,pageRequest);
        } else if (!StringUtils.hasText(model) && StringUtils.hasText(make)) {
            pageCars = listCarByMake(make,pageRequest);
        } else if (StringUtils.hasText(model) && StringUtils.hasText(make)) {
            pageCars = listCarByModelAndMake(make,model,pageRequest);
        } else {
            pageCars = carRepository.findAll(pageRequest);
        }

        if(yearCar != null){
            pageCars = new PageImpl<>(
                    pageCars.stream()
                    .filter(car -> yearCar.equals(car.getYearCar()))
                    .toList()
            );
        }

        return pageCars.map(carMapper::carToCarDto);
    }

    public Page<Car> listCarByModel(String model,Pageable pageable){
        // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
        return carRepository.findAllByModelIsLikeIgnoreCase("%"+ model +"%", pageable);
    }

    public Page<Car> listCarByMake(String make, Pageable pageable){
        // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
        return carRepository.findAllByMakeIsLikeIgnoreCase("%"+ make +"%", pageable);
    }

    public Page<Car> listCarByModelAndMake(String make,String model,Pageable pageable){
        // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
        return carRepository.findAllByMakeIsLikeIgnoreCaseAndModelIsLikeIgnoreCase("%"+ make +"%", "%"+ model +"%", pageable);
    }

    @Override
    public Boolean deleteCar(UUID uuid) {
        if (carRepository.existsById(uuid)){
            auditClient.createAudit(
                    Audit.builder()
                            .description("Car deleted")
                            .level(Level.INFO)
                            .auditType(AuditType.WEBSERVICE)
                            .webservice(CAR_PATH)
                            .log("Car with id "+uuid+" deleted")
                            .build()
            );
            carRepository.deleteById(uuid);
            return true;
        }else {
            auditClient.createAudit(
                    Audit.builder()
                            .description("Car deleted")
                            .level(Level.ERROR)
                            .auditType(AuditType.WEBSERVICE)
                            .webservice(CAR_PATH)
                            .log("Car with id "+uuid+" deleted")
                            .build()
            );
            return false;
        }
    }
}
