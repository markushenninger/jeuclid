/*
 * Copyright 2002 - 2006 JEuclid, http://jeuclid.sf.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: ChangeTrackingElement.java,v 1.1.2.1 2006/09/12 05:36:28 maxberger Exp $ */

package net.sourceforge.jeuclid.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * generic implementation of Element that tries to track if a change has
 * happened.
 * 
 * @author Max Berger
 */
public class ChangeTrackingElement extends PartialElementImpl {

    private boolean changed = true;

    /** {@inheritDoc} */
    public Node appendChild(Node newChild) {
        this.setChanged(true);
        return super.appendChild(newChild);
    }

    /** {@inheritDoc} */
    public void setAttribute(String name, String value) {
        this.setChanged(true);
        super.setAttribute(name, value);
    }

    /** {@inheritDoc} */
    public void setTextContent(String newTextContent) throws DOMException {
        this.setChanged(true);
        super.setTextContent(newTextContent);
    }

    /**
     * @return the hasChanged
     */
    protected boolean isChanged() {
        return changed;
    }

    /**
     * @param hasChanged
     *            the hasChanged to set
     */
    protected void setChanged(boolean hasChanged) {
        this.changed = hasChanged;
    }
}
