package org.jtheque.books.view.models;

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
