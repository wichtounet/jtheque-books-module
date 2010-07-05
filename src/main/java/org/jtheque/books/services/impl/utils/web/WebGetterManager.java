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

import java.util.Collection;

/**
 * Manage the automatic research of informations of a film on the web.
 *
 * @author Baptiste Wicht
 */
public final class WebGetterManager {
    private final WebGetterFactory factory = new WebGetterFactory();

    /**
     * Return the books for a language with a specific search.
     *
     * @param language The language.
     * @param search   The search.
     *
     * @return A List containing all the books.
     */
    public Collection<BookResult> getBooks(String language, String search) {
        WebGetter getter = factory.getWebGetter(language);

        return getter.getBooks(search);
    }

    /**
     * Return the book.
     *
     * @param search The search.
     *
     * @return The filled book.
     */
    public Book getBook(BookResult search) {
        WebGetter getter = factory.getWebGetter(search.getLanguage());

        return getter.getBook(search, null, null);
    }

    /**
     * Edit the book.
     *
     * @param search The search.
     * @param book   The book to edit.
     * @param args   The args of the edit.
     */
    public void modifyBook(BookResult search, Book book, EditArguments args) {
        WebGetter getter = factory.getWebGetter(search.getLanguage());

        book.saveToMemento();

        getter.getBook(search, book, args);
    }
}
