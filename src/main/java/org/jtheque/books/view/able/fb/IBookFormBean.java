package org.jtheque.books.view.able.fb;

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IBookFormBean extends FormBean {
    /**
     * Set the title of the book.
     *
     * @param title The title of the book.
     */
    void setTitle(String title);

    /**
     * Set the authors of the book.
     *
     * @param authors The authors of the book.
     */
    void setAuthors(Collection<Person> authors);

    /**
     * Set the year of the book.
     *
     * @param year The year of the book.
     */
    void setYear(int year);

    /**
     * Set the pages of the book.
     *
     * @param pages The pages of the book.
     */
    void setPages(int pages);

    /**
     * Set the ISBN-10 of the book.
     *
     * @param isbn10 The ISBN-10 of the book.
     */
    void setIsbn10(String isbn10);

    /**
     * Set the ISBN-13 of the book.
     *
     * @param isbn13 The ISBN-13 of the book.
     */
    void setIsbn13(String isbn13);

    /**
     * Set the resume of the book.
     *
     * @param resume The resume of the book.
     */
    void setResume(String resume);

    /**
     * Set the kind of the book.
     *
     * @param theKind The kind of the book.
     */
    void setTheKind(SimpleData theKind);

    /**
     * Set the type of the book.
     *
     * @param theType The type of the book.
     */
    void setTheType(SimpleData theType);

    /**
     * Set the note of the book.
     *
     * @param note The note of the book.
     */
    void setNote(Note note);

    /**
     * Set the language of the of the book.
     *
     * @param theLanguage The language of the book.
     */
    void setTheLanguage(SimpleData theLanguage);

    /**
     * Set the editor of the book.
     *
     * @param theEditor The editor of the book.
     */
    void setTheEditor(Editor theEditor);

    /**
     * Set the saga of the book.
     *
     * @param theSaga The saga of the book.
     */
    void setTheSaga(SimpleData theSaga);

    /**
     * Fill the book with the infos of the form bean.
     *
     * @param book The book to fill.
     */
    void fillBook(Book book);
}
