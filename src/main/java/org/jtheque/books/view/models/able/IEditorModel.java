package org.jtheque.books.view.models.able;

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.ui.able.IModel;

/**
 * @author Baptiste Wicht
 */
public interface IEditorModel extends IModel {
    /**
     * Set the current editor.
     *
     * @param editor The current editor.
     */
    void setEditor(Editor editor);

    /**
     * Return the current editor.
     *
     * @return The current editor.
     */
    Editor getEditor();
}
