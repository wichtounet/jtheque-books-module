package org.jtheque.books.view.models;

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
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.Collection;

/**
 * A principal data model for the author view.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsModel extends PrincipalDataModel<Person> implements IAuthorsModel {
    private Person currentAuthor;

    public AuthorsModel() {
        super();

        Managers.getManager(IBeansManager.class).<IAuthorsService>getBean("authorsService").addDataListener(this);
    }

    @Override
    public Collection<Person> getDatas() {
        return Managers.getManager(IBeansManager.class).<IAuthorsService>getBean("authorsService").getDatas();
    }

    @Override
    public Person getCurrentAuthor() {
        return currentAuthor;
    }

    /**
     * Set the current book.
     *
     * @param currentAuthor The new current book
     */
    @Override
    public void setCurrentAuthor(Person currentAuthor) {
        this.currentAuthor = currentAuthor;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentAuthor));
    }
}
