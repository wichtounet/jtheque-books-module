package org.jtheque.books.view.actions.author;

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

import org.jtheque.books.view.controllers.able.IAuthorController;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to delete the current author.
 *
 * @author Baptiste Wicht
 */
public final class AcDeleteAuthor extends JThequeAction {
    private static final long serialVersionUID = -4065895872194033911L;

    @Resource
    private IAuthorController authorController;

    /**
     * Construct a new AcDeleteAuthor.
     */
    public AcDeleteAuthor() {
        super("author.actions.delete");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        IAuthorsModel model = authorController.getView().getModel();

        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmDelete", model.getCurrentAuthor().getDisplayableText()),
                Managers.getManager(ILanguageManager.class).getMessage("author.dialogs.confirmDelete.title"));

        if (yes) {
            authorController.deleteCurrentAuthor();
        }
    }
}