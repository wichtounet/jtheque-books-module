package org.jtheque.books.view.models.list;

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
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;

import javax.annotation.Resource;
import javax.swing.DefaultListModel;

/**
 * A list model for languages.
 *
 * @author Baptiste Wicht
 */
public final class LanguagesListModel extends DefaultListModel {
    private static final long serialVersionUID = 7060472242015464173L;

    private final String[] languages;

    @Resource
    private IBookAutoService bookAutoService;

    /**
     * Construct a new LanguagesListModel.
     */
    public LanguagesListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        languages = bookAutoService.getLanguages();
    }

    @Override
    public Object getElementAt(int index) {
        return languages[index];
    }

    @Override
    public Object get(int index) {
        return languages[index];
    }

    @Override
    public int getSize() {
        return languages.length;
    }
}