package org.jtheque.books.view.controllers.state.book;

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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.books.view.controllers.undo.delete.DeletedBookEdit;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "view" of the book view.
 *
 * @author Baptiste Wicht
 */
public final class ViewBookState implements ControllerState {
    @Resource
    private IBookController controller;

    @Resource
    private IBooksService booksService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IBooksModel getViewModel() {
        return controller.getViewModel();
    }

    @Override
    public void apply() {
        controller.getViewModel().setEnabled(false);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.VIEW);

        if (getViewModel().getCurrentBook() != null) {
            controller.getView().select(getViewModel().getCurrentBook());
        }
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Book book = (Book) data;

        getViewModel().setCurrentBook(book);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState save(FormBean bean) {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState cancel() {
        //Do nothing

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
    public ControllerState delete() {
        Book book = getViewModel().getCurrentBook();

        boolean deleted = booksService.delete(book);

        if (deleted) {
            if (booksService.getBooks().isEmpty()) {
                controller.getView().clear();
            } else {
                controller.getView().selectFirst();
            }

            Managers.getManager(IUndoRedoManager.class).addEdit(new DeletedBookEdit(book));
        }

        return null;
    }

    @Override
    public ControllerState view(Data data) {
        Book book = (Book) data;

        getViewModel().setCurrentBook(book);

        return null;
    }
}