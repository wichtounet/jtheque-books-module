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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * A sorter to sort by Editor.
 *
 * @author Baptiste Wicht
 */
public final class ByEditorSorter implements Sorter {
    @Resource
    private IBooksService booksService;

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(IBooksService.DATA_TYPE) && sortType.equals(IEditorsService.DATA_TYPE);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<Editor, Category> groups = new HashMap<Editor, Category>(booksService.getNumberOfBooks() / 4);
        Iterable<Book> contents = booksService.getBooks();

        for (Book book : contents) {
            Editor editor = book.getTheEditor();

            if (!groups.containsKey(editor)) {
                Category category = new Category(editor.getDisplayableText());

                root.add(category);
                groups.put(editor, category);
            }

            groups.get(editor).add(book);
        }

        model.setRootElement(root);
    }
}