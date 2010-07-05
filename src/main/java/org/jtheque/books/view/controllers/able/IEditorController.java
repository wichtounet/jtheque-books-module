package org.jtheque.books.view.controllers.able;

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
import org.jtheque.core.managers.view.able.controller.Controller;

/**
 * An editor controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IEditorController extends Controller {
    /**
     * Display the view to edit a editor.
     *
     * @param editor The editor to edit.
     */
    void editEditor(Editor editor);

    /**
     * Save modifications made to the editor.
     *
     * @param name The name of the editor.
     */
    void save(String name);

    void newEditor();
}
