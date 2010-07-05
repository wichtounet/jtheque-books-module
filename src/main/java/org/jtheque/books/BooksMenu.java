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
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    protected List<Feature> getFileMenuSubFeatures() {
        return features(
                createSubFeature(100, new AcRefreshList())
        );
    }
}