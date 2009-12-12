package org.jtheque.books.view.fb;

import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Person;

/**
 * @author Baptiste Wicht
 */
public interface IAuthorFormBean extends FormBean {
    /**
     * Set the name of the author.
     *
     * @param name The name of the author.
     */
    void setName(String name);

    /**
     * Set the firstName of the author.
     *
     * @param firstName The first name of the author.
     */
    void setFirstName(String firstName);

    /**
     * Set the note of the realizer.
     *
     * @param note The note to set.
     */
    void setNote(Note note);

    /**
     * Set the country of the realizer.
     *
     * @param country The country to set.
     */
    void setCountry(Country country);

    /**
     * Fill the specified author with the informations of the form bean.
     *
     * @param author The author object to fill.
     */
    void fillAuthor(Person author);
}
