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
import org.jtheque.primary.controller.able.IPrincipalController;

/**
 * A book controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IBookController extends IPrincipalController<Book> {
    /**
     * Save the current book.
     */
    void save();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    @Override
    IBooksModel getViewModel();

    @Override
    IBookView getView();
}
