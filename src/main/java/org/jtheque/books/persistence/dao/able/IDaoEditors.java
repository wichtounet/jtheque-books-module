package org.jtheque.books.persistence.dao.able;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
