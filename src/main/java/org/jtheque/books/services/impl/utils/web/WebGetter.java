package org.jtheque.books.services.impl.utils.web;

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
import org.jtheque.books.services.impl.utils.EditArguments;
import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

import java.util.Collection;

/**
 * A web getter. It seems an object who can search informations about a book
 *
 * @author Baptiste Wicht
 */
public interface WebGetter {
    /**
     * Indicate if this getter can get on a specific language.
     *
     * @param language The language to test.
     * @return true if the getter can get on else false.
     */
    boolean canGetOn(String language);

    /**
     * Set the analyzer.
     *
     * @param analyzer The analyzer to set.
     */
    void setAnalyzer(Analyzer analyzer);

    /**
     * Return the books for a search.
     *
     * @param search The search.
     * @return A List containing all the results corresponding to the search.
     */
    Collection<BookResult> getBooks(String search);

    /**
     * Return the book corresponding to the search and filled.
     *
     * @param search The search.
     * @param book   The book.
     * @param args   The args of the edit.
     * @return The filled book.
     */
    Book getBook(BookResult search, Book book, EditArguments args);
}