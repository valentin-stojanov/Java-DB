package com.example.demo.services;

import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAuthors();
//        this.seedService.seedCategories();

        // Initialize DB
//        this.seedService.seedAll();

        //Write Queries
//-----------------------------------

//        this._01_booksAfter2000(); // 1.	Get all books after the year 2000. Print only their titles.

//        this._02_allAuthorsWithBooksBefore1990(); // 2.	Get all authors with at least one book with release date before 1990. Print their first name and last name.

        this._03_allAuthorsOrderedByBookCount(); // 3.	Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count.

    }

    private void _03_allAuthorsOrderedByBookCount() {

        List<Author> authors = this.authorRepository.findAll();

        authors
                .stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(author -> System.out.printf("%s %s -> %d%n",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size())
                );
    }

    private void _02_allAuthorsWithBooksBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1, 1);

        List<Author> authors = this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);

        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
        System.out.println(authors.size());
    }


    private void _01_booksAfter2000() {
        LocalDate yearAfter2000 = LocalDate.of(2000, 12, 31);

        List<Book> books = this.bookRepository.findByReleaseDateAfter(yearAfter2000);

        books.forEach(b -> System.out.println(b.getReleaseDate() + "----" + b.getTitle()));
//        System.out.println(books.size());
    }
}
