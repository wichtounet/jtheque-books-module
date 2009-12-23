package org.jtheque.books.persistence.od.impl;

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

import org.jtheque.books.BooksModule;
import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.impl.abstraction.AbstractBook;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.properties.IPropertiesManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.primary.od.able.Person;
import org.jtheque.utils.bean.HashCodeUtils;

import javax.swing.Icon;

/**
 * A book.
 *
 * @author Baptiste Wicht
 */
public final class BookImpl extends AbstractBook {
    private Book memento;
    private boolean mementoState;

    @Override
    public String getDisplayableText() {
        return getTitle();
    }

    @Override
    public String toString() {
        return getDisplayableText();
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return Managers.getManager(IPropertiesManager.class).areEquals(
                this, obj,
                "title", "year", "pages", "authors", "resume", "theLending", "theKind", "isbn10", "isbn13", "theLanguage",
                "note", "theEditor", "theType", "theSaga");
    }

    @Override
    public void saveToMemento() {
        mementoState = true;

        memento = Managers.getManager(IPropertiesManager.class).createMemento(this);
    }

    @Override
    public void restoreMemento() {
        if (mementoState) {
            Managers.getManager(IPropertiesManager.class).restoreMemento(this, memento);
        }
    }

    @Override
    public Icon getIcon() {
        return Managers.getManager(IResourceManager.class).getIcon(BooksModule.IMAGE_BASE_NAME, "book", ImageType.PNG);
    }

    @Override
    public void addAuthor(Person author) {
        assert author.getType().equals(IAuthorsService.PERSON_TYPE) : "The person must of type Author";

        getAuthors().add(author);
    }
}