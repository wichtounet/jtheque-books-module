package org.jtheque.books.view.controllers.choice;

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
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.primary.controller.impl.undo.GenericDataDeletedEdit;
import org.jtheque.primary.view.impl.choice.AbstractPrimaryDeleteChoiceAction;
import org.jtheque.primary.view.impl.choice.Deleter;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractPrimaryDeleteChoiceAction {
    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction() {
        super();

        addDeleters(new EditorDeleter());
        addPrimaryDeleters();
    }

    /**
     * A <code>Deleter</code> for Editor.
     *
     * @author Baptiste Wicht
     */
    private static final class EditorDeleter extends Deleter<Editor> {
        /**
         * Construct a new <code>EditorDeleter</code>.
         */
        private EditorDeleter() {
            super(IEditorsService.DATA_TYPE);
        }

        @Override
        public void delete(Editor o) {
            addEditIfDeleted(
                    CoreUtils.<IEditorsService>getBean("editorsService").delete(o),
                    new GenericDataDeletedEdit<Editor>("editorsService", o));
        }
    }
}
