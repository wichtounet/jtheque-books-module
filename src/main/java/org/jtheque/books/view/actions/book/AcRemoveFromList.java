package org.jtheque.books.view.actions.book;

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

import org.jtheque.books.view.panels.IInfosPanel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.DefaultListModel;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * An action to remove an author from the list.
 *
 * @author Baptiste Wicht
 */
public final class AcRemoveFromList extends JThequeSimpleAction {
    /**
     * Construct a new <code>AcRemoveFromList</code>.
     */
    public AcRemoveFromList() {
        super();

        setText(" << ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IInfosPanel infosView = Managers.getManager(IBeansManager.class).getBean("bookPanelInfos");

        DefaultListModel authorsModel = infosView.getAuthorsModel();
        DefaultListModel authorsBookModel = infosView.getAuthorsBookModel();

        int[] selectedActors = infosView.getSelectedAuthorsBookIndexes();

        Arrays.sort(selectedActors);

        ArrayUtils.reverse(selectedActors);

        for (int index : selectedActors) {
            Object actor = authorsBookModel.remove(index);
            authorsModel.addElement(actor);
        }
    }
}