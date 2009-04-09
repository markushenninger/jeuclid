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

package cTree;

import org.w3c.dom.Element;

public class CFrac extends CElement {

	public CFrac(Element element){
		this.element = element;
	}
	
	public CType getCType() {
		return CType.FRAC;
	}
	
	public CElement getZaehler(){
		return getFirstChild();
	}
	
	public CElement getNenner(){
		return getFirstChild().getNextSibling();
	}
	
	public static CFrac createFraction(CElement zaehler, CElement nenner){
		CFrac fraction = (CFrac) CElementHelper.createAll(zaehler.getElement(), "mfrac", "mfrac", CRolle.UNKNOWN, null);
		fraction.appendPraefixAndChild(zaehler);
		fraction.appendPraefixAndChild(nenner);
		zaehler.setCRolle(CRolle.ZAEHLER);
		nenner.setCRolle(CRolle.NENNER);
		return fraction;
	}
	
	public void normalize(){};		
}