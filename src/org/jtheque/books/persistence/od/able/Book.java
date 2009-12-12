package org.jtheque.books.persistence.od.able;

import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Notable;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.od.able.Type;

import java.util.Collection;

/**
 * A book specification.
 *
 * @author Baptiste Wicht
 */
public interface Book extends Data, Notable {
    /**
     * Return the title of the book.
     *
     * @return The title of the book.
     */
    String getTitle();

    /**
     * Set the title of the book.
     *
     * @param title The title of the book.
     */
    void setTitle(String title);

    /**
     * Return the authors.
     *
     * @return The authors.
     */
    Collection<Person> getAuthors();

    /**
     * Return the year.
     *
     * @return The year.
     */
    int getYear();

    /**
     * Set the year of the book.
     *
     * @param year The year of the book.
     */
    void setYear(int year);

    /**
     * Return the pages of the book.
     *
     * @return The number of pages of the book.
     */
    int getPages();

    /**
     * Set the pages of the book.
     *
     * @param pages The pages of the book.
     */
    void setPages(int pages);

    /**
     * Return the isbn10 of the book.
     *
     * @return The isbn10 of the book.
     */
    String getIsbn10();

    /**
     * Set the ISBN-10 of the book.
     *
     * @param isbn10 The ISBN-10 of the book.
     */
    void setIsbn10(String isbn10);

    /**
     * Return the ISBN-13 of the book.
     *
     * @return The ISBN-13 of the book.
     */
    String getIsbn13();

    /**
     * Set the ISBN-13 of the book.
     *
     * @param isbn13 The ISBN-13 of the book.
     */
    void setIsbn13(String isbn13);

    /**
     * Return the resume of the book.
     *
     * @return The resume of book.
     */
    String getResume();

    /**
     * Set the resume of the book.
     *
     * @param resume The resume of the book.
     */
    void setResume(String resume);

    /**
     * Return the kind of the book.
     *
     * @return The kind of the book.
     */
    Kind getTheKind();

    /**
     * Set the kind of the book.
     *
     * @param theKind The kind of the book.
     */
    void setTheKind(Kind theKind);

    /**
     * Return the the type of the book.
     *
     * @return The type of the book.
     */
    Type getTheType();

    /**
     * Set the type of the book.
     *
     * @param theType The type of the book.
     */
    void setTheType(Type theType);

    /**
     * Return the language of the book.
     *
     * @return The language of the book.
     */
    Language getTheLanguage();

    /**
     * Set the language of the of the book.
     *
     * @param theLanguage The language of the book.
     */
    void setTheLanguage(Language theLanguage);

    /**
     * Return the editor of the book.
     *
     * @return The editor of the book.
     */
    Editor getTheEditor();

    /**
     * Set the editor of the book.
     *
     * @param theEditor The editor of the book.
     */
    void setTheEditor(Editor theEditor);

    /**
     * Return the lending of the book.
     *
     * @return The lending of the book.
     */
    Lending getTheLending();

    /**
     * Set the lending.
     *
     * @param theLending The lending.
     */
    void setTheLending(Lending theLending);

    /**
     * Return the saga of the book.
     *
     * @return The saga of the book.
     */
    Saga getTheSaga();

    /**
     * Set the saga of the book.
     *
     * @param theSaga The saga of the book.
     */
    void setTheSaga(Saga theSaga);

    /**
     * Add author to the book.
     *
     * @param author The author to add to the book.
     */
    void addAuthor(Person author);
}
