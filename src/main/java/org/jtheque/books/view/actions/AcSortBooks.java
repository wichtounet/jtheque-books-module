package org.jtheque.books.view.actions;

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
