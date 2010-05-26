package org.jtheque.books.view.models.list;

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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;

/**
 * A list model for authors.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsListModel extends SimpleListModel<Person> implements DataListener {
    @Resource
    private IAuthorsService authorsService;

    /**
     * Construct a new AuthorsListModel.
     */
    public AuthorsListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        authorsService.addDataListener(this);

        setElements(authorsService.getPersons());
    }

    @Override
    public void dataChanged() {
        setElements(authorsService.getPersons());
    }

    /**
     * Reload the model.
     */
    public void reload() {
        setElements(authorsService.getPersons());
    }
}