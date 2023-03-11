package jedi.followmypath.webapp.services.cars;

import org.springframework.data.domain.PageRequest;

public interface PageRequestService {
    PageRequest buildPageRequest(Integer pageNumber, Integer pageSize);
}
