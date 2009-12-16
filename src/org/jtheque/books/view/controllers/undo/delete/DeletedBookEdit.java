package org.jtheque.books.view.controllers.undo.delete;

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
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit corresponding to a delete of an book.
 *
 * @author Baptiste Wicht
 */
public final class DeletedBookEdit extends AbstractUndoableEdit {
    private final Book book;

    @Resource
    private IBooksService booksService;

    /**
     * Construct a new DeleteAuthorEdit.
     *
     * @param book The deleted author.
     */
    public DeletedBookEdit(Book book) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.book = book;
    }

    @Override
    public void undo(){
        super.undo();

        booksService.create(book);
    }

    @Override
    public void redo(){
        super.redo();

        booksService.delete(book);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}