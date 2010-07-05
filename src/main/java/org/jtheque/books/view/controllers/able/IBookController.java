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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.view.able.IBookView;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.primary.controller.able.IPrincipalController;

/**
 * A book controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IBookController extends IPrincipalController<Book> {
    /**
     * Save the current book.
     */
    void save();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    @Override
    IBooksModel getViewModel();

    @Override
    IBookView getView();
}
