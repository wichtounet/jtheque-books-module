package org.jtheque.books.view.frames;

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
import org.jtheque.books.view.able.IEditorView;
import org.jtheque.books.view.actions.CloseViewAction;
import org.jtheque.books.view.actions.editor.AcValidateEditorView;
import org.jtheque.books.view.models.able.IEditorModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.primary.od.able.Data;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * An editor view.
 *
 * @author Baptiste Wicht
 */
public final class EditorView extends SwingDialogView implements IEditorView {
    private JTextField fieldName;

    private static final int NAME_LENGTH_LIMIT = 50;
    private static final int FIELD_COLUMNS = 15;

    /**
     * Construct a new JFrameEditor modal to his parent view.
     *
     * @param frame          The parent frame.
     */
    public EditorView(Frame frame) {
        super(frame);

        setContentPane(buildContentPane());
        reload();

        pack();

        setLocationRelativeTo(getOwner());
    }

    @Override
    public void reload() {
        setTitleKey("editor.view.title");

        fieldName.setText("");

        getModel().setEditor(null);
    }

    @Override
    public void reload(Data newEditor) {
        Editor editor = (Editor) newEditor;

        setTitle(Managers.getManager(ILanguageManager.class).getMessage("editor.view.title.modify") + editor.getName());

        fieldName.setText(editor.getName());

        getModel().setEditor(editor);
    }

    /**
     * Build the content pane.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        builder.addI18nLabel("editor.view.name", builder.gbcSet(0, 0));

        Action validateAction = new AcValidateEditorView();

        fieldName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));
        SwingUtils.addFieldLengthLimit(fieldName, NAME_LENGTH_LIMIT);
        SwingUtils.addFieldValidateAction(fieldName, validateAction);

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1), validateAction,
                new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    public JTextField getFieldName() {
        return fieldName;
    }

    @Override
    public void refreshText() {
        super.refreshText();

        if (getModel().getEditor() != null) {
            setTitle(Managers.getManager(ILanguageManager.class).getMessage("editor.view.title.modify") +
                    getModel().getEditor().getName());
        }
    }

    @Override
    public IEditorModel getModel() {
        return (IEditorModel) super.getModel();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfEmpty(fieldName.getText(), "editor.view.name", errors);
    }
}