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

import org.jtheque.books.services.impl.utils.web.analyzers.GenericBookAnalyzer;
import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

/**
 * Factory of <code>WebGetter</code>.
 *
 * @author Baptiste Wicht
 */
final class WebGetterFactory {
    private final WebGetter[] getters;

    /**
     * Construct a new <code>WebGetterFactory</code> and initialize all the getters.
     */
    WebGetterFactory() {
        super();

        Analyzer analyzer = new GenericBookAnalyzer("amazon.xml");

        getters = new WebGetter[]{new AmazonENGetter(analyzer), new AmazonFRGetter(analyzer)};
    }

    /**
     * Return the getter for the specified getter.
     *
     * @param language The language on which we want search.
     *
     * @return The good web getter if we found it or <code>null</code> if we doesn't found.
     */
    public WebGetter getWebGetter(String language) {
        WebGetter webGetter = null;

        for (WebGetter getter : getters) {
            if (getter.canGetOn(language)) {
                webGetter = getter;
                break;
            }
        }

        return webGetter;
    }
}