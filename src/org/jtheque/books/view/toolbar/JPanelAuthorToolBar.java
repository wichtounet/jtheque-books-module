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

import org.jtheque.books.view.actions.author.AcCancelAuthor;
import org.jtheque.books.view.actions.author.AcDeleteAuthor;
import org.jtheque.books.view.actions.author.AcEditAuthor;
import org.jtheque.books.view.actions.author.AcNewAuthor;
import org.jtheque.books.view.actions.author.AcSaveAuthor;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
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
     * 
     */
    public JPanelAuthorToolBar() {
        super();

        build();
    }

    /**
     * Build the toolbar.
     */
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        buttonSave = new JButton(new AcSaveAuthor());
        buttonCancel = new JButton(new AcCancelAuthor());
        buttonDelete = new JButton(new AcDeleteAuthor());
        buttonAdd = new JButton(new AcNewAuthor());
        buttonEdit = new JButton(new AcEditAuthor());

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