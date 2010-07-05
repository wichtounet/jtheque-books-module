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

import org.jtheque.books.view.actions.author.AcDeleteAuthor;
import org.jtheque.books.view.actions.author.AcSaveAuthor;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.impl.actions.principal.CancelPrincipalAction;
import org.jtheque.primary.view.impl.actions.principal.CreateNewPrincipalAction;
import org.jtheque.primary.view.impl.actions.principal.ManualEditPrincipalAction;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A toolbar for the panel author.
 *
 * @author Baptiste Wicht
 */
public final class JPanelAuthorToolBar extends JPanel implements ToolbarView {
    private JButton buttonAdd;
    private JButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JButton buttonDelete;

    private ViewMode mode = ViewMode.VIEW;

    private final GridBagUtils gbc = new GridBagUtils();

    /**
     * Create a new JPanelAuthorToolBar.
     */
    public JPanelAuthorToolBar() {
        super();

        build();
    }

    /**
     * Build the toolbar.
     */
    private void build() {
        PanelBuilder builder = new JThequePanelBuilder(this);

        buttonSave = new JButton(new AcSaveAuthor());
        buttonDelete = new JButton(new AcDeleteAuthor());
        buttonCancel = new JButton(new CancelPrincipalAction("author.actions.cancel", "authorController"));
        buttonAdd = new JButton(new CreateNewPrincipalAction("author.actions.add", "authorController"));
        buttonEdit = new JButton(new ManualEditPrincipalAction("author.actions.edit", "authorController"));

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

            Managers.getManager(IViewManager.class).refresh(this);
        }
    }

    @Override
    public void setDisplayMode(ViewMode mode) {
        setMode(mode);
    }
}
