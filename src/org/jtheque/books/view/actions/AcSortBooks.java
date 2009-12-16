package org.jtheque.books.view.actions;

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

import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.view.able.IBookView;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.primary.view.impl.sort.SortManager;

import java.awt.event.ActionEvent;

/**
 * An action to sort the books.
 *
 * @author Baptiste Wicht
 */
public final class AcSortBooks extends JThequeSimpleAction {
    private final SortManager sorter = new SortManager();

    private final String sortType;

    /**
     * Construct a new AcSortBooks.
     *
     * @param text     The text of action
     * @param sortType The type of sort.
     */
    public AcSortBooks(String text, String sortType) {
        super();

        setTextKey(text);

        this.sortType = sortType;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        IBookView bookView = Managers.getManager(IBeansManager.class).getBean("bookView");

        bookView.sort(sortType);
        sorter.sort(bookView.getTreeModel(), IBooksService.DATA_TYPE, sortType);
    }
}