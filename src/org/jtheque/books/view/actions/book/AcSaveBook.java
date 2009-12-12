package org.jtheque.books.view.actions.book;

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

import org.jtheque.books.view.controllers.able.IBookController;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to save the current book.
 *
 * @author Baptiste Wicht
 */
public final class AcSaveBook extends JThequeAction {
    private static final long serialVersionUID = -7830940231903754057L;

    @Resource
    private IBookController bookController;

    /**
     * Construct a AcSaveBook.
     */
    public AcSaveBook() {
        super("book.actions.save");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (bookController.getView().validateContent()) {
            bookController.save();
        }
    }
}