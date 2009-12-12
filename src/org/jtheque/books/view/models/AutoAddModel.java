package org.jtheque.books.view.models;

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