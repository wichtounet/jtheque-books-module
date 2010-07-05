package org.jtheque.books.services.able;

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
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.primary.services.able.DataService;

import java.util.Collection;

/**
 * A service for the films functions.
 *
 * @author Baptiste Wicht
 */
public interface IBooksService extends DataContainer<Book>, DataService<Book> {
    String DATA_TYPE = "Books";

    /**
     * Return an empty film.
     *
     * @return An empty film.
     */
    Book getDefaultBook();

    /**
     * Return all the books.
     *
     * @return A List containing all the books.
     */
    Collection<Book> getBooks();

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
