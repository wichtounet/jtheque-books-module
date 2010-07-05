package org.jtheque.books.view.models.list;

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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;

/**
 * A list model for authors.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsListModel extends SimpleListModel<Person> implements DataListener {
    @Resource
    private IAuthorsService authorsService;

    /**
     * Construct a new AuthorsListModel.
     */
    public AuthorsListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        authorsService.addDataListener(this);

        setElements(authorsService.getPersons());
    }

    @Override
    public void dataChanged() {
        setElements(authorsService.getPersons());
    }

    /**
     * Reload the model.
     */
    public void reload() {
        setElements(authorsService.getPersons());
    }
}
