package org.jtheque.books.view.actions.auto;

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

import org.jtheque.books.services.able.IBookAutoService;
import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.able.IAutoView;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;

import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Action to search the about view.
 *
 * @author Baptiste Wicht
 */
public final class AcSearch extends JThequeSimpleAction {
    private final IAutoView autoView;
    private final IBookAutoService bookAutoService;

    /**
     * Construct a new <code>AcSearch</code>.
     */
    public AcSearch() {
        super();

        setText(">>");

        autoView = Managers.getManager(IBeansManager.class).getBean("autoView");
        bookAutoService = Managers.getManager(IBeansManager.class).getBean("bookAutoService");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (autoView.validateContent(IAutoView.PHASE_1)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoView.startWait();

                    new Thread(new ChooseSiteRunnable()).start();
                }
            });
        }
    }

    /**
     * A Runnable to choose the site and search for books in.
     *
     * @author Baptiste Wicht
     */
    private final class ChooseSiteRunnable implements Runnable {
        @Override
        public void run() {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoView.getModelListBooks().clear();
                }
            });

            final Collection<BookResult> results = bookAutoService.getBooks(
                    autoView.getSelectedLanguage(),
                    autoView.getFieldTitle().getText());

            for (final BookResult result : results) {
                Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                    @Override
                    public void run() {
                        autoView.getModelListBooks().addElement(result.getTitle());
                    }
                });
            }

            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoView.getModel().setResults(results);
                }
            });

            autoView.stopWait();
        }
    }
}