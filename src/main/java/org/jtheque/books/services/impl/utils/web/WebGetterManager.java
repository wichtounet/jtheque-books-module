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