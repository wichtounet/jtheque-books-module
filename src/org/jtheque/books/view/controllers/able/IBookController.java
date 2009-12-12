package org.jtheque.books.view.controllers.able;

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
import org.jtheque.books.view.able.IBookView;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.primary.controller.able.ControllerState;

import javax.swing.event.TreeSelectionListener;

/**
 * A book controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IBookController extends Controller, TreeSelectionListener {
    /**
     * Save the current book.
     */
    void save();

    /**
     * Display a book in the interface.
     *
     * @param book The book to edit.
     */
    void view(Book book);

    /**
     * Edit manually a book.
     */
    void manualEdit();

    /**
     * Create a new book.
     */
    void createFilm();

    /**
     * Delete the current book.
     */
    void deleteCurrentBook();

    /**
     * Cancel the current state.
     */
    void cancel();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    IBooksModel getViewModel();

    @Override
    IBookView getView();

    /**
     * Return the auto add state of the controller.
     *
     * @return The auto add state.
     */
    ControllerState getAutoAddState();

    /**
     * Return the view state of the controller.
     *
     * @return The view state.
     */
    ControllerState getViewState();

    /**
     * Return the view state of the controller.
     *
     * @return The view state.
     */
    ControllerState getNewObjectState();

    /**
     * Return the view state of the controller.
     *
     * @return The view state.
     */
    ControllerState getModifyState();
}
