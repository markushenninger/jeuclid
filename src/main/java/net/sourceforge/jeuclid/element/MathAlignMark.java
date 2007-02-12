/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
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

/* $Id: MathAlignMark.java,v 1.1.2.3 2007/02/10 22:57:22 maxberger Exp $ */

package net.sourceforge.jeuclid.element;

import java.awt.Graphics2D;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractInvisibleMathElement;

/**
 * This class represents the malignmark tag.
 * 
 * @author PG
 * @since Jan 20, 2005
 */
public class MathAlignMark extends AbstractInvisibleMathElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "malignmark";

    private int width = 0;

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathAlignMark(MathBase base) {
        super(base);
    }

    /** {@inheritDoc} */
    public int calculateWidth(Graphics2D g) {
        return width;
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return ELEMENT;
    }

}
