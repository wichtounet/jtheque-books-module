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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.books.view.controllers.able.IAuthorController;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;

/**
 * A controller for the author view.
 *
 * @author Baptiste Wicht
 */
public final class AuthorController extends PrincipalController<Person> implements IAuthorController {
    @Resource
    private IAuthorView authorView;

    public AuthorController(ControllerState viewState, ControllerState modifyState,
                            ControllerState newObjectState, ControllerState autoAddState) {
        super(viewState, modifyState, newObjectState, autoAddState);
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(authorView.fillFilmFormBean());

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IAuthorsModel getViewModel() {
        return authorView.getModel();
    }

    @Override
    public IAuthorView getView() {
        return authorView;
    }

    @Override
    public String getDataType() {
        return IAuthorsService.DATA_TYPE;
    }
}