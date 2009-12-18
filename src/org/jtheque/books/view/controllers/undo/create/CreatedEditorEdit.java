package org.jtheque.books.view.controllers.undo.create;

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
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit corresponding to a creation of an editor.
 *
 * @author Baptiste Wicht
 */
public final class CreatedEditorEdit extends AbstractUndoableEdit {
    private final Editor editor;

    @Resource
    private IEditorsService editorsService;

    /**
     * Construct a new CreatedEditorEdit.
     *
     * @param editor The created editor.
     */
    public CreatedEditorEdit(Editor editor) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.editor = editor;
    }

    @Override
    public void undo() {
        super.undo();

        editorsService.delete(editor);
    }

    @Override
    public void redo() {
        super.redo();

        editorsService.create(editor);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.create");
    }
}