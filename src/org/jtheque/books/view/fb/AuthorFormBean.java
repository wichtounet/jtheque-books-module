package org.jtheque.books.view.fb;

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

import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Person;

/**
 * A form bean for an author.
 *
 * @author Baptiste Wicht
 */
public final class AuthorFormBean implements IAuthorFormBean {
    private String name;
    private String firstName;
    private Country country;
    private Note note;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public void fillAuthor(Person author) {
        author.setName(name);
        author.setFirstName(firstName);
        author.setNote(note);
        author.setTheCountry(country);
    }
}