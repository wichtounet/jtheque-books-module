package org.jtheque.books.view.controllers.able;

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

import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Person;

/**
 * An author controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IAuthorController extends IPrincipalController<Person> {
    /**
     * Save the current author.
     */
    void save();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    @Override
    IAuthorsModel getViewModel();

    @Override
    IAuthorView getView();
}
