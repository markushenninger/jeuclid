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

package cTree.cCombine;

import cTree.CElement;
import cTree.CFences;
import cTree.CFrac;
import cTree.CMinTerm;
import cTree.CNum;
import cTree.CPlusRow;
import cTree.CRolle;
import cTree.CTimesRow;

public class CC_StrichFracNum extends CC_ {

    private CFrac cF;

    private CElement cNenner;

    @Override
    protected boolean canCombine(final CElement parent, final CElement frac,
            final CElement num) {
        this.cF = (CFrac) frac;
        this.cNenner = this.cF.getNenner();
        return !(this.cNenner instanceof CNum)
                || (((CNum) this.cNenner).getValue() != 0);
    }

    @Override
    protected CElement createCombination(final CElement parent,
            final CElement cE1, final CElement cE2) {
        System.out.println("Add Frac and Num");
        if (this.cF.hasNumberValue()) {
            System.out.println("Add Frac and Num NumberV");
            return this.createNumberCombination(parent, cE1, cE2);
        } else {
            System.out.println("Add Frac and Num Term");
            return this.createTermCombination(parent, cE1, cE2);
        }
    }

    protected CElement createNumberCombination(final CElement parent,
            final CElement cE1, final CElement cE2) {
        final int numVal = ((CNum) cE2).getValue();
        final int zVal = ((CNum) this.cF.getZaehler()).getValue();
        final int nVal = ((CNum) this.cF.getNenner()).getValue();
        final int vz1 = cE1.hasExtMinus() ? -1 : 1;
        final int vz2 = cE2.hasExtMinus() ? -1 : 1;
        final int wertZ = vz1 * zVal + vz2 * numVal * nVal;
        final int aWertZ = Math.abs(wertZ);
        final int vzWert = (wertZ < 0) ? -1 : 1;
        final CFrac arg = (CFrac) cE1.cloneCElement(false);
        ((CNum) arg.getZaehler()).setValue(aWertZ);
        CElement newChild = arg;
        if (cE1.getCRolle() == CRolle.SUMMAND1) {
            if (cE2.hasExtMinus() && (wertZ < 0)) {
                newChild = CMinTerm.createMinTerm(arg, CRolle.SUMMAND1);
            }
        } else if (cE1.getCRolle() == CRolle.NACHVZMINUS) {
            if (wertZ < 0) {
                newChild = CMinTerm.createMinTerm(arg, CRolle.SUMMAND1);
            }
        } else {
            if (vzWert * vz1 < 0) {
                newChild = CFences.createFenced(CMinTerm.createMinTerm(arg));
            }
        }
        return newChild;
    }

    protected CElement createTermCombination(final CElement parent,
            final CElement cE1, final CElement cE2) {
        final CElement cZN = this.cF.getZaehler().cloneCElement(false);
        final CElement cNN = this.cF.getNenner().cloneCElement(false);
        final CElement cNN2 = this.cF.getNenner().cloneCElement(false);
        final CElement cNumN = cE2.cloneCElement(false);
        final CTimesRow cT = CTimesRow.createRow(CTimesRow.createList(cNumN,
                cNN));
        cT.correctInternalPraefixesAndRolle();
        final CPlusRow cP = CPlusRow.createRow(CPlusRow.createList(cT, cZN));
        cP.correctInternalPraefixesAndRolle();
        final CFrac arg = CFrac.createFraction(cP, cNN2);
        CElement newChild = arg;
        if (!cE1.hasExtMinus() && cE2.hasExtMinus() || cE1.hasExtMinus()
                && !cE2.hasExtMinus()) {
            arg.getZaehler().getFirstChild().getNextSibling()
                    .togglePlusMinus(false);
        }
        if (cE1.getCRolle() == CRolle.NACHVZMINUS) {
            newChild = CMinTerm.createMinTerm(newChild, CRolle.SUMMAND1);
        }
        return newChild;
    }
}
