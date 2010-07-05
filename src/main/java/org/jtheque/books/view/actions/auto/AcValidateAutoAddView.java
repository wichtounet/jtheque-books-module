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

import org.jtheque.books.view.able.IAutoView;
import org.jtheque.books.view.controllers.able.IBookAutoController;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * An action to validate the auto add view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateAutoAddView extends JThequeAction {
    /**
     * Construct a new AcValidateAutoAddView.
     */
    public AcValidateAutoAddView() {
        super("auto.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final IAutoView autoView = Managers.getManager(IBeansManager.class).getBean("autoView");
        final IBookAutoController bookAutoController = Managers.getManager(IBeansManager.class).getBean("bookAutoController");

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
