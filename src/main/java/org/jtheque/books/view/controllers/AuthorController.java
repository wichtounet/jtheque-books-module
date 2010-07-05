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
