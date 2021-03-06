package org.jtheque.books.view.panels;

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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.books.view.able.IBookView;
import org.jtheque.books.view.able.IOthersPanel;
import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.books.view.actions.AcSortBooks;
import org.jtheque.books.view.fb.BookFormBean;
import org.jtheque.books.view.models.BooksModel;
import org.jtheque.books.view.models.able.IBooksModel;
import org.jtheque.books.view.toolbar.JPanelBookToolBar;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.TabTitleUpdater;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.view.impl.components.panels.AbstractPrincipalDataPanel;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import javax.annotation.PostConstruct;
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
public final class BookView extends AbstractPrincipalDelegatedView<AbstractPrincipalDataPanel<IBooksModel>> implements IBookView {
    public BookView() {
        super(0, "data.titles.book");
    }

    @PostConstruct
    public void init() {
        buildInEDT();

        getModel().addCurrentObjectListener(this);
    }

    @Override
    public IBooksModel getModel() {
        return (IBooksModel) super.getModel();
    }

    @Override
    public JThequeTreeModel getTreeModel() {
        return getImplementationView().getTreeModel();
    }

    @Override
    protected void buildDelegatedView() {
        BookViewImpl impl = new BookViewImpl();
        setView(impl);
        impl.build();
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        getImplementationView().setCurrent(event.getObject());
    }

    @Override
    public IBookFormBean fillBookFormBean() {
        IBookFormBean formBean = new BookFormBean();

        getImplementationView().fillFormBean(formBean);

        return formBean;
    }

    private static final class BookViewImpl extends AbstractPrincipalDataPanel<IBooksModel> {
        private static final int TITLE_MAX_LENGTH = 150;
        private static final double A_QUARTER = 0.25;

        private JXTitledPanel booksPanel;
        private JTextField fieldTitle;
        private DataContainerCachedComboBoxModel<SimpleData> kindsModel;
        private DataContainerCachedComboBoxModel<SimpleData> typesModel;
        private JComboBox comboKind;
        private JComboBox comboType;

        private JButton buttonNewType;
        private JButton buttonNewKind;

        private final IInfosPanel panelInfos;
        private final IOthersPanel panelOthers;

        private BookViewImpl() {
            super(IBooksService.DATA_TYPE);

            setModel(new BooksModel());

            panelInfos = Managers.getManager(IBeansManager.class).getBean("booksPanelInfos");
            panelOthers = Managers.getManager(IBeansManager.class).getBean("booksPanelOthers");
        }

        /**
         * Build the view.
         */
        private void build() {
            PanelBuilder builder = new JThequePanelBuilder(this);

            buildListPanel(builder);
            buildSortPanel(builder);
            buildBooksPanel(builder);

            getTree().addTreeSelectionListener(Managers.getManager(IBeansManager.class).<TreeSelectionListener>getBean("bookController"));

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

            PanelBuilder builder = new JThequePanelBuilder();

            setTreeModel(getSorter().createInitialModel(IBooksService.DATA_TYPE));

            getModel().addDisplayListListener(this);

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
            JXTitledPanel panelTri = new JThequeTitledPanel("book.panel.sort.title");
            panelTri.setBorder(new DropShadowBorder());
            panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

            PanelBuilder builder = new JThequePanelBuilder();

            addSortAction(builder, 0, "book.actions.sort.editor", IEditorsService.DATA_TYPE);
            addSortAction(builder, 1, "book.actions.sort.kind", SimpleData.DataType.KIND.getDataType());
            addSortAction(builder, 2, "book.actions.sort.type", SimpleData.DataType.TYPE.getDataType());
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

            I18nPanelBuilder builder = new JThequePanelBuilder();

            JPanelBookToolBar toolBar = new JPanelBookToolBar();

            setToolBar(toolBar);

            builder.add(toolBar,
                    builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));

            builder.addI18nLabel("book.title", builder.gbcSet(0, 1));

            fieldTitle = builder.add(new JTextField(10),
                    builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
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
        private void addKindFields(I18nPanelBuilder builder) {
            builder.addI18nLabel("book.kind", builder.gbcSet(0, 2));

            kindsModel = new DataContainerCachedComboBoxModel<SimpleData>(
                    Managers.getManager(IBeansManager.class).<DataContainer<SimpleData>>getBean("kindsService"));

            comboKind = new JComboBox(kindsModel);

            comboKind = builder.addComboBox(kindsModel, builder.gbcSet(1, 2));
            comboKind.setEnabled(false);

            buttonNewKind = builder.addButton(Managers.getManager(IResourceManager.class).getAction("newKindAction"),
                    builder.gbcSet(2, 2, GridBagUtils.NONE, GridBagUtils.REMAINDER, 1));
            buttonNewKind.setEnabled(false);
        }

        /**
         * Add the fields for the type.
         *
         * @param builder The builder of the view.
         */
        private void addTypeFields(I18nPanelBuilder builder) {
            builder.addI18nLabel("book.type", builder.gbcSet(0, 3));

            typesModel = new DataContainerCachedComboBoxModel<SimpleData>(
                    Managers.getManager(IBeansManager.class).<DataContainer<SimpleData>>getBean("typesServices"));

            comboType = builder.addComboBox(typesModel, builder.gbcSet(1, 3));
            comboType.setEnabled(false);

            buttonNewType = builder.addButton(Managers.getManager(IResourceManager.class).getAction("newTypeAction"),
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
        public void fillFormBean(FormBean fb) {
            IBookFormBean formBean = (IBookFormBean) fb;

            formBean.setTitle(fieldTitle.getText());
            formBean.setTheKind(kindsModel.getSelectedData());
            formBean.setTheType(typesModel.getSelectedData());

            panelInfos.fillFormBean(formBean);
            panelOthers.fillFormBean(formBean);
        }

        @Override
        public void setCurrent(Object object) {
            Book book = (Book) object;

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
        protected void validate(Collection<JThequeError> errors) {
            ValidationUtils.rejectIfEmpty(fieldTitle.getText(), "book.generals.title", errors);
            ValidationUtils.rejectIfNothingSelected(kindsModel, "book.kind", errors);
            ValidationUtils.rejectIfNothingSelected(typesModel, "book.type", errors);

            ValidationUtils.rejectIfLongerThan(fieldTitle.getText(), "book.generals.title", TITLE_MAX_LENGTH, errors);

            panelInfos.validate(errors);
            panelOthers.validate(errors);
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
        public void clear() {
            booksPanel.setTitle(getMessage("book.panel.book.title"));

            fieldTitle.setText("");

            kindsModel.setSelectedItem(null);
            typesModel.setSelectedItem(null);

            panelInfos.clear();
            panelOthers.clear();
        }
    }
}
