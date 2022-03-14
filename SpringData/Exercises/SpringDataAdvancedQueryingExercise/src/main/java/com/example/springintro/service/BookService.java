package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> booksTitleByAgeRestriction(String ageRestriction);

    List<String> selectGoldEditionBook5000Copies();

    List<Book> selectBookByPriceLowerThan5HigherThan40();

    List<String> selectNotReleasedBook(int year);

    List<String> bookReleasedBeforeDate(String strDate);

    List<String> bookContainingString(String str);

    List<String> booksWrittenByAuthorsLastNameContains(String str);
}
