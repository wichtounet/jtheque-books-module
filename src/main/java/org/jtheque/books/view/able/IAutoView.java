package org.jtheque.books.view.able;

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

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.models.able.IAutoAddModel;
import org.jtheque.core.managers.view.able.IWindowView;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

/**
 * An auto view specification.
 *
 * @author Baptiste Wicht
 */
public interface IAutoView extends IWindowView {
    int PHASE_1 = 1;
    int PHASE_2 = 2;

    /**
     * Return the field of title.
     *
     * @return The JTextField used to set the title of the film searched.
     */
    JTextField getFieldTitle();

    /**
     * Return the model of the list of the films.
     *
     * @return The ListModel used for the of the films.
     */
    DefaultListModel getModelListBooks();

    /**
     * Return the selected book.
     *
     * @return The selected book.
     */
    BookResult getSelectedBook();

    /**
     * Return the selected language.
     *
     * @return The selected language.
     */
    String getSelectedLanguage();

    /**
     * Validate the content.
     *
     * @param phase The current phase.
     *
     * @return true if the content is valid else false.
     */
    boolean validateContent(int phase);

    /**
     * Stop the wait progress of the view.
     */
    void stopWait();

    @Override
    IAutoAddModel getModel();

    /**
     * Start the wait progress of the view.
     */
    void startWait();
}
