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