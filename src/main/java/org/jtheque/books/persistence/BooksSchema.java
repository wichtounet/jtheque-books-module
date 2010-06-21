package org.jtheque.books.persistence;

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

import org.jtheque.books.persistence.dao.able.IDaoBooks;
import org.jtheque.books.persistence.dao.able.IDaoEditors;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.schema.AbstractSchema;
import org.jtheque.core.managers.schema.HSQLImporter;
import org.jtheque.core.managers.schema.Insert;
import org.jtheque.primary.dao.able.IDaoLendings;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.utils.bean.Version;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The database schema for the Movies Module.
 *
 * @author Baptiste Wicht
 */
public final class BooksSchema extends AbstractSchema {
    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    /**
     * Construct a new MoviesSchema.
     */
    public BooksSchema() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public Version getVersion() {
        return new Version("1.1");
    }

    @Override
    public String getId() {
        return "Books-Schema";
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"PrimaryUtils-Schema"};
    }

    @Override
    public void install() {
        createDataTable();
        createReferentialIntegrityConstraints();
    }

    @Override
    public void update(Version from) {
        if ("1.0".equals(from.getVersion())) {
            createReferentialIntegrityConstraints();
            convertAuthorsToPerson();
        }
    }

    /**
     * Create the data table.
     */
    private void createDataTable() {
        jdbcTemplate.update("CREATE TABLE " + IDaoEditors.TABLE + " (ID INT IDENTITY PRIMARY KEY, NAME VARCHAR(150) NOT NULL UNIQUE)");
        jdbcTemplate.update("CREATE TABLE " + IDaoBooks.TABLE + " (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(150) NOT NULL UNIQUE, YEAR INT, ISBN10 VARCHAR(20), ISBN13 VARCHAR(13), NOTE INT, PAGES_NUMBER INT, RESUME VARCHAR(2000), THE_EDITOR_FK INT, THE_KIND_FK INT, THE_LANGUAGE_FK INT, THE_LENDING_FK INT, THE_SAGA_FK INT, THE_TYPE_FK INT)");
        jdbcTemplate.update("CREATE TABLE " + IDaoBooks.BOOKS_AUTHOR_TABLE + " (THE_BOOK_FK INT NOT NULL, THE_AUTHOR_FK INT NOT NULL)");

        jdbcTemplate.update("CREATE INDEX BOOK_EDITOR_IDX ON " + IDaoEditors.TABLE + "(ID)");
        jdbcTemplate.update("CREATE INDEX BOOKS_IDX ON " + IDaoBooks.TABLE + "(ID)");
    }

    /**
     * Create the constraints of referential integrity.
     */
    private void createReferentialIntegrityConstraints() {
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.BOOKS_AUTHOR_TABLE + " ADD FOREIGN KEY (THE_BOOK_FK) REFERENCES  " + IDaoBooks.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.BOOKS_AUTHOR_TABLE + " ADD FOREIGN KEY (THE_AUTHOR_FK) REFERENCES  " + IDaoPersons.TABLE + "  (ID) ON UPDATE SET NULL");

        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.TABLE + " ADD FOREIGN KEY (THE_KIND_FK) REFERENCES  " + SimpleData.DataType.KIND.getTable() + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.TABLE + " ADD FOREIGN KEY (THE_LANGUAGE_FK) REFERENCES  " + SimpleData.DataType.LANGUAGE.getTable() + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.TABLE + " ADD FOREIGN KEY (THE_LENDING_FK) REFERENCES  " + IDaoLendings.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.TABLE + " ADD FOREIGN KEY (THE_SAGA_FK) REFERENCES  " + SimpleData.DataType.SAGA.getTable() + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.TABLE + " ADD FOREIGN KEY (THE_TYPE_FK) REFERENCES  " + SimpleData.DataType.TYPE.getTable() + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoBooks.TABLE + " ADD FOREIGN KEY (THE_EDITOR_FK) REFERENCES  " + IDaoEditors.TABLE + "  (ID) ON UPDATE SET NULL");
    }

    /**
     * Convert the old authors DB table with the new person table.
     */
    private void convertAuthorsToPerson() {
        List<Object[]> authors = jdbcTemplate.query("SELECT * FROM T_AUTHORS", new AuthorRowMapper());

        String query = "INSERT INTO " + IDaoPersons.TABLE + "(NAME, FIRST_NAME, NOTE, THE_COUNTRY_FK, TYPE) VALUES (?,?,?,?,?)";

        for (Object[] author : authors) {
            jdbcTemplate.update(query, author[0], author[1], author[2], author[3], IAuthorsService.PERSON_TYPE);
        }

        jdbcTemplate.update("DROP TABLE IF EXISTS T_AUTHORS");
    }

    /**
     * A simple mapper to get authors from the old table.
     *
     * @author Baptiste Wicht
     */
    private static final class AuthorRowMapper implements RowMapper<Object[]> {
        @Override
        public Object[] mapRow(ResultSet rs, int i) throws SQLException {
            Object[] author = new Object[4];

            author[0] = rs.getString("NAME");
            author[1] = rs.getString("FIRSTNAME");
            author[2] = rs.getInt("NOTE");
            author[3] = rs.getInt("THE_COUNTRY_FK");

            return author;
        }
    }

    @Override
    public void importDataFromHSQL(Iterable<Insert> inserts) {
        HSQLImporter importer = new HSQLImporter();

        importer.match("OD_SAGA", "INSERT INTO " + SimpleData.DataType.SAGA.getTable() + " (ID, NAME, IMPL) VALUES (?,?,?)", "Books", 0, 2);
        importer.match("OD_KIND_BOOK", "INSERT INTO " + SimpleData.DataType.KIND.getTable() + " (ID, NAME, IMPL) VALUES (?,?,?)", "Books", 0, 2);
        importer.match("OD_TYPE_BOOK", "INSERT INTO " + SimpleData.DataType.TYPE.getTable() + " (ID, NAME, IMPL) VALUES (?,?,?)", "Books", 0, 2);
        importer.match("OD_LENDING_BOOK", "INSERT INTO " + IDaoLendings.TABLE + " (ID, DATE, THE_BORROWER_FK, IMPL) VALUES (?,?,?,?)", "Books", 0, 2, 3);
        importer.match("OD_EDITOR", "INSERT INTO " + IDaoEditors.TABLE + " (ID, NAME) VALUES (?,?)", 0, 2);
        importer.match("OD_AUTHOR", "INSERT INTO " + IDaoPersons.TABLE + " (ID, NAME, FIRST_NAME, NOTE, THE_COUNTRY_FK, TYPE) VALUES (?,?,?,?,?)", IAuthorsService.PERSON_TYPE, 0, 3, 2, 4, 5);
        importer.match("BOOK_AUTHOR", "INSERT INTO " + IDaoBooks.BOOKS_AUTHOR_TABLE + " (THE_BOOK_FK, THE_AUTHOR_FK) VALUES (?,?)", 0, 1);
        importer.match("OD_BOOK", "INSERT INTO " + IDaoBooks.TABLE + " (TITLE, YEAR, ISBN10, ISBN13, NOTE, PAGES_NUMBER, RESUME, THE_EDITOR_FK, THE_KIND_FK, THE_LANGUAGE_FK, THE_LENDING_FK, THE_SAGA_FK, THE_TYPE_FK) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                7, 8, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14);

        importer.importInserts(inserts);
    }
}