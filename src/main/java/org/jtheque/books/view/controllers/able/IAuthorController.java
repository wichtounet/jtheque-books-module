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

import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Person;

/**
 * An author controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IAuthorController extends IPrincipalController<Person> {
    /**
     * Save the current author.
     */
    void save();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    @Override
    IAuthorsModel getViewModel();

    @Override
    IAuthorView getView();
}
