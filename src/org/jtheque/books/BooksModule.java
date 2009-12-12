package org.jtheque.books;

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

import org.jtheque.books.persistence.BooksSchema;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.feature.Feature;
import org.jtheque.core.managers.feature.Feature.FeatureType;
import org.jtheque.core.managers.feature.IFeatureManager;
import org.jtheque.core.managers.feature.IFeatureManager.CoreFeature;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.annotations.Plug;
import org.jtheque.core.managers.module.annotations.PrePlug;
import org.jtheque.core.managers.module.annotations.UnPlug;
import org.jtheque.core.managers.schema.ISchemaManager;
import org.jtheque.core.managers.schema.Schema;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.ISagasService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.utils.DataTypeManager;
import org.jtheque.primary.view.impl.choice.ChoiceAction;
import org.jtheque.primary.view.impl.choice.ChoiceActionFactory;
import org.jtheque.primary.view.impl.sort.Sorter;
import org.jtheque.primary.view.impl.sort.SorterFactory;

/**
 * A JTheque Module for managing books.
 *
 * @author Baptiste Wicht
 */
@Module(id = "jtheque-books-module", i18n = "classpath:org/jtheque/books/ressources/i18n/books", version = "1.3.1-SNAPSHOT", core = "2.0.2",
        jarFile = "jtheque-books-module-1.3.1-SNAPSHOT.jar", updateURL = "http://jtheque.developpez.com/public/versions/BooksModule.versions")
public final class BooksModule {
    public static final String IMAGE_BASE_NAME = "org/jtheque/books/ressources/images";

    //Features
    private Feature refreshFeature;
    private Feature othersFeature;

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

        this.tabComponents = tabComponents;
        this.sorters = sorters;
        this.choiceActions = choiceActions;
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
        DataTypeManager.bindDataTypeToKey(IKindsService.DATA_TYPE, "data.titles.kind");
        DataTypeManager.bindDataTypeToKey(ILendingsService.DATA_TYPE, "data.titles.lending");
        DataTypeManager.bindDataTypeToKey(ISagasService.DATA_TYPE, "data.titles.saga");
        DataTypeManager.bindDataTypeToKey(ITypesService.DATA_TYPE, "data.titles.type");

        for (Sorter sorter : sorters) {
            SorterFactory.getInstance().addSorter(sorter);
        }

        addFeatures();

        for (ChoiceAction action : choiceActions) {
            ChoiceActionFactory.addChoiceAction(action);
        }

        for (TabComponent component : tabComponents) {
            Managers.getManager(IViewManager.class).addTabComponent(component);
        }
    }

    /**
     * Add the features.
     */
    private void addFeatures() {
        //File

        IFeatureManager manager = Managers.getManager(IFeatureManager.class);

        refreshFeature = manager.addSubFeature(manager.getFeature(CoreFeature.FILE), "refreshAction", FeatureType.ACTION, 100);

        //Others

        othersFeature = manager.createFeature(500, FeatureType.PACK, "actions.others");

        addNewFeature(manager);
        addDeleteFeature(manager);
        addEditFeature(manager);

        Managers.getManager(IFeatureManager.class).addFeature(othersFeature);
    }

    /**
     * Add the features to create new objects.
     *
     * @param feature The feature manager.
     */
    private void addNewFeature(IFeatureManager feature) {
        Feature newFeature = feature.createFeature(1, FeatureType.ACTIONS, "actions.others.new");

        feature.addSubFeature(newFeature, "newKindAction", FeatureType.ACTION, 1);
        feature.addSubFeature(newFeature, "newTypeAction", FeatureType.ACTION, 2);
        feature.addSubFeature(newFeature, "newLanguageAction", FeatureType.ACTION, 3);
        feature.addSubFeature(newFeature, "newCountryAction", FeatureType.ACTION, 4);
        feature.addSubFeature(newFeature, "newEditorAction", FeatureType.ACTION, 5);

        othersFeature.addSubFeature(newFeature);
    }

    /**
     * Add the features to delete objects.
     *
     * @param manager The feature manager.
     */
    private void addDeleteFeature(IFeatureManager manager) {
        Feature deleteFeature = manager.createFeature(2, FeatureType.ACTIONS, "actions.others.delete");

        manager.addSubFeature(deleteFeature, "deleteKindAction", FeatureType.ACTION, 1);
        manager.addSubFeature(deleteFeature, "deleteTypeAction", FeatureType.ACTION, 2);
        manager.addSubFeature(deleteFeature, "deleteLanguageAction", FeatureType.ACTION, 3);
        manager.addSubFeature(deleteFeature, "deleteCountryAction", FeatureType.ACTION, 4);
        manager.addSubFeature(deleteFeature, "deleteEditorAction", FeatureType.ACTION, 5);

        othersFeature.addSubFeature(deleteFeature);
    }

    /**
     * Add the features to edit objects.
     *
     * @param manager The feature manager.
     */
    private void addEditFeature(IFeatureManager manager) {
        Feature editFeature = manager.createFeature(3, FeatureType.ACTIONS, "actions.others.modify");

        manager.addSubFeature(editFeature, "editKindAction", FeatureType.ACTION, 1);
        manager.addSubFeature(editFeature, "editTypeAction", FeatureType.ACTION, 2);
        manager.addSubFeature(editFeature, "editLanguageAction", FeatureType.ACTION, 3);
        manager.addSubFeature(editFeature, "editCountryAction", FeatureType.ACTION, 4);
        manager.addSubFeature(editFeature, "editEditorAction", FeatureType.ACTION, 5);

        othersFeature.addSubFeature(editFeature);
    }

    /**
     * Un plug the module.
     */
    @UnPlug
    public void unplug() {
        for (ChoiceAction action : choiceActions) {
            ChoiceActionFactory.removeChoiceAction(action);
        }

        Managers.getManager(IFeatureManager.class).removeFeature(othersFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.FILE).removeSubFeature(refreshFeature);

        DataTypeManager.unbindDataType(IAuthorsService.DATA_TYPE);
        DataTypeManager.unbindDataType(IBooksService.DATA_TYPE);
        DataTypeManager.unbindDataType(IEditorsService.DATA_TYPE);
        DataTypeManager.unbindDataType(IKindsService.DATA_TYPE);
        DataTypeManager.unbindDataType(ILendingsService.DATA_TYPE);
        DataTypeManager.unbindDataType(ISagasService.DATA_TYPE);
        DataTypeManager.unbindDataType(ITypesService.DATA_TYPE);

        for (Sorter sorter : sorters) {
            SorterFactory.getInstance().removeSorter(sorter);
        }

        for (TabComponent tabComponent : tabComponents) {
            Managers.getManager(IViewManager.class).removeTabComponent(tabComponent);
        }

        PrimaryUtils.unplug();

        Managers.getManager(ISchemaManager.class).unregisterSchema(schema);
    }
}