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
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.books.view.controllers.able.IAuthorController;
import org.jtheque.books.view.fb.AuthorFormBean;
import org.jtheque.books.view.fb.IAuthorFormBean;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.books.view.toolbar.JPanelAuthorToolBar;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.components.panels.PrincipalDataPanel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.JThequeTreeCellRenderer;
import org.jtheque.primary.view.impl.sort.SortManager;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.Collection;

/**
 * The principal view for authors.
 *
 * @author Baptiste Wicht
 */
public final class AuthorView extends PrincipalDataPanel<IAuthorsModel> implements IAuthorView {
    private static final long serialVersionUID = -7634054000892878297L;

    private JXTitledPanel authorsPanel;

    private JTextField fieldFirstName;
    private JTextField fieldName;
    private DataContainerCachedComboBoxModel<Country> countriesModel;
    private NotesComboBoxModel notesModel;

    private JXTree treeAuthors;

    private final SortManager sorter = new SortManager();

    private Action newCountryAction;
    private Action sortByCountry;
    private Action sortByNote;
    private JPanelAuthorToolBar toolBar;

    private JComboBox comboCountries;
    private JComboBox comboNote;

    @Resource
    private ICountriesService countriesService;

    @Resource
    private IAuthorController authorController;
    private JButton buttonNewCountry;
    private static final int NAMES_LIMIT = 80;
    private static final double A_QUARTER = 0.25;

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        buildListPanel(builder);
        buildSortPanel(builder);
        buildAuthorsPanel(builder);

        addListeners();
    }

    /**
     * Add the listeners.
     */
    private void addListeners() {
        treeAuthors.addTreeSelectionListener(authorController);

        getModel().addDisplayListListener(this);
        getModel().addCurrentObjectListener(this);
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

        PanelBuilder builder = new PanelBuilder();

        setTreeModel(sorter.createInitialModel(IAuthorsService.DATA_TYPE));

        treeAuthors = new JXTree(getTreeModel());
        treeAuthors.setCellRenderer(new JThequeTreeCellRenderer());
        treeAuthors.putClientProperty("JTree.lineStyle", "None");

        builder.addScrolled(treeAuthors, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

        panelList.setContentContainer(builder.getPanel());

        parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, A_QUARTER, 1.0));
    }

    /**
     * Build the internal panel for the sorts.
     *
     * @param parent The parent builder.
     */
    private void buildSortPanel(PanelBuilder parent) {
        JXTitledPanel panelTri = new JThequeTitledPanel("author.panel.sort.title");
        panelTri.setBorder(new DropShadowBorder());
        panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        JXHyperlink linkSortCountry = builder.add(new JXHyperlink(sortByCountry),
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));
        linkSortCountry.setClickedColor(linkSortCountry.getUnclickedColor());

        JXHyperlink linkSortNote = builder.add(new JXHyperlink(sortByNote),
                builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));
        linkSortNote.setClickedColor(linkSortNote.getUnclickedColor());

        panelTri.setContentContainer(builder.getPanel());

        parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, A_QUARTER, 0.0));
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

        PanelBuilder builder = new PanelBuilder();

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
    private void addFirstNameField(PanelBuilder builder) {
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
    private void addNameField(PanelBuilder builder) {
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
    private void addCountryField(PanelBuilder builder) {
        builder.addI18nLabel("author.country", builder.gbcSet(0, 3));

        countriesModel = new DataContainerCachedComboBoxModel<Country>(countriesService);

        comboCountries = builder.addComboBox(countriesModel, builder.gbcSet(1, 3));
        comboCountries.setEnabled(false);

        buttonNewCountry = builder.addButton(newCountryAction,
                builder.gbcSet(2, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
        buttonNewCountry.setEnabled(false);
    }

    /**
     * Add the note field and label.
     *
     * @param builder The builder to use.
     */
    private void addNoteField(PanelBuilder builder) {
        builder.addI18nLabel("author.note", builder.gbcSet(0, 4));

        notesModel = new NotesComboBoxModel();

        comboNote = builder.addComboBox(notesModel,
                builder.gbcSet(1, 4, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, GridBagUtils.REMAINDER, 1.0, 1.0));
        comboNote.setEnabled(false);
    }

    @Override
    public String getDataType() {
        return IAuthorsService.DATA_TYPE;
    }

    @Override
    protected JXTree getTree() {
        return treeAuthors;
    }

    @Override
    public JComponent getImpl() {
        return this;
    }

    @Override
    public Integer getPosition() {
        return 1;
    }

    @Override
    public String getTitleKey() {
        return "data.titles.author";
    }

    @Override
    public ToolbarView getToolbarView() {
        return toolBar;
    }

    @Override
    public boolean isEnabled() {
        return getModel().isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        getModel().setEnabled(enabled);

        fieldFirstName.setEnabled(enabled);
        fieldName.setEnabled(enabled);
        buttonNewCountry.setEnabled(enabled);
        comboNote.setEnabled(enabled);
        comboCountries.setEnabled(enabled);
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        setCurrentAuthor((Person) event.getObject());
    }

    /**
     * Set the current author.
     *
     * @param author The current author.
     */
    private void setCurrentAuthor(Person author) {
        authorsPanel.setTitle(author.getDisplayableText());

        fieldFirstName.setText(author.getFirstName());
        fieldName.setText(author.getName());
        countriesModel.setSelectedItem(author.getTheCountry());
        notesModel.setSelectedItem(author.getNote());
    }

    @Override
    public void clear() {
        authorsPanel.setTitle(getMessage("author.panel.author.title"));

        fieldFirstName.setText("");
        fieldName.setText("");
        countriesModel.setSelectedItem(null);
        notesModel.setSelectedItem(null);
    }

    /**
     * Fill a <code>AuthorFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>AuthorFormBean</code>.
     */
    @Override
    public IAuthorFormBean fillFilmFormBean() {
        IAuthorFormBean fb = new AuthorFormBean();

        fb.setName(fieldName.getText());
        fb.setFirstName(fieldFirstName.getText());
        fb.setNote(notesModel.getSelectedNote());
        fb.setCountry(countriesModel.getSelectedData());

        return fb;
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
    public JComponent getComponent() {
        return this;
    }

    /**
     * Set the toolbar.
     *
     * @param toolBar The toolbar.
     */
    public void setToolBar(JPanelAuthorToolBar toolBar) {
        this.toolBar = toolBar;
    }

    /**
     * Set the action to sort by note.
     *
     * @param sortByNote The action to sort by note.
     */
    public void setSortByNote(Action sortByNote) {
        this.sortByNote = sortByNote;
    }

    /**
     * Set the action to sort by country.
     *
     * @param sortByCountry The action to sort by country.
     */
    public void setSortByCountry(Action sortByCountry) {
        this.sortByCountry = sortByCountry;
    }

    /**
     * Set the action to create a new country.
     *
     * @param newCountryAction The action to create a new country.
     */
    public void setNewCountryAction(Action newCountryAction) {
        this.newCountryAction = newCountryAction;
    }
}