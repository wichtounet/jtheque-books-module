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
import org.jtheque.primary.view.impl.listeners.ViewStateListener;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A principal data model for the books view.
 *
 * @author Baptiste Wicht
 */
public final class BooksModel extends PrincipalDataModel<Book> implements IBooksModel {
    private boolean enabled;
    private Book currentBook;

    private Collection<Book> displayList;

    private IBooksService booksService;

    public BooksModel() {
        super();

        booksService = Managers.getManager(IBeansManager.class).getBean("booksService");
        booksService.addDataListener(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateDisplayList(Collection<Book> books) {
        displayList.clear();

        if (books == null) {
            displayList.addAll(booksService.getBooks());
        } else {
            displayList.addAll(books);
        }

        fireDisplayListChanged();
    }

    @Override
    protected void updateDisplayList() {
        updateDisplayList(null);
    }

    @Override
    public Collection<Book> getDisplayList() {
        if (displayList == null) {
            displayList = new ArrayList<Book>(25);
            updateDisplayList();
        }

        return displayList;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        fireStateChanged();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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

    @Override
    public void addViewStateListener(ViewStateListener listener) {
        getEventListenerList().add(ViewStateListener.class, listener);
    }

    /**
     * Fire a event who indicate that the current state has changed.
     */
    private void fireStateChanged() {
        ViewStateListener[] listeners = getEventListenerList().getListeners(ViewStateListener.class);

        for (ViewStateListener listener : listeners) {
            listener.stateChanged();
        }
    }
}