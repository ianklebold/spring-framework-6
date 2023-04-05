package jedi.followmypath.webapp.services.pagesrequest;

import org.springframework.data.domain.PageRequest;

public interface PageRequestService {
    PageRequest buildPageRequest(Integer pageNumber, Integer pageSize,String propertyToSort);
}
