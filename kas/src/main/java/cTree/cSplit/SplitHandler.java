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

package cTree.cSplit;

import java.util.HashMap;

import cTree.CElement;
import cTree.CFences;
import cTree.adapter.DOMElementMap;
import cTree.cDefence.DefenceHandler;

public class SplitHandler {
    private volatile static SplitHandler uniqueInstance;

    public HashMap<String, CSplitter1> getSplitter;

    private SplitHandler() {
        this.getSplitter = new HashMap<String, CSplitter1>();
        this.getSplitter.put("*", new CSplitterTimes());
        this.getSplitter.put(":", new CSplitterDiv());
        this.getSplitter.put("+", new CSplitterPlus());
        this.getSplitter.put("-", new CSplitterMin());
        this.getSplitter.put("^", new CSplitterPot());
    }

    public static SplitHandler getInstance() {
        if (SplitHandler.uniqueInstance == null) {
            synchronized (DOMElementMap.class) {
                if (SplitHandler.uniqueInstance == null) {
                    SplitHandler.uniqueInstance = new SplitHandler();
                }
            }
        }
        return SplitHandler.uniqueInstance;
    }

    public CElement split(final CElement parent, final CElement cE1,
            final String s) {
        System.out.println("Split " + parent.getText() + " " + cE1.getText());
        final String operation = s.substring(0, 1);
        final String operator = s.substring(1);
        if (this.getSplitter.containsKey(operation)) {
            System.out.println("SplitKey found");
            if (this.getSplitter.get(operation).check(cE1, operator)) {
                System.out.println("Split check erfolgreich");
                cE1.removeCActiveProperty();
                final CFences cF = CFences.createFenced(this.getSplitter.get(
                        operation).split(parent, cE1, operator));
                parent.replaceChild(cF, cE1, true, true);
                cF.setCActiveProperty();
                return DefenceHandler.getInstance().defence(parent, cF,
                        cF.getInnen());
            } else {
                return cE1;
            }
        } else {
            return cE1;
        }
    }

}
