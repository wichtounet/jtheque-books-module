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

import org.jtheque.books.persistence.dao.able.IDaoBooks;
import org.jtheque.books.persistence.dao.able.IDaoEditors;
import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.persistence.od.impl.BookAuthorRelation;
import org.jtheque.books.persistence.od.impl.BookImpl;
import org.jtheque.core.managers.persistence.GenericDao;
import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.db.DaoNotes.NoteType;
import org.jtheque.primary.dao.able.IDaoLendings;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.dao.able.IDaoSimpleDatas;
import org.jtheque.primary.od.able.Person;
import org.jtheque.utils.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * A Dao implementation for books.
 *
 * @author Baptiste Wicht
 */
public final class DaoBooks extends GenericDao<Book> implements IDaoBooks {
    private final ParameterizedRowMapper<Book> rowMapper = new BookRowMapper();
    private final ParameterizedRowMapper<BookAuthorRelation> relationRowMapper = new RelationRowMapper();
    private final QueryMapper queryMapper = new BookQueryMapper();

    private Collection<BookAuthorRelation> relationsToActors;

    @Resource
    private IDaoPersistenceContext persistenceContext;

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    @Resource
    private IDaoSimpleDatas daoSagas;

    @Resource
    private IDaoEditors daoEditors;

    @Resource
    private IDaoSimpleDatas daoTypes;

    @Resource
    private IDaoPersons daoPersons;

    @Resource
    private IDaoSimpleDatas daoLanguages;

    @Resource
    private IDaoLendings daoLendings;

    @Resource
    private IDaoSimpleDatas daoKinds;

    /**
     * Construct a new DaoBooks.
     */
    public DaoBooks() {
        super(TABLE);
    }

    @Override
    public Collection<Book> getBooks() {
        return getAll();
    }

    @Override
    public boolean delete(Book book) {
        boolean deleted = super.delete(book);

        jdbcTemplate.update("DELETE FROM " + BOOKS_AUTHOR_TABLE + " WHERE THE_BOOK_FK = ?", book.getId());

        return deleted;
    }

    @Override
    public void save(Book book) {
        super.save(book);

        jdbcTemplate.update("DELETE FROM " + BOOKS_AUTHOR_TABLE + " WHERE THE_BOOK_FK = ?", book.getId());

        createLinks(book);
    }

    @Override
    public void create(Book book) {
        //First we create the book in the table
        super.create(book);

        //next, we can create the links to the author
        createLinks(book);
    }

    /**
     * Create the links between the book and the authors.
     *
     * @param book The book to create the links for.
     */
    private void createLinks(Book book) {
        for (Person author : book.getAuthors()) {
            jdbcTemplate.update("INSERT INTO " + BOOKS_AUTHOR_TABLE + " (THE_BOOK_FK, THE_AUTHOR_FK) VALUES(?,?)", book.getId(), author.getId());
        }
    }

    @Override
    protected ParameterizedRowMapper<Book> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected QueryMapper getQueryMapper() {
        return queryMapper;
    }

    @Override
    protected void loadCache() {
        relationsToActors = jdbcTemplate.query("SELECT * FROM " + BOOKS_AUTHOR_TABLE, relationRowMapper);

        Collection<Book> books = persistenceContext.getSortedList(TABLE, rowMapper);

        for (Book book : books) {
            getCache().put(book.getId(), book);
        }

        setCacheEntirelyLoaded();

        relationsToActors.clear();
    }

    @Override
    protected void load(int i) {
        Book book = persistenceContext.getDataByID(TABLE, i, rowMapper);

        getCache().put(i, book);
    }

    /**
     * A mapper to map resultset to relation between author and book.
     *
     * @author Baptiste Wicht
     */
    private static final class RelationRowMapper implements ParameterizedRowMapper<BookAuthorRelation> {
        @Override
        public BookAuthorRelation mapRow(ResultSet rs, int i) throws SQLException {
            BookAuthorRelation relation = new BookAuthorRelation();

            relation.setBook(rs.getInt("THE_BOOK_FK"));
            relation.setAuthor(rs.getInt("THE_AUTHOR_FK"));

            return relation;
        }
    }

    @Override
    public Book createBook() {
        return new BookImpl();
    }

    /**
     * A mapper to map resultset to book.
     *
     * @author Baptiste Wicht
     */
    private final class BookRowMapper implements ParameterizedRowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            Book book = createBook();

            book.setId(rs.getInt("ID"));
            book.setTitle(rs.getString("TITLE"));
            book.setPages(rs.getInt("PAGES_NUMBER"));
            book.setResume(rs.getString("RESUME"));
            book.setIsbn10(rs.getString("ISBN10"));
            book.setIsbn13(rs.getString("ISBN13"));
            book.setYear(rs.getInt("YEAR"));
            book.setTheEditor(daoEditors.getEditor(rs.getInt("THE_EDITOR_FK")));
            book.setTheSaga(daoSagas.getSimpleData(rs.getInt("THE_SAGA_FK")));
            book.setTheType(daoTypes.getSimpleData(rs.getInt("THE_TYPE_FK")));
            book.setTheLending(daoLendings.getLending(rs.getInt("THE_LENDING_FK")));
            book.setTheKind(daoKinds.getSimpleData(rs.getInt("THE_KIND_FK")));
            book.setTheLanguage(daoLanguages.getSimpleData(rs.getInt("THE_LANGUAGE_FK")));

			if (StringUtils.isNotEmpty(rs.getString("NOTE"))){
				book.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.getEnum(rs.getInt("NOTE"))));
			}

            mapRelations(book);

            return book;
        }

        /**
         * Map the relations of the book.
         *
         * @param book The book to get the relations for.
         */
        private void mapRelations(Book book) {
            if (relationsToActors != null && !relationsToActors.isEmpty()) {
                for (BookAuthorRelation relation : relationsToActors) {
                    if (relation.getBook() == book.getId()) {
                        book.addAuthor(daoPersons.getPerson(relation.getAuthor()));
                    }
                }
            } else {
                relationsToActors = jdbcTemplate.query("SELECT * FROM " + BOOKS_AUTHOR_TABLE + " WHERE THE_BOOK_FK = ?", relationRowMapper, book.getId());

                for (BookAuthorRelation relation : relationsToActors) {
                    book.addAuthor(daoPersons.getPerson(relation.getAuthor()));
                }

                relationsToActors.clear();
            }
        }
    }

    /**
     * A mapper to map books to query.
     *
     * @author Baptiste Wicht
     */
    private static final class BookQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity) {
            Book book = (Book) entity;

            String query = "INSERT INTO " + TABLE + " (TITLE, NOTE, PAGES_NUMBER, RESUME, ISBN10, ISBN13, YEAR, " +
                    "THE_EDITOR_FK, THE_SAGA_FK, THE_TYPE_FK, THE_LENDING_FK, THE_KIND_FK, THE_LANGUAGE_FK) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            Object[] parameters = {
                    book.getTitle(),
                    book.getNote() == null ? 0 : book.getNote().getValue().intValue(),
                    book.getPages(),
                    book.getResume(),
                    book.getIsbn10(),
                    book.getIsbn13(),
                    book.getYear(),
                    book.getTheEditor() == null ? null : book.getTheEditor().getId(),
                    book.getTheSaga() == null ? null : book.getTheSaga().getId(),
                    book.getTheType() == null ? null : book.getTheType().getId(),
                    book.getTheLending() == null ? null : book.getTheLending().getId(),
                    book.getTheKind() == null ? null : book.getTheKind().getId(),
                    book.getTheLanguage() == null ? null : book.getTheLanguage().getId()
            };

            return new Query(query, parameters);
        }

        @Override
        public Query constructUpdateQuery(Entity entity) {
            Book book = (Book) entity;

            String query = "UPDATE " + TABLE + " SET TITLE = ?, NOTE = ?, PAGES_NUMBER = ?, RESUME = ?, ISBN10 = ?, ISBN13 = ?, YEAR = ?, " +
                    "THE_EDITOR_FK = ?, THE_SAGA_FK = ?, THE_TYPE_FK = ?, THE_LENDING_FK = ?, THE_KIND_FK = ?, THE_LANGUAGE_FK = ? WHERE ID = ?";

            Object[] parameters = {
                    book.getTitle(),
                    book.getNote() == null ? 0 : book.getNote().getValue().intValue(),
                    book.getPages(),
                    book.getResume(),
                    book.getIsbn10(),
                    book.getIsbn13(),
                    book.getYear(),
                    book.getTheEditor() == null ? null : book.getTheEditor().getId(),
                    book.getTheSaga() == null ? null : book.getTheSaga().getId(),
                    book.getTheType() == null ? null : book.getTheType().getId(),
                    book.getTheLending() == null ? null : book.getTheLending().getId(),
                    book.getTheKind() == null ? null : book.getTheKind().getId(),
                    book.getTheLanguage() == null ? null : book.getTheLanguage().getId(),
                    book.getId()
            };

            return new Query(query, parameters);
        }
    }
}