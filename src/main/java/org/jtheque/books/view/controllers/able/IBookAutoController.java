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

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.able.IAutoView;
import org.jtheque.core.managers.view.able.controller.Controller;

/**
 * An auto controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IBookAutoController extends Controller {
    /**
     * Auto add a book.
     */
    void add();

    /**
     * Auto edit a book.
     */
    void edit();

    /**
     * Auto edit or add the result.
     *
     * @param selectedBook The book result.
     */
    void auto(BookResult selectedBook);

    @Override
    IAutoView getView();
}
