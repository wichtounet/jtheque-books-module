package org.jtheque.books.view.actions.auto;

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
