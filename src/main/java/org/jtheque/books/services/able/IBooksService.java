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
import org.jtheque.core.managers.persistence.able.DataContainer;

import java.util.Collection;

/**
 * A service for the films functions.
 *
 * @author Baptiste Wicht
 */
public interface IBooksService extends DataContainer<Book> {
    String DATA_TYPE = "Books";

    /**
     * Return an empty film.
     *
     * @return An empty film.
     */
    Book getDefaultBook();

    /**
     * Create the book.
     *
     * @param book The book to create.
     */
    void create(Book book);

    /**
     * Return all the books.
     *
     * @return A List containing all the books.
     */
    Collection<Book> getBooks();

    /**
     * Delete the book.
     *
     * @param book The book to delete.
     * @return true if the book has been deleted.
     */
    boolean delete(Book book);

    /**
     * Save the book.
     *
     * @param book The book to save.
     */
    void save(Book book);

    /**
     * Return an empty book.
     *
     * @return An empty book.
     */
    Book getEmptyBook();

    /**
     * Return the number of books.
     *
     * @return The number of books.
     */
    int getNumberOfBooks();
}