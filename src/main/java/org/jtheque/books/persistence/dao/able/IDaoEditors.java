package org.jtheque.books.persistence.dao.able;

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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.core.managers.persistence.able.JThequeDao;

import java.util.Collection;

/**
 * A DAO for editors specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoEditors extends JThequeDao {
    String TABLE = "T_BOOK_EDITORS";

    /**
     * Return all the editors.
     *
     * @return A List containing all the editors.
     */
    Collection<Editor> getEditors();

    /**
     * Return the editor with the specified ID.
     *
     * @param id The ID of the searched editor.
     *
     * @return The editor.
     */
    Editor getEditor(int id);

    /**
     * Return the editor with the specified name.
     *
     * @param name The searched name.
     *
     * @return The editor.
     */
    Editor getEditor(String name);

    /**
     * Indicate if an editor with this name exists in the application.
     *
     * @param name The searched name.
     *
     * @return true if an editor exist with this name.
     */
    boolean exists(String name);

    /**
     * Save the editor.
     *
     * @param editor The editor to save.
     */
    void save(Editor editor);

    /**
     * Create the editor.
     *
     * @param editor The editor to create.
     */
    void create(Editor editor);

    /**
     * Delete the editor.
     *
     * @param editor The editor to delete.
     *
     * @return true if the object is deleted else false.
     */
    boolean delete(Editor editor);

    /**
     * Create an empty editor.
     *
     * @return An empty editor, not persisted.
     */
    Editor createEditor();
}