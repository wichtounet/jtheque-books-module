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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.books.view.fb.IBookFormBean;
import org.jtheque.books.view.models.list.AuthorsListModel;
import org.jtheque.books.view.models.list.SimpleAuthorsModel;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ISagasService;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultFormatter;
import java.text.NumberFormat;
import java.util.Collection;

/**
 * The panel "infos" of the books view.
 *
 * @author Baptiste Wicht
 */
public final class JPanelBookInfos extends JPanel implements IInfosPanel {
    private static final long serialVersionUID = 6833310794095034469L;

    private DataContainerCachedComboBoxModel<Editor> editorsModel;
    private DataContainerCachedComboBoxModel<Language> languagesModel;
    private DataContainerCachedComboBoxModel<Saga> sagasModel;

    private JComboBox comboEditors;
    private JComboBox comboLanguages;
    private JComboBox comboSagas;

    private JFormattedTextField fieldYear;
    private JFormattedTextField fieldPages;

    private final Action newEditorAction;
    private final Action newLanguageAction;
    private final Action newSagaAction;

    private final Action addAuthorAction;
    private final Action removeAuthorAction;

    private JList authorsList;
    private JList authorsBookList;

    private AuthorsListModel authorsModel;
    private SimpleAuthorsModel authorsBookModel;

    private JButton buttonNewEditor;
    private JButton buttonNewLanguage;
    private JButton buttonNewSaga;

    @Resource
    private ILanguagesService languagesService;

    @Resource
    private IEditorsService editorsService;

    @Resource
    private ISagasService sagasService;
    private static final double AN_HALF = 0.5;

    /**
     * Construct a new JPanelBookInfos.
     *
     * @param newEditorAction    The action to create a new editor.
     * @param newLanguageAction  The action to create a new language.
     * @param newSagaAction      The action to create a new saga.
     * @param addAuthorAction    The action to add an author.
     * @param removeAuthorAction The action to add an author.
     */
    public JPanelBookInfos(Action newEditorAction, Action newLanguageAction, Action newSagaAction, Action addAuthorAction, Action removeAuthorAction) {
        super();

        this.newEditorAction = newEditorAction;
        this.newLanguageAction = newLanguageAction;
        this.newSagaAction = newSagaAction;
        this.addAuthorAction = addAuthorAction;
        this.removeAuthorAction = removeAuthorAction;
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        addEditorField(builder);
        addLanguageField(builder);
        addSagaField(builder);
        addFormattedFields(builder);
        addAuthorsPanel(builder);
    }

    /**
     * Add the field to choose the editor of the book.
     *
     * @param builder The builder of the panel.
     */
    private void addEditorField(PanelBuilder builder) {
        builder.addI18nLabel("book.editor", builder.gbcSet(0, 0));

        editorsModel = new DataContainerCachedComboBoxModel<Editor>(editorsService);

        comboEditors = builder.addComboBox(editorsModel, builder.gbcSet(1, 0));
        comboEditors.setEnabled(false);

        buttonNewEditor = builder.addButton(newEditorAction, builder.gbcSet(2, 0, GridBagUtils.NONE, GridBagUtils.REMAINDER, 1));
        buttonNewEditor.setEnabled(false);
    }

    /**
     * Add the field to choose the language of the book.
     *
     * @param builder The builder of the panel.
     */
    private void addLanguageField(PanelBuilder builder) {
        builder.addI18nLabel("book.language", builder.gbcSet(0, 1));

        languagesModel = new DataContainerCachedComboBoxModel<Language>(languagesService);

        comboLanguages = builder.addComboBox(languagesModel, builder.gbcSet(1, 1));
        comboLanguages.setEnabled(false);

        buttonNewLanguage = builder.addButton(newLanguageAction, builder.gbcSet(2, 1, GridBagUtils.NONE, GridBagUtils.REMAINDER, 1));
        buttonNewLanguage.setEnabled(false);
    }

    /**
     * Add the field to choose the saga of the book.
     *
     * @param builder The builder of the panel.
     */
    private void addSagaField(PanelBuilder builder) {
        builder.addI18nLabel("book.saga", builder.gbcSet(0, 2));

        sagasModel = new DataContainerCachedComboBoxModel<Saga>(sagasService);

        comboSagas = builder.addComboBox(sagasModel, builder.gbcSet(1, 2));
        comboSagas.setEnabled(false);

        buttonNewSaga = builder.addButton(newSagaAction, builder.gbcSet(2, 2, GridBagUtils.NONE, GridBagUtils.REMAINDER, 1));
        buttonNewSaga.setEnabled(false);
    }

    /**
     * Add the fields to choose the year and pages of the book.
     *
     * @param builder The builder of the panel.
     */
    private void addFormattedFields(PanelBuilder builder) {
        builder.addI18nLabel("book.year", builder.gbcSet(0, 3));

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setParseIntegerOnly(true);
        format.setGroupingUsed(false);

        fieldYear = builder.add(new JFormattedTextField(format), builder.gbcSet(1, 3));
        fieldYear.setColumns(5);
        ((DefaultFormatter) fieldYear.getFormatter()).setAllowsInvalid(false);
        SwingUtils.addFieldLengthLimit(fieldYear, 4);
        fieldYear.setEnabled(false);

        builder.addI18nLabel("book.pages", builder.gbcSet(0, 4));

        fieldPages = builder.add(new JFormattedTextField(format), builder.gbcSet(1, 4));
        fieldPages.setColumns(5);
        fieldPages.setEnabled(false);
    }

    /**
     * Add the panel to choose the authors of the book.
     *
     * @param parent The builder of the panel.
     */
    private void addAuthorsPanel(PanelBuilder parent) {
        PanelBuilder builder = new PanelBuilder();

        authorsModel = new AuthorsListModel();

        authorsList = new JList(authorsModel);
        authorsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        builder.addScrolled(authorsList, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1, GridBagUtils.REMAINDER, AN_HALF, 1.0));

        addAuthorAction.setEnabled(false);
        builder.addButton(addAuthorAction, builder.gbcSet(1, 0));

        removeAuthorAction.setEnabled(false);
        builder.addButton(removeAuthorAction, builder.gbcSet(1, 1));

        authorsBookModel = new SimpleAuthorsModel();

        authorsBookList = new JList(authorsBookModel);
        authorsBookList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        builder.addScrolled(authorsBookList, builder.gbcSet(2, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1, GridBagUtils.REMAINDER, AN_HALF, 1.0));

        parent.add(builder.getPanel(), parent.gbcSet(0, 5, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 3, 1, 1.0, 1.0));
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        Book book = (Book) event.getObject();

        comboEditors.setSelectedItem(book.getTheEditor());
        comboLanguages.setSelectedItem(book.getTheLanguage());
        comboSagas.setSelectedItem(book.getTheSaga());

        fieldYear.setText(Integer.toString(book.getYear()));
        fieldPages.setText(Integer.toString(book.getPages()));

        authorsBookModel.removeAllElements();
        authorsModel.reload();

        Collection<Person> authors = book.getAuthors();

        for (Person author : authors) {
            authorsModel.removeElement(author);
            authorsBookModel.addElement(author);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        comboEditors.setEnabled(enabled);
        comboLanguages.setEnabled(enabled);
        comboSagas.setEnabled(enabled);

        fieldYear.setEnabled(enabled);
        fieldPages.setEnabled(enabled);

        buttonNewLanguage.setEnabled(enabled);
        buttonNewEditor.setEnabled(enabled);
        buttonNewSaga.setEnabled(enabled);
        addAuthorAction.setEnabled(enabled);
        removeAuthorAction.setEnabled(enabled);

        authorsList.setEnabled(enabled);
        authorsBookList.setEnabled(enabled);
    }

    @Override
    public DefaultListModel getAuthorsModel() {
        return authorsModel;
    }

    @Override
    public DefaultListModel getAuthorsBookModel() {
        return authorsBookModel;
    }

    @Override
    public int[] getSelectedAuthorsIndexes() {
        return authorsList.getSelectedIndices();
    }

    @Override
    public int[] getSelectedAuthorsBookIndexes() {
        return authorsBookList.getSelectedIndices();
    }

    @Override
    public void fillFormBean(IBookFormBean formBean) {
        formBean.setTheEditor(editorsModel.getSelectedData());
        formBean.setTheLanguage(languagesModel.getSelectedData());
        formBean.setTheSaga(sagasModel.getSelectedData());
        formBean.setYear(Integer.parseInt(fieldYear.getText()));
        formBean.setPages(Integer.parseInt(fieldPages.getText()));
        formBean.setAuthors(authorsBookModel.getAuthors());
    }

    @Override
    public void clear() {
        comboEditors.setSelectedItem(null);
        comboLanguages.setSelectedItem(null);
        comboSagas.setSelectedItem(null);

        fieldYear.setText("0");
        fieldPages.setText("0");

        authorsBookModel.removeAllElements();
        authorsModel.reload();
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfNothingSelected(languagesModel, "book.language", errors);

        ValidationUtils.rejectIfNotNumerical(fieldYear.getText(), "book.year", errors);
        ValidationUtils.rejectIfNotNumerical(fieldPages.getText(), "book.pages", errors);
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}