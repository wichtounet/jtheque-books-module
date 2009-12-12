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
import org.jtheque.books.services.able.INotesService;
import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Person;
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
        Iterable<Person> authors = authorsService.getAuthors();

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