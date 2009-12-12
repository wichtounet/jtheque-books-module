package org.jtheque.books.services.impl.utils.web;

import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

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
 * An amazon getter for the french site.
 *
 * @author Baptiste Wicht.
 */
public final class AmazonFRGetter extends AmazonGetter {
    /**
     * Construct a new <code>AmazonFRGetter</code>.
     *
     * @param analyzer The analyzer.
     */
    public AmazonFRGetter(Analyzer analyzer) {
        super();

        setAnalyzer(analyzer);
    }

    @Override
    public String getSearchURL() {
        return "http://www.amazon.fr/s/ref=nb_ss_b?url=search-alias%3Dstripbooks&field-keywords=";
    }

    @Override
    public String getLanguage() {
        return "Amazon FR";
    }
}