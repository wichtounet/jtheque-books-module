package org.jtheque.books.services.impl;

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
import org.jtheque.books.services.able.IBookAutoService;
import org.jtheque.books.services.impl.utils.EditArguments;
import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.services.impl.utils.web.WebGetterManager;
import org.jtheque.primary.services.able.ISimpleDataService;

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
    private ISimpleDataService kindsService;

    @Override
    public String[] getLanguages() {
        return LANGUAGES.clone();
    }

    @Override
    public Book getBook(BookResult bookResult) {
        Book book = getManager().getBook(bookResult);

        if (book.getTheKind() == null) {
            book.setTheKind(kindsService.getDefaultSimpleData());
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
