package org.jtheque.books.view.sort;

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

import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

/**
 * Only add the elements in the order given by the display list.
 *
 * @author Baptiste Wicht
 */
public final class NoneSorter implements Sorter {
    private final IPrincipalController<? extends Data> controller;

    /**
     * Construct a new NoneSorter for the specific principal controller.
     *
     * @param controller The principal controller to get data from.
     */
    public NoneSorter(IPrincipalController<? extends Data> controller) {
        this.controller = controller;
    }

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(controller.getDataType()) && "None".equals(sortType);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        root.addAll(controller.getDisplayList());

        model.setRootElement(root);
    }
}