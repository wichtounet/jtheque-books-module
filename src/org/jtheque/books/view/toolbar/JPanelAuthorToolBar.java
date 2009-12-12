package org.jtheque.books.view.toolbar;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A toolbar for the panel author.
 *
 * @author Baptiste Wicht
 */
public final class JPanelAuthorToolBar extends JPanel implements ToolbarView {
    private static final long serialVersionUID = -6509195368792317882L;

    private JButton buttonAdd;
    private JButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JButton buttonDelete;

    private final Action addAction;
    private final Action editAction;
    private final Action saveAction;
    private final Action cancelAction;
    private final Action deleteAction;

    private ViewMode mode = ViewMode.VIEW;

    private final GridBagUtils gbc = new GridBagUtils();

    /**
     * Create a new JPanelAuthorToolBar.
     *
     * @param addAction    The action to add an author.
     * @param editAction   The action to edit an author.
     * @param saveAction   The action to save an author.
     * @param cancelAction The action to cancel.
     * @param deleteAction The action to delete the current author.
     */
    public JPanelAuthorToolBar(Action addAction, Action editAction, Action saveAction,
                               Action cancelAction, Action deleteAction) {
        super();

        this.addAction = addAction;
        this.editAction = editAction;
        this.saveAction = saveAction;
        this.cancelAction = cancelAction;
        this.deleteAction = deleteAction;
    }

    /**
     * Build the toolbar.
     */
    @PostConstruct
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        buttonSave = new JButton(saveAction);
        buttonCancel = new JButton(cancelAction);
        buttonDelete = new JButton(deleteAction);
        buttonAdd = new JButton(addAction);
        buttonEdit = new JButton(editAction);

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