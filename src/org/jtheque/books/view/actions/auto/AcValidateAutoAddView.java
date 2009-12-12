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

import org.jtheque.books.view.able.IAutoView;
import org.jtheque.books.view.controllers.able.IBookAutoController;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to validate the auto add view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateAutoAddView extends JThequeAction {
    private static final long serialVersionUID = -859591053047928367L;

    @Resource
    private IAutoView autoView;

    @Resource
    private IBookAutoController bookAutoController;

    /**
     * Construct a new AcValidateAutoAddView.
     */
    public AcValidateAutoAddView() {
        super("auto.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (autoView.validateContent(IAutoView.PHASE_2)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoView.startWait();

                    Thread addFilm = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            bookAutoController.auto(autoView.getSelectedBook());

                            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                                @Override
                                public void run() {
                                    autoView.stopWait();
                                    autoView.closeDown();
                                }
                            });
                        }
                    });
                    addFilm.start();
                }
            });
        }
    }
}