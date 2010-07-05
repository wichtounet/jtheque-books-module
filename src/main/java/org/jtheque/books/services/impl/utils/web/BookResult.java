package org.jtheque.books.services.impl.utils.web;

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
 * A book result.
 *
 * @author Baptiste Wicht
 */
public final class BookResult {
    private String language;
    private String index;
    private String title;

    /**
     * Set the index of the result.
     *
     * @param index The index of the result.
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * Return the index of the result.
     *
     * @return The index of the result.
     */
    public String getIndex() {
        return index;
    }

    /**
     * Set the title of the result.
     *
     * @param title The title of the result.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the title of the result.
     *
     * @return The title of the result.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the language of the result.
     *
     * @param language The language of the result.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Return the language of the result.
     *
     * @return The language of the result.
     */
    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Result : \n\tTitle = " + title + "\n\tIndex = " + index + "\n\tAbstractLanguage = " + language;
    }
}
