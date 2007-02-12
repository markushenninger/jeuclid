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

/* $Id: DOMMathBuilder.java,v 1.1.2.5 2007/02/12 08:46:47 maxberger Exp $ */

package net.sourceforge.jeuclid;

import net.sourceforge.jeuclid.element.MathAlignGroup;
import net.sourceforge.jeuclid.element.MathAlignMark;
import net.sourceforge.jeuclid.element.MathDocumentElement;
import net.sourceforge.jeuclid.element.MathEnclose;
import net.sourceforge.jeuclid.element.MathFrac;
import net.sourceforge.jeuclid.element.MathLabeledTableRow;
import net.sourceforge.jeuclid.element.MathOperator;
import net.sourceforge.jeuclid.element.MathOver;
import net.sourceforge.jeuclid.element.MathSpace;
import net.sourceforge.jeuclid.element.MathString;
import net.sourceforge.jeuclid.element.MathStyle;
import net.sourceforge.jeuclid.element.MathTable;
import net.sourceforge.jeuclid.element.MathTableData;
import net.sourceforge.jeuclid.element.MathTableRow;
import net.sourceforge.jeuclid.element.MathUnder;
import net.sourceforge.jeuclid.element.MathUnderOver;
import net.sourceforge.jeuclid.element.generic.AbstractMathElement;
import net.sourceforge.jeuclid.element.helpers.AttributeMap;
import net.sourceforge.jeuclid.element.helpers.DOMAttributeMap;
import net.sourceforge.jeuclid.element.helpers.OperatorDictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The builder for creating a MathElement tree.
 * 
 * @author <a href="mailto:stephan@vern.chem.tu-berlin.de">Stephan Michels</a>
 * @author Max Berger
 */
public class DOMMathBuilder {
    /**
     * Logger for this class
     */
    // unused
    // private static final Log LOGGER =
    // LogFactory.getLog(DOMMathBuilder.class);
    /**
     * Reference to the root element of the math elements tree.
     */
    private MathDocumentElement rootElement;

    private MathBase m_base;

    /**
     * Constructs a builder.
     * 
     * @param document
     *            The MathML document.
     * @param mathBase
     *            Math base
     */
    public DOMMathBuilder(final Document document, final MathBase mathBase) {

        this.m_base = mathBase;

        final Element documentElement = document.getDocumentElement();

        this.rootElement = new MathDocumentElement(this.m_base);
        mathBase.setRootElement(this.rootElement);

        this.traverse(documentElement, this.rootElement, null);
        this.rootElement.eventAllElementsComplete();
    }

    /**
     * Returns the root element of a math tree.
     * 
     * @return Root element.
     */
    public MathDocumentElement getMathRootElement() {
        return this.rootElement;
    }

    /**
     * Creates a MathElement through traversing the DOM tree.
     * 
     * @param node
     *            Current element of the DOM tree.
     * @param parent
     *            Current element of the MathElement tree.
     * @param alignmentScope
     *            Alignment scope of elements.
     */
    private void traverse(final Node node, final Node parent,
            MathTableData alignmentScope) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }
        String tagname = node.getNodeName();
        int posSeparator = -1;

        if ((posSeparator = tagname.indexOf(":")) >= 0) {
            tagname = tagname.substring(posSeparator + 1);
        }
        final AttributeMap attributes = new DOMAttributeMap(node
                .getAttributes());

        final AbstractMathElement element = (AbstractMathElement) MathElementFactory
                .elementFromName(tagname, attributes, this.m_base);

        // TODO: All theses should be handled within the appropriate class
        if (tagname.equals(MathFrac.ELEMENT)) {
            if (attributes.hasAttribute("linethickness")) {
                ((MathFrac) element).setLinethickness(attributes.getString(
                        "linethickness", "2px"));
            }
            ((MathFrac) element).setBevelled(attributes.getBoolean(
                    "bevelled", false));
        } else if (tagname.equals(MathUnder.ELEMENT)) {
            ((MathUnder) element).setAccentUnder(attributes.getBoolean(
                    "accentunder", false));
        } else if (tagname.equals(MathOver.ELEMENT)) {
            ((MathOver) element).setAccent(attributes.getBoolean("accent",
                    false));
        } else if (tagname.equals(MathUnderOver.ELEMENT)) {
            ((MathUnderOver) element).setAccent(attributes.getBoolean(
                    "accent", false));
            ((MathUnderOver) element).setAccentUnder(attributes.getBoolean(
                    "accentunder", false));
        } else if (tagname.equals(MathSpace.ELEMENT)) {
            final MathSpace space = (MathSpace) element;
            if (attributes.hasAttribute("height")) {
                space.setSpaceHeight(attributes.getString("height", "0"));
            }
            if (attributes.hasAttribute("width")) {
                space.setSpaceWidth(attributes.getString("width", "0"));
            }
            if (attributes.hasAttribute("depth")) {
                space.setDepth(attributes.getString("depth", "0"));
            }
        } else if (tagname.equals(MathString.ELEMENT)) {
            ((MathString) element).setLQuote(attributes.getString("lquote",
                    "\""));
            ((MathString) element).setRQuote(attributes.getString("rquote",
                    "\""));
        } else if (tagname.equals(MathTable.ELEMENT)) {
            final MathTable tabl = (MathTable) element;

            if (attributes.hasAttribute("groupalign")) {
                tabl.setGroupAlign(attributes.getString("groupalign", ""));
            }
            if (attributes.hasAttribute("rowspacing")) {
                tabl.setRowspacing(attributes.getString("rowspacing", ""));
            }
            if (attributes.hasAttribute("columnpacing")) {
                tabl.setColumnspacing(attributes
                        .getString("columnpacing", ""));
            }
            if (attributes.hasAttribute("framespacing")) {
                tabl
                        .setFramespacing(attributes.getString("framespacing",
                                ""));
            }
            if (attributes.hasAttribute("align")) {
                tabl.setAlign(attributes.getString("align", "axis"));
            }

        } else if (tagname.equals(MathTableRow.ELEMENT)) {
            if (attributes.hasAttribute("groupalign")) {
                ((MathTableRow) element).setGroupAlign(attributes.getString(
                        "groupalign", ""));
            }
        } else if (tagname.equals(MathEnclose.ELEMENT)) {
            if (attributes.hasAttribute("notation")) {
                ((MathEnclose) element).setNotation(attributes.getString(
                        "notation", ""));
            }
        } else if (tagname.equals(MathTableData.ELEMENT)) {
            alignmentScope = (MathTableData) element;
            if (attributes.hasAttribute("groupalign")) {
                ((MathTableData) element).setGroupAlign(attributes.getString(
                        "groupalign", ""));
            }
        } else if (tagname.equals(MathOperator.ELEMENT)) {
            final MathOperator mo = (MathOperator) element;
            mo.setMoveableLimits(attributes
                    .getBoolean("movablelimits", false));
            if (attributes.hasAttribute("rspace")) {
                mo.setRSpace(attributes.getString("rspace", ""));
            }
            if (attributes.hasAttribute("lspace")) {
                mo.setLSpace(attributes.getString("lspace", ""));
            }
            if (attributes.hasAttribute("form")) {
                final String form = attributes.getString("form", "uknown");
                if (form.equals("uknown")) {
                    mo.setForm(MathOperator.FORM_UKNOWN);
                } else if (form.equals("infix")) {
                    mo.setForm(OperatorDictionary.VALUE_INFIX);
                } else if (form.equals("postfix")) {
                    mo.setForm(OperatorDictionary.VALUE_POSTFIX);
                } else if (form.equals("prefix")) {
                    mo.setForm(OperatorDictionary.VALUE_PREFIX);
                }
            }
        } else if (tagname.equals(MathStyle.ELEMENT)) {
            if (attributes.hasAttribute("scriptsizemultiplier")) {
                final float scrm = Float.valueOf(
                        attributes.getString("scriptsizemultiplier", String
                                .valueOf(element.getScriptSizeMultiplier())))
                        .floatValue();
                element.setScriptSizeMultiplier(scrm);
            }
        } else if (tagname.equals(MathAlignGroup.ELEMENT)) {
            if (alignmentScope != null) {
                alignmentScope.addAlignGroupElement((MathAlignGroup) element);
            }
        } else if (tagname.equals(MathAlignMark.ELEMENT)) {
            if (alignmentScope != null) {
                alignmentScope.addAlignMarkElement((MathAlignMark) element);
            }
        }
        // end of TODO

        if (parent instanceof MathLabeledTableRow) {
            if (((MathLabeledTableRow) parent).labelIgnored) {
                parent.appendChild(element);
            } else {
                ((MathLabeledTableRow) parent).labelIgnored = true;
            }
        } else {
            parent.appendChild(element);
        }

        if (attributes.hasAttribute("mathbackground")) {
            // element.setMathBackground(attributes.getColor("mathbackground",
            // null));
        }

        final NodeList childs = node.getChildNodes();
        MathTableData prevScope = null;

        if (tagname.equals(MathTableData.ELEMENT)) {
            prevScope = alignmentScope;
            alignmentScope = (MathTableData) element;
        }

        for (int i = 0; i < childs.getLength(); i++) {
            if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
                this.traverse(childs.item(i), element, alignmentScope);
            } else if (childs.item(i).getNodeType() == Node.TEXT_NODE) {
                element.addText(childs.item(i).getNodeValue());
            } else if (childs.item(i).getNodeType() == Node.ENTITY_REFERENCE_NODE
                    && childs.item(i).hasChildNodes()) {
                final String entityValue = childs.item(i).getFirstChild()
                        .getNodeValue();
                if (entityValue != null) {
                    element.addText(entityValue);
                }
            }
        }

        element.eventElementComplete();
        if (prevScope != null) {
            alignmentScope = prevScope;
        }

    }

}
