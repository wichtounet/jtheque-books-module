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
import org.jtheque.books.view.controllers.undo.delete.DeletedAuthorEdit;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "view" of the author view.
 *
 * @author Baptiste Wicht
 */
public final class ViewAuthorState implements ControllerState {
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
        controller.getView().setEnabled(false);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.VIEW);

        if (getViewModel().getCurrentAuthor() != null) {
            controller.getView().select(getViewModel().getCurrentAuthor());
        }
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Person author = (Person) data;

        getViewModel().setCurrentAuthor(author);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState save(FormBean infos) {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState cancel() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState delete() {
        Person deletedAuthor = getViewModel().getCurrentAuthor();

        boolean deleted = authorsService.delete(deletedAuthor);

        if (deleted) {
            if (authorsService.getAuthors().isEmpty()) {
                controller.getView().clear();
            } else {
                controller.getView().selectFirst();
            }

            Managers.getManager(IUndoRedoManager.class).addEdit(new DeletedAuthorEdit(deletedAuthor));
        }

        return null;
    }

    @Override
    public ControllerState create() {
        return controller.getNewObjectState();
    }

    @Override
    public ControllerState manualEdit() {
        return controller.getModifyState();
    }

    @Override
    public ControllerState view(Data data) {
        Person author = (Person) data;

        getViewModel().setCurrentAuthor(author);

        return null;
    }
}