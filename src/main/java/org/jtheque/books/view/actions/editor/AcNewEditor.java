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

import org.jtheque.books.view.controllers.able.IEditorController;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * An action to create a new editor.
 *
 * @author Baptiste Wicht
 */
public final class AcNewEditor extends JThequeAction {
    /**
     * Construct a AcNewEditor.
     */
    public AcNewEditor() {
        super("editor.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Managers.getManager(IBeansManager.class).<IEditorController>getBean("editorController").newEditor();
    }
}