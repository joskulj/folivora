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

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import de.jochenskulj.taskmanager.io.TagNames;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementSelection;
import de.jochenskulj.taskmanager.model.TaskElement;

/**
 * Factory for XML-Nodes for tasks 
 */
public class TaskNodeFactory {

	private static Logger logger = Logger.getRootLogger();
		
	private Document currentDocument;

	/**
	 * creates an instance
	 * @param aDocument
	 *        XML-Document to use
	 */
	public TaskNodeFactory(Document aDocument) {
		currentDocument = aDocument;
	}

	/**
	 * creates an XML-Element for a Task
	 * @param aTaskElement
	 *        a Task
	 * @return created XML-Element
	 */
	public Element createXMLElement(TaskElement aTaskElement) {
		NodeFactory nf = new NodeFactory(currentDocument, TagNames.TASK);
		
		nf.add(TagNames.TASK_ID, aTaskElement);
		nf.add(TagNames.TASK_NAME, aTaskElement.getName());
		nf.add(TagNames.TASK_PROJECT_ID, aTaskElement.getProject());
		nf.add(TagNames.TASK_DUE_DATE, aTaskElement.getDueDate());
		nf.add(TagNames.TASK_NOTES, aTaskElement.getNotes());
		
		if (aTaskElement.getStatus() != null) {
			String value = aTaskElement.getStatus().getValue();
			nf.add(TagNames.TASK_STATUS, value);
		}
		Element result = nf.getCurrentElement();
		
		nf = new NodeFactory(currentDocument, TagNames.TASK_PEOPLE_REFS);
		ElementSelection selection = aTaskElement.getPeopleSelection();
		for (ElementBase selectedElement : selection.getSelectedElements()) {
			nf.add(TagNames.TASK_PEOPLE_ID, selectedElement);
		}
		result.appendChild(nf.getCurrentElement());

		nf = new NodeFactory(currentDocument, TagNames.TASK_CONTEXT_REFS);
		selection = aTaskElement.getContextSelection();
		for (ElementBase selectedElement : selection.getSelectedElements()) {
			nf.add(TagNames.TASK_CONTEXT_ID, selectedElement);
		}
		result.appendChild(nf.getCurrentElement());

		
		return result;
	}
}
