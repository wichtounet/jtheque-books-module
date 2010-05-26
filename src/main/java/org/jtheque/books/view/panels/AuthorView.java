package org.jtheque.books.view.panels;

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

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.books.view.actions.AcSortAuthors;
import org.jtheque.books.view.models.AuthorsModel;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.books.view.toolbar.JPanelAuthorToolBar;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.view.able.fb.IPersonFormBean;
import org.jtheque.primary.view.impl.components.panels.AbstractPrincipalDataPanel;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.fb.PersonFormBean;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionListener;
import java.awt.Font;
import java.util.Collection;

/**
 * The principal view for authors.
 *
 * @author Baptiste Wicht
 */
public final class AuthorView extends AbstractPrincipalDelegatedView<AbstractPrincipalDataPanel<IAuthorsModel>> implements IAuthorView {
    private static final int NAMES_LIMIT = 80;
    private static final double A_QUARTER = 0.25;

    public AuthorView() {
        super(1, "data.titles.author");
    }

    @PostConstruct
    public void init() {
        buildInEDT();

        getModel().addCurrentObjectListener(this);
    }

    @Override
    public IAuthorsModel getModel() {
        return (IAuthorsModel) super.getModel();
    }

    @Override
    protected void buildDelegatedView() {
        AuthorViewImpl impl = new AuthorViewImpl();
        setView(impl);
        impl.build();

        getModel().addCurrentObjectListener(this);
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        getImplementationView().setCurrent(event.getObject());
    }

    /**
     * Fill a <code>AuthorFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>AuthorFormBean</code>.
     */
    @Override
    public IPersonFormBean fillFilmFormBean() {
        IPersonFormBean fb = new PersonFormBean();

        getImplementationView().fillFormBean(fb);

        return fb;
    }

    @Override
    public JThequeTreeModel getTreeModel() {
        return getImplementationView().getTreeModel();
    }

    private static final class AuthorViewImpl extends AbstractPrincipalDataPanel<IAuthorsModel> {
        private JXTitledPanel authorsPanel;

        private JTextField fieldFirstName;
        private JTextField fieldName;
        private DataContainerCachedComboBoxModel<SimpleData> countriesModel;
        private NotesComboBoxModel notesModel;

        private JComboBox comboCountries;
        private JComboBox comboNote;

        private JButton buttonNewCountry;

        private AuthorViewImpl() {
            super(IAuthorsService.DATA_TYPE);

            setModel(new AuthorsModel());
        }

        /**
         * Build the view.
         */
        private void build() {
            getModel().addDisplayListListener(this);

            PanelBuilder builder = new JThequePanelBuilder(this);

            buildListPanel(builder);
            buildSortPanel(builder);
            buildAuthorsPanel(builder);

            getTree().addTreeSelectionListener(Managers.getManager(IBeansManager.class).<TreeSelectionListener>getBean("authorController"));
        }

        /**
         * Build the internal panel for the list of authors.
         *
         * @param parent The parent builder.
         */
        private void buildListPanel(PanelBuilder parent) {
            JXTitledPanel panelList = new JThequeTitledPanel("author.panel.list.title");
            panelList.setBorder(new DropShadowBorder());
            panelList.setTitleFont(panelList.getTitleFont().deriveFont(Font.BOLD));

            PanelBuilder builder = new JThequePanelBuilder();

            setTreeModel(getSorter().createInitialModel(IAuthorsService.DATA_TYPE));

            initTree();

            builder.addScrolled(getTree(), builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

            panelList.setContentContainer(builder.getPanel());

            parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, A_QUARTER, 1.0));
        }

        /**
         * Build the internal panel for the sorts.
         *
         * @param parent The parent builder.
         */
        private static void buildSortPanel(PanelBuilder parent) {
            JXTitledPanel panelTri = new JThequeTitledPanel("author.panel.sort.title");
            panelTri.setBorder(new DropShadowBorder());
            panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

            PanelBuilder builder = new JThequePanelBuilder();

            addSortAction(builder, 0, "author.actions.sort.country", SimpleData.DataType.COUNTRY.getDataType());
            addSortAction(builder, 1, "author.actions.sort.note", "Notes");

            panelTri.setContentContainer(builder.getPanel());

            parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, A_QUARTER, 0.0));
        }

        private static void addSortAction(PanelBuilder builder, int row, String key, String dataType) {
            JXHyperlink linkSortCountry = builder.add(new JXHyperlink(new AcSortAuthors(key, dataType)),
                    builder.gbcSet(0, row, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));
            linkSortCountry.setClickedColor(linkSortCountry.getUnclickedColor());
        }

        /**
         * Build the internal panel for the author.
         *
         * @param parent the parent builder
         */
        private void buildAuthorsPanel(PanelBuilder parent) {
            authorsPanel = new JThequeTitledPanel("author.panel.author.title");
            authorsPanel.setBorder(new DropShadowBorder());
            authorsPanel.setTitleFont(authorsPanel.getTitleFont().deriveFont(Font.BOLD));

            I18nPanelBuilder builder = new JThequePanelBuilder();

            JPanelAuthorToolBar toolBar = new JPanelAuthorToolBar();

            setToolBar(toolBar);

            builder.add(toolBar, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));

            addFirstNameField(builder);
            addNameField(builder);
            addCountryField(builder);
            addNoteField(builder);

            authorsPanel.setContentContainer(builder.getPanel());

            parent.add(authorsPanel, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1, 2, 1 - A_QUARTER, 1.0));
        }

        /**
         * Add the first name field and label.
         *
         * @param builder The builder to use.
         */
        private void addFirstNameField(I18nPanelBuilder builder) {
            builder.addI18nLabel("author.firstname", builder.gbcSet(0, 1));

            fieldFirstName = builder.add(new JTextField(10),
                    builder.gbcSet(1, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
            SwingUtils.addFieldLengthLimit(fieldFirstName, NAMES_LIMIT);
            fieldFirstName.setEnabled(false);
        }

        /**
         * Add the name field and label.
         *
         * @param builder The builder to use.
         */
        private void addNameField(I18nPanelBuilder builder) {
            builder.addI18nLabel("author.name", builder.gbcSet(0, 2));

            fieldName = builder.add(new JTextField(10),
                    builder.gbcSet(1, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
            SwingUtils.addFieldLengthLimit(fieldName, NAMES_LIMIT);
            fieldName.setEnabled(false);
        }

        /**
         * Add the country field and label.
         *
         * @param builder The builder to use.
         */
        private void addCountryField(I18nPanelBuilder builder) {
            builder.addI18nLabel("author.country", builder.gbcSet(0, 3));

            countriesModel = new DataContainerCachedComboBoxModel<SimpleData>(
                    Managers.getManager(IBeansManager.class).<DataContainer<SimpleData>>getBean("countriesService"));

            comboCountries = builder.addComboBox(countriesModel, builder.gbcSet(1, 3));
            comboCountries.setEnabled(false);

            buttonNewCountry = builder.addButton(Managers.getManager(IResourceManager.class).getAction("newCountryAction"),
                    builder.gbcSet(2, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
            buttonNewCountry.setEnabled(false);
        }

        /**
         * Add the note field and label.
         *
         * @param builder The builder to use.
         */
        private void addNoteField(I18nPanelBuilder builder) {
            builder.addI18nLabel("author.note", builder.gbcSet(0, 4));

            notesModel = new NotesComboBoxModel(daoNotes);

            comboNote = builder.addComboBox(notesModel,
                    builder.gbcSet(1, 4, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, GridBagUtils.REMAINDER, 1.0, 1.0));
            comboNote.setEnabled(false);
        }

        @Override
        public void fillFormBean(FormBean formBean) {
            IPersonFormBean fb = (IPersonFormBean) formBean;

            fb.setName(fieldName.getText());
            fb.setFirstName(fieldFirstName.getText());
            fb.setNote(notesModel.getSelectedNote());
            fb.setCountry(countriesModel.getSelectedData());
        }

        @Override
        public void setCurrent(Object object) {
            Person author = (Person) object;

            authorsPanel.setTitle(author.getDisplayableText());

            fieldFirstName.setText(author.getFirstName());
            fieldName.setText(author.getName());
            countriesModel.setSelectedItem(author.getTheCountry());
            notesModel.setSelectedItem(author.getNote());
        }

        @Override
        protected void validate(Collection<JThequeError> errors) {
            ValidationUtils.rejectIfEmpty(fieldName.getText(), "author.name", errors);
            ValidationUtils.rejectIfEmpty(fieldFirstName.getText(), "author.firstname", errors);

            ValidationUtils.rejectIfLongerThan(fieldName.getText(), "author.name", 100, errors);
            ValidationUtils.rejectIfLongerThan(fieldFirstName.getText(), "author.firstname", 100, errors);

            ValidationUtils.rejectIfNothingSelected(notesModel, "author.note", errors);
            ValidationUtils.rejectIfNothingSelected(countriesModel, "author.country", errors);
        }

        @Override
        public void setEnabled(boolean enabled) {
            fieldFirstName.setEnabled(enabled);
            fieldName.setEnabled(enabled);
            buttonNewCountry.setEnabled(enabled);
            comboNote.setEnabled(enabled);
            comboCountries.setEnabled(enabled);
        }

        @Override
        public void clear() {
            authorsPanel.setTitle(getMessage("author.panel.author.title"));

            fieldFirstName.setText("");
            fieldName.setText("");
            countriesModel.setSelectedItem(null);
            notesModel.setSelectedItem(null);
        }
    }
}