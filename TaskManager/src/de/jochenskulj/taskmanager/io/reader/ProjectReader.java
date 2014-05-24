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
import de.jochenskulj.taskmanager.model.Priority;
import de.jochenskulj.taskmanager.model.ProjectElement;

/**
 * Class to read a Project element 
 */
public class ProjectReader {
	
	private static Logger logger = Logger.getRootLogger();
	
	private ApplicationModel model;
	
	/**
	 * creates instance
	 * @param aModel
	 *        model to add elements
	 */
	public ProjectReader(ApplicationModel aModel) {
		model = aModel;
	}

	/**
	 * reads a Project element from a XML-Structure
	 * @param aNodelist
	 *        XML-Structure to read
	 */
	public void createElement(NodeList aNodeList) {
		
		logger.debug("Method entered");
		
		long projectId = -1;
		String projectName = null;
		String projectPriority = null;
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.PROJECT_ID)) {
					String text = element.getTextContent();
					projectId = Long.valueOf(text);
				}
				if (element.getTagName().equals(TagNames.PROJECT_NAME)) {
					projectName = element.getTextContent();
				}
				if (element.getTagName().equals(TagNames.PROJECT_PRIORITY)) {
					projectPriority = element.getTextContent();
				}
			}
		}
		ProjectElement projectElement = new ProjectElement();
		projectElement.setName(projectName);
		projectElement.setPriority(new Priority(projectPriority));
		projectElement.register(projectId);
		model.getProjectList().addElement(projectElement);
		
		logger.debug("Method exited");
	}
}
