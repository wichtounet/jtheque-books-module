package org.jtheque.books.view.controllers.choice;

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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.books.view.controllers.undo.delete.DeletedCountryEdit;
import org.jtheque.books.view.controllers.undo.delete.DeletedEditorEdit;
import org.jtheque.books.view.controllers.undo.delete.DeletedLanguageEdit;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.primary.controller.impl.undo.DeletedKindEdit;
import org.jtheque.primary.controller.impl.undo.DeletedLendingEdit;
import org.jtheque.primary.controller.impl.undo.DeletedPersonEdit;
import org.jtheque.primary.controller.impl.undo.DeletedSagaEdit;
import org.jtheque.primary.controller.impl.undo.DeletedTypeEdit;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.IBorrowersService;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.ISagasService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.impl.choice.AbstractDeleteChoiceAction;
import org.jtheque.primary.view.impl.choice.Deleter;

import javax.annotation.Resource;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractDeleteChoiceAction {
    @Resource
    private IEditorsService editorsService;

    @Resource
    private IKindsService kindsService;

    @Resource
    private ISagasService sagasService;

    @Resource
    private IBorrowersService borrowersService;

    @Resource
    private ILendingsService lendingsService;

    @Resource
    private ICountriesService countriesService;

    @Resource
    private ILanguagesService languagesService;

    @Resource
    private ITypesService typesService;

    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction() {
        super();

        setDeleters(new EditorDeleter(), new KindDeleter(), new TypeDeleter(), new LanguageDeleter(),
                new CountryDeleter(), new BorrowerDeleter(), new SagaDeleter(), new LendingDeleter());
    }

    @Override
    public void execute() {
        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("choice.dialogs.delete") + ' ' + getSelectedItem().toString(),
                Managers.getManager(ILanguageManager.class).getMessage("choice.dialogs.delete.title"));

        if (yes) {
            delete();
        }
    }

    /**
     * A <code>Deleter</code> for Editor.
     *
     * @author Baptiste Wicht
     */
    private final class EditorDeleter extends Deleter {
        /**
         * Construct a new <code>EditorDeleter</code>.
         */
        private EditorDeleter() {
            super(IEditorsService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            addEditIfDeleted(
                    editorsService.delete((Editor) o),
                    new DeletedEditorEdit((Editor) o));
        }
    }

    /**
     * A <code>Deleter</code> for Kind.
     *
     * @author Baptiste Wicht
     */
    private final class KindDeleter extends Deleter {
        /**
         * Construct a new <code>KindDeleter</code>.
         */
        private KindDeleter() {
            super(IKindsService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = kindsService.delete((Kind) o);

            addEditIfDeleted(deleted, new DeletedKindEdit((Kind) o));
        }
    }

    /**
     * A <code>Deleter</code> for Type.
     *
     * @author Baptiste Wicht
     */
    private final class TypeDeleter extends Deleter {
        /**
         * Construct a new <code>TypeDeleter</code>.
         */
        private TypeDeleter() {
            super(ITypesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = typesService.delete((Type) o);

            addEditIfDeleted(deleted, new DeletedTypeEdit((Type) o));
        }
    }

    /**
     * A <code>Deleter</code> for Language.
     *
     * @author Baptiste Wicht
     */
    private final class LanguageDeleter extends Deleter {
        /**
         * Construct a new <code>LanguageDeleter</code>.
         */
        private LanguageDeleter() {
            super(ILanguagesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = languagesService.delete((Language) o);

            addEditIfDeleted(deleted, new DeletedLanguageEdit((Language) o));
        }
    }

    /**
     * A <code>Deleter</code> for Country.
     *
     * @author Baptiste Wicht
     */
    private final class CountryDeleter extends Deleter {
        /**
         * Construct a new <code>CountryDeleter</code>.
         */
        private CountryDeleter() {
            super(ICountriesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = countriesService.delete((Country) o);

            addEditIfDeleted(deleted, new DeletedCountryEdit((Country) o));
        }
    }

    /**
     * A <code>Deleter</code> for Lending.
     *
     * @author Baptiste Wicht
     */
    private final class LendingDeleter extends Deleter {
        /**
         * Construct a new <code>LendingDeleter</code>.
         */
        private LendingDeleter() {
            super(ILendingsService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = lendingsService.delete((Lending) o);

            addEditIfDeleted(deleted, new DeletedLendingEdit((Lending) o));
        }
    }

    /**
     * A <code>Deleter</code> for Borrower.
     *
     * @author Baptiste Wicht
     */
    private final class BorrowerDeleter extends Deleter {
        /**
         * Construct a new <code>BorrowerDeleter</code>.
         */
        private BorrowerDeleter() {
            super(IBorrowersService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = borrowersService.delete((Person) o);

            addEditIfDeleted(deleted, new DeletedPersonEdit((Person) o));
        }
    }

    /**
     * A <code>Deleter</code> for Saga.
     *
     * @author Baptiste Wicht
     */
    private final class SagaDeleter extends Deleter {
        /**
         * Construct a new <code>SagaDeleter</code>.
         */
        private SagaDeleter() {
            super(ISagasService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = sagasService.delete((Saga) o);

            addEditIfDeleted(deleted, new DeletedSagaEdit((Saga) o));
        }
    }
}