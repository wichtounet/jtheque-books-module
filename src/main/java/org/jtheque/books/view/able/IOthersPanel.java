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

import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

import javax.swing.JComponent;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IOthersPanel extends CurrentObjectListener {
    /**
     * Set if the panel is enabled (<code>true</code>) or disabled (<code>false</code>).
     *
     * @param enabled A boolean tag indicating if the panel is enabled or not.
     */
    void setEnabled(boolean enabled);

    /**
     * Fill the form bean with the informations of the panel.
     *
     * @param formBean The form bean to fill.
     */
    void fillFormBean(IBookFormBean formBean);

    /**
     * Clear the view.
     */
    void clear();

    /**
     * Validate the view.
     *
     * @param errors The errors to fill.
     */
    void validate(Collection<JThequeError> errors);

    /**
     * Return the real implementation of the panel.
     *
     * @return The real implementation of the panel.
     */
    JComponent getImpl();
}
