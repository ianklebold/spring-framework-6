package jedi.followmypath.webapp.model.restauditdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true,value = "pageable")
public class CustomPageImpl<Audit> extends PageImpl<com.auditsystem.auditsystemcommons.entities.Audit> {


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomPageImpl(@JsonProperty("_embedded" )Map _embedded,
                          @JsonProperty("_links" )Map _links,
                          @JsonProperty("page" )Map page) {

        super((List<com.auditsystem.auditsystemcommons.entities.Audit>) _embedded.get("audit"),
                PageRequest.of((Integer) page.get("number"),
                        (Integer) page.get("size")), ((Integer)
                        page.get("number")).longValue()
        );

    }

    public CustomPageImpl(List<com.auditsystem.auditsystemcommons.entities.Audit> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomPageImpl(List<com.auditsystem.auditsystemcommons.entities.Audit> content) {
        super(content);
    }
}
