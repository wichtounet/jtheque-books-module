package org.jtheque.books.view.frames;

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

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.able.IAutoView;
import org.jtheque.books.view.actions.auto.AcSearch;
import org.jtheque.books.view.actions.auto.AcValidateAutoAddView;
import org.jtheque.books.view.models.AutoAddModel;
import org.jtheque.books.view.models.able.IAutoAddModel;
import org.jtheque.books.view.models.list.LanguagesListModel;
import org.jtheque.errors.able.IError;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Frame;
import java.util.Collection;

/**
 * User interface to automatically add books.
 *
 * @author Baptiste Wicht
 */
public final class AutoView extends SwingBuildedDialogView<IAutoAddModel> implements IAutoView {
    private JTextField fieldTitle;
    private JList listLanguages;
    private JList listBooks;

    private DefaultListModel modelListBooks;

    private int phase = PHASE_1;

    /**
     * Construct a new JFrameAutoAdd.
     *
     * @param frame The parent frame.
     */
    public AutoView(Frame frame) {
        super(frame);

        build();
    }

    @Override
    protected void initView() {
        setModel(new AutoAddModel());
        setTitleKey("auto.view.title");
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addI18nLabel("auto.view.title.film", builder.gbcSet(0, 0));

        Action searchAction = new AcSearch();

        fieldTitle = builder.add(new JTextField(), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, 2, 1));
        SwingUtils.addFieldValidateAction(fieldTitle, searchAction);

        listLanguages = new JList(new LanguagesListModel());
        listLanguages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        builder.addScrolled(listLanguages, builder.gbcSet(0, 1, GridBagUtils.BOTH));

        builder.addButton(searchAction, builder.gbcSet(1, 1));

        modelListBooks = new DefaultListModel();

        listBooks = new JList(modelListBooks);
        listBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        builder.addScrolled(listBooks, builder.gbcSet(2, 1, GridBagUtils.HORIZONTAL));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                new AcValidateAutoAddView(), getCloseAction("generic.actions.cancel"));
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("title".equals(message)) {
            fieldTitle.setText((String) value);
        }
    }

    @Override
    public JTextField getFieldTitle() {
        return fieldTitle;
    }

    @Override
    public DefaultListModel getModelListBooks() {
        return modelListBooks;
    }

    @Override
    public BookResult getSelectedBook() {
        return (BookResult) listBooks.getSelectedValue();
    }

    @Override
    public String getSelectedLanguage() {
        return (String) listLanguages.getSelectedValue();
    }

    @Override
    public boolean validateContent(int phase) {
        this.phase = phase;

        return validateContent();
    }

    @Override
    protected void validate(Collection<IError> errors) {
        if (phase == PHASE_1) {
            ValidationUtils.rejectIfEmpty(fieldTitle.getText(), "auto.view.title.film", errors);
            ValidationUtils.rejectIfNothingSelected(listLanguages, "auto.view.languages", errors);
        } else if (phase == PHASE_2) {
            ValidationUtils.rejectIfNothingSelected(listBooks, "auto.view.book", errors);
        }
    }
}
