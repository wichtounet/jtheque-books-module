package org.jtheque.books.view.able;

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

import org.jtheque.books.services.impl.utils.web.BookResult;
import org.jtheque.books.view.models.able.IAutoAddModel;
import org.jtheque.core.managers.view.able.IWindowView;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

/**
 * An auto view specification.
 *
 * @author Baptiste Wicht
 */
public interface IAutoView extends IWindowView {
    int PHASE_1 = 1;
    int PHASE_2 = 2;

    /**
     * Return the field of title.
     *
     * @return The JTextField used to set the title of the film searched.
     */
    JTextField getFieldTitle();

    /**
     * Return the model of the list of the films.
     *
     * @return The ListModel used for the of the films.
     */
    DefaultListModel getModelListBooks();

    /**
     * Return the selected book.
     *
     * @return The selected book.
     */
    BookResult getSelectedBook();

    /**
     * Return the selected language.
     *
     * @return The selected language.
     */
    String getSelectedLanguage();

    /**
     * Validate the content.
     *
     * @param phase The current phase.
     *
     * @return true if the content is valid else false.
     */
    boolean validateContent(int phase);

    /**
     * Stop the wait progress of the view.
     */
    void stopWait();

    @Override
    IAutoAddModel getModel();

    /**
     * Start the wait progress of the view.
     */
    void startWait();
}
