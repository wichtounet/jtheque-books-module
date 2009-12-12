package org.jtheque.books.services.impl.utils.web;

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

import org.jtheque.books.persistence.od.able.Book;
import org.jtheque.books.services.able.IBooksService;
import org.jtheque.books.services.able.INotesService;
import org.jtheque.books.services.impl.utils.EditArguments;
import org.jtheque.books.services.impl.utils.web.analyzers.AbstractBookAnalyzer;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ITypesService;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * An amazon getter.
 *
 * @author Baptiste Wicht.
 */
public abstract class AmazonGetter extends AbstractWebGetter {
    @Resource
    private INotesService notesService;

    @Resource
    private ILanguagesService languagesService;

    @Resource
    private ITypesService typesService;

    @Resource
    private IBooksService booksService;

    @Logger
    private IJThequeLogger logger;

    /**
     * Construct a new AmazonGetter.
     */
    AmazonGetter() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public Collection<BookResult> getBooks(String search) {
        Collection<BookResult> books = new ArrayList<BookResult>(20);

        Scanner scanner = null;
        try {
            String searched = search.replace(" ", "+");

            scanner = openConnectionToURL(getSearchURL() + searched);

            boolean end = false;

            while (scanner.hasNextLine() && !end) {
                String line = scanner.nextLine();

                if (line.contains("<span class=\"srTitle\">")) {
                    books.add(readBookResult(line));
                }

                if (line.contains("Recherche effectuée par")) {
                    end = true;
                }
            }
        } catch (MalformedURLException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return books;
    }

    /**
     * Read the book result.
     *
     * @param line The line to read.
     * @return The filled book result.
     */
    private BookResult readBookResult(String line) {
        int start = line.indexOf("\">", 26);

        String index = line.substring(line.indexOf("<a href=\"") + 9, start);
        String name = line.substring(line.indexOf("<span class=\"srTitle\">") + 22, line.indexOf("</span></a>"));

        BookResult result = new BookResult();
        result.setIndex(index);
        result.setTitle(name);
        result.setLanguage(getLanguage());

        return result;
    }

    @Override
    public Book getBook(BookResult search, Book bookToModify, EditArguments args) {
        configureBook(bookToModify, args);

        String url = search.getIndex();

        fillDefaultValueOfFilm(search);

        Scanner scanner = null;
        try {
            scanner = openConnectionToURL(url);

            getAnalyzer().setScanner(scanner);

            while (scanner.hasNextLine() && !getAnalyzer().isComplete()) {
                String line = scanner.nextLine().trim();

                getAnalyzer().analyzeLine(line);
            }
        } catch (MalformedURLException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        getAnalyzer().reset();

        return getAnalyzer().getBook();
    }

    /**
     * Fill the default value of the result.
     *
     * @param search The search to fill.
     */
    private void fillDefaultValueOfFilm(BookResult search) {
        String title = search.getTitle();

        getAnalyzer().getBook().setTitle(title);
        getAnalyzer().getBook().setTheLanguage(languagesService.getDefaultLanguage());
        getAnalyzer().getBook().setNote(notesService.getDefaultNote());
        getAnalyzer().getBook().setTheType(typesService.getDefaultType());
        getAnalyzer().getBook().setResume("");
    }

    /**
     * Configure the analyzer and the book depending on the args.
     *
     * @param bookToModify The book to modify.
     * @param args         The edit arguments.
     */
    private void configureBook(Book bookToModify, EditArguments args) {
        Book book;

        if (bookToModify == null) {
            book = booksService.getEmptyBook();
        } else {
            book = bookToModify;

            if (args != null) {
                getAnalyzer().configureWithEditArgs(args);
            }
        }

        getAnalyzer().setBook(book);
    }

    /**
     * Open the connection to URL.
     *
     * @param url The url.
     * @return The Scanner to the url.
     * @throws IOException if an error occurs during url connection opening.
     */
    private static Scanner openConnectionToURL(String url) throws IOException {
        URL fileUrl = new URL(url);

        URLConnection urlConnection = fileUrl.openConnection();
        urlConnection.setUseCaches(false);
        urlConnection.connect();

        return new Scanner(urlConnection.getInputStream());
    }

    @Override
    final AbstractBookAnalyzer getAnalyzer() {
        return (AbstractBookAnalyzer) super.getAnalyzer();
    }

    @Override
    public boolean canGetOn(String language) {
        return getLanguage().equals(language);
    }

    /**
     * Return the managed language.
     *
     * @return The manager language.
     */
    protected abstract String getLanguage();

    /**
     * Return the search URL.
     *
     * @return The search URL.
     */
    protected abstract String getSearchURL();
}