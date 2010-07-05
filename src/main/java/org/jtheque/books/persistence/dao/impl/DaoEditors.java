package org.jtheque.books.persistence.dao.impl;

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
import org.jtheque.books.persistence.od.impl.EditorImpl;
import org.jtheque.core.managers.persistence.GenericDao;
import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * A Dao implementation for editors.
 *
 * @author Baptiste Wicht
 */
public final class DaoEditors extends GenericDao<Editor> implements IDaoEditors {
    private final ParameterizedRowMapper<Editor> rowMapper = new EditorRowMapper();
    private final QueryMapper queryMapper = new EditorQueryMapper();

    @Resource
    private IDaoPersistenceContext persistenceContext;

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    /**
     * Construct a new DaoEditors.
     */
    public DaoEditors() {
        super(TABLE);
    }

    @Override
    public boolean exists(String name) {
        return getEditor(name) != null;
    }

    @Override
    public Collection<Editor> getEditors() {
        return getAll();
    }

    @Override
    public Editor getEditor(int id) {
        return get(id);
    }

    @Override
    public Editor getEditor(String name) {
        List<Editor> editors = jdbcTemplate.query("SELECT * FROM " + TABLE + " WHERE NAME = ?", rowMapper, name);

        if (editors.isEmpty()) {
            return null;
        }

        Editor editor = editors.get(0);

        if (isNotInCache(editor.getId())) {
            getCache().put(editor.getId(), editor);
        }

        return getCache().get(editor.getId());
    }

    @Override
    protected ParameterizedRowMapper<Editor> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected QueryMapper getQueryMapper() {
        return queryMapper;
    }

    @Override
    protected void loadCache() {
        Collection<Editor> editors = persistenceContext.getSortedList(TABLE, rowMapper);

        for (Editor editor : editors) {
            getCache().put(editor.getId(), editor);
        }

        setCacheEntirelyLoaded();
    }

    @Override
    protected void load(int i) {
        Editor editor = persistenceContext.getDataByID(TABLE, i, rowMapper);

        getCache().put(i, editor);
    }

    @Override
    public Editor createEditor() {
        return new EditorImpl();
    }

    /**
     * A mapper to map resultset to editor.
     *
     * @author Baptiste Wicht
     */
    private final class EditorRowMapper implements ParameterizedRowMapper<Editor> {
        @Override
        public Editor mapRow(ResultSet rs, int i) throws SQLException {
            Editor editor = createEditor();

            editor.setId(rs.getInt("ID"));
            editor.setName(rs.getString("NAME"));

            return editor;
        }
    }

    /**
     * A mapper to map resultset to editor.
     *
     * @author Baptiste Wicht
     */
    private static final class EditorQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity) {
            Editor editor = (Editor) entity;

            String query = "INSERT INTO " + TABLE + " (NAME) VALUES(?)";

            Object[] parameters = {
                    editor.getName()
            };

            return new Query(query, parameters);
        }

        @Override
        public Query constructUpdateQuery(Entity entity) {
            Editor editor = (Editor) entity;

            String query = "UPDATE " + TABLE + " SET NAME = ? WHERE ID = ?";

            Object[] parameters = {
                    editor.getName(),
                    editor.getId()};

            return new Query(query, parameters);
        }
    }
}
