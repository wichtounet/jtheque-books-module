package org.jtheque.books.view.sort;

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
