package org.jtheque.books.view.controllers;

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
import org.jtheque.books.view.able.IEditorView;
import org.jtheque.books.view.controllers.able.IEditorController;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.primary.controller.impl.undo.GenericDataCreatedEdit;
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

            Managers.getManager(IUndoRedoManager.class).addEdit(
                    new GenericDataCreatedEdit<Editor>("editorsService", currentEditor));
        } else {
            editorsService.save(currentEditor);
        }
    }

    @Override
    public IEditorView getView() {
        return editorView;
    }
}
