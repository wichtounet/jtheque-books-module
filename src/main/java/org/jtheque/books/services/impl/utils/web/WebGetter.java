package org.jtheque.books.services.impl.utils.web;

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
     *
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
     *
     * @return A List containing all the results corresponding to the search.
     */
    Collection<BookResult> getBooks(String search);

    /**
     * Return the book corresponding to the search and filled.
     *
     * @param search The search.
     * @param book   The book.
     * @param args   The args of the edit.
     *
     * @return The filled book.
     */
    Book getBook(BookResult search, Book book, EditArguments args);
}
