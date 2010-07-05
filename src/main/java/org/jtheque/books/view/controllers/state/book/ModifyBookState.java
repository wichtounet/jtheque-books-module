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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "modify" of the book view.
 *
 * @author Baptiste Wicht
 */
public final class ModifyBookState extends AbstractControllerState {
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
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.EDIT);

        getViewModel().getCurrentBook().saveToMemento();
    }

    @Override
    public ControllerState cancel() {
        getViewModel().getCurrentBook().restoreMemento();

        return controller.getViewState();
    }

    @Override
    public ControllerState save(FormBean bean) {
        IBookFormBean infos = (IBookFormBean) bean;

        Book book = getViewModel().getCurrentBook();

        String oldTitle = book.getTitle();

        infos.fillBook(book);

        booksService.save(book);

        if (!oldTitle.equals(book.getTitle())) {
            controller.getView().resort();
        }

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
