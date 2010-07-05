package org.jtheque.books.view.toolbar;

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

import org.jtheque.books.view.actions.book.AcAutoAddBook;
import org.jtheque.books.view.actions.book.AcAutoEditBook;
import org.jtheque.books.view.actions.book.AcDeleteBook;
import org.jtheque.books.view.actions.book.AcSaveBook;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.impl.actions.principal.CancelPrincipalAction;
import org.jtheque.primary.view.impl.actions.principal.CreateNewPrincipalAction;
import org.jtheque.primary.view.impl.actions.principal.ManualEditPrincipalAction;
import org.jtheque.primary.view.impl.components.JDropDownButton;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A toolbar for the panel book.
 *
 * @author Baptiste Wicht
 */
public final class JPanelBookToolBar extends JPanel implements ToolbarView {
    private JDropDownButton buttonAdd;
    private JDropDownButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JButton buttonDelete;

    private final GridBagUtils gbc = new GridBagUtils();

    private ViewMode mode = ViewMode.VIEW;

    public JPanelBookToolBar() {
        super();

        build();
    }

    /**
     * Build the tool bar.
     */
    private void build() {
        PanelBuilder builder = new JThequePanelBuilder(this);

        buttonSave = new JButton(new AcSaveBook());
        buttonCancel = new JButton(new CancelPrincipalAction("book.actions.cancel", "bookController"));
        buttonDelete = new JButton(new AcDeleteBook());

        buttonAdd = new JDropDownButton(
                new CreateNewPrincipalAction("book.actions.add.manual", "bookController"),
                new AcAutoAddBook());

        buttonEdit = new JDropDownButton(
                new ManualEditPrincipalAction("book.actions.edit.manual", "bookController"),
                new AcAutoEditBook());

        if (mode == ViewMode.VIEW) {
            builder.add(buttonAdd, gbc.gbcSet(0, 0));
            builder.add(buttonEdit, gbc.gbcSet(1, 0));
            builder.add(buttonDelete, gbc.gbcSet(2, 0));
        } else {
            builder.add(buttonSave, gbc.gbcSet(0, 0));
            builder.add(buttonCancel, gbc.gbcSet(1, 0));
        }
    }

    /**
     * Set the display mode of the tool bar.
     *
     * @param mode The display mode.
     */
    void setMode(ViewMode mode) {
        if (this.mode != mode) {
            this.mode = mode;

            remove(buttonSave);
            remove(buttonCancel);
            remove(buttonAdd);
            remove(buttonEdit);
            remove(buttonDelete);

            switch (mode) {
                case VIEW:
                    add(buttonAdd, gbc.gbcSet(0, 0));
                    add(buttonEdit, gbc.gbcSet(1, 0));
                    add(buttonDelete, gbc.gbcSet(2, 0));

                    break;
                case NEW:
                case EDIT:
                case AUTO:
                    add(buttonSave, gbc.gbcSet(0, 0));
                    add(buttonCancel, gbc.gbcSet(1, 0));

                    break;
            }
        }
    }

    @Override
    public void setDisplayMode(ViewMode mode) {
        setMode(mode);
    }
}
