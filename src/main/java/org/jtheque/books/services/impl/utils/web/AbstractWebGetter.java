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

import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

/**
 * An abstract web getter.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractWebGetter implements WebGetter {
    private Analyzer analyzer;

    /**
     * Return the analyzer.
     *
     * @return The analyzer.
     */
    Analyzer getAnalyzer() {
        return analyzer;
    }

    @Override
    public final void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }
}
