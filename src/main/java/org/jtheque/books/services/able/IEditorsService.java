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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.core.managers.persistence.able.DataContainer;

/**
 * A editors service specification.
 *
 * @author Baptiste Wicht
 */
public interface IEditorsService extends DataContainer<Editor> {
    String DATA_TYPE = "Editors";

    /**
     * Return the default editor.
     *
     * @return The default editor.
     */
    Editor getDefaultEditor();

    /**
     * Create the editor.
     *
     * @param editor The editor to create.
     */
    void create(Editor editor);

    /**
     * Return the editor with the name.
     *
     * @param name The name to search for.
     * @return The editor.
     */
    Editor getEditor(String name);

    /**
     * Indicate if exists an editor with a name.
     *
     * @param name The name to search for.
     * @return true if the author exists else false.
     */
    boolean exists(String name);

    /**
     * Delete the editor.
     *
     * @param editor The editor to delete.
     * @return true if the editor has been deleted else false.
     */
    boolean delete(Editor editor);

    /**
     * Save the editor.
     *
     * @param editor The editor to save.
     */
    void save(Editor editor);

    /**
     * Create an empty editor.
     *
     * @return An empty editor, not persisted.
     */
    Editor getEmptyEditor();
}