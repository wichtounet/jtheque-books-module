package org.jtheque.books.services.impl.utils.web;

import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
