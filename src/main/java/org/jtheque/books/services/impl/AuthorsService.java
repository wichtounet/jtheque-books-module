package org.jtheque.books.services.impl;

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
import org.jtheque.primary.services.impl.PersonService;

/**
 * An authors service implementation.
 *
 * @author Baptiste Wicht
 */
public final class AuthorsService extends PersonService implements IAuthorsService {
    public AuthorsService() {
        super(PERSON_TYPE, DATA_TYPE);
    }

    @Override
    public int getNumberOfAuthors() {
        return getPersons().size();
    }
}
