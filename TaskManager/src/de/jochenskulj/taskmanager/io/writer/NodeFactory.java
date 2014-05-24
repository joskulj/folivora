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

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import de.jochenskulj.taskmanager.model.ElementBase;

/**
 *  Class to create XML-Structures 
 */
public class NodeFactory {

	private Document currentDocument;
	private Element currentElement;
	
	/**
	 * creates an instance
	 * @param aDocument
	 *        XML-Document to use
	 * @param aTagName
	 *        tag name of the element to create
	 */
	public NodeFactory(Document aDocument, String aTagName) {
		currentDocument = aDocument;
		currentElement = currentDocument.createElement(aTagName);
	}
	
	/**
	 * returns the created XML-Element
	 * @return the created XML-Element
	 */
	public Element getCurrentElement() {
		return currentElement;
	}
	
	/**
	 * adds a node with a given text
	 * @param aTagName
	 *        tag name to use
	 * @param aValue
	 *        tag to add
	 */
	public void add(String aTagName, String aValue) {
		if (aValue != null) {
			Element node = currentDocument.createElement(aTagName);
			Text text = currentDocument.createTextNode(aValue);
			node.appendChild(text);
			currentElement.appendChild(node);
		}
	}
	
	/**
	 * adds a node with the id of an element
	 * @param aTagName
	 *        tag name to use
	 * @param anElement
	 *        element to take the id
	 */
	public void add(String aTagName, ElementBase anElement) {
		if (anElement != null) {
			if (anElement.isRegistered()) {
				String data = Long.toString(anElement.getId());
				Element node = currentDocument.createElement(aTagName);
				Text text = currentDocument.createTextNode(data);
				node.appendChild(text);
				currentElement.appendChild(node);				
			}
		}
	}

	/**
	 * adds a node with a date value
	 * @param aTagName
	 *        tag name to use
	 * @param aDate
	 *        date value to add
	 */
	public void add(String aTagName, Date aDate) {
		if (aDate != null) {
			String data = Long.toString(aDate.getTime());
			Element node = currentDocument.createElement(aTagName);
			Text text = currentDocument.createTextNode(data);
			node.appendChild(text);
			currentElement.appendChild(node);				
		}
	}
	
	public void add(String aTagName, long aValue) {
		
	}
}
