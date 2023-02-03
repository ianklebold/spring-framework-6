package jedi.springframework.spring6webapp.bootstrap;

import jedi.springframework.spring6webapp.domain.Author;
import jedi.springframework.spring6webapp.domain.Book;
import jedi.springframework.spring6webapp.domain.Publisher;
import jedi.springframework.spring6webapp.repositories.AuthorRepository;
import jedi.springframework.spring6webapp.repositories.BookRepository;
import jedi.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private final PublisherRepository publisherRepository;

    @Autowired
    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository,PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author ian = new Author();

        ian.setFirstName("Ian");
        ian.setLastName("Fernandez");

        Book ddd = new Book();

        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Author ianSaved = authorRepository.save(ian);
        Book dddSaved = bookRepository.save(ddd);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("54757585");


        Author rodSaved = authorRepository.save(rod);
        Book noEJBSaved = bookRepository.save(noEJB);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Publicador 1");
        publisher.setAddress("Calle falsa 123");
        publisher.setCity("Ciudad falsa 123");
        publisher.setZip("zip 123");

        Publisher publisherSaved = publisherRepository.save(publisher);

        dddSaved.setPublisher(publisherSaved);
        noEJBSaved.setPublisher(publisherSaved);

        ianSaved.getBooks().add(dddSaved);
        rodSaved.getBooks().add(noEJBSaved);
        dddSaved.getAuthors().add(ianSaved);
        noEJBSaved.getAuthors().add(rodSaved);

        authorRepository.save(ianSaved);
        authorRepository.save(rodSaved);
        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);

        System.out.println("Authors saved : count -> " + authorRepository.count());
        System.out.println("Books saved : count -> " + bookRepository.count());
        System.out.println("Publishers saved : count -> " + publisherRepository.count());
    }
}
