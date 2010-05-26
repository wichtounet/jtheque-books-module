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
import org.jtheque.books.view.controllers.able.IEditorController;
import org.jtheque.primary.view.impl.choice.AbstractChoiceAction;
import org.jtheque.primary.view.impl.choice.AbstractModifyChoiceAction;

import javax.annotation.Resource;

/**
 * An action to modify the selected item.
 *
 * @author Baptiste Wicht
 */
public final class ModifyChoiceAction extends AbstractModifyChoiceAction {
    @Resource
    private IEditorController editorController;

    @Override
    public boolean canDoAction(String action) {
        return "edit".equals(action);
    }

    @Override
    public void execute() {
        if(execute(getSelectedItem(), getContent())){
            return;
        }

        if (IEditorsService.DATA_TYPE.equals(getContent())) {
            editorController.editEditor((Editor) getSelectedItem());
        }
    }
}