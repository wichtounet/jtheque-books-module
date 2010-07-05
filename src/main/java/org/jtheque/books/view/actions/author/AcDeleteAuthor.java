package org.jtheque.books.view.actions.author;

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

import org.jtheque.books.view.controllers.able.IAuthorController;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to delete the current author.
 *
 * @author Baptiste Wicht
 */
public final class AcDeleteAuthor extends JThequeAction {
    /**
     * Construct a new AcDeleteAuthor.
     */
    public AcDeleteAuthor() {
        super("author.actions.delete");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        IAuthorsModel model = Managers.getManager(IBeansManager.class).<IAuthorController>getBean("authorController").getView().getModel();

        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmDelete", model.getCurrentAuthor().getDisplayableText()),
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmDelete.title"));

        if (yes) {
            Managers.getManager(IBeansManager.class).<IAuthorController>getBean("authorController").deleteCurrent();
        }
    }
}
