package org.jtheque.books.view.fb;

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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
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
     * The saga.
     */
    private SimpleData theSaga;


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
    public void setTheKind(SimpleData theKind) {
        this.theKind = theKind;
    }

    @Override
    public void setTheType(SimpleData theType) {
        this.theType = theType;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public void setTheLanguage(SimpleData theLanguage) {
        this.theLanguage = theLanguage;
    }

    @Override
    public void setTheEditor(Editor theEditor) {
        this.theEditor = theEditor;
    }

    @Override
    public void setTheSaga(SimpleData theSaga) {
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
