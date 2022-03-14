package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
//         seedData();
//         printAllBooksAfterYear(2000);
//         printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//         printAllAuthorsAndNumberOfTheirBooks();
//         printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
//        -------------------------------------------------
        Scanner scanner = new Scanner(System.in);


        String input = scanner.nextLine();



//        _08_bookTitleSearch(scanner.nextLine());
//        _07_booksSearch(scanner.nextLine());
//        _06_authorsSearch(scanner.nextLine());
//        _05_booksReleasedBeforeDate(scanner.nextLine());
//        _04_notReleasedBook(scanner.nextLine());
//        _03_booksByPrice();
//        _02_goldenBooks();
//        _01_booksTitleByAgeRestriction(scanner.nextLine());


    }

    private void _08_bookTitleSearch(String input) {
        this.bookService.booksWrittenByAuthorsLastNameContains(input)
                .forEach(System.out::println);
    }

    private void _07_booksSearch(String input) {
        this.bookService.bookContainingString(input)
                .forEach(System.out::println);
    }

    private void _06_authorsSearch(String input) {
        this.authorService.autorsSearchNamesEndsWith(input)
                .forEach(System.out::println);
    }

    private void _05_booksReleasedBeforeDate(String dateStr) {
        this.bookService.bookReleasedBeforeDate(dateStr)
                .forEach(System.out::println);
    }

    private void _04_notReleasedBook(String yearStr) {
        this.bookService.selectNotReleasedBook(Integer.parseInt(yearStr))
                .forEach(System.out::println);
    }

    private void _03_booksByPrice() {
        this.bookService.selectBookByPriceLowerThan5HigherThan40()
                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    private void _02_goldenBooks() {
        this.bookService.selectGoldEditionBook5000Copies()
                .forEach(System.out::println);
    }

    private void _01_booksTitleByAgeRestriction(String input) {
        this.bookService.booksTitleByAgeRestriction(input)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
