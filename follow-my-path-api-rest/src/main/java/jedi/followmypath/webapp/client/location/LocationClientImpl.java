package jedi.followmypath.webapp.client.location;

import jedi.followmypath.webapp.client.mapper.LocationMapper;
import jedi.followmypath.webapp.model.dto.LocationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LocationClientImpl implements LocationClient{

    public static final  String POSITION_PATH = "https://{host_api}/";

    public static final String FORMAT_RESULT = "json";
    @Value("${key.api.iplocation}")
    public String API_KEY_IP2LOCATION;

    @Value("${host.api.ip2location}")
    public String HOST_IP2LOCATION;

    private final LocationMapper locationMapper;

    @Override
    public LocationDTO getCarPosition() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        // Armar url con varaibles "https://{host_api}/?key={api_key}&ip={user_ip}&format=json"
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(POSITION_PATH.replace("{host_api}",HOST_IP2LOCATION))
                .queryParam("key",API_KEY_IP2LOCATION)
                .queryParam("ip",getIpCar())
                .queryParam("format",FORMAT_RESULT);

        URI uriBuilder = builder.build().toUri();

        Object result = restTemplate.getForEntity(uriBuilder, Object.class).getBody();

        return locationMapper.mapToLocationDTO((Map) result);
    }

    private String getIpCar(){
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String miIP = in.readLine();
            in.close();
            return miIP;
        } catch (Exception e) {
            System.out.println("No se pudo obtener la dirección IP pública: " + e.getMessage());
            return null;
        }
    }
}
