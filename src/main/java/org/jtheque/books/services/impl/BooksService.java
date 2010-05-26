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

import org.jtheque.books.persistence.dao.able.IDaoBooks;
import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.primary.services.able.INotesService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * A books service implementation.
 *
 * @author Baptiste Wicht
 */
public final class BooksService implements IBooksService {
    private Book defaultBook;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private IEditorsService editorsService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IDaoBooks daoBooks;

    @Override
    public Book getDefaultBook() {
        if (defaultBook == null) {
            defaultBook = getEmptyBook();

            defaultBook.setTitle(Managers.getManager(ILanguageManager.class).getMessage("book.actions.new"));
            defaultBook.setNote(notesService.getDefaultNote());
            defaultBook.setTheEditor(editorsService.getDefaultEditor());
            defaultBook.setTheKind(kindsService.getDefaultSimpleData());
            defaultBook.setTheType(typesService.getDefaultSimpleData());
            defaultBook.setTheLanguage(languagesService.getDefaultSimpleData());
        }

        return defaultBook;
    }

    @Override
    @Transactional
    public void create(Book book) {
        daoBooks.create(book);
    }

    @Override
    public Collection<Book> getBooks() {
        return daoBooks.getBooks();
    }

    @Override
    @Transactional
    public boolean delete(Book book) {
        return daoBooks.delete(book);
    }

    @Override
    @Transactional
    public void save(Book book) {
        daoBooks.save(book);
    }

    @Override
    public Collection<Book> getDatas() {
        return daoBooks.getBooks();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoBooks.addDataListener(listener);
    }

    @Override
    @Transactional
    public void clearAll() {
        daoBooks.clearAll();
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public Book getEmptyBook() {
        return daoBooks.createBook();
    }

    @Override
    public int getNumberOfBooks() {
        return getBooks().size();
    }
}