/*
 * Copyright 2009 Erhard Kuenzel
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

package cTree.cDefence;

import cTree.CElement;
import cTree.CRolle;

public class CD_1StrichMinrow extends CD_1 {

    @Override
    public CElement defence(final CElement parent, final CElement fences,
            final CElement content) {
        System.out.println("Do the defence work");
        final boolean first = (fences.getCRolle() == CRolle.SUMMAND1);
        final CElement insertion = this.createInsertion(fences, content);
        DefenceHandler.getInstance().replaceFoP(parent, insertion, fences,
                false);
        if (!first) {
            insertion.togglePlusMinus(false);
        }
        return insertion;
    }

    @Override
    protected CElement createInsertion(final CElement fences,
            final CElement content) {
        System.out.println("Defence strich minrow");
        CElement newChild = null;
        if (fences.getCRolle() == CRolle.SUMMAND1) {
            newChild = content.cloneCElement(false);
        } else {
            newChild = content.getFirstChild().cloneCElement(false);
        }
        return newChild;
    }
}
