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
import org.jtheque.books.view.able.IOthersPanel;
import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.NoteComboRenderer;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import java.util.Collection;

/**
 * The panel "other" of the books view.
 *
 * @author Baptiste Wicht
 */
public final class JPanelBookOthers extends JPanel implements IOthersPanel {
    private NotesComboBoxModel modelNote;
    private JComboBox comboNote;
    private JTextArea areaResume;
    private JTextField fieldISBN10;
    private JTextField fieldISBN13;

    private static final int ISBN_MAX_LENGTH = 20;
    private static final int RESUME_MAX_LENGTH = 2000;

    public JPanelBookOthers() {
        super();

        build();
    }

    /**
     * Build the view.
     */
    private void build() {
        I18nPanelBuilder builder = new JThequePanelBuilder(this);

        builder.addI18nLabel("book.note", builder.gbcSet(0, 0));

        ListCellRenderer noteRenderer = new NoteComboRenderer(daoNotes);

        modelNote = new NotesComboBoxModel(daoNotes);

        comboNote = builder.addComboBox(modelNote,
                builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
        comboNote.setEnabled(false);
        comboNote.setRenderer(noteRenderer);

        builder.addI18nLabel("book.isbn10", builder.gbcSet(0, 1));

        fieldISBN10 = builder.add(new JTextField(10),
                builder.gbcSet(1, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
        SwingUtils.addFieldLengthLimit(fieldISBN10, 100);
        fieldISBN10.setEnabled(false);

        builder.addI18nLabel("book.isbn13", builder.gbcSet(0, 2));

        fieldISBN13 = builder.add(new JTextField(10),
                builder.gbcSet(1, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
        SwingUtils.addFieldLengthLimit(fieldISBN13, 100);
        fieldISBN13.setEnabled(false);

        builder.addI18nLabel("book.resume",
                builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));

        areaResume = new JTextArea();
        areaResume.setEnabled(false);
        areaResume.setRows(3);

        builder.addScrolled(areaResume,
                builder.gbcSet(0, 4, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, GridBagUtils.REMAINDER, 1, 1.0, 0.0));
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        Book book = (Book) event.getObject();

        fieldISBN10.setText(book.getIsbn10());
        fieldISBN13.setText(book.getIsbn13());
        comboNote.setSelectedItem(book.getNote());
        areaResume.setText(book.getResume());
    }

    @Override
    public void setEnabled(boolean enabled) {
        comboNote.setEnabled(enabled);
        areaResume.setEnabled(enabled);
        fieldISBN10.setEnabled(enabled);
        fieldISBN13.setEnabled(enabled);
    }

    @Override
    public void fillFormBean(IBookFormBean formBean) {
        formBean.setIsbn10(fieldISBN10.getText());
        formBean.setIsbn13(fieldISBN13.getText());
        formBean.setNote(modelNote.getSelectedNote());
        formBean.setResume(areaResume.getText());
    }

    @Override
    public void clear() {
        fieldISBN10.setText("");
        fieldISBN13.setText("");
        comboNote.setSelectedItem(null);
        areaResume.setText("");
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfLongerThan(areaResume.getText(), "", RESUME_MAX_LENGTH, errors);
        ValidationUtils.rejectIfLongerThan(fieldISBN10.getText(), "", ISBN_MAX_LENGTH, errors);
        ValidationUtils.rejectIfLongerThan(fieldISBN13.getText(), "", ISBN_MAX_LENGTH, errors);
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}
