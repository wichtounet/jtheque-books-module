package org.jtheque.books.services.impl;

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
import org.jtheque.books.services.able.INotesService;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ICountriesService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * An authors service implementation.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsService implements IAuthorsService {
    private Person emptyAuthor;

    @Resource
    private ICountriesService countriesService;

    @Resource
    private INotesService notesService;

    @Resource
    private IDaoPersons daoPersons;

    @Override
    public Person getDefaultAuthor() {
        if (emptyAuthor == null) {
            emptyAuthor = getEmptyAuthor();

            emptyAuthor.setName("");
            emptyAuthor.setFirstName("");
            emptyAuthor.setNote(notesService.getDefaultNote());
            emptyAuthor.setTheCountry(countriesService.getDefaultCountry());
        }

        return emptyAuthor;
    }

    @Override
    public Person getEmptyAuthor() {
        Person author = daoPersons.createPerson();

        author.setType(PERSON_TYPE);

        return author;
    }

    @Override
    public int getNumberOfAuthors() {
        return getAuthors().size();
    }

    @Override
    @Transactional
    public void create(Person author) {
        author.setType(PERSON_TYPE);

        daoPersons.create(author);
    }

    @Override
    public boolean exists(String firstName, String name) {
        return daoPersons.exists(firstName, name, PERSON_TYPE);
    }

    @Override
    public Person getAuthor(String firstName, String name) {
        return daoPersons.getPerson(firstName, name, PERSON_TYPE);
    }

    @Override
    public Collection<Person> getAuthors() {
        return daoPersons.getPersons(PERSON_TYPE);
    }

    @Override
    @Transactional
    public boolean delete(Person author) {
        return daoPersons.delete(author);
    }

    @Override
    @Transactional
    public void save(Person author) {
        author.setType(PERSON_TYPE);

        daoPersons.save(author);
    }

    @Override
    public Collection<Person> getDatas() {
        return getAuthors();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoPersons.addDataListener(listener);
    }

    @Override
    @Transactional
    public void clearAll() {
        daoPersons.clearAll(PERSON_TYPE);
    }

    @Override
    @Transactional
    public String getDataType() {
        return DATA_TYPE;
    }
}