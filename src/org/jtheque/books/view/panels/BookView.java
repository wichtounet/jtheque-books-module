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
import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.books.view.able.IBookView;
import org.jtheque.books.view.able.IOthersPanel;
import org.jtheque.books.view.actions.AcSortBooks;
import org.jtheque.books.view.actions.DisplayBeanViewAction;
import org.jtheque.books.view.fb.BookFormBean;
import org.jtheque.books.view.fb.IBookFormBean;
import org.jtheque.books.view.models.BooksModel;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.books.view.toolbar.JPanelBookToolBar;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.TabTitleUpdater;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.components.panels.PrincipalDataPanel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.renderers.JThequeTreeCellRenderer;
import org.jtheque.primary.view.impl.sort.SortManager;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionListener;
import java.awt.Font;
import java.awt.Insets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The principal view for authors.
 *
 * @author Baptiste Wicht
 */
public final class BookView extends PrincipalDataPanel<IBooksModel> implements IBookView {
    private JXTitledPanel booksPanel;
    private JTextField fieldTitle;
    private DataContainerCachedComboBoxModel<Kind> kindsModel;
    private DataContainerCachedComboBoxModel<Type> typesModel;
    private JComboBox comboKind;
    private JComboBox comboType;
    private JXTree treeBooks;

    private final IInfosPanel panelInfos;
    private final IOthersPanel panelOthers;

    private JPanelBookToolBar toolBar;

    private JButton buttonNewType;
    private JButton buttonNewKind;

    private final SortManager sorter = new SortManager();


    private static final int TITLE_MAX_LENGTH = 150;
    private static final double A_QUARTER = 0.25;

    public BookView(IInfosPanel panelInfos, IOthersPanel panelOthers) {
        super();

        setModel(new BooksModel());

        this.panelInfos = panelInfos;
        this.panelOthers = panelOthers;

        build();
    }

    /**
     * Build the view.
     */
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        buildListPanel(builder);
        buildSortPanel(builder);
        buildBooksPanel(builder);

        addListeners();
    }

    /**
     * Add listeners to and from view.
     */
    private void addListeners() {
        treeBooks.addTreeSelectionListener(Managers.getManager(IBeansManager.class).<TreeSelectionListener>getBean("bookController"));

        getModel().addViewStateListener(this);

        getModel().addCurrentObjectListener(this);
        getModel().addCurrentObjectListener(panelInfos);
        getModel().addCurrentObjectListener(panelOthers);
    }

    /**
     * Build the internal panel for the list of books.
     *
     * @param parent The parent builder.
     */
    private void buildListPanel(PanelBuilder parent) {
        JXTitledPanel panelList = new JThequeTitledPanel("book.panel.list.title");
        panelList.setBorder(new DropShadowBorder());
        panelList.setTitleFont(panelList.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        setTreeModel(sorter.createInitialModel(IBooksService.DATA_TYPE));

        getModel().addDisplayListListener(this);

        treeBooks = new JXTree(getTreeModel());
        treeBooks.setCellRenderer(new JThequeTreeCellRenderer());
        treeBooks.putClientProperty("JTree.lineStyle", "None");

        builder.addScrolled(treeBooks, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

        panelList.setContentContainer(builder.getPanel());

        parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, A_QUARTER, 1.0));
    }

    /**
     * Build the internal panel for the sorts.
     *
     * @param parent The parent builder.
     */
    private static void buildSortPanel(PanelBuilder parent) {
        JXTitledPanel panelTri = new JThequeTitledPanel("book.panel.sort.title");
        panelTri.setBorder(new DropShadowBorder());
        panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        addSortAction(builder, 0, "book.actions.sort.editor", IEditorsService.DATA_TYPE);
        addSortAction(builder, 1, "book.actions.sort.kind", IKindsService.DATA_TYPE);
        addSortAction(builder, 2, "book.actions.sort.type", ITypesService.DATA_TYPE);
        addSortAction(builder, 3, "book.actions.sort.year", "Year");

        panelTri.setContentContainer(builder.getPanel());

        parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, A_QUARTER, 0.0));
    }

    private static void addSortAction(PanelBuilder builder, int row, String key, String dataType) {
        JXHyperlink sortLink = builder.add(new JXHyperlink(new AcSortBooks(key, dataType)),
                    builder.gbcSet(0, row, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        sortLink.setClickedColor(sortLink.getUnclickedColor());
    }

    /**
     * Build the internal panel for the book.
     *
     * @param parent The parent builder.
     */
    private void buildBooksPanel(PanelBuilder parent) {
        booksPanel = new JThequeTitledPanel("book.panel.book.title");
        booksPanel.setBorder(new DropShadowBorder());
        booksPanel.setTitleFont(booksPanel.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        toolBar = new JPanelBookToolBar();

        builder.add(toolBar,
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));

        builder.addI18nLabel("book.title", builder.gbcSet(0, 1));

        fieldTitle = builder.add(new JTextField(10),
                builder.gbcSet(1, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
        SwingUtils.addFieldLengthLimit(fieldTitle, 100);
        fieldTitle.setEnabled(false);

        addKindFields(builder);
        addTypeFields(builder);
        addTab(builder);

        booksPanel.setContentContainer(builder.getPanel());

        parent.add(booksPanel, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1, 2, 1 - A_QUARTER, 1.0));
    }

    /**
     * Add the field for kind choice.
     *
     * @param builder The builder of the panel.
     */
    private void addKindFields(PanelBuilder builder) {
        builder.addI18nLabel("book.kind", builder.gbcSet(0, 2));

        kindsModel = new DataContainerCachedComboBoxModel<Kind>(
                Managers.getManager(IBeansManager.class).<DataContainer<Kind>>getBean("kindsService"));

        comboKind = new JComboBox(kindsModel);

        comboKind = builder.addComboBox(kindsModel, builder.gbcSet(1, 2));
        comboKind.setEnabled(false);

        buttonNewKind = builder.addButton(new DisplayBeanViewAction("kind.actions.new", "kindView"),
                builder.gbcSet(2, 2, GridBagUtils.NONE, GridBagUtils.REMAINDER, 1));
        buttonNewKind.setEnabled(false);
    }

    /**
     * Add the fields for the type.
     *
     * @param builder The builder of the view.
     */
    private void addTypeFields(PanelBuilder builder) {
        builder.addI18nLabel("book.type", builder.gbcSet(0, 3));

        typesModel = new DataContainerCachedComboBoxModel<Type>(
                Managers.getManager(IBeansManager.class).<DataContainer<Type>>getBean("typesService"));

        comboType = builder.addComboBox(typesModel, builder.gbcSet(1, 3));
        comboType.setEnabled(false);

        buttonNewType = builder.addButton(new DisplayBeanViewAction("type.actions.new", "typeView"),
                builder.gbcSet(2, 3, GridBagUtils.NONE, GridBagUtils.REMAINDER, 1));
        buttonNewType.setEnabled(false);
    }

    /**
     * Add the tabbed pane.
     *
     * @param builder The builder of the view.
     */
    private void addTab(PanelBuilder builder) {
        Insets oldInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));

        JTabbedPane tabInfos = new JTabbedPane();

        UIManager.put("TabbedPane.contentBorderInsets", oldInsets);

        Map<JComponent, String> components = new HashMap<JComponent, String>(3);

        components.put(panelInfos.getImpl(), "book.panel.infos.title");
        tabInfos.addTab(getMessage("book.panel.general.title"), panelInfos.getImpl());

        components.put(panelOthers.getImpl(), "book.panel.others.title");
        tabInfos.addTab(getMessage("book.panel.others.title"), panelOthers.getImpl());

        builder.add(tabInfos,
                builder.gbcSet(0, 4, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, GridBagUtils.REMAINDER, 1.0, 1.0));

        Managers.getManager(ILanguageManager.class).addInternationalizable(new TabTitleUpdater(tabInfos, components));
    }

    @Override
    public String getDataType() {
        return IBooksService.DATA_TYPE;
    }

    @Override
    protected JXTree getTree() {
        return treeBooks;
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
        return "data.titles.book";
    }

    @Override
    public ToolbarView getToolbarView() {
        return toolBar;
    }

    @Override
    public void stateChanged() {
        setEnabled(getModel().isEnabled());
    }

    @Override
    public boolean isEnabled() {
        return getModel().isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        fieldTitle.setEnabled(enabled);
        comboKind.setEnabled(enabled);
        comboType.setEnabled(enabled);

        buttonNewKind.setEnabled(enabled);
        buttonNewType.setEnabled(enabled);

        panelInfos.setEnabled(enabled);
        panelOthers.setEnabled(enabled);
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        Book book = (Book) event.getObject();

        booksPanel.setTitle(book.getDisplayableText());

        fieldTitle.setText(book.getTitle());

        if (book.getTheKind() != null) {
            kindsModel.setSelectedItem(book.getTheKind());
        }

        if (book.getTheType() != null) {
            typesModel.setSelectedItem(book.getTheType());
        }
    }

    @Override
    public IBookFormBean fillBookFormBean() {
        IBookFormBean formBean = new BookFormBean();

        formBean.setTitle(fieldTitle.getText());
        formBean.setTheKind(kindsModel.getSelectedData());
        formBean.setTheType(typesModel.getSelectedData());

        panelInfos.fillFormBean(formBean);
        panelOthers.fillFormBean(formBean);

        return formBean;
    }

    @Override
    public IInfosPanel getPanelInfos() {
        return panelInfos;
    }

    @Override
    public void clear() {
        booksPanel.setTitle(getMessage("book.panel.book.title"));

        fieldTitle.setText("");

        kindsModel.setSelectedItem(null);
        typesModel.setSelectedItem(null);

        panelInfos.clear();
        panelOthers.clear();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfEmpty(fieldTitle.getText(), "book.generals.title", errors);
        ValidationUtils.rejectIfNothingSelected(kindsModel, "book.kind", errors);
        ValidationUtils.rejectIfNothingSelected(typesModel, "book.type", errors);

        ValidationUtils.rejectIfLongerThan(fieldTitle.getText(), "book.generals.title", TITLE_MAX_LENGTH, errors);

        panelInfos.validate(errors);
        panelOthers.validate(errors);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}