package org.jtheque.books.persistence.od.impl;

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
