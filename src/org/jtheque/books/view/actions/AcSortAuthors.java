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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.primary.view.impl.sort.SortManager;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to sort the authors.
 *
 * @author Baptiste Wicht
 */
public final class AcSortAuthors extends JThequeSimpleAction {
    private static final long serialVersionUID = 1651417572586677829L;

    private final SortManager sorter = new SortManager();

    private final String sortType;

    @Resource
    private IAuthorView authorView;

    /**
     * Construct a new AcSortAuthors.
     *
     * @param text     The text of action
     * @param sortType The type of sort.
     */
    public AcSortAuthors(String text, String sortType) {
        super();

        setTextKey(text);

        this.sortType = sortType;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        authorView.sort(sortType);
        sorter.sort(authorView.getTreeModel(), IAuthorsService.DATA_TYPE, sortType);
    }
}