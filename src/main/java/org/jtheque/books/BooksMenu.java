package org.jtheque.books;

import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.books.view.actions.editor.AcNewEditor;
import org.jtheque.books.view.actions.generals.AcRefreshList;
import org.jtheque.core.managers.feature.AbstractMenu;
import org.jtheque.core.managers.feature.Feature;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.view.impl.actions.choice.ChoiceViewAction;

import java.util.List;

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

public class BooksMenu extends AbstractMenu {
    public BooksMenu() {
        super();

        PrimaryUtils.enableMenu(
                features(createSubFeature(5, new AcNewEditor())),
                features(createSubFeature(5, new ChoiceViewAction("actions.editor", "delete", IEditorsService.DATA_TYPE, choiceController))),
                features(createSubFeature(5, new ChoiceViewAction("actions.editor", "edit", IEditorsService.DATA_TYPE, choiceController)))
        );
    }

    @Override
    protected List<Feature> getFileMenuSubFeatures(){
        return features(
                createSubFeature(100, new AcRefreshList())
        );
    }
}