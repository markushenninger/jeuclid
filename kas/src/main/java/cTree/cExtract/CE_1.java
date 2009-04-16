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

package cTree.cExtract;

import java.util.ArrayList;

import cTree.CElement;

public class CE_1 {

    public CElement extract(final CElement parent,
            final ArrayList<CElement> selection, final CElement cE2) {
        System.out.println("Lazy extracting");
        if (!this.canExtract(parent, selection)) {
            return selection.get(0);
        }
        // weitere Property entfernen
        final boolean replace = ExtractHandler.getInstance().justAll(
                selection);
        final boolean hasMinus = selection.get(0).hasExtMinus();
        System.out.println("Replace? " + replace);
        System.out.println("Wie viele Member in Selection? "
                + selection.size());
        final CElement newChild = this.createExtraction(parent, selection,
                cE2);
        ExtractHandler.getInstance().insertOrReplace(parent, newChild,
                selection, replace);
        if (hasMinus) {
            newChild.toggleToPraefixEmptyOrPlus();
        }
        return newChild;
    }

    protected CElement createExtraction(final CElement parent,
            final ArrayList<CElement> selection, final CElement cE2) {
        System.out.println("Cant create extraction");
        // Vorsicht! Fehlbedienung m�glich. Repeller einsetzen!
        return cE2;
    }

    protected boolean canExtract(final CElement parent,
            final ArrayList<CElement> selection) {
        System.out.println("Repell standard for extract?");
        return false;
    }

}
