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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * A sorter to sort by year.
 *
 * @author Baptiste Wicht
 */
public final class ByYearSorter implements Sorter {
    private static final String SORT_BY_YEAR = "Year";

    @Resource
    private IBooksService booksService;

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(IBooksService.DATA_TYPE) && sortType.equals(SORT_BY_YEAR);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<Integer, Category> groups = new HashMap<Integer, Category>(20);
        Iterable<Book> contents = booksService.getBooks();

        for (Book book : contents) {
            int year = book.getYear();

            if (!groups.containsKey(year)) {
                Category category = new Category(Integer.toString(year));

                root.add(category);
                groups.put(year, category);
            }

            groups.get(year).add(book);
        }

        model.setRootElement(root);
    }
}
