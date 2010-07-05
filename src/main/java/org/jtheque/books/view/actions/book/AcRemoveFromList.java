package org.jtheque.books.view.actions.book;

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
