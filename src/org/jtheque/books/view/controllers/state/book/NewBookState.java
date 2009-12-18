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
import org.jtheque.books.view.controllers.undo.create.CreatedBookEdit;
import org.jtheque.books.view.fb.IBookFormBean;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "new" of the book view.
 *
 * @author Baptiste Wicht
 */
public final class NewBookState implements ControllerState {
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
        getViewModel().setCurrentBook(booksService.getDefaultBook());
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.NEW);
    }

    @Override
    public ControllerState autoEdit(Data data) {
        switchBook(data);

        return controller.getAutoAddState();
    }

    /**
     * Switch the current book.
     *
     * @param data The new book to display.
     */
    private void switchBook(Data data) {
        Book book = (Book) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("book.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("book.dialogs.confirmSave.title"))) {
            controller.save();
        }

        getViewModel().setCurrentBook(book);
    }

    @Override
    public ControllerState cancel() {
        controller.getView().selectFirst();

        if (booksService.getBooks().size() <= 0) {
            return controller.getViewState();
        }

        return null;
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
        IBookFormBean infos = (IBookFormBean) bean;

        Book book = booksService.getEmptyBook();

        infos.fillBook(book);

        booksService.create(book);

        Managers.getManager(IUndoRedoManager.class).addEdit(new CreatedBookEdit(book));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        switchBook(data);

        return controller.getViewState();
    }
}