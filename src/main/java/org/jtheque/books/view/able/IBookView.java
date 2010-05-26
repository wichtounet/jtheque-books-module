package org.jtheque.books.view.able;

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

import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.primary.view.able.PrincipalDataView;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;

/**
 * A book view specification.
 *
 * @author Baptiste Wicht
 */
public interface IBookView extends PrincipalDataView, CurrentObjectListener, TabComponent {
    /**
     * Fill a <code>BookFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>BookFormBean</code>.
     */
    IBookFormBean fillBookFormBean();

    /**
     * Return the tree model.
     *
     * @return The tree model.
     */
    JThequeTreeModel getTreeModel();
}
