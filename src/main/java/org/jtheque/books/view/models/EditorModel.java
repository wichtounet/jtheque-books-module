package org.jtheque.books.view.models;

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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.view.models.able.IEditorModel;

/**
 * A model for the editor view.
 *
 * @author Baptiste Wicht
 */
public final class EditorModel implements IEditorModel {
    private Editor editor;

    @Override
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    @Override
    public Editor getEditor() {
        return editor;
    }
}