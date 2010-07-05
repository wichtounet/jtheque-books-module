package org.jtheque.books.view.actions.editor;

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

import org.jtheque.books.view.able.IEditorView;
import org.jtheque.books.view.controllers.able.IEditorController;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * An action to validate the editor view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateEditorView extends JThequeAction {
    /**
     * Construct a AcValidateEditorView.
     */
    public AcValidateEditorView() {
        super("editor.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IEditorView view = (IEditorView) Managers.getManager(IBeansManager.class).<IEditorController>getBean("editorController").getView();

        if (view.validateContent()) {
            Managers.getManager(IBeansManager.class).<IEditorController>getBean("editorController").save(view.getFieldName().getText());

            view.closeDown();
        }
    }
}
