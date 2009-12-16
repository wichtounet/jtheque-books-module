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
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A list model for authors.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsListModel extends DefaultListModel implements DataListener {
    @Resource
    private IAuthorsService authorsService;

    private List<Person> authors;

    /**
     * Construct a new AuthorsListModel.
     */
    public AuthorsListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        authorsService.addDataListener(this);
        authors = new ArrayList<Person>(authorsService.getAuthors());
    }

    @Override
    public Object getElementAt(int index) {
        return authors.get(index);
    }

    @Override
    public Object get(int index) {
        return authors.get(index);
    }

    @Override
    public int getSize() {
        return authors.size();
    }

    @Override
    public Object remove(int i) {
        Person actor = authors.remove(i);
        fireIntervalRemoved(this, i, i);
        return actor;
    }

    @Override
    public void addElement(Object obj) {
        authors.add((Person) obj);
        fireIntervalAdded(this, getSize(), getSize());
    }

    @Override
    public void removeAllElements() {
        authors.clear();
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public boolean removeElement(Object obj) {
        Person author = (Person) obj;

        int index = authors.indexOf(author);
        boolean remove = authors.remove(author);
        fireIntervalRemoved(this, index, index);
        return remove;
    }

    @Override
    public void dataChanged() {
        update();
    }

    /**
     * Reload the model.
     */
    public void reload() {
        update();
    }

    /**
     * Update the list model.
     */
    private void update() {
        authors = new ArrayList<Person>(authorsService.getAuthors());
        fireContentsChanged(this, 0, getSize());
    }
}