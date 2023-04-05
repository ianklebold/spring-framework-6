package jedi.followmypath.webapp.services.pagesrequest.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageRequestService implements jedi.followmypath.webapp.services.pagesrequest.PageRequestService {
    private static final  int DEFAULT_PAGE = 0;
    private static final  int DEFAULT_PAGE_SIZE = 25;
    @Override
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize,String propertyToSort) {
        int queryPageNumber;
        int queryPageSize;

        //El paginado de spring siempre comienza de 0, 1 menos de lo que llega en la request, por ello descontamos
        if(pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        }else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if(pageSize == null){
            queryPageSize = DEFAULT_PAGE_SIZE;
        }else {
            queryPageSize = pageSize;
        }

        if(propertyToSort != null)
        {
            Sort sortPages = Sort.by(Sort.Order.asc(propertyToSort));

            return PageRequest.of(queryPageNumber,queryPageSize,sortPages);
        }else {
            return PageRequest.of(queryPageNumber,queryPageSize);
        }
    }
}
