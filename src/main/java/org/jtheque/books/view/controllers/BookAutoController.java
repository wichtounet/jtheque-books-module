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
import org.jtheque.books.services.able.IBookAutoService;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.impl.utils.EditArguments;
import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.able.IAutoView;
import org.jtheque.books.view.controllers.able.IBookAutoController;
import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.core.managers.view.able.controller.AbstractController;

import javax.annotation.Resource;

/**
 * A controller for the auto view.
 *
 * @author Baptiste Wicht
 */
public final class BookAutoController extends AbstractController implements IBookAutoController {
    private boolean editMode;
    private EditArguments args;

    @Resource
    private IBooksService booksService;

    @Resource
    private IBookController bookController;

    @Resource
    private IBookAutoService bookAutoService;

    @Resource
    private IAutoView autoView;

    @Override
    public void add() {
        displayView();

        editMode = false;
    }

    @Override
    public void edit() {
        displayView();

        editMode = true;
        args = EditArguments.createFullArgs();

        //TODO: Create a view to chose the fields to edit like JTheque Films Module. 
    }

    @Override
    public void auto(BookResult selectedBook) {
        if (editMode) {
            Book book = bookController.getViewModel().getCurrentBook();

            bookAutoService.modifyBook(selectedBook, book, args);

            bookController.view(book);
            bookController.manualEdit();
        } else {
            Book book = bookAutoService.getBook(selectedBook);

            booksService.create(book);

            bookController.view(book);
        }
    }

    @Override
    public IAutoView getView() {
        return autoView;
    }

    @Override
    public void displayView() {
        autoView.display();

        if (editMode) {
            autoView.sendMessage("title", bookController.getViewModel().getCurrentBook().getTitle());
        }
    }
}
