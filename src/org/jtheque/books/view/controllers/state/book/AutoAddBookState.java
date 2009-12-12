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
import org.jtheque.books.view.fb.IBookFormBean;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "auto add" of the book view.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddBookState implements ControllerState {
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
        getViewModel().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.AUTO);

        getViewModel().getCurrentBook().saveToMemento();
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Book book = (Book) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("book.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("book.dialogs.confirmSave.title"))) {
            controller.save();
        } else {
            getViewModel().getCurrentBook().restoreMemento();
        }

        getViewModel().setCurrentBook(book);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState cancel() {
        if (getViewModel().getCurrentBook().isSaved()) {
            getViewModel().getCurrentBook().restoreMemento();
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
        IBookFormBean infos = (IBookFormBean) bean;

        Book book;

        book = getViewModel().getCurrentBook().isSaved() ? getViewModel().getCurrentBook() : booksService.getEmptyBook();

        infos.fillBook(book);

        if (getViewModel().getCurrentBook().isSaved()) {
            booksService.save(book);
        } else {
            booksService.create(book);
        }

        controller.getView().resort();
        controller.getView().select(book);

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        Book book = (Book) data;

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("book.dialogs.confirmSave"),
                Managers.getManager(ILanguageManager.class).getMessage("book.dialogs.confirmSave.title"))) {
            controller.save();
        } else {
            getViewModel().getCurrentBook().restoreMemento();
        }

        getViewModel().setCurrentBook(book);

        return controller.getViewState();
    }
}