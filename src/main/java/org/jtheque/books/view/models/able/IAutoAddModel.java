package org.jtheque.books.view.models.able;

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.core.managers.view.able.components.IModel;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IAutoAddModel extends IModel {
    /**
     * Return the results of the search.
     *
     * @return A list of the results of the search.
     */
    Collection<BookResult> getResults();

    /**
     * Set the results of the search.
     *
     * @param results The results to set.
     */
    void setResults(Collection<BookResult> results);
}
