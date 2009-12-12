package org.jtheque.books.services.impl.utils.web.analyzers;

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

import org.jtheque.books.BooksModule;
import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.books.services.able.INotesService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.utils.web.analyzers.generic.GenericGenerator;
import org.jtheque.primary.utils.web.analyzers.generic.field.FieldGetter;
import org.jtheque.primary.utils.web.analyzers.generic.operation.ScannerPossessor;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.DataUtils;

import javax.annotation.Resource;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A generic book analyzer. It seems an analyzer who takes its parser information from an XML file.
 *
 * @author Baptiste Wicht
 */
public final class GenericBookAnalyzer extends AbstractBookAnalyzer implements ScannerPossessor {
    @Resource
    private IKindsService kindsService;

    @Resource
    private IEditorsService editorsService;

    @Resource
    private INotesService notesService;

    @Resource
    private ICountriesService countriesService;

    @Resource
    private IAuthorsService authorsService;

    @Resource
    private BooksModule booksModule;

    /**
     * The generator of the field getters.
     */
    private final GenericGenerator generator;

    private FieldGetter dateGetter;
    private FieldGetter pagesGetter;
    private FieldGetter kindGetter;
    private FieldGetter editorGetter;
    private FieldGetter resumeGetter;
    private FieldGetter authorsGetter;
    private FieldGetter isbn10Getter;
    private FieldGetter isbn13Getter;

    private static final Pattern ACTOR_SEPARATOR_PATTERN = Pattern.compile("\\+");

    /**
     * Construct a new GenericBookAnalyzer from a XML file.
     *
     * @param file The XML file.
     */
    public GenericBookAnalyzer(String file) {
        super();

        generator = new GenericGenerator(this);
        generator.generate("org/jtheque/books/ressources/analyzers/" + file);

        init();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    /**
     * Init the parser.
     */
    private void init() {
        dateGetter = generator.getFieldGetter("date");
        pagesGetter = generator.getFieldGetter("pages");
        kindGetter = generator.getFieldGetter("kind");
        editorGetter = generator.getFieldGetter("editor");
        resumeGetter = generator.getFieldGetter("resume");
        authorsGetter = generator.getFieldGetter("authors");
        isbn10Getter = generator.getFieldGetter("isbn10");
        isbn13Getter = generator.getFieldGetter("isbn13");
    }

    @Override
    public void findDate(String line) {
        if (isDateDo()) {
            return;
        }

        if (dateGetter.mustGet(line)) {
            String current = dateGetter.performOperations(line, this);

            String value = dateGetter.getValue(current);

            if (value != null) {
                getBook().setYear(Integer.parseInt(value));

                setDate(true);
            }
        }
    }

    @Override
    public void findPages(String line) {
        if (isDurationDo()) {
            return;
        }

        if (pagesGetter.mustGet(line)) {
            String current = pagesGetter.performOperations(line, this);

            String value = pagesGetter.getValue(current);

            if (value != null) {
                int pages = Integer.parseInt(value);

                getBook().setPages(pages);

                setPages(true);
            }
        }
    }

    @Override
    public void findKind(String line) {
        if (isKindDo()) {
            return;
        }

        if (kindGetter.mustGet(line)) {
            String current = kindGetter.performOperations(line, this);

            String value = kindGetter.getValue(current);

            if (value != null) {
                value = StringUtils.setFirstLetterOnlyUpper(value);

                if (kindsService.exists(value)) {
                    getBook().setTheKind(kindsService.getKind(value));
                } else {
                    Kind kind = kindsService.getEmptyKind();

                    kind.setName(value);

                    kindsService.create(kind);

                    getBook().setTheKind(kind);
                }

                setKind(true);
            }
        }
    }

    @Override
    public void findEditor(String line) {
        if (isRealizerDo()) {
            return;
        }

        if (editorGetter.mustGet(line)) {
            String current = editorGetter.performOperations(line, this);

            String value = editorGetter.getValue(current);

            if (value != null) {
                if (editorsService.exists(value)) {
                    getBook().setTheEditor(editorsService.getEditor(value));
                } else {
                    Editor editor = editorsService.getEmptyEditor();
                    editor.setName(value);

                    editorsService.create(editor);

                    getBook().setTheEditor(editor);
                }

                setEditor(true);
            }
        }
    }

    @Override
    public void findAuthors(String line) {
        if (isActorsDo()) {
            return;
        }

        if (authorsGetter.mustGet(line)) {
            String current = authorsGetter.performOperations(line, this);

            String value = authorsGetter.getValue(current);

            if (value != null) {
                value = StringUtils.removeHTMLEntities(value);

                String[] actorsTemp = ACTOR_SEPARATOR_PATTERN.split(value);

                for (String name : actorsTemp) {
                    String[] nameAndFirstName = DataUtils.getNameAndFirstName(name);

                    addAuthor(nameAndFirstName[0], nameAndFirstName[1]);
                }

                setAuthors(true);
            }
        }
    }

    /**
     * Add an author to the book. If the author doesn't exists, we create it.
     *
     * @param name      The name of the author.
     * @param firstName The first name of the author.
     */
    private void addAuthor(String name, String firstName) {
        if (authorsService.exists(firstName, name)) {
            getBook().addAuthor(authorsService.getAuthor(firstName, name));
        } else {
            Person author = authorsService.getEmptyAuthor();
            author.setName(name);
            author.setFirstName(firstName);
            author.setTheCountry(countriesService.getDefaultCountry());
            author.setNote(notesService.getDefaultNote());

            authorsService.create(author);

            getBook().addAuthor(author);
        }
    }

    @Override
    public void findResume(String line) {
        if (isResumeDo()) {
            return;
        }

        if (resumeGetter.mustGet(line)) {
            String current = resumeGetter.performOperations(line, this);

            String value = resumeGetter.getValue(current);

            if (value != null) {
                value = value.replace("+", "\n");
                getBook().setResume(StringUtils.removeHTMLEntities(value));

                setResume(true);
            }
        }
    }

    @Override
    public void findIsbn10(String line) {
        if (isIsbn10Do()) {
            return;
        }

        if (isbn10Getter.mustGet(line)) {
            String current = isbn10Getter.performOperations(line, this);

            String value = isbn10Getter.getValue(current);

            if (value != null) {
                getBook().setIsbn10(value);

                setIsbn10(true);
            }
        }
    }

    @Override
    public void findIsbn13(String line) {
        if (isIsbn13Do()) {
            return;
        }

        if (isbn13Getter.mustGet(line)) {
            String current = isbn13Getter.performOperations(line, this);

            String value = isbn13Getter.getValue(current);

            if (value != null) {
                getBook().setIsbn13(value);

                setIsbn13(true);
            }
        }
    }

    @Override
    public Scanner getScanner() {
        return null;
    }
}