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
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.controller.impl.undo.GenericDataCreatedEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

import javax.annotation.Resource;

/**
 * The state "new" of the author view.
 *
 * @author Baptiste Wicht
 */
public final class NewAuthorState extends AbstractControllerState {
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
        getViewModel().setCurrentAuthor(authorsService.getDefaultPerson());
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.NEW);
    }

    @Override
    public ControllerState cancel() {
        ControllerState nextState = null;

        controller.getView().selectFirst();

        if (authorsService.getPersons().size() <= 0) {
            nextState = controller.getViewState();
        }

        return nextState;
    }

    @Override
    public ControllerState save(FormBean bean) {
        IPersonFormBean infos = (IPersonFormBean) bean;

        Person author = authorsService.getEmptyPerson();

        infos.fillPerson(author);

        authorsService.create(author);

        Managers.getManager(IUndoRedoManager.class).addEdit(new GenericDataCreatedEdit<Person>("authorsService", author));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        switchCurrentAuthor(data);

        return controller.getViewState();
    }

    /**
     * Switch the current author.
     *
     * @param data The author to display.
     */
    private void switchCurrentAuthor(Data data) {
        Person author = (Person) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("actor.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("actor.dialogs.confirmSave.title"))) {
            controller.save();
        }

        getViewModel().setCurrentAuthor(author);
    }
}
