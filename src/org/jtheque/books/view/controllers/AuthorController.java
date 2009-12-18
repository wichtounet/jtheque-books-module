package org.jtheque.books.view.controllers;

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

import org.jtheque.books.services.able.IAuthorsService;
import org.jtheque.books.view.able.IAuthorView;
import org.jtheque.books.view.controllers.able.IAuthorController;
import org.jtheque.books.view.models.able.IAuthorsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.models.tree.TreeElement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.util.Collection;

/**
 * A controller for the author view.
 *
 * @author Baptiste Wicht
 */
public final class AuthorController extends PrincipalController<Person> implements IAuthorController {
    @Resource
    private IAuthorView authorView;

    /**
     * Init the controller.
     */
    @PostConstruct
    public void init() {
        setState(getViewState());
    }

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        TreePath current = ((JTree) event.getSource()).getSelectionPath();

        if (current != null && current.getLastPathComponent() instanceof Data) {
            Data author = (Data) current.getLastPathComponent();

            if (author != null) {
                ControllerState newState = getState().view(author);

                if (newState != null) {
                    setAndApplyState(newState);
                }
            }
        }
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(authorView.fillFilmFormBean());

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IAuthorsModel getViewModel() {
        return authorView.getModel();
    }

    @Override
    public void manualEdit() {
        ControllerState newState = getState().manualEdit();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void createAuthor() {
        ControllerState newState = getState().create();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void deleteCurrentAuthor() {
        ControllerState newState = getState().delete();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void cancel() {
        ControllerState newState = getState().cancel();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IAuthorView getView() {
        return authorView;
    }

    @Override
    public String getDataType() {
        return IAuthorsService.DATA_TYPE;
    }

    @Override
    public Collection<Person> getDisplayList() {
        return getViewModel().getDisplayList();
    }
}