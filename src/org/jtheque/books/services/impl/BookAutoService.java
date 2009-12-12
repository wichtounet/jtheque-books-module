package org.jtheque.books.services.impl;

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
import org.jtheque.books.services.able.IBookAutoService;
import org.jtheque.books.services.impl.utils.EditArguments;
import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.services.impl.utils.web.WebGetterManager;
import org.jtheque.primary.services.able.IKindsService;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * An auto service implementation.
 *
 * @author Baptiste Wicht
 */
public final class BookAutoService implements IBookAutoService {
    private WebGetterManager webGetterManager;
    private static final String[] LANGUAGES = {"Amazon FR", "Amazon EN"};

    @Resource
    private IKindsService kindsService;

    @Override
    public String[] getLanguages() {
        return LANGUAGES.clone();
    }

    @Override
    public Book getBook(BookResult bookResult) {
        Book book = getManager().getBook(bookResult);

        if (book.getTheKind() == null) {
            book.setTheKind(kindsService.getDefaultKind());
        }

        return book;
    }

    @Override
    public Collection<BookResult> getBooks(String language, String search) {
        return getManager().getBooks(language, search);
    }

    @Override
    public void modifyBook(BookResult bookResult, Book book, EditArguments args) {
        getManager().modifyBook(bookResult, book, args);
    }

    /**
     * Return the web getter manager.
     *
     * @return The web getter manager.
     */
    private WebGetterManager getManager() {
        if (webGetterManager == null) {
            webGetterManager = new WebGetterManager();
        }

        return webGetterManager;
    }
}