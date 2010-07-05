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

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.able.IAutoView;
import org.jtheque.core.managers.view.able.controller.Controller;

/**
 * An auto controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IBookAutoController extends Controller {
    /**
     * Auto add a book.
     */
    void add();

    /**
     * Auto edit a book.
     */
    void edit();

    /**
     * Auto edit or add the result.
     *
     * @param selectedBook The book result.
     */
    void auto(BookResult selectedBook);

    @Override
    IAutoView getView();
}
