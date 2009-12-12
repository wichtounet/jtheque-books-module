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
 * An action to cancel book view.
 *
 * @author Baptiste Wicht
 */
public final class AcCancelBook extends JThequeAction {
    private static final long serialVersionUID = -6709810280801155818L;

    @Resource
    private IBookController bookController;

    /**
     * Construct a AcCancelBook.
     */
    public AcCancelBook() {
        super("book.actions.cancel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        bookController.cancel();
    }
}