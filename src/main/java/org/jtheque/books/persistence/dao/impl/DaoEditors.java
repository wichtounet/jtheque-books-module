package org.jtheque.books.persistence.dao.impl;

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