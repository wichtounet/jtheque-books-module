package org.jtheque.books.services.impl.utils.web.analyzers;

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
import org.jtheque.books.services.impl.utils.EditArguments;
import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

import java.util.Scanner;

/**
 * An abstract Analyzer for book information recuperation.
 *
 * @author Baptiste Wicht.
 */
public abstract class AbstractBookAnalyzer implements Analyzer {
    private Book book;
    private Scanner scanner;

    private boolean resume;
    private boolean date;
    private boolean kind;
    private boolean pages;
    private boolean editor;
    private boolean authors;
    private boolean isbn10;
    private boolean isbn13;

    /**
     * Return the book to fill.
     *
     * @return The book to fill.
     */
    public final Book getBook() {
        return book;
    }

    /**
     * Set the book to fill.
     *
     * @param book The book to fill.
     */
    public final void setBook(Book book) {
        this.book = book;
    }

    /**
     * Set the scanner on which we read the website.
     *
     * @param scanner The scanner.
     */
    public final void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Analyze a line of the website and extract all the informations.
     *
     * @param line The line we must analyze.
     */
    public final void analyzeLine(String line) {
        findResume(line);
        findDate(line);
        findKind(line);
        findPages(line);
        findEditor(line);
        findAuthors(line);
        findIsbn10(line);
        findIsbn13(line);
    }

    /**
     * Indicate if the we have all the informations.
     *
     * @return <code>true</code> if we have extracted all the informations else <code>false</code>.
     */
    public final boolean isComplete() {
        return resume &&
                date &&
                kind &&
                pages &&
                editor &&
                authors &&
                isbn10 &&
                isbn13;
    }

    /**
     * Reset the analyzer.
     */
    public final void reset() {
        resume = false;
        date = false;
        kind = false;
        pages = false;
        editor = false;
        authors = false;
        isbn10 = false;
        isbn13 = false;

        scanner.close();
    }

    /**
     * Find the resume on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findResume(String line);

    /**
     * Find the date on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findDate(String line);

    /**
     * Find the kind on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findKind(String line);

    /**
     * Find the duration on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findPages(String line);

    /**
     * Find the realizer on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findEditor(String line);

    /**
     * Find the actors of the book on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findAuthors(String line);

    /**
     * Find the ISBN-10 of the book.
     *
     * @param line The line on which we search.
     */
    protected abstract void findIsbn10(String line);

    /**
     * Find the ISBN-13 of the book.
     *
     * @param line The line on which we search.
     */
    protected abstract void findIsbn13(String line);

    /**
     * Set the state of the resume get.
     *
     * @param resume A boolean value indicate the state of the resume get.
     */
    final void setResume(boolean resume) {
        this.resume = resume;
    }

    /**
     * Indicate if the resume has been extracted or not.
     *
     * @return <code>true</code> if we have found the resume else <code>false</code>.
     */
    final boolean isResumeDo() {
        return resume;
    }

    /**
     * Set the state of the date get.
     *
     * @param date A boolean value indicate the state of the date get.
     */
    final void setDate(boolean date) {
        this.date = date;
    }

    /**
     * Indicate if the date has been extracted or not.
     *
     * @return <code>true</code> if we have found the date else <code>false</code>.
     */
    final boolean isDateDo() {
        return date;
    }

    /**
     * Set the state of the kind get.
     *
     * @param genre A boolean value indicate the state of the kind get.
     */
    final void setKind(boolean genre) {
        kind = genre;
    }

    /**
     * Indicate if the kind has been extracted or not.
     *
     * @return <code>true</code> if we have found the kind else <code>false</code>.
     */
    final boolean isKindDo() {
        return kind;
    }

    /**
     * Set the state of the pages get.
     *
     * @param pages A boolean value indicate the state of the pages get.
     */
    final void setPages(boolean pages) {
        this.pages = pages;
    }

    /**
     * Indicate if the pages has been extracted or not.
     *
     * @return <code>true</code> if we have found the pages else <code>false</code>.
     */
    final boolean isDurationDo() {
        return pages;
    }

    /**
     * Set the realizer of the resume get.
     *
     * @param realizer A boolean value indicate the state of the realizer get.
     */
    final void setEditor(boolean realizer) {
        editor = realizer;
    }

    /**
     * Indicate if the realizer has been extracted or not.
     *
     * @return <code>true</code> if we have found the realizer else <code>false</code>.
     */
    final boolean isRealizerDo() {
        return editor;
    }

    /**
     * Set the state of the actors get.
     *
     * @param actors A boolean value indicate the state of the actors get.
     */
    final void setAuthors(boolean actors) {
        authors = actors;
    }

    /**
     * Indicate if the actors has been extracted or not.
     *
     * @return <code>true</code> if we have found the actors else <code>false</code>.
     */
    final boolean isActorsDo() {
        return authors;
    }

    /**
     * Is ISBN-10 done.
     *
     * @return true if the ISBN-10 is already found else false.
     */
    final boolean isIsbn10Do() {
        return isbn10;
    }

    /**
     * Set the ISBN-10 done flag.
     *
     * @param isbn10 true if the ISBN-10 is found else false.
     */
    final void setIsbn10(boolean isbn10) {
        this.isbn10 = isbn10;
    }

    /**
     * Is ISBN-13 done.
     *
     * @return true if the ISBN-13 is already found else false.
     */
    final boolean isIsbn13Do() {
        return isbn13;
    }

    /**
     * Set the ISBN-13 done flag.
     *
     * @param isbn13 true if the ISBN-13 is found else false.
     */
    final void setIsbn13(boolean isbn13) {
        this.isbn13 = isbn13;
    }

    /**
     * Configure the scanner with the arguments of the edit. When we doesn't edit something we pass the state to
     * <code>true</code> to indicate that we mustn't edit this field.
     *
     * @param args The arguments of the edit
     */
    public final void configureWithEditArgs(EditArguments args) {
        authors = !args.mustEditAuthors();
        date = !args.mustEditYear();
        pages = !args.mustEditPages();
        kind = !args.mustEditKind();
        editor = !args.mustEditEditor();
        resume = !args.mustEditResume();
    }
}
