package org.jtheque.books.persistence.dao.able;

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
import org.jtheque.core.managers.persistence.able.JThequeDao;

import java.util.Collection;

/**
 * A DAO for books specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoBooks extends JThequeDao {
    String TABLE = "T_BOOKS";
    String BOOKS_AUTHOR_TABLE = "T_AUTHORS_BOOKS";

    /**
     * Return all the books.
     *
     * @return A List containing all the books.
     */
    Collection<Book> getBooks();

    /**
     * Save the book.
     *
     * @param book The book to save.
     */
    void save(Book book);

    /**
     * Create the book.
     *
     * @param book The book to create.
     */
    void create(Book book);

    /**
     * Delete the book.
     *
     * @param book The book to delete.
     *
     * @return true if the object is deleted else false.
     */
    boolean delete(Book book);

    /**
     * Create an empty book.
     *
     * @return An empty book, not persisted.
     */
    Book createBook();
}