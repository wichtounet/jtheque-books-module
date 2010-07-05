package org.jtheque.books.persistence.od.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.books.BooksModule;
import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.PropertiesUtils;
import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.od.impl.abstraction.AbstractData;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.HashCodeUtils;

import javax.swing.Icon;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A book.
 *
 * @author Baptiste Wicht
 */
public final class BookImpl extends AbstractData implements Book {
    private Book memento;
    private boolean mementoState;

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
    private SimpleData theKind;

    /**
     * The type of book.
     */
    private SimpleData theType;

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
    private SimpleData theLanguage;

    /**
     * The lending.
     */
    private Lending theLending;

    /**
     * The saga.
     */
    private SimpleData theSaga;

    //Data methods

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Collection<Person> getAuthors() {
        return authors;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int getPages() {
        return pages;
    }

    @Override
    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String getIsbn10() {
        return isbn10;
    }

    @Override
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    @Override
    public String getIsbn13() {
        return isbn13;
    }

    @Override
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    @Override
    public String getResume() {
        return resume;
    }

    @Override
    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public SimpleData getTheKind() {
        return theKind;
    }

    @Override
    public void setTheKind(SimpleData theKind) {
        this.theKind = theKind;
    }

    @Override
    public SimpleData getTheType() {
        return theType;
    }

    @Override
    public void setTheType(SimpleData theType) {
        this.theType = theType;
    }

    /**
     * Return the note of the book.
     *
     * @return The note of the book.
     */
    @Override
    public Note getNote() {
        return note;
    }

    /**
     * Set the note of the book.
     *
     * @param note The note of the book.
     */
    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public SimpleData getTheLanguage() {
        return theLanguage;
    }

    @Override
    public void setTheLanguage(SimpleData theLanguage) {
        this.theLanguage = theLanguage;
    }

    @Override
    public Editor getTheEditor() {
        return theEditor;
    }

    @Override
    public void setTheEditor(Editor theEditor) {
        this.theEditor = theEditor;
    }

    @Override
    public Lending getTheLending() {
        return theLending;
    }

    @Override
    public void setTheLending(Lending theLending) {
        this.theLending = theLending;
    }

    @Override
    public SimpleData getTheSaga() {
        return theSaga;
    }

    @Override
    public void setTheSaga(SimpleData theSaga) {
        this.theSaga = theSaga;
    }

    //Utility methods

    @Override
    public String getDisplayableText() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(
                title, year, pages, authors, resume, theLending, theKind, isbn10, isbn13, theLanguage,
                note, theEditor, theType, theSaga
        );
    }

    @Override
    public boolean equals(Object obj) {
        Book other = (Book) obj;

        return EqualsUtils.areEqualsDirect(
                this, obj,
                title, year, pages, authors, resume, theLending, theKind, isbn10, isbn13, theLanguage,
                note, theEditor, theType, theSaga,
                other.getTitle(), other.getYear(), other.getPages(), other.getAuthors(), other.getResume(),
                other.getTheLending(), other.getTheKind(), other.getIsbn10(), other.getIsbn13(), other.getTheLanguage(),
                other.getNote(), other.getTheEditor(), other.getTheType(), other.getTheSaga());
    }

    @Override
    public void saveToMemento() {
        mementoState = true;

        memento = PropertiesUtils.createMemento(this);
    }

    @Override
    public void restoreMemento() {
        if (mementoState) {
            PropertiesUtils.restoreMemento(this, memento);
        }
    }

    @Override
    public Icon getIcon() {
        return Managers.getManager(IResourceManager.class).getIcon(BooksModule.IMAGE_BASE_NAME, "book", ImageType.PNG);
    }

    @Override
    public void addAuthor(Person author) {
        assert author.getType().equals(IAuthorsService.PERSON_TYPE) : "The person must of type Author";

        authors.add(author);
    }
}
