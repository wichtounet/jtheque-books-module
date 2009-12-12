package org.jtheque.books.view.actions.generals;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.primary.view.able.PrincipalDataView;

import java.awt.event.ActionEvent;

/**
 * An action to refresh the list of the current view.
 *
 * @author Baptiste Wicht
 */
public final class AcRefreshList extends JThequeAction {
    private static final long serialVersionUID = -6059011169838015671L;

    /**
     * Construct a AcRefreshList.
     */
    public AcRefreshList() {
        super("actions.refresh");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrincipalDataView view = (PrincipalDataView) Managers.getManager(IViewManager.class).getViews().getSelectedView().getComponent();

        view.resort();
    }
}