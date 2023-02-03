package jedi.springframework.spring6webapp.services;

import jedi.springframework.spring6webapp.domain.Book;

public interface BookService {
    Iterable<Book> findAll();
}
