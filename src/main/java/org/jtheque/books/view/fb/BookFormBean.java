package org.jtheque.books.view.fb;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.od.able.Type;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A form bean for a book.
 *
 * @author Baptiste Wicht
 */
public final class BookFormBean implements IBookFormBean {
    /**
     * The title of the book.
     */
    private String title;

    /**
     * The authors of the book.
     */
    private Iterable<Person> authors = new ArrayList<Person>(5);

    /**
     * The year of the book.
     */
    private int year;

    /**
     * The number of pages.
     */
    private int pages;

    /**
     * The ISBN-10.
     */
    private String isbn10;

    /**
     * The ISBN-13.
     */
    private String isbn13;

    /**
     * The resume.
     */
    private String resume;

    /**
     * The kind of book.
     */
    private Kind theKind;

    /**
     * The type of book.
     */
    private Type theType;

    /**
     * The note.
     */
    private Note note;

    /**
     * The editor of the book.
     */
    private Editor theEditor;

    /**
     * The language.
     */
    private Language theLanguage;

    /**
     * The saga.
     */
    private Saga theSaga;


    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setAuthors(Collection<Person> authors) {
        this.authors = CollectionUtils.copyOf(authors);
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    @Override
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    @Override
    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public void setTheKind(Kind theKind) {
        this.theKind = theKind;
    }

    @Override
    public void setTheType(Type theType) {
        this.theType = theType;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public void setTheLanguage(Language theLanguage) {
        this.theLanguage = theLanguage;
    }

    @Override
    public void setTheEditor(Editor theEditor) {
        this.theEditor = theEditor;
    }

    @Override
    public void setTheSaga(Saga theSaga) {
        this.theSaga = theSaga;
    }

    @Override
    public void fillBook(Book book) {
        book.setTitle(title);
        book.setTheKind(theKind);
        book.setTheType(theType);
        book.setNote(note);

        for (Person author : authors) {
            book.addAuthor(author);
        }

        book.setTheEditor(theEditor);
        book.setTheLanguage(theLanguage);
        book.setYear(year);
        book.setPages(pages);
        book.setResume(resume);
        book.setIsbn10(isbn10);
        book.setIsbn13(isbn13);
        book.setTheSaga(theSaga);
    }
}