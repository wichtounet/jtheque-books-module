package org.jtheque.books.services.impl;

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

import org.jtheque.books.persistence.dao.able.IDaoEditors;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.core.managers.persistence.able.DataListener;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collection;

/**
 * An editors service implementation.
 *
 * @author Baptiste Wicht
 */
public final class EditorsService implements IEditorsService {
    @Resource
    private IDaoEditors daoEditors;

    /* Variables */
    private Editor defaultEditor;

    @Override
    @Transactional
    public Editor getDefaultEditor() {
        if (defaultEditor == null) {
            defaultEditor = daoEditors.getEditor("Unknown");

            if (defaultEditor == null) {
                defaultEditor = getEmptyEditor();

                defaultEditor.setName("Unknown");
                daoEditors.create(defaultEditor);
            }
        }

        return defaultEditor;
    }

    @Override
    @Transactional
    public void create(Editor editor) {
        daoEditors.create(editor);
    }

    @Override
    public Editor getEditor(String name) {
        return daoEditors.getEditor(name);
    }

    @Override
    public boolean exists(String name) {
        return daoEditors.exists(name);
    }

    @Override
    @Transactional
    public boolean delete(Editor editor) {
        return daoEditors.delete(editor);
    }

    @Override
    @Transactional
    public void save(Editor editor) {
        daoEditors.save(editor);
    }

    @Override
    public Editor getEmptyEditor() {
        return daoEditors.createEditor();
    }

    @Override
    public Collection<Editor> getDatas() {
        return daoEditors.getEditors();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoEditors.addDataListener(listener);
    }

    @Override
    @Transactional
    public void clearAll() {
        daoEditors.clearAll();
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }
}
