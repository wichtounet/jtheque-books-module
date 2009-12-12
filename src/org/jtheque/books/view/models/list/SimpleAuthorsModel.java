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

import org.jtheque.primary.od.able.Person;

import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple list model for authors. This list isn't attached to a dao.
 *
 * @author Baptiste Wicht
 */
public final class SimpleAuthorsModel extends DefaultListModel {
    private static final long serialVersionUID = 627034111249354845L;

    private final List<Person> authors;

    /**
     * Construct a new SimpleAuthorsModel.
     */
    public SimpleAuthorsModel() {
        super();

        authors = new ArrayList<Person>(10);
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
    public Object remove(int index) {
        Person author = authors.remove(index);
        fireIntervalRemoved(this, index, index);
        return author;
    }

    @Override
    public void addElement(Object obj) {
        authors.add((Person) obj);
        fireIntervalAdded(this, getSize() - 1, getSize() - 1);
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

    /**
     * Return all the authors.
     *
     * @return All the authors.
     */
    public Collection<Person> getAuthors() {
        return authors;
    }
}