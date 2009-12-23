package org.jtheque.books.persistence.od.able;

import org.jtheque.primary.od.able.Data;

/**
 * A collection specification.
 *
 * @author Baptiste Wicht
 */
public interface Editor extends Data {
    /**
     * Return the name.
     *
     * @return The name.
     */
    String getName();

    /**
     * Set the name.
     *
     * @param name The name.
     */
    void setName(String name);
}
