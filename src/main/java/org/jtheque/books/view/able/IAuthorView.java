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

import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.primary.view.able.PrincipalDataView;
import org.jtheque.primary.view.able.fb.IPersonFormBean;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;

/**
 * An author view specification.
 *
 * @author Baptiste Wicht
 */
public interface IAuthorView extends PrincipalDataView, CurrentObjectListener, TabComponent {
    /**
     * Return the tree model of the view.
     *
     * @return The tree model.
     */
    JThequeTreeModel getTreeModel();

    /**
     * Fill the form bean with the data of the author and return it.
     *
     * @return The filled form bean.
     */
    IPersonFormBean fillFilmFormBean();

    @Override
    IAuthorsModel getModel();
}
