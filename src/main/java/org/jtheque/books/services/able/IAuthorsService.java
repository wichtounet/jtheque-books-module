package org.jtheque.books.services.able;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.primary.od.able.Person;

import java.util.Collection;

/**
 * A service for the actor functions.
 *
 * @author Baptiste Wicht
 */
public interface IAuthorsService extends DataContainer<Person> {
    String DATA_TYPE = "Authors";
    String PERSON_TYPE = "Author";

    /**
     * Return an empty actor.
     *
     * @return An empty actor.
     */
    Person getDefaultAuthor();

    /**
     * Create the author.
     *
     * @param author The author to create.
     */
    void create(Person author);

    /**
     * Indicate if an author with this firstName and name exists.
     *
     * @param firstName The firstName to search for.
     * @param name      The name to search for.
     * @return true if the author exist else false.
     */
    boolean exists(String firstName, String name);

    /**
     * Return the author denoted by this firstName and name.
     *
     * @param firstName The firstName to search for.
     * @param name      The name to search for.
     * @return The author.
     */
    Person getAuthor(String firstName, String name);

    /**
     * Return all the authors.
     *
     * @return A List containing all the authors.
     */
    Collection<Person> getAuthors();

    /**
     * Delete the author.
     *
     * @param author The author to delete.
     * @return true if the author has been deleted else false.
     */
    boolean delete(Person author);

    /**
     * Save the author.
     *
     * @param author The author to save.
     */
    void save(Person author);

    /**
     * Return an empty author.
     *
     * @return An empty author.
     */
    Person getEmptyAuthor();

    /**
     * Return the number of authors.
     *
     * @return The number of authors.
     */
    int getNumberOfAuthors();
}