package jedi.springframework.spring6restmvc.controller;

import jedi.springframework.spring6restmvc.model.Can;
import jedi.springframework.spring6restmvc.services.CanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
//Con rest controller le indicamos a Spring que el Response body no sera un html, si no un JSON
//RestController hace uso de @ResponseBody que es el encargado de serializar la respuesta dentro de un JSON
//Spring hace uso de JACKSON como libreria por default para convertir Java Objects a JSON
@RestController
public class CanController {

    public static final  String CAN_PATH = "/api/v1/dogs";
    public static final  String CAN_PATH_ID = CAN_PATH + "/{dogId}";
    private final CanService canService;

    @PatchMapping(value = CAN_PATH_ID)
    public ResponseEntity patchDogById(@RequestBody Can can, @PathVariable(name = "dogId") UUID dogId){
        /**
         * El metodo patch no es muy usado, justamente porque en la parte del servicio hay que controlar y setear
         * solamente aquellos atributos que no son nulos, es mucho mas verboso que UPDATE.
         * No es muy utilizado y casi siempre cuando debamos usado estara explicitamente indicado den el caso de uso.
         */

        canService.patchCan(can,dogId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = CAN_PATH_ID)
    public ResponseEntity deleteDogById(@PathVariable(name = "dogId") UUID dogId){
        canService.deleteById(dogId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = CAN_PATH_ID)
    public ResponseEntity updateCan(@RequestBody Can can, @PathVariable(name = "dogId") UUID dogId){
        canService.updateCan(can,dogId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = CAN_PATH)
    public ResponseEntity createCan(@RequestBody Can can){

        Can canSaved = canService.saveCan(can);

        //El header indica al cliente donde puede encontrar el recurso recien creado (Buena practica usarlo)
        HttpHeaders headers = new HttpHeaders();

        headers.add("Location","/api/v1/dogs/"+canSaved.getId());

        //Response entity, es la respuesta de la operacion
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    //@RequestMapping("/api/v1/dogs") RequestMapping acepta cualquier tipo de metodo HTTP, pero para hacerlo mas estricto
    // Usamos un GETMAPPING, POSTMAPPING, PUTMAPPING, ETC.
    @GetMapping(value = CAN_PATH)
    public List<Can> getCans(){
        return canService.getCans();
    }

    // GETMAPPING AÑADE AUTOMATICAMENTE UN "/" ANTES DEL PRIMER VALUE
    // PATHVARIABLE PERMITE TOMAR LA VARIABLE QUE SE ENVIA POR LA URI y utilizarla en el metodo
    @GetMapping(value = CAN_PATH_ID)
    public Can getCanById(@PathVariable(name = "dogId") UUID dogId){
        Can can = canService.getCanById(dogId);

        if(can != null){
            log.info("Dog by Id : " + can.getId());
            return can;
        }else {
            log.warn("We couldn't find the dog with id : " + dogId);
            return null;
        }
    }


}
