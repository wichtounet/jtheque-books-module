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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A principal data model for the author view.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsModel extends PrincipalDataModel<Person> implements IAuthorsModel {
    private boolean enabled;
    private Person currentAuthor;

    private Collection<Person> displayList;

    @Resource
    private IAuthorsService authorsService;

    /**
     * Init the model.
     */
    @PostConstruct
    private void init() {
        authorsService.addDataListener(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateDisplayList(Collection<Person> datas) {
        displayList.clear();

        if (datas == null) {
            displayList.addAll(authorsService.getAuthors());
        } else {
            displayList.addAll(datas);
        }

        fireDisplayListChanged();
    }

    @Override
    protected void updateDisplayList() {
        updateDisplayList(null);
    }

    /**
     * Return the display list.
     *
     * @return The display list
     */
    @Override
    public Collection<Person> getDisplayList() {
        if (displayList == null) {
            displayList = new ArrayList<Person>(25);
            updateDisplayList();
        }

        return displayList;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Person getCurrentAuthor() {
        return currentAuthor;
    }

    /**
     * Set the current book.
     *
     * @param currentAuthor The new current book
     */
    @Override
    public void setCurrentAuthor(Person currentAuthor) {
        this.currentAuthor = currentAuthor;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentAuthor));
    }
}