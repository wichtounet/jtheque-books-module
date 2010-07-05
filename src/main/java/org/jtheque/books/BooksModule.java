package org.jtheque.books;

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

import org.jtheque.books.persistence.BooksSchema;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.feature.IFeatureManager;
import org.jtheque.core.managers.feature.Menu;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.annotations.Plug;
import org.jtheque.core.managers.module.annotations.PrePlug;
import org.jtheque.core.managers.module.annotations.UnPlug;
import org.jtheque.core.managers.schema.ISchemaManager;
import org.jtheque.core.managers.schema.Schema;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.utils.DataTypeManager;
import org.jtheque.primary.view.impl.choice.ChoiceAction;
import org.jtheque.primary.view.impl.choice.ChoiceActionFactory;
import org.jtheque.primary.view.impl.sort.Sorter;
import org.jtheque.primary.view.impl.sort.SorterFactory;
import org.jtheque.utils.collections.ArrayUtils;

/**
 * A JTheque Module for managing books.
 *
 * @author Baptiste Wicht
 */
@Module(id = "jtheque-books-module", i18n = "classpath:org/jtheque/books/i18n/books", version = "1.3.1", core = "2.0.2",
        jarFile = "jtheque-books-module-1.3.1.jar", updateURL = "http://jtheque.developpez.com/public/versions/BooksModule.versions")
public final class BooksModule {
    public static final String IMAGE_BASE_NAME = "org/jtheque/books/images";

    private Menu booksMenu;

    private Schema schema;

    private final TabComponent[] tabComponents;
    private final Sorter[] sorters;
    private final ChoiceAction[] choiceActions;

    /**
     * Construct a new BooksModule.
     *
     * @param tabComponents The tab components.
     * @param sorters       The sorters
     * @param choiceActions The choice actions.
     */
    public BooksModule(TabComponent[] tabComponents, Sorter[] sorters, ChoiceAction[] choiceActions) {
        super();

        this.tabComponents = ArrayUtils.copyOf(tabComponents);
        this.sorters = ArrayUtils.copyOf(sorters);
        this.choiceActions = ArrayUtils.copyOf(choiceActions);
    }

    /**
     * Pre plug the module.
     */
    @PrePlug
    public void prePlug() {
        schema = new BooksSchema();

        Managers.getManager(ISchemaManager.class).registerSchema(schema);

        PrimaryUtils.setPrimaryImpl("Books");
        PrimaryUtils.prePlug();
    }

    /**
     * Plug the module.
     */
    @Plug
    public void plug() {
        PrimaryUtils.plug();

        DataTypeManager.bindDataTypeToKey(IAuthorsService.DATA_TYPE, "data.titles.author");
        DataTypeManager.bindDataTypeToKey(IBooksService.DATA_TYPE, "data.titles.book");
        DataTypeManager.bindDataTypeToKey(IEditorsService.DATA_TYPE, "data.titles.editor");
        DataTypeManager.bindDataTypeToKey(ILendingsService.DATA_TYPE, "data.titles.lending");

        booksMenu = new BooksMenu();

        Managers.getManager(IFeatureManager.class).addMenu(booksMenu);

        for (Sorter sorter : sorters) {
            SorterFactory.getInstance().addSorter(sorter);
        }

        for (ChoiceAction action : choiceActions) {
            ChoiceActionFactory.addChoiceAction(action);
        }

        for (TabComponent component : tabComponents) {
            Managers.getManager(IViewManager.class).addTabComponent(component);
        }
    }

    /**
     * Un plug the module.
     */
    @UnPlug
    public void unplug() {
        for (ChoiceAction action : choiceActions) {
            ChoiceActionFactory.removeChoiceAction(action);
        }

        Managers.getManager(IFeatureManager.class).removeMenu(booksMenu);

        DataTypeManager.unbindDataType(IAuthorsService.DATA_TYPE);
        DataTypeManager.unbindDataType(IBooksService.DATA_TYPE);
        DataTypeManager.unbindDataType(IEditorsService.DATA_TYPE);
        DataTypeManager.unbindDataType(ILendingsService.DATA_TYPE);

        for (Sorter sorter : sorters) {
            SorterFactory.getInstance().removeSorter(sorter);
        }

        for (TabComponent tabComponent : tabComponents) {
            Managers.getManager(IViewManager.class).removeTabComponent(tabComponent);
        }

        PrimaryUtils.unplug();

        Managers.getManager(IFeatureManager.class).removeMenu(booksMenu);

        Managers.getManager(ISchemaManager.class).unregisterSchema(schema);
    }
}