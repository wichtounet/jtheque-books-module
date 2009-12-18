package org.jtheque.books.view.controllers.undo.create;

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
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit corresponding to a creation of an author.
 *
 * @author Baptiste Wicht
 */
public final class CreatedAuthorEdit extends AbstractUndoableEdit {
    private final Person author;

    @Resource
    private IAuthorsService authorsService;

    /**
     * Construct a new CreatedAuthorEdit.
     *
     * @param author The created author.
     */
    public CreatedAuthorEdit(Person author) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.author = author;
    }

    @Override
    public void undo() {
        super.undo();

        authorsService.delete(author);
    }

    @Override
    public void redo() {
        super.redo();

        authorsService.create(author);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.create");
    }
}