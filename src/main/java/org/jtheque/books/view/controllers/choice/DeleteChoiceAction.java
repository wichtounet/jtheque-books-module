package org.jtheque.books.view.controllers.choice;

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