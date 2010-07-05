package org.jtheque.books.view.models;

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

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.models.able.IAutoAddModel;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * A model for the auto add view.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddModel implements IAutoAddModel {
    private Collection<BookResult> results;

    @Override
    public Collection<BookResult> getResults() {
        return results;
    }

    @Override
    public void setResults(Collection<BookResult> results) {
        this.results = CollectionUtils.copyOf(results);
    }
}
