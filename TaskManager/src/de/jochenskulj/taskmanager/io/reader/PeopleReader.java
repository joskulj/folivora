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
package de.jochenskulj.taskmanager.io.reader;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.jochenskulj.taskmanager.io.TagNames;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.PeopleElement;

/**
 * Class to read a People element 
 */
public class PeopleReader {
	
	private static Logger logger = Logger.getRootLogger();
	
	private ApplicationModel model;

	/**
	 * creates instance
	 * @param aModel
	 *        model to add elements
	 */
	public PeopleReader(ApplicationModel aModel) {
		model = aModel;
	}

	/**
	 * reads a People element from a XML-Structure
	 * @param aNodelist
	 *        XML-Structure to read
	 */
	public void createElement(NodeList aNodeList) {
		
		logger.debug("Method entered");
		
		long peopleId = -1;
		String firstname = null;
		String lastname = null;
		String initials = null;
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.PEOPLE_ID)) {
					String text = element.getTextContent();
					peopleId = Long.valueOf(text);
				}
				if (element.getTagName().equals(TagNames.PEOPLE_FIRSTNAME)) {
					firstname = element.getTextContent();
				}
				if (element.getTagName().equals(TagNames.PEOPLE_LASTNAME)) {
					lastname = element.getTextContent();
				}
				if (element.getTagName().equals(TagNames.PEOPLE_INITIALS)) {
					initials = element.getTextContent();
				}

			}
		}
		PeopleElement peopleElement = new PeopleElement();
		peopleElement.setFirstname(firstname);
		peopleElement.setLastname(lastname);
		peopleElement.setInitials(initials);
		peopleElement.register(peopleId);
		model.getPeopleList().addElement(peopleElement);
		
		logger.debug("Method exited");
	}
}
