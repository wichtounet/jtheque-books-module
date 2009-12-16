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

import org.jtheque.books.view.actions.book.AcAutoAddBook;
import org.jtheque.books.view.actions.book.AcAutoEditBook;
import org.jtheque.books.view.actions.book.AcCancelBook;
import org.jtheque.books.view.actions.book.AcDeleteBook;
import org.jtheque.books.view.actions.book.AcManualEditBook;
import org.jtheque.books.view.actions.book.AcNewBook;
import org.jtheque.books.view.actions.book.AcSaveBook;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
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
        PanelBuilder builder = new PanelBuilder(this);

        buttonSave = new JButton(new AcSaveBook());
        buttonCancel = new JButton(new AcCancelBook());
        buttonDelete = new JButton(new AcDeleteBook());

        buttonAdd = new JDropDownButton(new AcNewBook(), new AcAutoAddBook());
        buttonEdit = new JDropDownButton(new AcManualEditBook(), new AcAutoEditBook());

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