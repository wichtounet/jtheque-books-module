package org.jtheque.books.services.impl.utils.web;

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
