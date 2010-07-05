package org.jtheque.books.services.impl;

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
