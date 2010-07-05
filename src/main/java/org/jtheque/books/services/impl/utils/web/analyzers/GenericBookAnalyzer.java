package org.jtheque.books.services.impl.utils.web.analyzers;

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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.services.able.IEditorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.INotesService;
import org.jtheque.primary.services.able.ISimpleDataService;
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
    private ISimpleDataService kindsService;

    @Resource
    private IEditorsService editorsService;

    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private IAuthorsService authorsService;

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
        generator.generate("org/jtheque/books/analyzers/" + file);

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

                if (kindsService.exist(value)) {
                    getBook().setTheKind(kindsService.getSimpleData(value));
                } else {
                    SimpleData kind = kindsService.getEmptySimpleData();

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
        if (authorsService.exist(firstName, name)) {
            getBook().addAuthor(authorsService.getPerson(firstName, name));
        } else {
            Person author = authorsService.getEmptyPerson();
            author.setName(name);
            author.setFirstName(firstName);
            author.setTheCountry(countriesService.getDefaultSimpleData());
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
