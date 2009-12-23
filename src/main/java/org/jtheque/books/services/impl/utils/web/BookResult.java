package org.jtheque.books.services.impl.utils.web;

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

/**
 * A book result.
 *
 * @author Baptiste Wicht
 */
public final class BookResult {
    private String language;
    private String index;
    private String title;

    /**
     * Set the index of the result.
     *
     * @param index The index of the result.
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * Return the index of the result.
     *
     * @return The index of the result.
     */
    public String getIndex() {
        return index;
    }

    /**
     * Set the title of the result.
     *
     * @param title The title of the result.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the title of the result.
     *
     * @return The title of the result.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the language of the result.
     *
     * @param language The language of the result.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Return the language of the result.
     *
     * @return The language of the result.
     */
    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Result : \n\tTitle = " + title + "\n\tIndex = " + index + "\n\tAbstractLanguage = " + language;
    }
}