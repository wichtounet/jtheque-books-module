package org.jtheque.books.view.controllers;

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
import org.jtheque.books.view.able.IBookView;
import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.util.Collection;

/**
 * A controller for the book view.
 *
 * @author Baptiste Wicht
 */
public final class BookController extends PrincipalController<Book> implements IBookController {
    @Resource
    private IBookView bookView;

    @Override
    public IBookView getView() {
        return bookView;
    }

    /**
     * Init the controller.
     */
    @PostConstruct
    public void init() {
        setState(getViewState());
    }

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        TreePath current = ((JTree) event.getSource()).getSelectionPath();

        if (current != null && current.getLastPathComponent() instanceof Book) {
            Book book = (Book) current.getLastPathComponent();

            if (book != null) {
                view(book);
            }
        }
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(bookView.fillBookFormBean());

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IBooksModel getViewModel() {
        return (IBooksModel) bookView.getModel();
    }

    @Override
    public void view(Book book) {
        ControllerState newState = getState().view(book);

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void manualEdit() {
        ControllerState newState = getState().manualEdit();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void createFilm() {
        ControllerState newState = getState().create();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void deleteCurrentBook() {
        ControllerState newState = getState().delete();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void cancel() {
        ControllerState newState = getState().cancel();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public String getDataType() {
        return IBooksService.DATA_TYPE;
    }

    @Override
    public Collection<Book> getDisplayList() {
        return getViewModel().getDisplayList();
    }
}