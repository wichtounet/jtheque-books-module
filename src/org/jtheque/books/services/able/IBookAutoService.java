package org.jtheque.books.services.able;

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
import org.jtheque.books.services.impl.utils.web.BookResult;

import java.util.Collection;

/**
 * A service for the automatic recuperation of data.
 *
 * @author Baptiste Wicht
 */
public interface IBookAutoService {
    /**
     * Return the books we found in a site for a specific search.
     *
     * @param language The language to search in.
     * @param search   The film search.
     * @return A List containing all the films found on the site for the search.
     */
    Collection<BookResult> getBooks(String language, String search);

    /**
     * Modify a film using a film found in a site.
     *
     * @param bookResult The result to get the informations from.
     * @param book       The film to edit.
     * @param args       The arguments who describe the fields to edit.
     */
    void modifyBook(BookResult bookResult, Book book, EditArguments args);

    /**
     * Fill a film with the information of the film result and return it.
     *
     * @param filmResult The fim result to get the information from.
     * @return A film filled with the information of the site.
     */
    Book getBook(BookResult filmResult);

    /**
     * Return all the available languages.
     *
     * @return All the languages.
     */
    String[] getLanguages();
}