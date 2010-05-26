package org.jtheque.books.view.controllers.state.author;

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