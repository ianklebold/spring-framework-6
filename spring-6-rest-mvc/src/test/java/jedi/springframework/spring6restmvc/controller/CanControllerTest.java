package jedi.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jedi.springframework.spring6restmvc.model.Can;
import jedi.springframework.spring6restmvc.services.CanService;
import jedi.springframework.spring6restmvc.services.CanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
/*
Un Test slice es aquel el cual nos permite tomar componentes del contexto de test de spring, pero solo una parte
y estos son: @Controller, @ControllerAdvice, @JsonComponent, Converter, Filter, WebMvcConfigurer
No Son : @Service, @Component, @Repository beans

Se configura con SpringRunner para que despliegue una aplicación web de forma parcial
auto configurando sólo aquellos componentes necesarios para una aplicación web MVC.
 */
@WebMvcTest(CanController.class) //Test Slice
class CanControllerTest {

    //<-- Con el test slice no es necesario autowired del controller -->
    //@Autowired
    //CanController canController;

    /**
     * Spring MockMVC allows you to test the controller interactions in a
     * servlet context without the application running in an application server.
     */
    @Autowired
    MockMvc mockMvc;

    @MockBean //<-- Le decimos quel Mock del servicio sea parte del contexto de test de spring -->
    CanService canService;

    CanServiceImpl canServiceImpl;

    @Autowired //<-- Spring context ya nos provee un mapeador de objetos json.
    ObjectMapper objectMapper;


    @Captor//<-- Sera el encargado de capturar el id que se envie. Se queda escuchando por un argumento
    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

    @Captor
    ArgumentCaptor<Can> canArgumentCaptor = ArgumentCaptor.forClass(Can.class);

    @BeforeEach
    void setUp(){
        //Por cada test se creara un nuevo objeto Service.
        this.canServiceImpl= new CanServiceImpl();
    }

    @Nested
    @DisplayName("TESTING GET ENDPOINTS")
    class testing_get_endpoints{

        @Test
        void getListOfDogs() throws Exception {
            List<Can> canTest = canServiceImpl.getCans(); //Obtenemos el objeto de test

            //Dado un servicio (interfaz), con un argumento UUID random debemos obtener un Can
            given(canService.getCans()).willReturn(canTest);

            mockMvc.perform(get(CanController.CAN_PATH)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) //Se espera un status 200
                    .andExpect(jsonPath("$.length()",is(3)));

        }

        @Test
        void getCanById() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0); //Obtenemos el objeto de test

            //Dado un servicio (interfaz), con un argumento UUID random debemos obtener un Can
            given(canService.getCanById(any(UUID.class))).willReturn(canTest);

            mockMvc.perform(get(CanController.CAN_PATH_ID, UUID.randomUUID())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) //Se espera un status 200
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)); //Se espera un media type de JSON

        }

        @Test
        void getCanById_json() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0); //Obtenemos el objeto de test

            //Dado un servicio (interfaz), con un argumento UUID del objeto can
            given(canService.getCanById(canTest.getId())).willReturn(canTest);

            mockMvc.perform(get(CanController.CAN_PATH_ID, canTest.getId())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) //Se espera un status 200
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //Se espera un media type de JSON
                    //Con Json Path podemos controlar y verificar el contenido que retorna el endpoint
                    .andExpect(jsonPath("$.name",is(canTest.getName())))
                    .andExpect(jsonPath("$.ownerName",is(canTest.getOwnerName())))
                    .andExpect(jsonPath("$.pedigree",is(canTest.getPedigree())))
            ;

        }
    }

    @Nested
    @DisplayName("TESTING POST ENDPOINTS")
    class testing_post_endpoints{
        @Test
        void convert_object_to_json() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0);

            //Hacemos unmarshing del objeto
            String jsonOBject = objectMapper.writeValueAsString(canTest);

            System.out.println(jsonOBject);
        }

        @Test
        void post_new_dog() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0);
            canTest.setId(null); //Cuando creamos un nuevo dog no tenemos ID

            //Obtengo el 1 porque, simulamos que obtenemos un CAN completo, con el ID autogenerado
            given(canService.saveCan(any(Can.class))).willReturn(canServiceImpl.getCans().get(1));

            mockMvc.perform(post(CanController.CAN_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(canTest)) //Enviamos el JSON
            )
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location")); //Esperamos que exista un header
        }

    }

    @Nested
    @DisplayName("TESTING UPDATE ENDPOINTS")
    class testing_update_endpoints{
        @Test
        void update_new_dog() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0);

            mockMvc.perform(put(CanController.CAN_PATH_ID,canTest.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(canTest))
            )
                    .andExpect(status().isNoContent());

            //Verify, verifica que almenos una vez se ejecute un metodo, en este caso updateCan
            verify(canService).updateCan(any(Can.class),any(UUID.class));
        }


    }

    @Nested
    @DisplayName("TESTING DELETE ENDPOINTS")
    class testing_delete_endpoints{
        @Test
        void delete_dog_by_id() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0);

            //Llamamos al controller y al endpoint
            mockMvc.perform(delete(CanController.CAN_PATH_ID,canTest.getId())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());




            //Verify, verifica que almenos una vez se ejecute un metodo, en este caso delete
            //Por el controller se ejecuta este metodo pero por el mock y se captura el argumento
            verify(canService).deleteById(uuidArgumentCaptor.capture());

            //Tenemos el argumento y ahora controlamos que los ID coincidan
            assertThat(canTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        }


    }

    @Nested
    @DisplayName("TESTING PATCH ENDPOINTS")
    class testing_patch_endpoints{
        @Test
        void patch_dog() throws Exception {
            Can canTest = canServiceImpl.getCans().get(0);


            //El objeto se construye con un MAP
            //Ya que patch puede ser una actualizacion parcial del objeto
            Map<String,Object> dogsMap = new HashMap<>();
            dogsMap.put("name","Merceditas Justicia");
            dogsMap.put("ownerName","Alba");

            mockMvc.perform(patch(CanController.CAN_PATH_ID,canTest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dogsMap))) //Convertimos el MAP en JSON
                    .andExpect(status().isNoContent());

            //Verificar que cuando llamo al endpoint se ejecute el servicio PatchCan
            verify(canService).patchCan(canArgumentCaptor.capture(),uuidArgumentCaptor.capture());

            assertThat(canTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());

            assertThat(dogsMap.get("name")).isEqualTo(canArgumentCaptor.getValue().getName());
            assertThat(dogsMap.get("ownerName")).isEqualTo(canArgumentCaptor.getValue().getOwnerName());

        }


    }

    @Nested
    @DisplayName("TESTING EXCEPTIONS")
    class testing_exceptions{
        @Test
        void getCanByIdNotFound() throws Exception {

            given(canService.getCanById(any(UUID.class))).willThrow(NotFoundException.class);

            mockMvc.perform(get(CanController.CAN_PATH_ID,UUID.randomUUID()))
                    .andExpect(status().isNotFound());

        }


    }



}