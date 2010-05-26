package org.jtheque.books.view.models.list;

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