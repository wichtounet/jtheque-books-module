package org.jtheque.books.view.controllers;

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
import org.jtheque.books.view.able.IBookView;
import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;

import javax.annotation.Resource;

/**
 * A controller for the book view.
 *
 * @author Baptiste Wicht
 */
public final class BookController extends PrincipalController<Book> implements IBookController {
    @Resource
    private IBookView bookView;

    public BookController(ControllerState viewState, ControllerState modifyState,
                          ControllerState newObjectState, ControllerState autoAddState) {
        super(viewState, modifyState, newObjectState, autoAddState);
    }

    @Override
    public IBookView getView() {
        return bookView;
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(bookView.fillBookFormBean());

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IBooksModel getViewModel() {
        return (IBooksModel) bookView.getModel();
    }

    @Override
    public String getDataType() {
        return IBooksService.DATA_TYPE;
    }
}
