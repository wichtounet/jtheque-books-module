package org.jtheque.books.view.panels;

import org.jtheque.books.view.able.fb.IBookFormBean;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IInfosPanel extends CurrentObjectListener {
    /**
     * Set if the panel is enabled (<code>true</code>) or disabled (<code>false</code>).
     *
     * @param enabled A boolean tag indicating if the panel is enabled or not.
     */
    void setEnabled(boolean enabled);

    /**
     * Return the authors model.
     *
     * @return The authors model.
     */
    DefaultListModel getAuthorsModel();

    /**
     * Return the model for the authors of the book.
     *
     * @return The model for the authors of the book.
     */
    DefaultListModel getAuthorsBookModel();

    /**
     * Return the selected indexes of the authors model.
     *
     * @return The selected indexes of the authors model.
     */
    int[] getSelectedAuthorsIndexes();

    /**
     * Return the selected indexes of the authors of the book model.
     *
     * @return The selected indexes of the authors of the book model.
     */
    int[] getSelectedAuthorsBookIndexes();

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
