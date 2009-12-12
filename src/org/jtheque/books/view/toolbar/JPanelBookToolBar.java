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

import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.impl.components.JDropDownButton;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A toolbar for the panel book.
 *
 * @author Baptiste Wicht
 */
public final class JPanelBookToolBar extends JPanel implements ToolbarView {
    private static final long serialVersionUID = -6509195368792317882L;

    /* Graphics components */
    private JDropDownButton buttonAdd;
    private JDropDownButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JButton buttonDelete;

    private Action autoAddAction;
    private Action manualAddAction;
    private Action autoEditAction;
    private Action manualEditAction;
    private Action saveAction;
    private Action cancelAction;
    private Action deleteAction;

    private final GridBagUtils gbc = new GridBagUtils();

    private ViewMode mode = ViewMode.VIEW;

    /**
     * Build the tool bar.
     */
    @PostConstruct
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        buttonSave = new JButton(saveAction);
        buttonCancel = new JButton(cancelAction);
        buttonDelete = new JButton(deleteAction);

        buttonAdd = new JDropDownButton(manualAddAction, autoAddAction);
        buttonEdit = new JDropDownButton(manualEditAction, autoEditAction);

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

    /**
     * Set the action to auto add a book.
     *
     * @param autoAddAction The action to auto add a book.
     */
    public void setAutoAddAction(Action autoAddAction) {
        this.autoAddAction = autoAddAction;
    }

    /**
     * Set the action to add a book.
     *
     * @param manualAddAction The action to add a book.
     */
    public void setManualAddAction(Action manualAddAction) {
        this.manualAddAction = manualAddAction;
    }

    /**
     * Set the action to auto edit a book.
     *
     * @param autoEditAction The action to auto edit a book.
     */
    public void setAutoEditAction(Action autoEditAction) {
        this.autoEditAction = autoEditAction;
    }

    /**
     * Set the action to edit a book.
     *
     * @param manualEditAction The action to edit a book.
     */
    public void setManualEditAction(Action manualEditAction) {
        this.manualEditAction = manualEditAction;
    }

    /**
     * Set the action to save a book.
     *
     * @param saveAction The action to save a book.
     */
    public void setSaveAction(Action saveAction) {
        this.saveAction = saveAction;
    }

    /**
     * Set the action to cancel  the modifications on a book.
     *
     * @param cancelAction The action to cancel  the modifications on a book.
     */
    public void setCancelAction(Action cancelAction) {
        this.cancelAction = cancelAction;
    }

    /**
     * Set the action to delete the current book.
     *
     * @param deleteAction The action to delete the current book.
     */
    public void setDeleteAction(Action deleteAction) {
        this.deleteAction = deleteAction;
    }
}