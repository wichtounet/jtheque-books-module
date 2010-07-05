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
import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.INotesService;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * A sorter to sort by note.
 *
 * @author Baptiste Wicht
 */
public final class ByNoteSorter implements Sorter {
    @Resource
    private IAuthorsService authorsService;

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(IAuthorsService.DATA_TYPE) && sortType.equals(INotesService.DATA_TYPE);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<Note, Category> groups = new HashMap<Note, Category>(authorsService.getNumberOfAuthors() / 4);
        Iterable<Person> authors = authorsService.getPersons();

        for (Person author : authors) {
            Note note = author.getNote();

            if (!groups.containsKey(note)) {
                Category category = new Category(note.getInternationalizedText());

                root.add(category);
                groups.put(note, category);
            }

            groups.get(note).add(author);
        }

        model.setRootElement(root);
    }
}
