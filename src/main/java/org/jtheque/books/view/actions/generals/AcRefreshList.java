package org.jtheque.books.view.actions.generals;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.primary.view.able.PrincipalDataView;

import java.awt.event.ActionEvent;

/**
 * An action to refresh the list of the current view.
 *
 * @author Baptiste Wicht
 */
public final class AcRefreshList extends JThequeAction {
    /**
     * Construct a AcRefreshList.
     */
    public AcRefreshList() {
        super("actions.refresh");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrincipalDataView view = (PrincipalDataView) Managers.getManager(IViewManager.class).getViews().getSelectedView().getComponent();

        view.resort();
    }
}
