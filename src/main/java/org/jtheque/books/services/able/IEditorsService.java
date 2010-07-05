package org.jtheque.books.services.able;

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
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.primary.services.able.DataService;

/**
 * A editors service specification.
 *
 * @author Baptiste Wicht
 */
public interface IEditorsService extends DataContainer<Editor>, DataService<Editor> {
    String DATA_TYPE = "Editors";

    /**
     * Return the default editor.
     *
     * @return The default editor.
     */
    Editor getDefaultEditor();

    /**
     * Return the editor with the name.
     *
     * @param name The name to search for.
     *
     * @return The editor.
     */
    Editor getEditor(String name);

    /**
     * Indicate if exists an editor with a name.
     *
     * @param name The name to search for.
     *
     * @return true if the author exists else false.
     */
    boolean exists(String name);

    /**
     * Create an empty editor.
     *
     * @return An empty editor, not persisted.
     */
    Editor getEmptyEditor();
}
