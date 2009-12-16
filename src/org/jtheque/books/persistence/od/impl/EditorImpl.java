package org.jtheque.books.persistence.od.impl;

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

import org.jtheque.books.persistence.od.able.Editor;
import org.jtheque.books.persistence.od.impl.abstraction.AbstractEditor;
import org.jtheque.utils.Constants;

/**
 * An editor.
 *
 * @author Baptiste Wicht
 */
public final class EditorImpl extends AbstractEditor {
    @Override
    public String getDisplayableText() {
        return getName();
    }

    @Override
    public String toString() {
        return getDisplayableText();
    }

    @Override
    public int hashCode() {
        int result = Constants.HASH_CODE_START;
        
        result = Constants.HASH_CODE_PRIME * result + getId();
        result = Constants.HASH_CODE_PRIME * result + (getName() == null ? 0 : getName().hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Editor other = (Editor) obj;


        if (getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!getName().equals(other.getName())) {
            return false;
        }

        return true;
    }
}