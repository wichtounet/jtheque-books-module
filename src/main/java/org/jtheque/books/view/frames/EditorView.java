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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.view.able.IEditorView;
import org.jtheque.books.view.actions.editor.AcValidateEditorView;
import org.jtheque.books.view.models.EditorModel;
import org.jtheque.books.view.models.able.IEditorModel;
import org.jtheque.errors.able.IError;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JTextField;

import java.awt.Frame;
import java.util.Collection;

/**
 * An editor view.
 *
 * @author Baptiste Wicht
 */
public final class EditorView extends SwingBuildedDialogView<IEditorModel> implements IEditorView {
    private JTextField fieldName;

    private static final int NAME_LENGTH_LIMIT = 50;
    private static final int FIELD_COLUMNS = 15;

    /**
     * Construct a new JFrameEditor modal to his parent view.
     *
     * @param frame The parent frame.
     */
    public EditorView(Frame frame) {
        super(frame);

        build();
    }

    @Override
    protected void initView() {
        setModel(new EditorModel());
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addI18nLabel("editor.view.name", builder.gbcSet(0, 0));

        Action validateAction = new AcValidateEditorView();

        fieldName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));
        SwingUtils.addFieldLengthLimit(fieldName, NAME_LENGTH_LIMIT);
        SwingUtils.addFieldValidateAction(fieldName, validateAction);

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1), validateAction,
                getCloseAction("generic.view.actions.cancel"));

        reload();
    }

    @Override
    public void reload() {
        Editor editor = getModel().getEditor();

        if (editor.isSaved()) {
            setTitle(getMessage("editor.view.title.modify") + editor.getName());
        } else {
            setTitleKey("editor.view.title");
        }

        fieldName.setText(editor.getName());
    }

    @Override
    public JTextField getFieldName() {
        return fieldName;
    }

    @Override
    public void refreshText() {
        super.refreshText();

        if (getModel().getEditor() != null) {
            setTitle(Managers.getManager(ILanguageService.class).getMessage("editor.view.title.modify") +
                    getModel().getEditor().getName());
        }
    }

    @Override
    protected void validate(Collection<IError> errors) {
        ValidationUtils.rejectIfEmpty(fieldName.getText(), "editor.view.name", errors);
    }
}
