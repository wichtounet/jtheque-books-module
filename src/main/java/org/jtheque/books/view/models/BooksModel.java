package org.jtheque.books.view.models;

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
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.Collection;

/**
 * A principal data model for the books view.
 *
 * @author Baptiste Wicht
 */
public final class BooksModel extends PrincipalDataModel<Book> implements IBooksModel {
    private Book currentBook;

    public BooksModel() {
        super();

        Managers.getManager(IBeansManager.class).<IBooksService>getBean("booksService").addDataListener(this);
    }

    @Override
    public Collection<Book> getDatas() {
        return Managers.getManager(IBeansManager.class).<IBooksService>getBean("booksService").getDatas();
    }

    @Override
    public Book getCurrentBook() {
        return currentBook;
    }

    @Override
    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentBook));
    }
}