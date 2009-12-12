package org.jtheque.books.view.controllers;

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