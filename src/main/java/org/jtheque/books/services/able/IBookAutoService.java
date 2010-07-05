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
     *
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
     *
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
