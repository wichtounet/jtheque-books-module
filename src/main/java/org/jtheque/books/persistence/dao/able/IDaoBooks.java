package org.jtheque.books.persistence.dao.able;

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
