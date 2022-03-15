package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.AuthorNamesWithTotalCount;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookInformationByTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction restriction);

    List<Book> findAllByCopiesLessThan(int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findByTitleContaining(String search);


    List<Book> findByAuthorLastNameStartingWith(String str);

    @Query("SELECT COUNT(b) FROM Book b WHERE length(b.title) > :length")
    int countBooksByTitleLength(int length);

    @Query("SELECT a.firstName AS firstName, a.lastName AS lastName, SUM(b.copies) AS totalCopies" +
            " FROM Author a" +
            " JOIN a.books AS b GROUP BY b.author" +
            " ORDER BY totalCopies DESC")
    List<AuthorNamesWithTotalCount> bookCopies();

    @Query("SELECT b.title AS title, b.editionType AS editionType, b.ageRestriction AS ageRestriction, b.price AS price" +
            " FROM Book b" +
            " WHERE b.title = :title")
    BookInformationByTitle findBookByTitle(String title);
}
