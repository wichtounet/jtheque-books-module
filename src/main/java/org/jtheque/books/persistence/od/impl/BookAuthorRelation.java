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

/**
 * A relation between an author and a book.
 *
 * @author Baptiste Wicht
 */
public final class BookAuthorRelation {
    private int author;
    private int book;

    /**
     * Return the author of the relation.
     *
     * @return The author of the relation.
     */
    public int getAuthor() {
        return author;
    }

    /**
     * Set the author of the relation.
     *
     * @param author The author of the relation.
     */
    public void setAuthor(int author) {
        this.author = author;
    }

    /**
     * Return the book of the relation.
     *
     * @return The book of the relation.
     */
    public int getBook() {
        return book;
    }

    /**
     * Set the book of the relation.
     *
     * @param book The book of the relation.
     */
    public void setBook(int book) {
        this.book = book;
    }
}