package org.jtheque.books.services.impl.utils;

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
 * This class is a bean representing the arguments of an automatic edit. It seems the fields who want to automatically
 * edit.
 *
 * @author Baptiste Wicht
 */
public final class EditArguments {
    private boolean editKind;
    private boolean editEditor;
    private boolean editYear;
    private boolean editPages;
    private boolean editAuthors;
    private boolean editResume;

    /**
     * Indicate if we have to edit the kind or not.
     *
     * @return <code>true</code> if we have to edit the kind else <code>false</code>.
     */
    public boolean mustEditKind() {
        return editKind;
    }

    /**
     * Sets if we have to edit the kind.
     *
     * @param editKind Indicate if we have to edit it.
     */
    public void setEditKind(boolean editKind) {
        this.editKind = editKind;
    }

    /**
     * Indicate if we have to edit the editor or not.
     *
     * @return <code>true</code> if we have to edit the editor else <code>false</code>.
     */
    public boolean mustEditEditor() {
        return editEditor;
    }

    /**
     * Sets if we have to edit the editor.
     *
     * @param editEditor Indicate if we have to edit it.
     */
    public void setEditEditor(boolean editEditor) {
        this.editEditor = editEditor;
    }

    /**
     * Indicate if we have to edit the year or not.
     *
     * @return <code>true</code> if we have to edit the year else <code>false</code>.
     */
    public boolean mustEditYear() {
        return editYear;
    }

    /**
     * Sets if we have to edit the year.
     *
     * @param editYear Indicate if we have to edit it.
     */
    public void setEditYear(boolean editYear) {
        this.editYear = editYear;
    }

    /**
     * Indicate if we have to edit the pages or not.
     *
     * @return <code>true</code> if we have to edit the pages else <code>false</code>.
     */
    public boolean mustEditPages() {
        return editPages;
    }

    /**
     * Sets if we have to edit the pages.
     *
     * @param editPages Indicate if we have to edit it.
     */
    public void setEditPages(boolean editPages) {
        this.editPages = editPages;
    }

    /**
     * Indicate if we have to edit the authors or not.
     *
     * @return <code>true</code> if we have to edit the authors else <code>false</code>.
     */
    public boolean mustEditAuthors() {
        return editAuthors;
    }

    /**
     * Sets if we have to edit the authors.
     *
     * @param editAuthors Indicate if we have to edit it.
     */
    public void setEditAuthors(boolean editAuthors) {
        this.editAuthors = editAuthors;
    }

    /**
     * Indicate if we have to edit the resume or not.
     *
     * @return <code>true</code> if we have to edit the resume else <code>false</code>.
     */
    public boolean mustEditResume() {
        return editResume;
    }

    /**
     * Sets if we have to edit the resume.
     *
     * @param editResume Indicate if we have to edit it.
     */
    public void setEditResume(boolean editResume) {
        this.editResume = editResume;
    }

    /**
     * Create the args with all args to true.
     *
     * @return The created EditArguments object.
     */
    public static EditArguments createFullArgs() {
        EditArguments args = new EditArguments();

        args.editKind = true;
        args.editEditor = true;
        args.editAuthors = true;
        args.editYear = true;
        args.editPages = true;
        args.editResume = true;

        return args;
    }
}
