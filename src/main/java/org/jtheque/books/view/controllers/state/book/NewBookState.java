package org.jtheque.books.view.controllers.state.book;

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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.controller.impl.undo.GenericDataCreatedEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "new" of the book view.
 *
 * @author Baptiste Wicht
 */
public final class NewBookState extends AbstractControllerState {
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
    public ControllerState save(FormBean bean) {
        IBookFormBean infos = (IBookFormBean) bean;

        Book book = booksService.getEmptyBook();

        infos.fillBook(book);

        booksService.create(book);

        Managers.getManager(IUndoRedoManager.class).addEdit(new GenericDataCreatedEdit<Book>("booksService", book));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        switchBook(data);

        return controller.getViewState();
    }
}
