package org.jtheque.books.view.sort;

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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * This class sort by country.
 *
 * @author Baptiste Wicht
 */
public final class ByCountrySorter implements Sorter {
    @Resource
    private IAuthorsService authorsService;

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(IAuthorsService.DATA_TYPE) && sortType.equals(SimpleData.DataType.COUNTRY.getDataType());
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<SimpleData, Category> groups = new HashMap<SimpleData, Category>(authorsService.getNumberOfAuthors() / 4);
        Iterable<Person> authors = authorsService.getPersons();

        for (Person author : authors) {
            SimpleData country = author.getTheCountry();

            if (!groups.containsKey(country)) {
                Category category = new Category(country.getDisplayableText());

                root.add(category);
                groups.put(country, category);
            }

            groups.get(country).add(author);
        }

        model.setRootElement(root);
    }
}