package org.jtheque.books.view.actions.editor;

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

import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.primary.controller.able.IChoiceController;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to delete an editor.
 *
 * @author Baptiste Wicht
 */
public final class AcDeleteEditor extends JThequeAction {
    private static final long serialVersionUID = -7839716885288257621L;

    @Resource
    private IChoiceController choiceController;

    /**
     * Construct a AcDeleteEditor.
     */
    public AcDeleteEditor() {
        super("actions.editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        choiceController.setAction("delete");
        choiceController.setContent(IEditorsService.DATA_TYPE);
        choiceController.displayView();
    }
}