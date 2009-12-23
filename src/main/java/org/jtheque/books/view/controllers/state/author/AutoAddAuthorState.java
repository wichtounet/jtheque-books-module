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
import org.jtheque.books.view.fb.IAuthorFormBean;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "auto add" of the author view.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddAuthorState implements ControllerState {
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
        controller.getView().getToolbarView().setDisplayMode(ViewMode.AUTO);

        getViewModel().getCurrentAuthor().saveToMemento();
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Person author = (Person) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmSave.title"))) {
            controller.save();
        } else {
            getViewModel().getCurrentAuthor().restoreMemento();
        }

        getViewModel().setCurrentAuthor(author);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState cancel() {
        if (getViewModel().getCurrentAuthor().isSaved()) {
            getViewModel().getCurrentAuthor().restoreMemento();
        }

        return controller.getViewState();
    }

    @Override
    public ControllerState create() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState manualEdit() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState delete() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState save(FormBean bean) {
        IAuthorFormBean infos = (IAuthorFormBean) bean;

        Person author = getViewModel().getCurrentAuthor().isSaved() ? getViewModel().getCurrentAuthor() : authorsService.getEmptyAuthor();

        infos.fillAuthor(author);

        if (getViewModel().getCurrentAuthor().isSaved()) {
            authorsService.save(author);
        } else {
            authorsService.create(author);
        }

        controller.getView().resort();
        controller.getView().select(author);

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        Person author = (Person) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("actor.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("actor.dialogs.confirmSave.title"))) {
            controller.save();
        } else {
            getViewModel().getCurrentAuthor().restoreMemento();
        }

        getViewModel().setCurrentAuthor(author);

        return controller.getViewState();
    }
}