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
import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.controller.impl.undo.GenericDataDeletedEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * The state "view" of the book view.
 *
 * @author Baptiste Wicht
 */
public final class ViewBookState extends AbstractControllerState {
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
        controller.getView().setEnabled(false);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.VIEW);

        if (getViewModel().getCurrentBook() != null) {
            controller.getView().select(getViewModel().getCurrentBook());
        }
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

            Managers.getManager(IUndoRedoManager.class).addEdit(new GenericDataDeletedEdit<Book>("authorsService", book));
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
