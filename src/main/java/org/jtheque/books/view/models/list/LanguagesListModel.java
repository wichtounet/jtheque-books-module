package org.jtheque.books.view.models.list;

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

import org.jtheque.books.services.able.IBookAutoService;
import org.jtheque.ui.utils.models.SimpleListModel;

/**
 * A list model for languages.
 *
 * @author Baptiste Wicht
 */
public final class LanguagesListModel extends SimpleListModel<String> {
    /**
     * Construct a new LanguagesListModel.
     */
    public LanguagesListModel() {
        super();

        IBookAutoService bookAutoService = CoreUtils.getBean("bookAutoService");

        setElements(bookAutoService.getLanguages());
    }
}
