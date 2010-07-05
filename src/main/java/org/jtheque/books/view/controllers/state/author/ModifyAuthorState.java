package org.jtheque.books.view.controllers.state.author;

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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.controllers.able.IAuthorController;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

import javax.annotation.Resource;

/**
 * The state "modify" of the author view.
 *
 * @author Baptiste Wicht
 */
public final class ModifyAuthorState extends AbstractControllerState {
    @Resource
    private IAuthorController controller;

    @Resource
    private IAuthorsService authorsService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IAuthorsModel getViewModel() {
        return controller.getViewModel();
    }

    @Override
    public void apply() {
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.EDIT);

        getViewModel().getCurrentAuthor().saveToMemento();
    }

    @Override
    public ControllerState cancel() {
        getViewModel().getCurrentAuthor().restoreMemento();

        return controller.getViewState();
    }

    @Override
    public ControllerState save(FormBean bean) {
        IPersonFormBean infos = (IPersonFormBean) bean;

        String oldName = getViewModel().getCurrentAuthor().getDisplayableText();

        Person author = authorsService.getEmptyPerson();

        infos.fillPerson(author);

        authorsService.save(getViewModel().getCurrentAuthor());

        if (!oldName.equals(getViewModel().getCurrentAuthor().getDisplayableText())) {
            controller.getView().resort();
            controller.getView().select(getViewModel().getCurrentAuthor());
        }

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        Person author = (Person) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmSave.title"))) {
            controller.save();
        } else {
            getViewModel().getCurrentAuthor().restoreMemento();
        }

        getViewModel().setCurrentAuthor(author);

        return controller.getViewState();
    }
}
