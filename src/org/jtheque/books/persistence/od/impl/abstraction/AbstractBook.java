package org.jtheque.books.persistence.od.impl.abstraction;

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
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.od.impl.abstraction.AbstractData;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstract book.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractBook extends AbstractData implements Book {
    /**
     * The title of the book.
     */
    private String title;

    /**
     * The authors of the book.
     */
    private final Collection<Person> authors = new ArrayList<Person>(10);

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
     * The lending.
     */
    private Lending theLending;

    /**
     * The saga.
     */
    private Saga theSaga;

    @Override
    public final String getTitle() {
        return title;
    }

    @Override
    public final void setTitle(String title) {
        this.title = title;
    }

    @Override
    public final Collection<Person> getAuthors() {
        return authors;
    }

    @Override
    public final int getYear() {
        return year;
    }

    @Override
    public final void setYear(int year) {
        this.year = year;
    }

    @Override
    public final int getPages() {
        return pages;
    }

    @Override
    public final void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public final String getIsbn10() {
        return isbn10;
    }

    @Override
    public final void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    @Override
    public final String getIsbn13() {
        return isbn13;
    }

    @Override
    public final void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    @Override
    public final String getResume() {
        return resume;
    }

    @Override
    public final void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public final Kind getTheKind() {
        return theKind;
    }

    @Override
    public final void setTheKind(Kind theKind) {
        this.theKind = theKind;
    }

    @Override
    public final Type getTheType() {
        return theType;
    }

    @Override
    public final void setTheType(Type theType) {
        this.theType = theType;
    }

    /**
     * Return the note of the book.
     *
     * @return The note of the book.
     */
    @Override
    public final Note getNote() {
        return note;
    }

    /**
     * Set the note of the book.
     *
     * @param note The note of the book.
     */
    @Override
    public final void setNote(Note note) {
        this.note = note;
    }

    @Override
    public final Language getTheLanguage() {
        return theLanguage;
    }

    @Override
    public final void setTheLanguage(Language theLanguage) {
        this.theLanguage = theLanguage;
    }

    @Override
    public final Editor getTheEditor() {
        return theEditor;
    }

    @Override
    public final void setTheEditor(Editor theEditor) {
        this.theEditor = theEditor;
    }

    @Override
    public final Lending getTheLending() {
        return theLending;
    }

    @Override
    public final void setTheLending(Lending theLending) {
        this.theLending = theLending;
    }

    @Override
    public final Saga getTheSaga() {
        return theSaga;
    }

    @Override
    public final void setTheSaga(Saga theSaga) {
        this.theSaga = theSaga;
    }
}