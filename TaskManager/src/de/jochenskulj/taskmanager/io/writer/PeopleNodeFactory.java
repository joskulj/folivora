/*
 *  Folivora -- Java Swing Application for Task Management
 *  Copyright (C) 2013 Jochen Skulj <jochen@jochenskulj.de>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/
 */
package de.jochenskulj.taskmanager.io.writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.jochenskulj.taskmanager.io.TagNames;
import de.jochenskulj.taskmanager.model.PeopleElement;

/**
 * Factory for XML-Nodes for People
 *
 */
public class PeopleNodeFactory {

	private Document currentDocument;

	/**
	 * creates an instance
	 * @param aDocument
	 *        XML-Document to use
	 */
	public PeopleNodeFactory(Document aDocument) {
		currentDocument = aDocument;
	}

	/**
	 * creates an XML-Element for a People element
	 * @param aPeopleElement
	 *        a People element
	 * @return created XML-Element
	 */
	public Element createXMLElement(PeopleElement aPeopleElement) {
		NodeFactory nf = new NodeFactory(currentDocument, TagNames.PEOPLE);
		nf.add(TagNames.PEOPLE_ID, aPeopleElement);
		nf.add(TagNames.PEOPLE_FIRSTNAME, aPeopleElement.getFirstname());
		nf.add(TagNames.PEOPLE_LASTNAME, aPeopleElement.getLastname());
		nf.add(TagNames.PEOPLE_INITIALS, aPeopleElement.getInitials());
		return nf.getCurrentElement();
	}
}
