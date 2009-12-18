package org.jtheque.books.view.controllers;

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
import org.jtheque.books.view.able.IEditorView;
import org.jtheque.books.view.controllers.able.IEditorController;
import org.jtheque.books.view.controllers.undo.create.CreatedEditorEdit;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A controller for the editor view.
 *
 * @author Baptiste Wicht
 */
public final class EditorController extends AbstractController implements IEditorController {
    private ViewMode state = ViewMode.NEW;
    private Editor currentEditor;

    @Resource
    private IEditorsService editorsService;

    @Resource
    private IEditorView editorView;

    @Override
    public void newEditor() {
        state = ViewMode.NEW;

        editorView.reload();
        currentEditor = editorsService.getEmptyEditor();
    }

    @Override
    public void editEditor(Editor editor) {
        state = ViewMode.EDIT;

        editorView.reload(editor);
        currentEditor = editor;

        displayView();
    }

    @Override
    public void save(String name) {
        if (currentEditor == null) {
            if (state == ViewMode.NEW) {
                currentEditor = editorsService.getEmptyEditor();
            } else {
                throw new IllegalStateException("The current editor cannot be null in EDIT mode. ");
            }
        }

        currentEditor.setName(name);

        if (state == ViewMode.NEW) {
            editorsService.create(currentEditor);

            Managers.getManager(IUndoRedoManager.class).addEdit(new CreatedEditorEdit(currentEditor));
        } else {
            editorsService.save(currentEditor);
        }
    }

    @Override
    public IEditorView getView() {
        return editorView;
    }
}